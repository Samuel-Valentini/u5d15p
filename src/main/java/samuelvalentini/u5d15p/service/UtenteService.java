package samuelvalentini.u5d15p.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import samuelvalentini.u5d15p.dto.RegisterRequest;
import samuelvalentini.u5d15p.entity.Utente;
import samuelvalentini.u5d15p.exception.BadRequestException;
import samuelvalentini.u5d15p.exception.NotFoundException;
import samuelvalentini.u5d15p.repository.UtenteRepository;

@Service
public class UtenteService {

    private final UtenteRepository utenteRepository;
    private final PasswordEncoder passwordEncoder;

    public UtenteService(UtenteRepository utenteRepository, PasswordEncoder passwordEncoder) {
        this.utenteRepository = utenteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Utente save(RegisterRequest registerRequest) {
        if (utenteRepository.existsByEmail(registerRequest.email())) {
            throw new BadRequestException("Email già in uso");
        }

        String passwordHash = passwordEncoder.encode(registerRequest.password());

        Utente nuovoUtente = new Utente(
                registerRequest.email(),
                passwordHash,
                registerRequest.ruolo()
        );

        return utenteRepository.save(nuovoUtente);
    }

    public Utente findById(Long utenteId) {
        return utenteRepository.findById(utenteId)
                .orElseThrow(() -> new NotFoundException("Utente con id " + utenteId + " non trovato"));
    }

    public Utente findByEmail(String email) {
        return utenteRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Utente con email " + email + " non trovato"));
    }


}