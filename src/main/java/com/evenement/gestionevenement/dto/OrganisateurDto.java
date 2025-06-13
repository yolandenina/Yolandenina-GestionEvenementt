package com.evenement.gestionevenement.dto;

import com.evenement.gestionevenement.entities.Organisateur;
import com.evenement.gestionevenement.entities.Participant;
import com.evenement.gestionevenement.entities.UserApp;
import com.evenement.gestionevenement.enums.UserTypeApp;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OrganisateurDto {
    private Long idUser;
    private String uuid;
    @Email @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String username;
    private UserTypeApp type;

    public static Organisateur toEntity(OrganisateurDto dto){
        UserApp userApp = Participant.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .email(dto.getEmail())
                .type(dto.getType())
                .build();
        return (Organisateur) userApp;
    }

    public static OrganisateurDto fromEntity(Organisateur model){
        OrganisateurDto dto = new OrganisateurDto();
        dto.setEmail(model.getEmail());
        dto.setUsername(model.getUsername());
        dto.setType(model.getType());
        return dto;
    }

}
