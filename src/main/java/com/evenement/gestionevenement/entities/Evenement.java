package com.evenement.gestionevenement.entities;

import com.evenement.gestionevenement.enums.EventType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Negative;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor @NoArgsConstructor @Getter @Setter @Builder
public class Evenement  implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private LocalDate date;
    private String lieu;
    private LocalDateTime heureDebut;
    private LocalDateTime heureFin;
    @NotEmpty @Negative
    private Long nbrePlace;
    private String description;
    @Enumerated(EnumType.STRING)
    private EventType type;
}
