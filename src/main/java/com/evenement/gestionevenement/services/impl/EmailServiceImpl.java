package com.evenement.gestionevenement.services.impl;

import com.evenement.gestionevenement.dto.EmailDetails;
import com.evenement.gestionevenement.services.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service @Slf4j
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String senderEmail;

    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Retryable(
            retryFor = { MailException.class },
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000)
    )
    @Override
    public void sendEmail(EmailDetails details) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setTo(details.getRecipient());
            helper.setFrom(details.getSender() != null ? details.getSender() : senderEmail);
            helper.setSubject(details.getSubject());
            helper.setText(details.getBody(), true);
            javaMailSender.send(mimeMessage);
            log.info("Email sent to {}", details.getRecipient());
        } catch (MessagingException | MailException e) {
            log.error("Email sending failed to {}: {}", details.getRecipient(), e.getMessage(), e);
            throw new RuntimeException("Email sending failed");
        }
    }
}
