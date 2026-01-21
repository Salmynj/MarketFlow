package controlador;

import java.io.IOException;
import java.util.ArrayList;
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

@WebServlet("/FiltrarPublicacionController")
public class FiltrarPublicacionController extends HttpServlet {
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
			this.aplicarFiltros(req, resp);
			break;
		
	}
	}

	private void aplicarFiltros(HttpServletRequest req, HttpServletResponse resp) {
		// Leer parámetros de filtros y búsqueda
		String q = req.getParameter("q"); // búsqueda libre
		String categoria = req.getParameter("categoria");
		String estadoStr = req.getParameter("estado");
		Estado estado = null;
		if (estadoStr != null && !estadoStr.isEmpty()) {
			try {
				estado = Estado.valueOf(estadoStr);
			} catch (IllegalArgumentException ex) { 
				estado = null; // valor no reconocido
			}
		}

		// Rango de precio: se espera formato "min-max" o "100+" para >100
		String precioRange = req.getParameter("precioRange");
		Double precioMin = null;
		Double precioMax = null;
		if (precioRange != null && !precioRange.isEmpty()) {
			if (precioRange.contains("-")) {
				String[] parts = precioRange.split("-");
				try { precioMin = Double.parseDouble(parts[0]); } catch (Exception e) { precioMin = null; }
				try { precioMax = Double.parseDouble(parts[1]); } catch (Exception e) { precioMax = null; }
			} else if (precioRange.endsWith("+")) {
				try { precioMin = Double.parseDouble(precioRange.replace("+", "")); } catch (Exception e) { precioMin = null; }
				precioMax = null;
			}
		}

		PublicacionDAO publicacionDAO = new PublicacionDAO();
		List<Publicacion> publicacionesFiltradas = publicacionDAO.obtenerPublicacionesFiltradas(estado, categoria, precioMin, precioMax, q);

		// Pasar parámetros para que la vista pueda mantener los filtros seleccionados
		req.setAttribute("publicaciones", publicacionesFiltradas);
		req.setAttribute("q", q);
		req.setAttribute("categoria", categoria);
		req.setAttribute("estado", estadoStr);
		req.setAttribute("precioRange", precioRange);

		try {
			RequestDispatcher rd = req.getRequestDispatcher("/vista/PanelCatalogo.jsp");
			rd.forward(req, resp);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}
}