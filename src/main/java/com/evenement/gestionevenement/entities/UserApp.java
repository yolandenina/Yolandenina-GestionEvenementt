package com.evenement.gestionevenement.entities;


import com.evenement.gestionevenement.enums.UserTypeApp;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.io.Serializable;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@AllArgsConstructor @NoArgsConstructor @Getter @Setter
@Entity @DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING,name = "user_app" , length = 20)
public abstract class UserApp implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private UserTypeApp type;
}
