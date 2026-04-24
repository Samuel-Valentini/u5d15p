package samuelvalentini.u5d15p.controller;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import samuelvalentini.u5d15p.dto.LoginRequest;
import samuelvalentini.u5d15p.dto.LoginResponse;
import samuelvalentini.u5d15p.dto.NewUtenteResponse;
import samuelvalentini.u5d15p.dto.RegisterRequest;
import samuelvalentini.u5d15p.entity.Utente;
import samuelvalentini.u5d15p.exception.BadRequestException;
import samuelvalentini.u5d15p.service.AuthorizationService;
import samuelvalentini.u5d15p.service.UtenteService;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthorizationController {
    private final AuthorizationService authorizationService;
    private final UtenteService utenteService;

    public AuthorizationController(AuthorizationService authorizationService, UtenteService utenteService) {
        this.authorizationService = authorizationService;
        this.utenteService = utenteService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Validated LoginRequest loginRequest, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getFieldErrors().stream().map(error -> error.getDefaultMessage()).toList();
            throw new BadRequestException(errors);
        }
        return new LoginResponse(this.authorizationService.checkCredentialsAndGenerateToken(loginRequest));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public NewUtenteResponse saveUtente(@RequestBody @Validated RegisterRequest registerRequest, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getFieldErrors().stream().map(error -> error.getDefaultMessage()).toList();
            throw new BadRequestException(errors);
        }
        Utente newUtente = this.utenteService.save(registerRequest);
        return new NewUtenteResponse(newUtente.getUtenteId());
    }
}
