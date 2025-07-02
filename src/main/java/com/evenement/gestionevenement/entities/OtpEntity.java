package com.evenement.gestionevenement.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Entity @AllArgsConstructor
@NoArgsConstructor @Builder @Getter @Setter
public class OtpEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String otp;
    @UuidGenerator
    @Column(nullable = false,unique = true)
    private String uuid;
    @CreationTimestamp
    private LocalDateTime createdAt;
    private LocalDateTime expirationAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private UserApp user;
}
