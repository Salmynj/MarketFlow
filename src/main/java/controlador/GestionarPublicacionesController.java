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
import modelo.entidad.Publicacion;

@WebServlet("/GestionarPublicacionesController")

public class GestionarPublicacionesController extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.ruteador(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.ruteador(req, resp);
	}

	private void ruteador(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String ruta = (req.getParameter("ruta") == null) ? "listar" : req.getParameter("ruta");

		switch (ruta) {
		case "listar":
			this.listarMisPublicaciones(req, resp);
			break;
	}
	}
	
	public void listarMisPublicaciones(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	   	
		// Reemplazo futuro:
				/*Usuario user = (Usuario) req.getSession().getAttribute("usuarioLogueado");
				int idUsuario = user.getId();
				List<Publicacion> publicaciones = publicacionDAO.listarPorUsuario(idUsuario);
				*/
		
		// --- LÓGICA TEMPORAL (DATOS QUEMADOS) ---
	    // Simulamos que el usuario logueado tiene el ID 1
		String usuarioQuemado = "Admin Temporal"; 
		req.setAttribute("nombreUsuario", usuarioQuemado);
	    
	    // ---------------------------------------

		PublicacionDAO publicacionDAO = new PublicacionDAO();
	    List<Publicacion> publicaciones = publicacionDAO.obtenerMisPublicaciones(usuarioQuemado); 

	    req.setAttribute("publicaciones", publicaciones);
	    req.getRequestDispatcher("/vista/PanelMisPublicaciones.jsp").forward(req, resp);
	}

}
