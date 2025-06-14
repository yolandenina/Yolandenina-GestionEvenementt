package com.evenement.gestionevenement.exception;

public class ErrorDetails {
    private String message;
    private String details;
    private String timestamp;
    private Object status;
    //private Object data;

    public ErrorDetails(String message, String details, String timestamp, Object status) {
        this.message = message;
        this.details = details;
        this.timestamp = timestamp;
        this.status = status;
        //this.data = data;
    }


}
