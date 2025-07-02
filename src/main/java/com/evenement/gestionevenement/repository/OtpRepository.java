package com.evenement.gestionevenement.repository;

import com.evenement.gestionevenement.entities.OtpEntity;
import com.evenement.gestionevenement.entities.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<OtpEntity, Long> {
    void deleteByUser(UserApp user);
    Optional<OtpEntity> findTopByUserOrderByCreatedAtDesc(UserApp user);
}
