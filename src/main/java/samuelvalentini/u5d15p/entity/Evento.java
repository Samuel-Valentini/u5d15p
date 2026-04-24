package samuelvalentini.u5d15p.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "eventi")
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "evento_id", nullable = false, updatable = false)
    private Long eventoId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "organizzatore_id", nullable = false)
    private Utente organizzatore;

    @NotBlank(message = "Il titolo è richiesto")
    @Size(max = 255, message = "Il titolo non può superare i 255 caratteri")
    @Column(name = "titolo", nullable = false)
    private String titolo;

    @Column(name = "descrizione", columnDefinition = "TEXT")
    private String descrizione;

    //per la data evento preferisco utilizzare un timestamp in maniera tale che sia indicata anche l'ora
    @NotNull(message = "Data e ora sono richieste")
    @Column(name = "data_evento", nullable = false)
    private LocalDateTime dataEvento;

    @NotBlank(message = "Il luogo è richiesto")
    @Size(max = 255, message = "Il luogo non può superare i 255 caratteri")
    @Column(name = "luogo", nullable = false)
    private String luogo;

    @NotNull(message = "Il numero di posti totali è richiesto")
    @Positive(message = "Il numero di posti totali deve essere maggiore di zero")
    @Column(name = "numero_posti_totali", nullable = false)
    private Integer numeroPostiTotali;

    public Evento(Utente organizzatore, String titolo, String descrizione, LocalDateTime dataEvento, String luogo, Integer numeroPostiTotali) {
        this.organizzatore = organizzatore;
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.dataEvento = dataEvento;
        this.luogo = luogo;
        this.numeroPostiTotali = numeroPostiTotali;
    }

    public Evento(Utente organizzatore, String titolo, LocalDateTime dataEvento, String luogo, Integer numeroPostiTotali) {
        this.organizzatore = organizzatore;
        this.titolo = titolo;
        this.dataEvento = dataEvento;
        this.luogo = luogo;
        this.numeroPostiTotali = numeroPostiTotali;
    }

    protected Evento() {
    }

    public Long getEventoId() {
        return eventoId;
    }

    public Utente getOrganizzatore() {
        return organizzatore;
    }

    public void setOrganizzatore(Utente organizzatore) {
        this.organizzatore = organizzatore;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public LocalDateTime getDataEvento() {
        return dataEvento;
    }

    public void setDataEvento(LocalDateTime dataEvento) {
        this.dataEvento = dataEvento;
    }

    public String getLuogo() {
        return luogo;
    }

    public void setLuogo(String luogo) {
        this.luogo = luogo;
    }

    public Integer getNumeroPostiTotali() {
        return numeroPostiTotali;
    }

    public void setNumeroPostiTotali(Integer numeroPostiTotali) {
        this.numeroPostiTotali = numeroPostiTotali;
    }

    @Override
    public String toString() {
        return "Evento{" +
                "eventoId=" + eventoId +
                ", organizzatore=" + (organizzatore != null ? organizzatore.getEmail() : null) +
                ", titolo='" + titolo + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", dataEvento=" + dataEvento +
                ", luogo='" + luogo + '\'' +
                ", numeroPostiTotali=" + numeroPostiTotali +
                '}';
    }
}
