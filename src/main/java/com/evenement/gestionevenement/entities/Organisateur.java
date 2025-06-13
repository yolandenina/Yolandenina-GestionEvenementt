package com.evenement.gestionevenement.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @DiscriminatorValue(value = "id_organisateur")
@AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class Organisateur extends UserApp {
    private Long idOrganisateur;
}
