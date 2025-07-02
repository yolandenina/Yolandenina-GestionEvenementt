package com.evenement.gestionevenement.web;

import com.evenement.gestionevenement.services.OtpService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(path = "/api/otp")
public class OtpController {
    private final OtpService otpService;

    public OtpController(OtpService otpService) {
        this.otpService = otpService;
    }

    @PostMapping("/generate")
    public ResponseEntity<?> generateOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        if (email == null || email.isEmpty()) {
            return ResponseEntity.badRequest().body("Email is required");
        }

        try {
            otpService.generateAndSendOtp(email);
            return ResponseEntity.ok("OTP sent successfully to " + email);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.badRequest().body("User not found with email: " + email);
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body("Error sending OTP");
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String otp = request.get("otp");
        if (email == null || otp == null || email.isEmpty() || otp.isEmpty()) {
            return ResponseEntity.badRequest().body("Email and OTP are required");
        }

        try {
            boolean isValid = otpService.verifyOtp(email, otp);
            if (isValid) {
                return ResponseEntity.ok("OTP is valid");
            } else {
                return ResponseEntity.badRequest().body("OTP is invalid or expired");
            }
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.badRequest().body("User not found with email: " + email);
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body("Error verifying OTP");
        }
    }
}
