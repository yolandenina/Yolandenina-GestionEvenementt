package com.evenement.gestionevenement.web;

import com.evenement.gestionevenement.dto.ParticipantDto;
import com.evenement.gestionevenement.exception.EmailAlreadyExistsException;
import com.evenement.gestionevenement.exception.ErrorDetails;
import com.evenement.gestionevenement.exception.ResourceNotFoundException;
import com.evenement.gestionevenement.exception.ValidationException;
import com.evenement.gestionevenement.services.ParticicpantService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/auth")
public class ParticipantApi {
    private final ParticicpantService particicpantService;

    public ParticipantApi(ParticicpantService particicpantService) {
        this.particicpantService = particicpantService;
    }

    @PostMapping(path="/participant")
    public ResponseEntity<?> createParticipant(@Valid @RequestBody ParticipantDto dto){
        try {
            ParticipantDto participant = particicpantService.createParticipant(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(participant);
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/participant/{uuid}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getParticipantByUuid(@PathVariable String uuid) {
        try {
            ParticipantDto participant = particicpantService.getParticipantByUuid(uuid);
            return ResponseEntity.ok(participant);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @DeleteMapping("/participant/{uuid}")
    @PreAuthorize("isAuthenticated()")
    //@PreAuthorize("hasRole('ADMIN') or hasAuthority('DELETE_PARTICIPANT')") // sécurisation
    public ResponseEntity<?> deleteParticipant(@PathVariable String uuid) {
        try {
            particicpantService.deleteParticipantByUuid(uuid);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/participants")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<ParticipantDto>> getAllParticipants() {
        List<ParticipantDto> participants = particicpantService.getAllParticipants();
        return ResponseEntity.ok(participants);
    }

    @PutMapping("/participant/{uuid}")
    @PreAuthorize("isAuthenticated()")
    //@PreAuthorize("hasRole('ADMIN') or #dto.email == authentication.name")
    public ResponseEntity<?> updateParticipant(@PathVariable String uuid, @Valid @RequestBody ParticipantDto dto) {
        try {
            ParticipantDto updated = particicpantService.updateParticipant(uuid, dto);
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
