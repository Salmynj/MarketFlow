package controlador;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import modelo.Mensaje;
import modelo.dao.MensajeDAO;

@WebServlet("/mensaje")
public class ContactarVendedorController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private MensajeDAO dao = new MensajeDAO();

    public void contactarVendedor() {
        // placeholder preserved
    }

    public boolean enviarMensaje(Mensaje mensaje) {
        try {
            dao.registrarMensaje(mensaje);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String remitente = req.getParameter("remitente");
        String destinatario = req.getParameter("destinatario");
        String contenido = req.getParameter("contenido");
        String fecha = java.time.LocalDateTime.now().toString();

        if (remitente == null) remitente = "anonimo";
        if (destinatario == null) destinatario = "vendedor";
        if (contenido == null) contenido = "";

        Mensaje m = new Mensaje(remitente, destinatario, contenido, fecha);
        boolean ok = enviarMensaje(m);
        if (ok) {
            resp.sendRedirect(req.getContextPath() + "/publicaciones");
        } else {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "No se pudo enviar el mensaje");
        }
    }
}