package org.example.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Entity
@Table(name = "prenotazione")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Prenotazione {
    @Id
    @GeneratedValue
    private UUID id;

    public UUID getId() {
        return id;
    }

    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;

    @OneToMany
    @JoinColumn(name = "evento")
    private Evento evento;

    public Prenotazione(Utente utente, Evento evento) {
        this.utente = utente;
        this.evento = evento;
    }
}
