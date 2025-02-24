package org.example.service;


import org.example.entity.Evento;
import org.example.entity.Ruolo;
import org.example.entity.Utente;
import org.example.exception.NotFoundException;
import org.example.exception.UnauthorizedException;
import org.example.repository.EventoRepository;
import org.example.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class EventoService {
    @Autowired
    private EventoRepository eventoRepository;
    @Autowired
    private UtenteRepository utenteRepository;

    public Evento save(Evento eventodto) {

        Utente utente = eventodto.getUtente();
        if (utente.getRuolo() == Ruolo.UTENTE_NORMALE) {
            throw new UnauthorizedException("Utente non autorizzato");

        }

        return this.eventoRepository.save(eventodto);

    }

    public void deleteEvento(UUID evento_id) {
        this.eventoRepository.deleteById(evento_id);
    }

    public Evento findById(UUID evento_id) {
        return this.eventoRepository.findById(evento_id).orElseThrow(() -> new NotFoundException(evento_id));

    }

    public Evento update(Evento nuovoEvento) {
        Evento vecchioEvento = this.findById(nuovoEvento.getId());
        vecchioEvento.setTitolo(nuovoEvento.getTitolo());
        vecchioEvento.setData(nuovoEvento.getData());
        vecchioEvento.setDescrizione(nuovoEvento.getDescrizione());
        vecchioEvento.setLuogo(nuovoEvento.getLuogo());
        vecchioEvento.setNumeroPosti(nuovoEvento.getNumeroPosti());
        return this.eventoRepository.save(vecchioEvento);

    }

}
