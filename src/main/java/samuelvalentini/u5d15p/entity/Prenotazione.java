package samuelvalentini.u5d15p.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "prenotazioni", uniqueConstraints = @UniqueConstraint(columnNames = {"utente_id", "evento_id"}))
public class Prenotazione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prenotazione_id", nullable = false, updatable = false)
    private Long prenotazioneId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "utente_id", nullable = false)
    private Utente utente;

    @ManyToOne(optional = false)
    @JoinColumn(name = "evento_id", nullable = false)
    private Evento evento;

    public Prenotazione(Utente utente, Evento evento) {
        this.utente = utente;
        this.evento = evento;
    }

    protected Prenotazione() {
    }

    public Long getPrenotazioneId() {
        return prenotazioneId;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    @Override
    public String toString() {
        return "Prenotazione{" +
                "prenotazioneId=" + prenotazioneId +
                ", utente=" + (utente != null ? utente.getEmail() : null) +
                ", evento=" + (evento != null ? evento.getTitolo() : null) +
                '}';
    }
}
