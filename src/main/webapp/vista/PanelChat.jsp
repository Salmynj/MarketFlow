<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List,modelo.entidad.Mensaje,jakarta.servlet.jsp.JspWriter" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%!
// Función para desplegar la conversación dinámica
public void desplegarInterfaz(List<Mensaje> mensajes, String usuarioActual, JspWriter out) throws java.io.IOException {
    if (mensajes == null || mensajes.isEmpty()) {
        out.write("<div class='chat-mensajes-fecha'><small>No hay mensajes en esta conversación</small></div>");
        return;
    }

    for (Mensaje m : mensajes) {
        // Determinamos la clase CSS basándonos en quién envió el mensaje
        String claseMensaje = m.getRemitente().equals(usuarioActual) ? "enviado" : "recibido";
        
        out.write("<div class='message " + claseMensaje + "'>");
        out.write(escapeHtml(m.getContenido()));
        out.write("  <div class='message-time'>" + m.getFechaDeEnvio() + "</div>");
        out.write("</div>");
    }
}

private String escapeHtml(String s) {
    if (s == null) return "";
    return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;").replace("'", "&#x27;");
}
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Mensajes - MarketFlow</title>
    <link rel="stylesheet" href="<c:url value='/assets/css/styles2.css'/>">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>

    <header>
        <div class="cajaLogo">
            <i class="fa-solid fa-handshake fa-2x"></i>
            <h2>MarketFlow</h2>
        </div>
        <div class="cajaBarraBusqueda"><input type="text" placeholder="🔍 Buscar productos..."></div>
        <div class="cajaPerfil">
            <ul>
                <li><img src="imagenes/fotoAdmin.png"></li>
                <li><h3>Hola, ${nombreUsuario}</h3></li>
            </ul>
        </div>
        <a href="LoginController?ruta=salir" class="btn-logout"><i class="fa-solid fa-right-from-bracket"></i> Salir</a>
    </header>

    <div class="contenedor-principal">
        <aside>
            <ul class="flexColumnaAside listaSinEstilo">
                <li><a href="VisualizarCatalogoController?ruta=cargar"><i class="fa-solid fa-store"></i> Catálogo</a></li>
                <li><a href="GestionarPublicacionesController?ruta=listar"><i class="fa-solid fa-box-open"></i> Mis Publicaciones</a></li>
                <li class="activo"><a href="ContactarVendedorController?ruta=contactar"><i class="fa-solid fa-comments"></i> Mensajes</a></li>
            </ul>
        </aside>

        <div class="cajaContenidoPrincipal">
            <div class="chat-container">
                <div class="chat-sidebar">
                    <div class="chats">Conversaciones</div>
                    <div class="chat-item activo">
                        <img src="https://i.pravatar.cc/150?img=45" class="chat-avatar">
                        <div>
                            <b>Soporte / Comprador</b>
                            <small class="letras-Chat-activo">Chat activo</small>
                        </div>
                    </div>
                </div>

                <div class="chat-main">
                    <div class="chat-header">
                        <img src="https://i.pravatar.cc/150?img=45" class="chat-avatar">
                        <div>
                            <div>Chat con ${destinatarioActual}</div>
                            <small class="letras-Chat-Linea">En línea</small>
                        </div>
                        <i class="fa-solid fa-ellipsis-vertical ellipsis-vertical"></i>
                    </div>
                    
                    <div class="chat-messages">
                        <div class="chat-mensajes-fecha"><small>Hoy</small></div>
                        
                        <%-- LLAMADA A LA FUNCIÓN LÓGICA --%>
                        <% 
                            List<Mensaje> listaMsg = (List<Mensaje>) request.getAttribute("listaMensajes");
                            String yo = (String) request.getAttribute("nombreUsuario");
                            desplegarInterfaz(listaMsg, yo, out); 
                        %>
                    </div>

                    <form action="ContactarVendedorController?ruta=enviar" method="post" class="chat-input-area">
                        <input type="hidden" name="destinatario" value="${destinatarioActual}">
                        <i class="fa-regular fa-face-smile cara-sonriente"></i>
                        <i class="fa-solid fa-paperclip clip-papel"></i>
                        <textarea name="txtMensaje" placeholder="Escribe un mensaje..."required></textarea>
                        <button type="submit" class="btn btn-primary">
                            <i class="fa-solid fa-paper-plane avion-papel"></i>
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </div>
    
    <script>
    // Función para bajar el scroll automáticamente
    const chatMessages = document.querySelector('.chat-messages');
    if (chatMessages) {
        chatMessages.scrollTop = chatMessages.scrollHeight;
    }
      </script>
</body>
</html>