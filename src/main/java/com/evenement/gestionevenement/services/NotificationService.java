package com.evenement.gestionevenement.services;

public interface NotificationService {
    void sendEmail(String recipient, String subject, String message);
}
