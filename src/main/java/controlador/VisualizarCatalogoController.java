package controlador;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import modelo.Publicacion;
import modelo.Categoria;
import modelo.Estado;

import modelo.dao.PublicacionDAO;

@WebServlet("/publicaciones")
public class VisualizarCatalogoController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private PublicacionDAO dao = new PublicacionDAO();
    private RegistrarPublicacionController regCtrl = new RegistrarPublicacionController();

    // Mantener API existente
    public List<Publicacion> listarPublicaciones() {
        return dao.obtenerPublicaciones();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("publicaciones", listarPublicaciones());
        req.getRequestDispatcher("/vista/PanelCatalogo.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Maneja creación de nueva publicación (reusa el controller de registro)
        String titulo = req.getParameter("titulo");
        Categoria categoria = Categoria.valueOf(req.getParameter("categoria"));
        String nombre = req.getParameter("nombreDelProducto");
        double precio = 0.0;
        try {
            String precioParam = req.getParameter("precio");
            if (precioParam != null && !precioParam.isEmpty()) precio = Double.parseDouble(precioParam);
        } catch (Exception e) {
            precio = 0.0;
        }
        Estado estado = Estado.valueOf(req.getParameter("estado"));
        String descripcion = req.getParameter("descripcion");
        String imagen = req.getParameter("imagen");
        String usuario = req.getParameter("usuario");

        boolean ok = regCtrl.guardarNuevaPublicacion(titulo, categoria, nombre, precio, estado, descripcion, imagen, usuario);
        if (ok) {
            resp.sendRedirect(req.getContextPath() + "/publicaciones");
        } else {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "No se pudo guardar la publicación");
        }
    }
}