package com.evenement.gestionevenement.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service @Slf4j
public class NotificationServiceImpl {
    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String emailSender;

    public NotificationServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(String recipient, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(recipient);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        mailMessage.setFrom(emailSender);
        mailMessage.setSentDate(new Date());

        javaMailSender.send(mailMessage);
    }
}
