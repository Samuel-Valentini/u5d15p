package samuelvalentini.u5d15p.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import samuelvalentini.u5d15p.entity.Utente;
import samuelvalentini.u5d15p.exception.UnauthorizedException;

import java.util.Date;

@Component
public class TokenTool {
    private final String secret;


    public TokenTool(@Value("${jwt.secret}") String secret) {
        this.secret = secret;
    }

    public String generateToken(Utente utente) {
        Date date = new Date(System.currentTimeMillis());
        return Jwts.builder().issuedAt(date).expiration(new Date(date.getTime() + 1000L * 60 * 60 * 24 * 7)).subject(String.valueOf(utente.getUtenteId())).signWith(Keys.hmacShaKeyFor(secret.getBytes())).compact();
    }

    public void verifyToken(String token) {
        try {
            Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build().parse(token);
        } catch (Exception ex) {
            throw new UnauthorizedException("Utente non autorizzato");
        }
    }

    public Long extractIdFromToken(String token) {
        return Long.valueOf(Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build().parseSignedClaims(token).getPayload().getSubject());
    }
}
