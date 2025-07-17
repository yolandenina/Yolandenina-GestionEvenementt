package com.evenement.gestionevenement.web;

import com.evenement.gestionevenement.dto.OrganisateurDto;
import com.evenement.gestionevenement.exception.EmailAlreadyExistsException;
import com.evenement.gestionevenement.exception.ErrorDetails;
import com.evenement.gestionevenement.exception.ResourceNotFoundException;
import com.evenement.gestionevenement.exception.ValidationException;
import com.evenement.gestionevenement.services.OrganisateurService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/auth")
public class OrganisateurApi {
    private final OrganisateurService organisateurService;

    public OrganisateurApi(OrganisateurService organisateurService) {
        this.organisateurService = organisateurService;
    }

    @PostMapping(path = "/organisateurs")
    public ResponseEntity<?> createOrganisation(@Valid @RequestBody OrganisateurDto dto)  {
        try {
            OrganisateurDto organisateur = organisateurService.createOrganisateur(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(organisateur);
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(path = "/organisateur/{uuid}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getOrganisateurByUuid(@PathVariable String uuid) {
        try {
            OrganisateurDto organisateur = organisateurService.getOrganisateurByUuid(uuid);
            return ResponseEntity.ok(organisateur);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/organisateur/{uuid}")
    @PreAuthorize("isAuthenticated()")
    //@PreAuthorize("hasRole('ADMIN') or hasAuthority('DELETE_ORGANISATEUR')")
    public ResponseEntity<?> deleteOrganisateur(@PathVariable String uuid) {
        try {
            organisateurService.deleteOrganisateurByUuid(uuid);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/organisateurs")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<OrganisateurDto>> getAllOrganisateurs() {
        List<OrganisateurDto> organisateurs = organisateurService.getAllOrganisateurs();
        return ResponseEntity.ok(organisateurs);
    }

    @PutMapping("/organisateur/{uuid}")
    @PreAuthorize("isAuthenticated()")
    //@PreAuthorize("hasRole('ADMIN') or #dto.email == authentication.name")
    public ResponseEntity<?> updateOrganisateur(@PathVariable String uuid, @Valid @RequestBody OrganisateurDto dto) {
        try {
            OrganisateurDto updated = organisateurService.updateOrganisateur(uuid, dto);
            return ResponseEntity.ok(updated);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @ExceptionHandler({EmailAlreadyExistsException.class})
    public ErrorDetails handleEmailAlreadyExistException(EmailAlreadyExistsException exception){
        return new ErrorDetails(HttpStatus.BAD_REQUEST, exception.getMessage(), LocalDateTime.now());
    }
}
