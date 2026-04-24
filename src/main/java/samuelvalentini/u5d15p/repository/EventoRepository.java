package samuelvalentini.u5d15p.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import samuelvalentini.u5d15p.entity.Evento;
import samuelvalentini.u5d15p.entity.Utente;

import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Long> {
    List<Evento> findByOrganizzatore(Utente organizzatore);

    //left join per prendere anche gli eventi senza nessuna prenotazione
    @Query("SELECT e FROM Evento e LEFT JOIN Prenotazione p ON p.evento = e  WHERE e.dataEvento > CURRENT_TIMESTAMP GROUP BY e HAVING COUNT(p.prenotazioneId) < e.numeroPostiTotali ORDER BY e.dataEvento ASC")
    List<Evento> findEventiPrenotabili();
}

