package org.example.controllers;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record NewPrenotazioneDTO(

        @NotNull(message = "L'evento è obbligatorio!")
        UUID eventoId) {
}
