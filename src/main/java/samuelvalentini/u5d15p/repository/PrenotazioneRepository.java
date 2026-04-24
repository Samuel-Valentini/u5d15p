package samuelvalentini.u5d15p.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import samuelvalentini.u5d15p.entity.Evento;
import samuelvalentini.u5d15p.entity.Prenotazione;
import samuelvalentini.u5d15p.entity.Utente;

import java.util.List;
import java.util.Optional;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {
    
    boolean existsByUtenteAndEvento(Utente utente, Evento evento);

    long countByEvento(Evento evento);

    List<Prenotazione> findByUtente(Utente utente);

    Optional<Prenotazione> findByUtenteAndEvento(Utente utente, Evento evento);
}