package samuelvalentini.u5d15p.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import samuelvalentini.u5d15p.dto.EventoRequest;
import samuelvalentini.u5d15p.dto.EventoResponse;
import samuelvalentini.u5d15p.entity.Utente;
import samuelvalentini.u5d15p.service.EventoService;

import java.util.List;

@RestController
@RequestMapping("/eventi")
public class EventoController {

    private final EventoService eventoService;

    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @GetMapping
    public List<EventoResponse> getAllEventi() {
        return eventoService.findAll();
    }

    @GetMapping("/{eventoId}")
    public EventoResponse getEventoById(@PathVariable Long eventoId) {
        return eventoService.findByIdResponse(eventoId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventoResponse createEvento(
            @RequestBody @Valid EventoRequest eventoRequest,
            @AuthenticationPrincipal Utente currentUser
    ) {
        return eventoService.save(eventoRequest, currentUser);
    }

    @PutMapping("/{eventoId}")
    public EventoResponse updateEvento(
            @PathVariable Long eventoId,
            @RequestBody @Valid EventoRequest eventoRequest,
            @AuthenticationPrincipal Utente currentUser
    ) {
        return eventoService.update(eventoId, eventoRequest, currentUser);
    }

    @DeleteMapping("/{eventoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEvento(
            @PathVariable Long eventoId,
            @AuthenticationPrincipal Utente currentUser
    ) {
        eventoService.delete(eventoId, currentUser);
    }
}