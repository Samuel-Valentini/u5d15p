package samuelvalentini.u5d15p.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import samuelvalentini.u5d15p.entity.Evento;
import samuelvalentini.u5d15p.entity.Prenotazione;
import samuelvalentini.u5d15p.entity.Utente;
import samuelvalentini.u5d15p.exception.BadRequestException;
import samuelvalentini.u5d15p.exception.NotFoundException;
import samuelvalentini.u5d15p.exception.UnauthorizedException;
import samuelvalentini.u5d15p.repository.PrenotazioneRepository;

import java.util.List;

@Service
public class PrenotazioneService {

    private final PrenotazioneRepository prenotazioneRepository;
    private final EventoService eventoService;

    public PrenotazioneService(PrenotazioneRepository prenotazioneRepository, EventoService eventoService) {
        this.prenotazioneRepository = prenotazioneRepository;
        this.eventoService = eventoService;
    }

    @Transactional
    public Prenotazione prenotaEvento(Long eventoId, Utente utente) {
        Evento evento = eventoService.findById(eventoId);

        if (prenotazioneRepository.existsByUtenteAndEvento(utente, evento)) {
            throw new BadRequestException("Hai già prenotato un posto per questo evento");
        }

        long prenotazioniEffettuate = prenotazioneRepository.countByEvento(evento);

        if (prenotazioniEffettuate >= evento.getNumeroPostiTotali()) {
            throw new BadRequestException("Non ci sono più posti disponibili per questo evento");
        }

        Prenotazione nuovaPrenotazione = new Prenotazione(utente, evento);

        return prenotazioneRepository.save(nuovaPrenotazione);
    }

    public List<Prenotazione> findByUtente(Utente utente) {
        return prenotazioneRepository.findByUtente(utente);
    }

    public Prenotazione findById(Long prenotazioneId) {
        return prenotazioneRepository.findById(prenotazioneId)
                .orElseThrow(() -> new NotFoundException("Prenotazione con id " + prenotazioneId + " non trovata"));
    }

    public void annullaPrenotazione(Long prenotazioneId, Utente utente) {
        Prenotazione prenotazione = findById(prenotazioneId);

        if (!prenotazione.getUtente().getUtenteId().equals(utente.getUtenteId())) {
            throw new UnauthorizedException("Puoi annullare solo le tue prenotazioni");
        }

        prenotazioneRepository.delete(prenotazione);
    }
}