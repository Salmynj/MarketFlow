package controlador;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.DAOs.MensajeDAO;
import modelo.entidad.Mensaje;

@WebServlet("/ContactarVendedorController")

public class ContactarVendedorController extends HttpServlet {
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
		String ruta = (req.getParameter("ruta") == null) ? "contactar" : req.getParameter("ruta");

		switch (ruta) {
		case "contactar":
			this.contactarVendedor(req, resp);
			break;
		case "enviar":
			this.enviarMensaje(req, resp);
			break;
		}		
	}
	
	public void contactarVendedor(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {    
        // 1. Datos quemados para la prueba (Luego vendrán de la sesión y del catálogo)
        String remitente = "Admin Temporal"; 
        String destinatario = req.getParameter("usuario") != null ? req.getParameter("usuario") : "TechMaster Store";

        // 2. Obtener la lista de mensajes de la base de datos
        List<Mensaje> conversacion = mensajeDAO.listarConversacion(remitente, destinatario);

        // 3. Pasar datos al JSP (PanelChat.jsp los necesita para desplegarInterfaz)
        req.setAttribute("listaMensajes", conversacion);
        req.setAttribute("nombreUsuario", remitente);
        req.setAttribute("destinatarioActual", destinatario);

        req.getRequestDispatcher("/vista/PanelChat.jsp").forward(req, resp);
    }
	
	public void enviarMensaje(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. Capturar datos del formulario
        String contenido = req.getParameter("txtMensaje"); // Coincide con el 'name' del textarea
        String remitente = "Admin Temporal"; 
        String destinatario = req.getParameter("destinatario");

        if (contenido != null && !contenido.trim().isEmpty()) {
            // 2. Crear el objeto Mensaje
            Mensaje nuevoMensaje = new Mensaje();
            nuevoMensaje.setContenido(contenido);
            nuevoMensaje.setRemitente(remitente);
            nuevoMensaje.setDestinatario(destinatario);
            
            // Generar fecha actual
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
            nuevoMensaje.setFechaDeEnvio(dtf.format(LocalDateTime.now()));

            // 3. Guardar en la BD
            mensajeDAO.registrarMensaje(nuevoMensaje);
        }
        
        // 4. Recargar el chat para ver el nuevo mensaje
        // Redirigimos a la ruta 'contactar' para que refresque la lista
        resp.sendRedirect("ContactarVendedorController?ruta=contactar&usuario=" + destinatario);
    }
	
}
