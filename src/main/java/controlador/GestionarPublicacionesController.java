package controlador;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import modelo.Publicacion;
import modelo.dao.PublicacionDAO;

@WebServlet("/misPublicaciones")
public class GestionarPublicacionesController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private PublicacionDAO dao = new PublicacionDAO();

    /**
     * Devuelve la lista de publicaciones del usuario indicado.
     */
    public List<Publicacion> listarMisPublicaciones(String usuario) {
        return dao.obtenerMisPublicaciones(usuario);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String usuario = req.getParameter("usuario");
        if (usuario == null || usuario.isEmpty()) {
            Object sUser = req.getSession().getAttribute("usuario");
            if (sUser != null) usuario = sUser.toString();
        }
        if (usuario == null || usuario.isEmpty()) {
            usuario = "usuarioDemo"; // default user for demo/testing
        }

        List<Publicacion> misPublicaciones = listarMisPublicaciones(usuario);

        // Debug logging: mostrar usuario y tamaño de la lista
        System.out.println("[DEBUG] GestionarPublicacionesController: usuarioParam=" + req.getParameter("usuario") + ", session.usuario=" + req.getSession().getAttribute("usuario") + ", usuarioUsed=" + usuario + ", resultCount=" + (misPublicaciones != null ? misPublicaciones.size() : 0));

        req.setAttribute("misPublicaciones", misPublicaciones);
        req.getRequestDispatcher("/vista/PanelMisPublicaciones.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}