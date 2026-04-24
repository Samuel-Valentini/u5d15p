package samuelvalentini.u5d15p.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import samuelvalentini.u5d15p.entity.Evento;
import samuelvalentini.u5d15p.entity.Utente;

import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Long> {
    List<Evento> findByOrganizzatore(Utente organizzatore);
}

