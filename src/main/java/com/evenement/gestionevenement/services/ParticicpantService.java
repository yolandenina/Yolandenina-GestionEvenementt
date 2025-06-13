package com.evenement.gestionevenement.services;

import com.evenement.gestionevenement.dto.ParticipantDto;
import com.evenement.gestionevenement.exception.ResourceNotFoundException;
import com.evenement.gestionevenement.exception.ValidationException;

import java.util.List;

public interface ParticicpantService {
    ParticipantDto createParticipant(ParticipantDto dto) throws ValidationException;

    ParticipantDto getParticipantByUuid(String uuid) throws ResourceNotFoundException;

    void deleteParticipantByUuid(String uuid) throws ResourceNotFoundException;

    List<ParticipantDto> getAllParticipants();

    ParticipantDto updateParticipant(String uuid, ParticipantDto dto) throws ResourceNotFoundException;
}
