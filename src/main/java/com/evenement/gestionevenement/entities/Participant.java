package com.evenement.gestionevenement.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @DiscriminatorValue(value = "PARTICIPANT")
@AllArgsConstructor @Getter @Setter
public class Participant extends UserApp {
    //private Long idParticipant;
}
