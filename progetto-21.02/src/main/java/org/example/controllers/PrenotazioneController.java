package org.example.controllers;

import org.example.entity.Prenotazione;
import org.example.entity.Utente;
import org.example.exception.BadRequestException;
import org.example.service.PrenotazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioneController {
    @Autowired
    private PrenotazioneService prenotazioniService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Prenotazione save(@AuthenticationPrincipal Utente currentAuthenticatedUtente, @RequestBody @Validated NewPrenotazioneDTO body, BindingResult validationResult) {

        if (validationResult.hasErrors()) {
            String message = validationResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload! " + message);
        }

        return this.prenotazioniService.save(currentAuthenticatedUtente.getId(), body);
    }


}
