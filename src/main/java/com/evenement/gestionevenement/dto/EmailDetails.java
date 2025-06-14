package com.evenement.gestionevenement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder @AllArgsConstructor @NoArgsConstructor
public class EmailDetails {
    private String recipient;
    private String subject;
    private String message;
    private String attachment;
}
