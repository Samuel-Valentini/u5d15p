package samuelvalentini.u5d15p.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import samuelvalentini.u5d15p.dto.LoginRequest;
import samuelvalentini.u5d15p.entity.Utente;
import samuelvalentini.u5d15p.exception.NotFoundException;
import samuelvalentini.u5d15p.exception.UnauthorizedException;
import samuelvalentini.u5d15p.security.TokenTool;

@Service
public class AuthorizationService {
    private final UtenteService utenteService;
    private final TokenTool tokenTool;
    private final PasswordEncoder bcrypt;

    public AuthorizationService(UtenteService utenteService, TokenTool tokenTool, PasswordEncoder bcrypt) {
        this.utenteService = utenteService;
        this.tokenTool = tokenTool;
        this.bcrypt = bcrypt;
    }

    public String checkCredentialsAndGenerateToken(LoginRequest loginRequest) {
        try {
            Utente found = this.utenteService.findByEmail(loginRequest.email());
            if (this.bcrypt.matches(loginRequest.password(), found.getPassword())) {
                return this.tokenTool.generateToken(found);
            } else {
                throw new UnauthorizedException("Credenziali non corrette");
            }

        } catch (NotFoundException ex) {
            throw new UnauthorizedException("Credenziali non corrette");
        }
    }
}
