package com.evenement.gestionevenement.dto;

import lombok.*;

import java.util.Date;

@AllArgsConstructor @NoArgsConstructor @Getter
@Setter
@Builder
public class EmailDetails {
    private String subject;
    private String body;
    private String recipient;
    private Date createAt;
    private String sender;
}
