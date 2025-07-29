package com.evenement.gestionevenement.entities;

import com.evenement.gestionevenement.enums.UserTypeApp;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @DiscriminatorValue(value = "ORGANISATEUR")
@AllArgsConstructor @Getter @Setter
public class Organisateur extends UserApp {
    public Organisateur(String email, String password, String username) {
        // Appeler le constructeur de la classe parente (UserApp)
        // en passant les valeurs requises, y compris le type d'utilisateur.
        super(email, password, username, UserTypeApp.ORGANISATEUR);
    }
    //private Long idOrganisateur;
}
