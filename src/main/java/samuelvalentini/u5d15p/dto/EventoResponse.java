package samuelvalentini.u5d15p.dto;

import java.time.LocalDateTime;

public record EventoResponse(Long eventoId,
                             String titolo,
                             String descrizione,
                             LocalDateTime dataEvento,
                             String luogo,
                             Integer numeroPostiTotali,
                             Integer postiDisponibili,
                             Long organizzatoreId,
                             String organizzatoreEmail) {
}
