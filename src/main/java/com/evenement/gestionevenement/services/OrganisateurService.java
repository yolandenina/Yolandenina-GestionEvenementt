package com.evenement.gestionevenement.services;

import com.evenement.gestionevenement.dto.OrganisateurDto;
import com.evenement.gestionevenement.exception.ResourceNotFoundException;
import com.evenement.gestionevenement.exception.ValidationException;

import java.util.List;

public interface OrganisateurService {
    OrganisateurDto createOrganisateur(OrganisateurDto dto) throws ValidationException;

    OrganisateurDto getOrganisateurByUuid(String uuid) throws ResourceNotFoundException;

    void deleteOrganisateurByUuid(String uuid) throws ResourceNotFoundException;

    List<OrganisateurDto> getAllOrganisateurs();

    OrganisateurDto updateOrganisateur(String uuid, OrganisateurDto dto) throws ResourceNotFoundException;
}
