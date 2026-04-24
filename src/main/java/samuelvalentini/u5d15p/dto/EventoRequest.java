package samuelvalentini.u5d15p.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record EventoRequest(
        @NotBlank(message = "Il titolo è richiesto")
        @Size(max = 255, message = "Il titolo non può superare i 255 caratteri")
        String titolo,

        String descrizione,

        @NotNull(message = "Data e ora sono richieste")
        @Future(message = "La data dell'evento deve essere futura")
        LocalDateTime dataEvento,

        @NotBlank(message = "Il luogo è richiesto")
        @Size(max = 255, message = "Il luogo non può superare i 255 caratteri")
        String luogo,

        @NotNull(message = "Il numero di posti totali è richiesto")
        @Positive(message = "Il numero di posti totali deve essere maggiore di zero")
        Integer numeroPostiTotali
) {
}
