package org.example.service;

import org.example.controllers.NewPrenotazioneDTO;
import org.example.entity.Evento;
import org.example.entity.Prenotazione;
import org.example.entity.Utente;
import org.example.exception.NotFoundException;
import org.example.exception.ValidationException;
import org.example.repository.EventoRepository;
import org.example.repository.PrenotazioneRepository;
import org.example.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PrenotazioneService {
    @Autowired
    private PrenotazioneRepository prenotazioneRepository;
    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private EventoRepository eventoRepository;

    public Prenotazione save(UUID utenteId, NewPrenotazioneDTO body) {


        Utente utente = utenteRepository.findById(utenteId)
                .orElseThrow(() -> new NotFoundException(utenteId));
        Evento evento = eventoRepository.findById(body.eventoId())
                .orElseThrow(() -> new NotFoundException(body.eventoId()));

        if (controlloDisponibilita(evento) == false) {
            throw new ValidationException("I posti per questo evento non sono disponibili");
        }

        Prenotazione newPrenotazione = new Prenotazione(utente, evento);
        return this.prenotazioneRepository.save(newPrenotazione);


    }


    public boolean controlloDisponibilita(Evento evento) {
        List<Prenotazione> prenotazioni = findDisponibilita(evento.getId());
        if (prenotazioni.size() == evento.getNumeroPosti()) {
            return false;
        } else {
            return true;
        }

    }

    public List<Prenotazione> findDisponibilita(UUID id) {
        return prenotazioneRepository.findByEventoId(id);
    }
}
