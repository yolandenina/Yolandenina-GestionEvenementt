package com.evenement.gestionevenement.entities;

import com.evenement.gestionevenement.enums.EventType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Negative;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor @NoArgsConstructor @Getter @Setter @Builder
public class Evenement  implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private LocalDate date;
    private String lieu;
    private LocalDateTime heureDebut;
    private LocalDateTime heureFin;
    @NotEmpty @Negative
    private Long nbrePlace;
    private String description;
    private String imagePath;
    @Enumerated(EnumType.STRING)
    private EventType type;
    @ManyToOne
    @JoinColumn(name = "organisateur_id")
    private Organisateur organisateur;
    @ManyToMany
    @JoinTable(
            name = "evenement_participants",
            joinColumns = @JoinColumn(name = "evenement_id"),
            inverseJoinColumns = @JoinColumn(name = "participant_id")
    )
    private List<Participant> participants = new ArrayList<>();
}
