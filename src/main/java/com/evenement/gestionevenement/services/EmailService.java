package com.evenement.gestionevenement.services;

import com.evenement.gestionevenement.dto.EmailDetails;
import org.springframework.mail.MailException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

public interface EmailService {
    @Retryable(
            retryFor = { MailException.class },
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000)
    )
    void sendEmail(EmailDetails details);
}
