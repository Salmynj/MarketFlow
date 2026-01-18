package controlador;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.DAOs.PublicacionDAO;
import modelo.entidad.Estado;
import modelo.entidad.Publicacion;

@WebServlet("/VisualizarCatalogoController")
public class VisualizarCatalogoController extends HttpServlet {

	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.ruteador(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.ruteador(req, resp);
	}

	private void ruteador(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String ruta = (req.getParameter("ruta") == null) ? "iniciar" : req.getParameter("ruta");

		switch (ruta) {
		case "cargar":
			this.listarPublicaciones(req, resp);
			break;
	}
		
	}
	public void listarPublicaciones(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Llamar al DAO para obtener las publicaciones
		PublicacionDAO publicacionDAO = new PublicacionDAO();
		List<Publicacion> publicaciones = publicacionDAO.obtenerPublicaciones();

		// Poner la lista como atributo en el request para que el JSP la pueda acceder
		req.setAttribute("publicaciones", publicaciones);

		// Reenviar a la vista PanelCatalogo.jsp
		RequestDispatcher rd = req.getRequestDispatcher("/vista/PanelCatalogo.jsp");
		rd.forward(req, resp);

		}
		
		
	
}