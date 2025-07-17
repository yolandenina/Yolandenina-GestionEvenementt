package com.evenement.gestionevenement.services;

import com.evenement.gestionevenement.entities.Evenement;
import com.evenement.gestionevenement.exception.ResourceNotFoundException;
import com.evenement.gestionevenement.exception.ValidationException;
import jakarta.transaction.Transactional;

public interface EventService {
    @Transactional
    Evenement createEvent(Long organisateurId, Evenement evenementData) throws ResourceNotFoundException, ValidationException;

    @Transactional
    void deleteEvenement(Long id) throws ResourceNotFoundException;

    Evenement getEvenementByName(String name) throws ResourceNotFoundException;

    @Transactional
    Evenement modifierEvent(Long eventId, Evenement evenementData) throws ResourceNotFoundException;

    @Transactional
    Evenement inviterParticipant(Long eventId, String emailParticipant) throws ResourceNotFoundException;
}
