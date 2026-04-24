package samuelvalentini.u5d15p.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import samuelvalentini.u5d15p.enumeration.Ruolo;

public record RegisterRequest(
        @NotBlank(message = "L'email è richiesta")
        @Email(message = "L'email deve essere valida")
        String email,

        @NotBlank(message = "La password è richiesta")
        @Size(min = 8, max = 255, message = "La password deve essere fra 8 e 255 caratteri")
        String password,

        @NotNull(message = "Il ruolo è richiesto")
        Ruolo ruolo
) {
}
