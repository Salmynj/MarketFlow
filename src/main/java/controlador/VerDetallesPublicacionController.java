package controlador;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import modelo.DAOs.PublicacionDAO;
import modelo.entidad.Publicacion;


@WebServlet("/VerDetallesPublicacionController")
public class VerDetallesPublicacionController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.ruteador(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.ruteador(req, resp);
	}

	private void ruteador(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String ruta = (req.getParameter("ruta") == null) ? "detallesPublicacion" : req.getParameter("ruta");

		switch (ruta) {
		case "detallesPublicacion":
			this.verDetallesPublicacion(req, resp);
			break;
		
	}
	}

	private void verDetallesPublicacion(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int idPublicacion = Integer.parseInt(req.getParameter("id"));

		PublicacionDAO publicacionDAO = new PublicacionDAO();
		Publicacion publicacion = publicacionDAO.buscarPorId(idPublicacion);

		req.setAttribute("publicacion", publicacion);
		

		RequestDispatcher rd = req.getRequestDispatcher("/vista/PanelVerDetallesPublicacion.jsp");
		rd.forward(req, resp);
		
	}
}