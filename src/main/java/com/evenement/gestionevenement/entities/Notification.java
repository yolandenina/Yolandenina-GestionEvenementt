package com.evenement.gestionevenement.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity @AllArgsConstructor @NoArgsConstructor @Getter
@Setter
public class Notification  implements Serializable {
    @Id @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    private Long idParticipant;
    private Long idEvent;
    private String message;
}
