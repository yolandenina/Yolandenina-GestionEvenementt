package com.evenement.gestionevenement.entities;


import com.evenement.gestionevenement.enums.UserTypeApp;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import lombok.AllArgsConstructor;

import java.io.Serializable;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@AllArgsConstructor @NoArgsConstructor @Getter @Setter
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING,name = "user_app" , length = 20)
public abstract class UserApp implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;
    @UuidGenerator
    private String uuid;
    @NotBlank @Email
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String username;
    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", insertable = false, updatable = false) // La colonne est gérée par @DiscriminatorColumn
    private UserTypeApp type; // Assurez-vous d'avoir cette propriété et l'enum UserTypeApp

    // Constructeur pour les champs communs, si nécessaire
    public UserApp(String email, String password, String username, UserTypeApp type) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.type = type;
    }
}
