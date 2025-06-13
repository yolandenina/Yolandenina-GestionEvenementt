package com.evenement.gestionevenement.repository;

import com.evenement.gestionevenement.entities.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant,Long> {
    Optional<Participant> findByEmail(String email);
    Optional<Participant> findByUuid(String uuid);
}
