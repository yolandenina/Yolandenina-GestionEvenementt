package com.evenement.gestionevenement.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class ErrorDetails {
    private Date timestamp;
    private String message;
    private String error;
    private String details;

    private Object object;

    public ErrorDetails(Date timestamp, String message, String details, Object object, String error) {
        this.timestamp = timestamp;
        this.message = message;
        this.error = error;
        this.details = details;
        this.object = object;
    }

    public ErrorDetails(Date date, String errorMessage, String requestURI, String message) {
        // Default constructor
    }

}
