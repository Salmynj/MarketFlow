package controlador;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import modelo.Categoria;
import modelo.Estado;
import modelo.Publicacion;
import modelo.dao.PublicacionDAO;

@WebServlet("/crearPublicacion")
public class RegistrarPublicacionController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private PublicacionDAO dao = new PublicacionDAO();

    public boolean guardarNuevaPublicacion(String titulo, Categoria categoria, String nombreDelProducto, double precio, Estado estado,
            String descripcion, String imagen, String usuario) {
        Publicacion p = new Publicacion(titulo, categoria, nombreDelProducto, precio, estado, descripcion, imagen, usuario);
        try {
            dao.guardarPublicacion(p);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ahora recibe request/response y realiza forward al JSP de registro
    public void crearPublicacion(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/vista/PanelRegistrarPublicacion.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Invocar el método que muestra el panel de registro
        crearPublicacion(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String titulo = req.getParameter("titulo");
        String categoriaS = req.getParameter("categoria");
        String nombre = req.getParameter("nombreDelProducto");
        String precioS = req.getParameter("precio");
        String estadoS = req.getParameter("estado");
        String descripcion = req.getParameter("descripcion");
        String imagen = req.getParameter("imagen");
        String usuario = req.getParameter("usuario");

        if (titulo == null || titulo.isBlank() || nombre == null || nombre.isBlank() || usuario == null || usuario.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Faltan datos requeridos");
            return;
        }

        double precio = 0.0;
        try {
            if (precioS != null && !precioS.isBlank()) precio = Double.parseDouble(precioS);
        } catch (NumberFormatException ex) {
            precio = 0.0;
        }

        Categoria categoria = null;
        Estado estado = null;
        try {
            if (categoriaS != null) categoria = Categoria.valueOf(categoriaS);
            if (estadoS != null) estado = Estado.valueOf(estadoS);
        } catch (IllegalArgumentException ex) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Categoria o estado inválido");
            return;
        }

        boolean ok = guardarNuevaPublicacion(titulo, categoria, nombre, precio, estado, descripcion, imagen, usuario);
        if (ok) {
            // Guardar el usuario en sesión para que /misPublicaciones lo lea automáticamente
            if (usuario != null && !usuario.isBlank()) {
                req.getSession().setAttribute("usuario", usuario);
            }
            resp.sendRedirect(req.getContextPath() + "/misPublicaciones?usuario=" + java.net.URLEncoder.encode(usuario, "UTF-8"));
        } else {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "No se pudo guardar la publicación");
        }
    }

}