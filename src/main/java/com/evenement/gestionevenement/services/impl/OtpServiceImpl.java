package com.evenement.gestionevenement.services.impl;

import com.evenement.gestionevenement.dto.EmailDetails;
import com.evenement.gestionevenement.entities.OtpEntity;
import com.evenement.gestionevenement.entities.UserApp;
import com.evenement.gestionevenement.repository.OtpRepository;
import com.evenement.gestionevenement.repository.UserAppRepository;
import com.evenement.gestionevenement.services.EmailService;
import com.evenement.gestionevenement.services.OtpService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

@Service @Slf4j
public class OtpServiceImpl implements OtpService {
    private final OtpRepository otpRepository;
    private final UserAppRepository userEntityRepository;
    private final EmailService emailService;
    private final int OTP_VALID_DURATION_MINUTES = 5;

    public OtpServiceImpl(OtpRepository otpRepository, UserAppRepository userEntityRepository, EmailService emailService) {
        this.otpRepository = otpRepository;
        this.userEntityRepository = userEntityRepository;
        this.emailService = emailService;
    }

    @Override
    public String generateOtp(){
        StringBuilder str = new StringBuilder();
        Random rdn = new Random();
        int count = 0;
        while (count < 6){
            str.append(rdn.nextInt(10));
            ++count;
        }
        return str.toString();
    }

    @Override
    public void generateAndSendOtp(String recipientEmail){
        Optional<UserApp> user = userEntityRepository.findByEmail(recipientEmail);
        if (user.isEmpty())
            throw new EntityNotFoundException("user not found");
        otpRepository.deleteByUser(user.get()); // suppression des anciens otp
        String otpCode = generateOtp();
        OtpEntity otp = OtpEntity.builder()
                .otp(otpCode)
                .user(user.get())
                .expirationAt(LocalDateTime.now().plus(Duration.ofMinutes(OTP_VALID_DURATION_MINUTES)))
                .build();
        otpRepository.save(otp);
        //String message = String.format("Your verification code is: %s\nIt expires in %d minutes.",otpCode,OTP_VALID_DURATION_MINUTES);
        String message = OtpServiceImpl.buildOtpEmailBody(otpCode,OTP_VALID_DURATION_MINUTES,user.get().getEmail());
        EmailDetails details = EmailDetails.builder()
                .body(message)
                .createAt(new Date())
                .recipient(recipientEmail)
                .subject("Your OTP code")
                .build();
        emailService.sendEmail(details);
    }

    @Override
    public boolean verifyOtp(String email,String provideOtp){
        Optional<UserApp> user = userEntityRepository.findByEmail(email);
        if (user.isEmpty())
            throw new EntityNotFoundException("user not found");
        Optional<OtpEntity> otpEntity = otpRepository.findTopByUserOrderByCreatedAtDesc(user.get()); // recherche du dernier otp valide
        if (otpEntity.isEmpty())
            return false;
        OtpEntity otp = otpEntity.get();
        if (otp.getExpirationAt().isBefore(LocalDateTime.now()))
            return false;
        return otp.getOtp().equals(provideOtp);
    }

    private static String buildOtpEmailBody(String otpCode, int validMinutes,String email) {
        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "  <style>" +
                "    .container { max-width: 600px; margin: auto; font-family: Arial, sans-serif; }" +
                "    .header { background-color: #4CAF50; color: white; padding: 10px; text-align: center; }" +
                "    .content { padding: 20px; font-size: 16px; }" +
                "    .otp-code { font-size: 24px; font-weight: bold; color: #333; margin: 20px 0; }" +
                "    .footer { font-size: 12px; color: #777; text-align: center; margin-top: 30px; }" +
                "  </style>" +
                "</head>" +
                "<body>" +
                "  <div class='container'>" +
                "    <div class='header'>Your OTP Verification Code</div>" +
                "    <div class='content'>" +
                "      <p>Dear " + email + ",</p>" +
                "      <p>Your verification code is:</p>" +
                "      <div class='otp-code'>" + otpCode + "</div>" +
                "      <p>This code will expire in " + validMinutes + " minutes.</p>" +
                "      <p>If you did not request this, please ignore this email.</p>" +
                "    </div>" +
                "    <div class='footer'>© 2025 Your Company Name</div>" +
                "  </div>" +
                "</body>" +
                "</html>";
    }
}
