package controlador;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import modelo.DAOs.PublicacionDAO;
import modelo.entidad.Estado;
import modelo.entidad.Publicacion;

@WebServlet("/RegistrarPublicacionController")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 10 * 1024 * 1024)
public class RegistrarPublicacionController extends HttpServlet {
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.ruteador(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.ruteador(req, resp);
	}

	private void ruteador(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String ruta = (req.getParameter("ruta") == null) ? "guardar" : req.getParameter("ruta");

		switch (ruta) {
		case "guardar":
			this.guardarNuevaPublicacion(req, resp);
			break;
		case "crear":
			this.crearPublicacion(req, resp);
			break;
	}
	}
	public void guardarNuevaPublicacion(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");

		// Obtener parámetros del formulario
		String titulo = req.getParameter("titulo");
		String categoria = req.getParameter("categoria");
		String nombreProducto = req.getParameter("NombreDelProducto");
		String precioStr = req.getParameter("precio");
		double precio = 0.0;
		try {
			if (precioStr != null && !precioStr.isEmpty()) {
				precio = Double.parseDouble(precioStr);
			}
		} catch (NumberFormatException e) {
			precio = 0.0;
		}
		String estadoParam = req.getParameter("estado");
		Estado estado = Estado.NUEVO;
		if (estadoParam != null) {
			try {
				estado = Estado.valueOf(estadoParam);
			} catch (IllegalArgumentException ex) {
				estado = Estado.NUEVO;
			}
		}
		String descripcion = req.getParameter("descripcion");
		String usuario = req.getParameter("usuario");

		// Manejo de imagen (multipart y opción URL)
		String imagenPath = "";
		try {
			// Primero revisar si el formulario incluyó campo imagenUrl (cuando se selecciona usar URL)
			String imagenUrl = req.getParameter("imagenUrl");
			if (imagenUrl != null && !imagenUrl.trim().isEmpty()) {
				imagenPath = imagenUrl.trim();
			} else {
				Part part = req.getPart("imagen");
				if (part != null && part.getSize() > 0) {
					String submittedFileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
					// Generar nombre único para evitar colisiones
					String ext = "";
					int dot = submittedFileName.lastIndexOf('.');
					if (dot >= 0) ext = submittedFileName.substring(dot);
					String uniqueName = UUID.randomUUID().toString() + ext;
					String imagesDir = getServletContext().getRealPath("/assets/img");
					File dir = new File(imagesDir);
					if (!dir.exists()) {
						dir.mkdirs();
					}
					String destination = imagesDir + File.separator + uniqueName;
					part.write(destination);
					// Guardar la URL pública completa para que pueda usarse en <img src="..."> desde la vista
					imagenPath = req.getContextPath() + "/assets/img/" + uniqueName;
				}
			}
		} catch (Exception ex) {
			// si no es multipart o ocurre error, intentar obtener imagen como parámetro simple
			String imgParam = req.getParameter("imagen");
			if (imgParam != null) {
				// si viene una ruta relativa, normalizar a URL pública
				if (!imgParam.startsWith("http://") && !imgParam.startsWith("https://") && !imgParam.startsWith(req.getContextPath())) {
					imagenPath = req.getContextPath() + (imgParam.startsWith("/") ? "" : "/") + imgParam;
				} else {
					imagenPath = imgParam;
				}
			}
		}

		// Crear la entidad Publicacion (id=0 para nueva publicación)
		Publicacion publicacion = new Publicacion(
				titulo,
				(categoria != null) ? categoria : "",
				(nombreProducto != null) ? nombreProducto : "",
				precio,
				estado,
				descripcion,
				imagenPath,
				(usuario != null) ? usuario : "",
				0
		);

		// Log detallado para depuración
		System.out.println(">>>> INFO:RegistrarPublicacionController: guardarPublicacion datos: titulo='" + titulo + "', categoria='" + categoria + "', nombre='" + nombreProducto + "', precio='" + precio + "', estado='" + estado + "', usuario='" + usuario + "', imagen='" + imagenPath + "'");

		// Guardar mediante DAO
		PublicacionDAO dao = new PublicacionDAO();
		boolean ok = dao.guardarPublicacion(publicacion);

		if (ok) {
			// Redirigir al catálogo para ver la nueva publicación (usar ruta=cargar que maneja VisualizarCatalogoController)
			System.out.println(">>>> INFO:RegistrarPublicacionController: publicación guardada, redirigiendo a catálogo");
			resp.sendRedirect(req.getContextPath() + "/VisualizarCatalogoController?ruta=cargar");
		} else {
			// En caso de error, reenviar al formulario con un mensaje
			req.setAttribute("error", "No se pudo guardar la publicación");
			RequestDispatcher rd = req.getRequestDispatcher("/vista/PanelRegistrarPublicacion.jsp");
			rd.forward(req, resp);
		}
		
	}
	public void crearPublicacion(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Reenviar a la vista PanelCatalogo.jsp para obtener el formulario de creación
		RequestDispatcher rd = req.getRequestDispatcher("/vista/PanelRegistrarPublicacion.jsp");
		rd.forward(req, resp);
	}

}