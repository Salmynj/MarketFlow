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


@WebServlet("/EliminarPublicacionController")
public class EliminarPublicacionController extends HttpServlet {
	private PublicacionDAO publicacionDAO = new PublicacionDAO();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.ruteador(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.ruteador(req, resp);
	}

	private void ruteador(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String ruta = (req.getParameter("ruta") == null) ? "eliminar" : req.getParameter("ruta");

		switch (ruta) {
		case "eliminar":
			this.eliminarPublicacion(req, resp);
			break;
		case "confirmar":
			this.confirmarEliminacion(req, resp);
			break;

	}
	}

	private void eliminarPublicacion(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Mostrar panel emergente para confirmar la eliminación
		String idParam = req.getParameter("idPublicacion");
		if (idParam == null || idParam.trim().isEmpty()) {
			// intentar con otro nombre de parámetro
			idParam = req.getParameter("id");
		}

		if (idParam == null || idParam.trim().isEmpty()) {
			req.setAttribute("titulo", "Error");
			req.setAttribute("mensaje", "ID de publicación no proporcionado.");
			req.setAttribute("urlCancelar", req.getContextPath() + "/GestionarPublicacionesController?ruta=listar");
			RequestDispatcher rd = req.getRequestDispatcher("/vista/PanelMensajeEmergente.jsp");
			rd.forward(req, resp);
			return;
		}

		int idPublicacion;
		try {
			idPublicacion = Integer.parseInt(idParam);
		} catch (NumberFormatException nfe) {
			req.setAttribute("titulo", "Error");
			req.setAttribute("mensaje", "ID de publicación inválido.");
			req.setAttribute("urlCancelar", req.getContextPath() + "/GestionarPublicacionesController?ruta=listar");
			RequestDispatcher rd = req.getRequestDispatcher("/vista/PanelMensajeEmergente.jsp");
			rd.forward(req, resp);
			return;
		}

		// Preparar datos para confirmar
		// Obtener nombre del producto para mostrar en el mensaje de confirmación
		String nombreProd = "";
		try {
			Publicacion pub = publicacionDAO.buscarPorId(idPublicacion);
			if (pub != null && pub.getNombreDelProducto() != null && !pub.getNombreDelProducto().trim().isEmpty()) {
				nombreProd = pub.getNombreDelProducto();
			} else if (pub != null && pub.getTitulo() != null) {
				nombreProd = pub.getTitulo();
			}
		} catch (Exception ex) {
			// ignore, nombreProd se quedará vacío
		}
		if (nombreProd == null) nombreProd = "";
		req.setAttribute("titulo", "Confirmar eliminación");
		req.setAttribute("mensaje", "¿Estás seguro que deseas eliminar la publicación '" + nombreProd + "' (ID: " + idPublicacion + ")? Esta acción no se puede deshacer.");
		String urlConfirmar = req.getContextPath() + "/EliminarPublicacionController?ruta=confirmar&idPublicacion=" + idPublicacion;
		req.setAttribute("urlConfirmar", urlConfirmar);
		req.setAttribute("urlCancelar", req.getContextPath() + "/GestionarPublicacionesController?ruta=listar");

		RequestDispatcher rd = req.getRequestDispatcher("/vista/PanelMensajeEmergente.jsp");
		rd.forward(req, resp);
	}

	private void confirmarEliminacion(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String idParam = req.getParameter("idPublicacion");
		if (idParam == null || idParam.trim().isEmpty()) {
			idParam = req.getParameter("id");
		}

		if (idParam == null || idParam.trim().isEmpty()) {
			req.setAttribute("titulo", "Error");
			req.setAttribute("mensaje", "ID de publicación no recibido para confirmar la eliminación.");
			req.setAttribute("urlCancelar", req.getContextPath() + "/GestionarPublicacionesController?ruta=listar");
			RequestDispatcher rd = req.getRequestDispatcher("/vista/PanelMensajeEmergente.jsp");
			rd.forward(req, resp);
			return;
		}

		int idPublicacion;
		try {
			idPublicacion = Integer.parseInt(idParam);
		} catch (NumberFormatException nfe) {
			req.setAttribute("titulo", "Error");
			req.setAttribute("mensaje", "ID de publicación inválido.");
			req.setAttribute("urlCancelar", req.getContextPath() + "/GestionarPublicacionesController?ruta=listar");
			RequestDispatcher rd = req.getRequestDispatcher("/vista/PanelMensajeEmergente.jsp");
			rd.forward(req, resp);
			return;
		}

		boolean eliminado = false;
		String nombreProd = "";
		try {
			// obtener nombre antes de eliminar para mostrar en el mensaje final
			Publicacion pub = publicacionDAO.buscarPorId(idPublicacion);
			if (pub != null) {
				if (pub.getNombreDelProducto() != null && !pub.getNombreDelProducto().trim().isEmpty()) {
					nombreProd = pub.getNombreDelProducto();
				} else if (pub.getTitulo() != null) {
					nombreProd = pub.getTitulo();
				}
			}
			eliminado = publicacionDAO.eliminarPublicacion(idPublicacion);
		} catch (Exception ex) {
			systemOut(ex);
		}

		if (eliminado) {
			req.setAttribute("titulo", "Eliminación correcta");
			req.setAttribute("mensaje", "La publicación '" + nombreProd + "' ha sido eliminada con éxito.");
			req.setAttribute("urlCancelar", req.getContextPath() + "/GestionarPublicacionesController?ruta=listar");
		} else {
			req.setAttribute("titulo", "Error al eliminar");
			req.setAttribute("mensaje", "No se pudo eliminar la publicación '" + nombreProd + "'. Puede que ya no exista o haya un problema en el servidor.");
			req.setAttribute("urlCancelar", req.getContextPath() + "/GestionarPublicacionesController?ruta=listar");
		}

		RequestDispatcher rd = req.getRequestDispatcher("/vista/PanelMensajeEmergente.jsp");
		rd.forward(req, resp);
	}

	private void systemOut(Exception ex) {
		// pequeño helper para no interrumpir la lógica de arriba con System.out en medio del flujo
		if (ex != null) ex.printStackTrace();
	}
}