package com.evenement.gestionevenement.entities;

import com.evenement.gestionevenement.enums.PayementMethod;
import com.evenement.gestionevenement.enums.StatusPayement;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor @NoArgsConstructor @Getter @Setter @Builder
public class Payement {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double montant;
    private Long idParticipant;
    private Long idEvent;
    @Enumerated(EnumType.STRING)
    private StatusPayement payement;
    @Enumerated(EnumType.STRING)
    private PayementMethod method;
}
