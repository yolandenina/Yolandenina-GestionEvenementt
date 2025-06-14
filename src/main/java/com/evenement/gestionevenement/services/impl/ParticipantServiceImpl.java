package com.evenement.gestionevenement.services.impl;

import com.evenement.gestionevenement.dto.ParticipantDto;
import com.evenement.gestionevenement.entities.Participant;
import com.evenement.gestionevenement.enums.UserTypeApp;
import com.evenement.gestionevenement.exception.ResourceNotFoundException;
import com.evenement.gestionevenement.exception.ValidationException;
import com.evenement.gestionevenement.repository.ParticipantRepository;
import com.evenement.gestionevenement.services.ParticicpantService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParticipantServiceImpl implements ParticicpantService {
    private final ParticipantRepository participantRepository;
    private static final String MESSAGE = "participant not found";

    public ParticipantServiceImpl(ParticipantRepository participantRepository) {
        this.participantRepository = participantRepository;
    }

    @Override
    public ParticipantDto createParticipant(ParticipantDto dto) throws ValidationException {
        Optional<Participant> email = participantRepository.findByEmail(dto.getEmail());
        if (email.isPresent())
            throw new ValidationException("participant already exists");
        Participant participant = ParticipantDto.toEntity(dto);
        dto.setType(UserTypeApp.PARTICIPANT);
        Participant savedParticipant = participantRepository.save(participant);
        return ParticipantDto.fromEntity(savedParticipant);
    }

    @Override
    public ParticipantDto getParticipantByUuid(String uuid) throws ResourceNotFoundException {
        Optional<Participant> repository = participantRepository.findByUuid(uuid);
        if (repository.isEmpty())
            throw new ResourceNotFoundException(MESSAGE);
        return ParticipantDto.fromEntity(repository.get());
    }

    @Override
    public void deleteParticipantByUuid(String uuid) throws ResourceNotFoundException {
        Optional<Participant> repository = participantRepository.findByUuid(uuid);
        if (repository.isEmpty())
            throw new ResourceNotFoundException(MESSAGE);
        participantRepository.deleteById(repository.get().getIdParticipant());
    }

    @Override
    public List<ParticipantDto> getAllParticipants(){
        List<Participant> participants = participantRepository.findAll();
        return participants.stream()
                .map(ParticipantDto::fromEntity)
                .toList();
    }

    @Override
    public ParticipantDto updateParticipant(String uuid, ParticipantDto dto) throws ResourceNotFoundException {
        Optional<Participant> repository = participantRepository.findByUuid(uuid);
        if (repository.isEmpty())
            throw new ResourceNotFoundException(MESSAGE);
        if (dto.getType().equals(UserTypeApp.PARTICIPANT)){
            dto.setUuid(uuid);
            Participant participant = ParticipantDto.toEntity(dto);
            return ParticipantDto.fromEntity(participant);
        }
        return dto;
    }
}
