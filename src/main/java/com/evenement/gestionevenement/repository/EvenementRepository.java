package com.evenement.gestionevenement.repository;

import com.evenement.gestionevenement.entities.Evenement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EvenementRepository extends JpaRepository<Evenement, Long> {
    Optional<Evenement> findByNomContainingIgnoreCase(String name);
}
