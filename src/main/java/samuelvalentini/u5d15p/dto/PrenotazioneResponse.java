package samuelvalentini.u5d15p.dto;

import java.time.LocalDateTime;

public record PrenotazioneResponse(Long prenotazioneId,
                                   Long utenteId,
                                   String utenteEmail,
                                   Long eventoId,
                                   String titoloEvento,
                                   LocalDateTime dataEvento,
                                   String luogo) {
}
