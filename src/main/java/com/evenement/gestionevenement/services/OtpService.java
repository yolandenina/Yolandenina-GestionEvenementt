package com.evenement.gestionevenement.services;

public interface OtpService {
    String generateOtp();

    void generateAndSendOtp(String recipientEmail);

    boolean verifyOtp(String email, String provideOtp);
}
