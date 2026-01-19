package modelo.entidad;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "mensajes")
@NamedQueries({
	@NamedQuery(name = "Mensaje.findAll", query = "SELECT m FROM Mensaje m"), 
	@NamedQuery(name = "Mensaje.findByRemitente", query = "SELECT m FROM Mensaje m WHERE m.remitente = :remitente")
	})
@XmlRootElement(name="mensajes")

public class Mensaje implements Serializable {
	
	private static final long serialVersionUID = 1L; 
	
	@Id 
	    @GeneratedValue(strategy = GenerationType.IDENTITY) 
	    @Column(name = "id")
		private int id; 
	
	    @Column(name = "contenido")
	    private String contenido;
	    
	    @Column(name = "fechaDeEnvio") 
	    private String fechaDeEnvio;
	    
	    @Column(name = "destinatario")
	    private String destinatario;
	    
	    @Column(name = "remitente")
	    private String remitente;

	    // Constructor sin-args requerido por JPA
	    public Mensaje() {
	    }
        
	    public Mensaje(int id, String contenido, String fechaDeEnvio, String destinatario, String remitente) {
			super();
			this.id = id;
			this.contenido = contenido;
			this.fechaDeEnvio = fechaDeEnvio;
			this.destinatario = destinatario;
			this.remitente = remitente;
		}
	    
		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
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

		public String getDestinatario() {
			return destinatario;
		}

		public void setDestinatario(String destinatario) {
			this.destinatario = destinatario;
		}

		public String getRemitente() {
			return remitente;
		}

		public void setRemitente(String remitente) {
			this.remitente = remitente;
		}

		public static long getSerialversionuid() {
			return serialVersionUID;
		}
	    
	   
	    
}
