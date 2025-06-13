package com.evenement.gestionevenement.entities;

import com.evenement.gestionevenement.enums.StatusTicket;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor @NoArgsConstructor @Getter @Setter @Builder
public class Ticket {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idParticipant;
    private Long idPayement;
    private Long idEvenement;
    private String codeTicket;
    @Enumerated(EnumType.STRING)
    private StatusTicket status;
}
