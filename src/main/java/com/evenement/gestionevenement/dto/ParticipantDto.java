package com.evenement.gestionevenement.dto;

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
        return (Participant) Participant.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .email(dto.getEmail())
                .type(dto.getType())
                .build();
    }

    public static ParticipantDto fromEntity(Participant model){
        ParticipantDto dto = new ParticipantDto();
        dto.setEmail(model.getEmail());
        dto.setUsername(model.getUsername());
        dto.setType(model.getType());
        return dto;
    }

}
