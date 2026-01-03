package modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "mensaje")
public class Mensaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String remitente;
    private String destinatario;
    private String contenido;
    private String fechaDeEnvio;

    public Mensaje() {
    }

    public Mensaje(String remitente, String destinatario, String contenido, String fechaDeEnvio) {
        this.remitente = remitente;
        this.destinatario = destinatario;
        this.contenido = contenido;
        this.fechaDeEnvio = fechaDeEnvio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRemitente() {
        return remitente;
    }

    public void setRemitente(String remitente) {
        this.remitente = remitente;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getFechaDeEnvio() {
        return fechaDeEnvio;
    }

    public void setFechaDeEnvio(String fechaDeEnvio) {
        this.fechaDeEnvio = fechaDeEnvio;
    }
}