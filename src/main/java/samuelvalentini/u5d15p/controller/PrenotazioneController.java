package samuelvalentini.u5d15p.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import samuelvalentini.u5d15p.dto.PrenotazioneResponse;
import samuelvalentini.u5d15p.entity.Prenotazione;
import samuelvalentini.u5d15p.entity.Utente;
import samuelvalentini.u5d15p.service.PrenotazioneService;

import java.util.List;

@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioneController {

    private final PrenotazioneService prenotazioneService;

    public PrenotazioneController(PrenotazioneService prenotazioneService) {
        this.prenotazioneService = prenotazioneService;
    }

    @PostMapping("/eventi/{eventoId}")
    @ResponseStatus(HttpStatus.CREATED)
    public PrenotazioneResponse prenotaEvento(
            @PathVariable Long eventoId,
            @AuthenticationPrincipal Utente currentUser
    ) {
        Prenotazione prenotazione = prenotazioneService.prenotaEvento(eventoId, currentUser);
        return toResponse(prenotazione);
    }

    @GetMapping("/me")
    public List<PrenotazioneResponse> getMiePrenotazioni(
            @AuthenticationPrincipal Utente currentUser
    ) {
        return prenotazioneService.findByUtente(currentUser)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @DeleteMapping("/{prenotazioneId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void annullaPrenotazione(
            @PathVariable Long prenotazioneId,
            @AuthenticationPrincipal Utente currentUser
    ) {
        prenotazioneService.annullaPrenotazione(prenotazioneId, currentUser);
    }

    private PrenotazioneResponse toResponse(Prenotazione prenotazione) {
        return new PrenotazioneResponse(
                prenotazione.getPrenotazioneId(),
                prenotazione.getUtente().getUtenteId(),
                prenotazione.getUtente().getEmail(),
                prenotazione.getEvento().getEventoId(),
                prenotazione.getEvento().getTitolo(),
                prenotazione.getEvento().getDataEvento(),
                prenotazione.getEvento().getLuogo()
        );
    }
}
