package com.evenement.gestionevenement.repository;

import com.evenement.gestionevenement.entities.Payement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayementRepository extends JpaRepository<Payement, Long> {

}
