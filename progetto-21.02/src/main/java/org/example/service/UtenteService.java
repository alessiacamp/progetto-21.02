package org.example.service;

import org.example.entity.Evento;
import org.example.entity.Utente;
import org.example.exception.NotFoundException;
import org.example.repository.EventoRepository;
import org.example.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UtenteService {
    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private PasswordEncoder crypt;

    public Utente save(Utente utentedto) {
        this.utenteRepository.findByEmail(utentedto.getEmail()).ifPresent(utente -> {
            throw new RuntimeException("Email già esistente");
        });

        return this.utenteRepository.save(utentedto);

    }

    public Utente findById(UUID utente_id) {
        return this.utenteRepository.findById(utente_id).orElseThrow(() -> new NotFoundException(utente_id));

    }

    public Utente findByEmail(String email) {
        return this.utenteRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("L'utente con email " + email + " non è stato trovato"));
    }
}