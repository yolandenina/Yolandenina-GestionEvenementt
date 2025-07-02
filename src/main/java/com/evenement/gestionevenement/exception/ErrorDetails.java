package com.evenement.gestionevenement.exception;

import java.time.LocalDateTime;

public record ErrorDetails(Object status, String description, LocalDateTime dateTime) {
}
