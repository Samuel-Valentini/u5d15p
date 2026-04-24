package samuelvalentini.u5d15p.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "L'email è richiesta")
        @Email(message = "L'email deve essere valida")
        String email,

        @NotBlank(message = "La password è richiesta")
        String password
) {
}
