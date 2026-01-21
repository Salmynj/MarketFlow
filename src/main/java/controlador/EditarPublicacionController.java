package controlador;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import modelo.DAOs.MensajeDAO;
import modelo.DAOs.PublicacionDAO;
import modelo.entidad.Estado;
import modelo.entidad.Mensaje;
import modelo.entidad.Publicacion;

import jakarta.servlet.annotation.MultipartConfig;

@MultipartConfig
@WebServlet("/EditarPublicacionController")
public class EditarPublicacionController extends HttpServlet {

	private MensajeDAO mensajeDAO = new MensajeDAO();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.ruteador(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.ruteador(req, resp);
	}

	private void ruteador(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String ruta = (req.getParameter("ruta") == null) ? "editar" : req.getParameter("ruta");

		switch (ruta) {
		case "editar":
			this.editarPublicacion(req, resp);
			break;
		case "actualizar":
			this.actualizarPublicacion(req, resp);
			break;
	}
	}
	public void actualizarPublicacion(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");

		// Obtener parámetros del formulario
		String titulo = req.getParameter("titulo");
		String categoria = req.getParameter("categoria");
		String nombreProducto = req.getParameter("nombreDelProducto");
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
		

		// Log detallado para depuración
		System.out.println(">>>> INFO:RegistrarPublicacionController: guardarPublicacion datos: titulo='" + titulo + "', categoria='" + categoria + "', nombre='" + nombreProducto + "', precio='" + precio + "', estado='" + estado + "', usuario='" + usuario + "', imagen='" + imagenPath + "'");

		// Guardar mediante DAO
		PublicacionDAO dao = new PublicacionDAO();
		String idParam = req.getParameter("id");
		if (idParam == null) {
		    req.setAttribute("error", "ID de publicación no recibido");
		    req.getRequestDispatcher("/vista/PanelRegistrarPublicacion.jsp").forward(req, resp);
		    return;
		}

		int id = Integer.parseInt(idParam);

		Publicacion publicacion = dao.buscarPorId(id);

		if (publicacion == null) {
		    req.setAttribute("error", "La publicación no existe");
		    req.getRequestDispatcher("/vista/PanelRegistrarPublicacion.jsp").forward(req, resp);
		    return;
		}

		// 🔥 APLICAR CAMBIOS
		publicacion.setTitulo(titulo);
		publicacion.setCategoria(categoria);
		publicacion.setNombreDelProducto(nombreProducto);
		publicacion.setPrecio(precio);
		publicacion.setEstado(estado);
		publicacion.setDescripcion(descripcion);
		publicacion.setUsuario(usuario);

		// solo cambiar imagen si llega una nueva
		if (imagenPath != null && !imagenPath.isEmpty()) {
		    publicacion.setImagen(imagenPath);
		}

		boolean ok = dao.actualizar(publicacion);
		if (ok) {
			// Forward al panel emergente con mensaje de éxito (personalizable)
			System.out.println(">>>> INFO:EditarPublicacionController: publicación actualizada, mostrando mensaje de éxito");
			String nombreProdDisplay = (publicacion.getNombreDelProducto() != null && !publicacion.getNombreDelProducto().trim().isEmpty()) ? publicacion.getNombreDelProducto() : publicacion.getTitulo();
			req.setAttribute("titulo", "Publicación actualizada");
			req.setAttribute("mensaje", "Los cambios en la publicación '" + nombreProdDisplay + "' se han guardado correctamente.");
			req.setAttribute("urlCancelar", req.getContextPath() + "/VisualizarCatalogoController?ruta=cargar");
			RequestDispatcher rd = req.getRequestDispatcher("/vista/PanelMensajeEmergente.jsp");
			rd.forward(req, resp);
 		} else {
 			// En caso de error, reenviar al formulario con un mensaje
 			req.setAttribute("error", "No se pudo guardar la publicación");
 			RequestDispatcher rd = req.getRequestDispatcher("/vista/PanelRegistrarPublicacion.jsp");
 			rd.forward(req, resp);
 		}
		
	}
	public void editarPublicacion(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int idPublicacion = Integer.parseInt(req.getParameter("id"));
		PublicacionDAO publicacionDAO = new PublicacionDAO();
		Publicacion publicacion = publicacionDAO.buscarPorId(idPublicacion);
		req.setAttribute("publicacion", publicacion);
		req.setAttribute("modo", "editar");
		// Reenviar a la vista PanelCatalogo.jsp para obtener el formulario de creación
		RequestDispatcher rd = req.getRequestDispatcher("/vista/PanelRegistrarPublicacion.jsp");
		rd.forward(req, resp);
	}
	
}