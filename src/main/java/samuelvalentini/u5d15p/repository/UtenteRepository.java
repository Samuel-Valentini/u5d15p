package samuelvalentini.u5d15p.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import samuelvalentini.u5d15p.entity.Utente;

import java.util.Optional;

public interface UtenteRepository extends JpaRepository<Utente, Long> {

    Optional<Utente> findByEmail(String email);

    boolean existsByEmail(String email);
}
