package recursos;

import jakarta.ws.rs.Path;

import java.util.List;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import modelo.DAOs.MensajeDAO;
import modelo.entidad.Mensaje;


@Path("/mensaje")

public class RecursoMensaje {
	private MensajeDAO mensajeDAO;
	public RecursoMensaje() {
		mensajeDAO = new MensajeDAO();
	}
	
	//cUANDO RECIBE SE PONE @ CONSUMES
	//Cuando ENVIA SE PONE @ PRODUCES\
	//Los datos primitivos no se ponen
	
	@GET
	@Path("/listar")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Mensaje> listarConversacion(
	    @QueryParam("remitente") String remitente, 
	    @QueryParam("destinatario") String destinatario) {
	    
	    // Ahora los datos vienen de la URL: /api/mensaje/listar?remitente=Pepe&destinatario=Juan
	    return mensajeDAO.listarConversacion(remitente, destinatario);
	}
	
	@POST
	@Path("/registrar")
	@Consumes(MediaType.APPLICATION_JSON)
	public void crearPublicacion(Mensaje mensaje) {
		mensajeDAO.registrarMensaje(mensaje);
	}
	
	
}
