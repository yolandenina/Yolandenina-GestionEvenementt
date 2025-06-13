package com.evenement.gestionevenement.repository;

import com.evenement.gestionevenement.entities.Organisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganisateurRepository extends JpaRepository<Organisateur,Long> {
    Optional<Organisateur> findByEmail(String email);
    Optional<Organisateur> findByUuid(String uuid);
}
