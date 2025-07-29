package com.evenement.gestionevenement.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.evenement.gestionevenement.entities.Participant;
import com.evenement.gestionevenement.enums.UserTypeApp;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ParticipantDto {
    private Long idUser;
    private String uuid;
    @Email @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String username;
    private UserTypeApp type;

    public static Participant toEntity(ParticipantDto dto){
        Participant model = new Participant();
        model.setIdUser(dto.getIdUser());
        model.setUuid(dto.getUuid());
        model.setEmail(dto.getEmail());
        model.setPassword(dto.getPassword());
        model.setUsername(dto.getUsername());
        model.setType(dto.getType());
        return model;
    }

    public static ParticipantDto fromEntity(Participant model){
        ParticipantDto dto = new ParticipantDto();
        dto.setIdUser(model.getIdUser());
        dto.setUuid(model.getUuid());
        dto.setEmail(model.getEmail());
        dto.setPassword(model.getPassword());
        dto.setUsername(model.getUsername());
        dto.setType(model.getType());
        return dto;
    }


}
