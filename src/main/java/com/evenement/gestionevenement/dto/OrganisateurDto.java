package com.evenement.gestionevenement.dto;

import com.evenement.gestionevenement.entities.Organisateur;
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
        Organisateur model = new Organisateur();
        model.setIdUser(dto.getIdUser());
        model.setUuid(dto.getUuid());
        model.setEmail(dto.getEmail());
        model.setPassword(dto.getPassword());
        model.setUsername(dto.getUsername());
        model.setType(dto.getType());
        return model;
    }

    public static OrganisateurDto fromEntity(Organisateur model){
        OrganisateurDto dto = new OrganisateurDto();
        dto.setIdUser(model.getIdUser());
        dto.setUuid(model.getUuid());
        dto.setEmail(model.getEmail());
        dto.setUsername(model.getUsername());
        dto.setPassword(model.getPassword());
        dto.setType(model.getType());
        return dto;
    }
}
