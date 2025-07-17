package com.evenement.gestionevenement.web;

import com.evenement.gestionevenement.entities.Evenement;
import com.evenement.gestionevenement.exception.ResourceNotFoundException;
import com.evenement.gestionevenement.exception.ValidationException;
import com.evenement.gestionevenement.services.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
public class EvenementController {
    private final EventService eventService;

    public EvenementController(EventService eventService) {
        this.eventService = eventService;
    }

    public ResponseEntity<?> createEventWithImage(Long organisateurId,
                                                  @RequestBody Evenement evenementData, @RequestPart("file") MultipartFile file) throws IOException, ValidationException, ResourceNotFoundException {
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        String uploadDir = "uploads/images/";

        File dir = new File(uploadDir);
        if (!dir.exists())
            dir.mkdirs();

        File savedFile = new File(uploadDir + fileName);
        file.transferTo(savedFile);
        evenementData.setImagePath(uploadDir+fileName);
        Evenement event = eventService.createEvent(organisateurId, evenementData);
        return ResponseEntity.status(HttpStatus.CREATED).body(event);
    }
}
