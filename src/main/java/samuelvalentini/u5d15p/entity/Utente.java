package samuelvalentini.u5d15p.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import samuelvalentini.u5d15p.enumeration.Ruolo;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "utenti")
@JsonIgnoreProperties({"accountNonExpired", "accountNonLocked", "authorities", "credentialsNonExpired", "enabled"})
public class Utente implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "utente_id", nullable = false, updatable = false)
    private Long utenteId;

    @NotBlank(message = "Il campo è richiesto")
    @Email(message = "Deve essere inserita una email valida")
    @Size(max = 255, message = "L'email non può superare i 255 caratteri")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Il campo è richiesto")
    @Size(min = 50, max = 255, message = "L'hash deve essere fra 50 e 255 caratteri")
    @JsonIgnore
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Il ruolo è richiesto")
    @Column(name = "ruolo", nullable = false)
    private Ruolo ruolo;

    public Utente(String email, String passwordHash, Ruolo ruolo) {
        this.email = email;
        this.passwordHash = passwordHash;
        this.ruolo = ruolo;
    }

    protected Utente() {
    }

    public Long getUtenteId() {
        return utenteId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Ruolo getRuolo() {
        return ruolo;
    }

    public void setRuolo(Ruolo ruolo) {
        this.ruolo = ruolo;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.ruolo.name()));
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public String getUsername() {
        return email;
    }
}
