package org.example.controllers;

import org.example.entity.Evento;
import org.example.entity.Utente;
import org.example.exception.BadRequestException;
import org.example.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/eventi")
public class EventoController {
    @Autowired
    private EventoService eventiService;


    @PostMapping
    @PreAuthorize("hasAuthority('ORGANIZZATORE_DI_EVENTI')")
    @ResponseStatus(HttpStatus.CREATED)
    public Evento save(@AuthenticationPrincipal Utente currentAuthenticatedUtente, @RequestBody @Validated NewEventoDTO body, BindingResult validationResult) {

        if (validationResult.hasErrors()) {
            String message = validationResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload! " + message);
        }
        Evento evento = new Evento(body.titolo(), body.descrizione(), body.data(), body.luogo(), body.postiDisponibili(), currentAuthenticatedUtente);
        return this.eventiService.save(evento);
    }


    @GetMapping("/{eventoId}")
    public Evento findById(@PathVariable UUID eventoId) {
        return this.eventiService.findById(eventoId);
    }


    @PutMapping("/{eventoId}")
    @PreAuthorize("hasAuthority('ORGANIZZATORE_DI_EVENTI')")
    public Evento findByIdAndUpdate(@PathVariable UUID eventoId, @RequestBody @Validated NewEventoDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            validationResult.getAllErrors().forEach(System.out::println);
            throw new BadRequestException("Ci sono stati errori nel payload!");
        }
        Evento evento = new Evento();
        evento.setLuogo(body.luogo());
        evento.setData(body.data());
        evento.setDescrizione(body.descrizione());
        evento.setTitolo(body.titolo());
        evento.setNumeroPosti(body.postiDisponibili());


        return this.eventiService.update(evento);
    }


    @DeleteMapping("/{eventoId}")
    @PreAuthorize("hasAuthority('ORGANIZZATORE_DI_EVENTI')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID eventoId) {
        this.eventiService.deleteEvento(eventoId);
    }


}
