package org.example.controllers;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record NewEventoDTO(
        @NotEmpty(message = "Il titolo è obbligatorio!")
        @Size(min = 2, max = 40, message = "Il titolo deve essere compreso tra 2 e 40 caratteri!")
        String titolo,

        @NotEmpty(message = "La descrizione è obbligatoria!")
        @Size(max = 500, message = "La descrizione non può superare i 500 caratteri!")
        String descrizione,

        @NotNull(message = "La data è obbligatoria!")
        LocalDate data,

        @NotEmpty(message = "Il luogo è obbligatorio!")
        @Size(min = 2, max = 100, message = "Il luogo deve essere compreso tra 2 e 100 caratteri!")
        String luogo,

        @Min(value = 1, message = "I posti disponibili devono essere un numero maggiore di zero.")
        int postiDisponibili


) {
}
