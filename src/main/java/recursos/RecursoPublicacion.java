package recursos;

import java.util.List;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import modelo.DAOs.PublicacionDAO;
import modelo.entidad.Publicacion;

//anotar una clase la convierte en servicio web
@Path("/publicacion")
public class RecursoPublicacion {
	private PublicacionDAO publicacionDAO;
	public RecursoPublicacion() {
		publicacionDAO = new PublicacionDAO();
	}
	

	//cUANDO RECIBE SE PONE @ PONSUMES
	//Cuando ENVIA SE PONE @ PRODUCES\
	//Los datos primitivos no se ponen
	
	@GET
	@Path("/listar")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Publicacion> listarPublicaciones() {
		return publicacionDAO.obtenerPublicaciones();
	}
	
	@POST
	@Path("/crear")
	@Consumes(MediaType.APPLICATION_JSON)
	public void crearPublicacion(Publicacion publicacion) {
		publicacionDAO.guardarPublicacion(publicacion);
	}
	
	@GET
	@Path("/{id1}")
	@Produces(MediaType.APPLICATION_JSON)
	public Publicacion obtenerPublicacionPorId(@PathParam("id1") int id) {
		return publicacionDAO.buscarPorId(id);
	}
	
	@PUT
	@Path("/actualizar")
	@Consumes(MediaType.APPLICATION_JSON)
	public void actualizarPublicacion(Publicacion publicacion) {
		publicacionDAO.actualizar(publicacion);
	}
	
	@DELETE
	@Path("/eliminar/{id1}")
	public void eliminarPublicacion(@PathParam("id1") int id) {
		boolean publicacion = publicacionDAO.eliminarPublicacion(id);

	}
	
	
	
}
