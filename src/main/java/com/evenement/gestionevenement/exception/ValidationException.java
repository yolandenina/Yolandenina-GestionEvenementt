package com.evenement.gestionevenement.exception;

import jakarta.validation.Valid;

public class ValidationException extends Exception {

    public ValidationException(String message){
        super(message);
    }
}
