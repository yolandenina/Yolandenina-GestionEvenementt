package com.evenement.gestionevenement.services.impl;

import com.evenement.gestionevenement.dto.OrganisateurDto;
import com.evenement.gestionevenement.entities.Organisateur;
import com.evenement.gestionevenement.enums.UserTypeApp;
import com.evenement.gestionevenement.exception.ResourceNotFoundException;
import com.evenement.gestionevenement.exception.ValidationException;
import com.evenement.gestionevenement.repository.OrganisateurRepository;
import com.evenement.gestionevenement.services.OrganisateurService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrganisateurServiceImpl implements OrganisateurService {
    private final OrganisateurRepository organisateurRepository;
    private final PasswordEncoder passwordEncoder;
    private static final String MESSAGE = "organisateur not found";

    public OrganisateurServiceImpl(OrganisateurRepository organisateurRepository, PasswordEncoder passwordEncoder) {
        this.organisateurRepository = organisateurRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public OrganisateurDto createOrganisateur(OrganisateurDto dto) throws ValidationException {
        Optional<Organisateur> email = organisateurRepository.findByEmail(dto.getEmail());
        if (email.isPresent())
            throw new ValidationException("organisateur already exists");

        dto.setType(UserTypeApp.ORGANISATEUR);
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));

        Organisateur organisateur = OrganisateurDto.toEntity(dto);
        Organisateur save = organisateurRepository.save(organisateur);
        return OrganisateurDto.fromEntity(save);
    }


    @Override
    public OrganisateurDto getOrganisateurByUuid(String uuid) throws ResourceNotFoundException {
        Optional<Organisateur> repository = organisateurRepository.findByUuid(uuid);
        if (repository.isEmpty())
            throw new ResourceNotFoundException(MESSAGE);
        return OrganisateurDto.fromEntity(repository.get());
    }

    @Override
    public void deleteOrganisateurByUuid(String uuid) throws ResourceNotFoundException {
        Optional<Organisateur> repository = organisateurRepository.findByUuid(uuid);
        if (repository.isEmpty())
            throw new ResourceNotFoundException(MESSAGE);
        organisateurRepository.deleteById(repository.get().getIdUser());
    }

    @Override
    public List<OrganisateurDto> getAllOrganisateurs(){
        List<Organisateur> organisateurs = organisateurRepository.findAll();
        return organisateurs.stream()
                .map(OrganisateurDto::fromEntity)
                .toList();
    }

    @Override
    public OrganisateurDto updateOrganisateur(String uuid, OrganisateurDto dto) throws ResourceNotFoundException {
        Optional<Organisateur> repository = organisateurRepository.findByUuid(uuid);
        if (repository.isEmpty())
            throw new ResourceNotFoundException(MESSAGE);
        if (dto.getType().equals(UserTypeApp.ORGANISATEUR)){
            dto.setUuid(uuid);
            Organisateur organisateur = OrganisateurDto.toEntity(dto);
            return OrganisateurDto.fromEntity(organisateur);
        }
        return dto;
    }
}
