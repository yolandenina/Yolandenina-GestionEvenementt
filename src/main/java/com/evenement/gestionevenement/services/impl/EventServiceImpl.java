package com.evenement.gestionevenement.services.impl;

import com.evenement.gestionevenement.entities.Evenement;
import com.evenement.gestionevenement.entities.Organisateur;
import com.evenement.gestionevenement.entities.Participant;
import com.evenement.gestionevenement.exception.ResourceNotFoundException;
import com.evenement.gestionevenement.exception.ValidationException;
import com.evenement.gestionevenement.repository.EvenementRepository;
import com.evenement.gestionevenement.repository.OrganisateurRepository;
import com.evenement.gestionevenement.repository.ParticipantRepository;
import com.evenement.gestionevenement.services.EventService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {
    private final OrganisateurRepository organisateurRepository;
    private final EvenementRepository evenementRepository;
    private final ParticipantRepository participantRepository;

    public EventServiceImpl(OrganisateurRepository organisateurRepository, EvenementRepository evenementRepository, ParticipantRepository participantRepository) {
        this.organisateurRepository = organisateurRepository;
        this.evenementRepository = evenementRepository;
        this.participantRepository = participantRepository;
    }

    @Transactional @Override
    public Evenement createEvent(Long organisateurId,Evenement evenementData) throws ResourceNotFoundException, ValidationException {
        Optional<Organisateur> organisateur = organisateurRepository.findById(organisateurId);
        if (organisateur.isEmpty()){
            throw new ResourceNotFoundException("Organisateur inexistant");
        }

        Optional<Evenement> evenement = evenementRepository.findByNomContainingIgnoreCase(evenementData.getNom());
        if (evenement.isPresent()){
            throw new ValidationException("Désolé cette évenement existe déja");
        }
        evenementData.setNom(evenement.get().getNom());
        evenementData.setOrganisateur(organisateur.get());
        return evenementRepository.save(evenementData);
    }

    @Transactional @Override
    public void deleteEvenement(Long id) throws ResourceNotFoundException {
        Optional<Evenement> evenement = evenementRepository.findById(id);
        if (evenement.isEmpty()){
            throw new ResourceNotFoundException("Désolé cette evenement n'existe pas");
        }
        evenementRepository.deleteById(id);
    }

    public List<Evenement> allEvent(){
        return evenementRepository.findAll();
    }

    @Override
    public Evenement getEvenementByName(String name) throws ResourceNotFoundException {
        Optional<Evenement> evenement = evenementRepository.findByNomContainingIgnoreCase(name);
        if (evenement.isEmpty()){
            throw new ResourceNotFoundException("Désolé cette évenement existe déja");
        }
        return evenement.get();
    }

    @Transactional @Override
    public Evenement modifierEvent(Long eventId,Evenement evenementData) throws ResourceNotFoundException {
        Optional<Evenement> evenement = evenementRepository.findById(eventId);
        if (evenement.isEmpty()){
            throw new ResourceNotFoundException("Evenement Introuvable");
        }
        evenementData.setId(eventId);
        return evenementRepository.save(evenementData);
    }

    @Transactional @Override
    public Evenement inviterParticipant(Long eventId,String emailParticipant) throws ResourceNotFoundException {
        Optional<Evenement> evenement = evenementRepository.findById(eventId);
        if (evenement.isEmpty()){
            throw new ResourceNotFoundException("Evenement introuvable");
        }

        Optional<Participant> participant = participantRepository.findByEmail(emailParticipant);
        if (participant.isEmpty()){
            throw new ResourceNotFoundException("Utilisateur introuvable");
        }

        if (!evenement.get().getParticipants().contains(participant.get())){
            evenement.get().getParticipants().add(participant.get());
        }

        return evenementRepository.save(evenement.get());
    }
}
