package com.evenement.gestionevenement.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor; // Assurez-vous d'avoir NoArgsConstructor si vous avez d'autres constructeurs
import lombok.Setter;
import com.evenement.gestionevenement.enums.UserTypeApp; // Assurez-vous d'importer l'enum

@Entity
@DiscriminatorValue(value = "PARTICIPANT")
@NoArgsConstructor // Ajouter NoArgsConstructor pour JPA. Garder AllArgsConstructor si vous en avez besoin pour d'autres cas.
@Getter @Setter
public class Participant extends UserApp {

    // N'ajoutez pas de champs spécifiques à Participant ici si vous n'en avez pas.
    // L'ID est déjà géré par UserApp.

    // Constructeur pour créer un Participant
    public Participant(String email, String password, String username) {
        // Appeler le constructeur de la classe parente (UserApp)
        // en passant les valeurs requises, y compris le type d'utilisateur.
        super(email, password, username, UserTypeApp.PARTICIPANT);
    }

    // Le constructeur généré par Lombok @AllArgsConstructor sur Participant prendrait
    // tous les champs de UserApp et de Participant. Si vous n'avez pas de champs
    // propres à Participant, ce @AllArgsConstructor sur Participant n'est pas très utile
    // et peut être retiré si vous ne l'utilisez pas ailleurs.
    // Si vous le gardez, il se peut que vous deviez aussi définir un constructeur avec tous
    // les champs de UserApp + ceux de Participant.
}