package com.evenement.gestionevenement.services;

import com.evenement.gestionevenement.dto.ParticipantDto;
import com.evenement.gestionevenement.entities.Participant;
import com.evenement.gestionevenement.exception.EmailAlreadyExistsException;
import com.evenement.gestionevenement.exception.ResourceNotFoundException;
import com.evenement.gestionevenement.repositories.ParticipantRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ParticicpantService {

    private final ParticipantRepository participantRepository;
    private final PasswordEncoder passwordEncoder;

    public ParticicpantService(ParticipantRepository participantRepository,
                               PasswordEncoder passwordEncoder) {
        this.participantRepository = participantRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ParticipantDto createParticipant(ParticipantDto dto) {
        // Vérifier si l'email existe déjà
        if (participantRepository.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyExistsException("Un utilisateur avec cet email existe déjà");
        }

        // Créer une nouvelle instance de Participant
        Participant participant = new Participant(
                dto.getEmail(),
                passwordEncoder.encode(dto.getPassword()), // Encoder le mot de passe
                dto.getUsername()
        );

        // Sauvegarder en base de données
        Participant savedParticipant = participantRepository.save(participant);

        // Convertir en DTO et retourner
        return convertToDto(savedParticipant);
    }

    public ParticipantDto getParticipantByUuid(String uuid) {
        Participant participant = participantRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Participant non trouvé avec UUID: " + uuid));
        return convertToDto(participant);
    }

    public List<ParticipantDto> getAllParticipants() {
        return participantRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public ParticipantDto updateParticipant(String uuid, ParticipantDto dto) {
        Participant participant = participantRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Participant non trouvé"));

        // Mise à jour des champs
        participant.setUsername(dto.getUsername());
        participant.setEmail(dto.getEmail());
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            participant.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        Participant updated = participantRepository.save(participant);
        return convertToDto(updated);
    }

    public void deleteParticipantByUuid(String uuid) {
        if (!participantRepository.findByUuid(uuid).isPresent()) {
            throw new ResourceNotFoundException("Participant non trouvé");
        }
        participantRepository.deleteByUuid(uuid);
    }

    private ParticipantDto convertToDto(Participant participant) {
        ParticipantDto dto = new ParticipantDto();
        dto.setUuid(participant.getUuid());
        dto.setEmail(participant.getEmail());
        dto.setUsername(participant.getUsername());
        dto.setType(participant.getType().toString());
        // Ne pas inclure le mot de passe dans le DTO de retour
        return dto;
    }
}