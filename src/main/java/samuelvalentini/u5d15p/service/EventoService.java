package samuelvalentini.u5d15p.service;

import org.springframework.stereotype.Service;
import samuelvalentini.u5d15p.dto.EventoRequest;
import samuelvalentini.u5d15p.dto.EventoResponse;
import samuelvalentini.u5d15p.entity.Evento;
import samuelvalentini.u5d15p.entity.Utente;
import samuelvalentini.u5d15p.enumeration.Ruolo;
import samuelvalentini.u5d15p.exception.NotFoundException;
import samuelvalentini.u5d15p.exception.UnauthorizedException;
import samuelvalentini.u5d15p.repository.EventoRepository;
import samuelvalentini.u5d15p.repository.PrenotazioneRepository;

import java.util.List;


@Service
public class EventoService {

    private final EventoRepository eventoRepository;
    //devo iniettare PrenotazioneRepository e non PrenotazioneService per evitare un riferimento circolare
    private final PrenotazioneRepository prenotazioneRepository;

    public EventoService(EventoRepository eventoRepository, PrenotazioneRepository prenotazioneRepository) {
        this.eventoRepository = eventoRepository;
        this.prenotazioneRepository = prenotazioneRepository;
    }

    public List<EventoResponse> findAll() {
        return eventoRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<EventoResponse> findEventiPrenotabili() {
        return eventoRepository.findEventiPrenotabili()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public Evento findById(Long eventoId) {
        return eventoRepository.findById(eventoId)
                .orElseThrow(() -> new NotFoundException("Evento con id " + eventoId + " non trovato"));
    }

    public EventoResponse findByIdResponse(Long eventoId) {
        Evento evento = findById(eventoId);
        return toResponse(evento);
    }

    public EventoResponse save(EventoRequest eventoRequest, Utente organizzatore) {
        checkOrganizzatore(organizzatore);

        Evento nuovoEvento = new Evento(organizzatore, eventoRequest.titolo(), eventoRequest.descrizione(), eventoRequest.dataEvento(), eventoRequest.luogo(), eventoRequest.numeroPostiTotali()
        );

        Evento savedEvento = eventoRepository.save(nuovoEvento);

        return toResponse(savedEvento);
    }

    public EventoResponse update(Long eventoId, EventoRequest eventoRequest, Utente organizzatore) {
        checkOrganizzatore(organizzatore);

        Evento found = findById(eventoId);

        checkProprietarioEvento(found, organizzatore);

        found.setTitolo(eventoRequest.titolo());
        found.setDescrizione(eventoRequest.descrizione());
        found.setDataEvento(eventoRequest.dataEvento());
        found.setLuogo(eventoRequest.luogo());
        found.setNumeroPostiTotali(eventoRequest.numeroPostiTotali());

        Evento updatedEvento = eventoRepository.save(found);

        return toResponse(updatedEvento);
    }

    public void delete(Long eventoId, Utente organizzatore) {
        checkOrganizzatore(organizzatore);

        Evento found = findById(eventoId);

        checkProprietarioEvento(found, organizzatore);

        eventoRepository.delete(found);
    }

    private void checkOrganizzatore(Utente utente) {
        if (utente.getRuolo() != Ruolo.ORGANIZZATORE) {
            throw new UnauthorizedException("Solo gli organizzatori possono gestire eventi");
        }
    }

    private void checkProprietarioEvento(Evento evento, Utente utente) {
        if (!evento.getOrganizzatore().getUtenteId().equals(utente.getUtenteId())) {
            throw new UnauthorizedException("Puoi modificare o eliminare solo i tuoi eventi");
        }
    }

    private EventoResponse toResponse(Evento evento) {
        long prenotazioniEffettuate = prenotazioneRepository.countByEvento(evento);
        int postiDisponibili = evento.getNumeroPostiTotali() - (int) prenotazioniEffettuate;

        return new EventoResponse(
                evento.getEventoId(),
                evento.getTitolo(),
                evento.getDescrizione(),
                evento.getDataEvento(),
                evento.getLuogo(),
                evento.getNumeroPostiTotali(),
                postiDisponibili,
                evento.getOrganizzatore().getUtenteId(),
                evento.getOrganizzatore().getEmail()
        );
    }
}

