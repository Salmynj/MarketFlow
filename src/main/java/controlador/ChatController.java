package controlador;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import modelo.Mensaje;
import modelo.dao.MensajeDAO;

@WebServlet("/chat")
public class ChatController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private MensajeDAO dao = new MensajeDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String usuario = req.getParameter("usuario");
        String otro = req.getParameter("otro");
        if (usuario == null || usuario.isEmpty()) {
            Object sUser = req.getSession().getAttribute("usuario");
            if (sUser != null) usuario = sUser.toString();
        }
        if (usuario == null || usuario.isEmpty()) usuario = "usuarioDemo";
        if (otro == null || otro.isEmpty()) otro = req.getParameter("destinatario");
        if (otro == null || otro.isEmpty()) otro = "vendedor";

        List<Mensaje> conversacion = dao.listarConversacion(usuario, otro);
        req.setAttribute("conversacion", conversacion);
        req.setAttribute("usuario", usuario);
        req.setAttribute("otro", otro);
        req.getRequestDispatcher("/vista/PanelChat.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String remitente = req.getParameter("remitente");
        String destinatario = req.getParameter("destinatario");
        String contenido = req.getParameter("contenido");
        String fecha = java.time.LocalDateTime.now().toString();

        if (remitente == null || remitente.isEmpty()) remitente = "anonimo";
        if (destinatario == null || destinatario.isEmpty()) destinatario = "vendedor";
        if (contenido == null) contenido = "";

        Mensaje m = new Mensaje(remitente, destinatario, contenido, fecha);
        boolean ok = new ContactarVendedorController().enviarMensaje(m);
        if (ok) {
            // redirigir al chat entre remitente y destinatario
            resp.sendRedirect(req.getContextPath() + "/chat?usuario=" + remitente + "&otro=" + destinatario);
        } else {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "No se pudo enviar el mensaje");
        }
    }
}
