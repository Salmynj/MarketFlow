<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Mensaje - MarketFlow</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/main.css" />
<style>
/* Pequeños estilos para el modal */
.modal-overlay {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0,0,0,0.5);
    display: flex;
    align-items: center;
    justify-content: center;
    z-index: 1000;
}
.modal {
    background: #fff;
    padding: 20px;
    border-radius: 6px;
    max-width: 500px;
    width: 90%;
    box-shadow: 0 2px 10px rgba(0,0,0,0.3);
}
.modal h2 { margin-top: 0; }
.modal .actions { text-align: right; margin-top: 15px; }
.modal .btn { padding: 8px 12px; border: none; border-radius: 4px; cursor: pointer; }
.modal .btn-primary { background: #28a745; color: white; }
.modal .btn-secondary { background: #6c757d; color: white; margin-right: 8px; }
</style>
</head>
<body>

<%!
    public void mostrarMensaje(
            String mensaje,
            jakarta.servlet.jsp.JspWriter out) throws java.io.IOException {

        out.println("<h1>Resultado</h1>");
        out.println("<p>" + mensaje + "</p>");
        out.println("<p>");
        out.println("<a href='publicaciones'>Volver al catálogo</a>");
        out.println("</p>");
    }
%>

<%
    String msg = (String) request.getAttribute("msg");
    String redirectUrl = (String) request.getAttribute("redirectUrl");
    if (msg != null && !msg.isBlank()) {
        // Mostrar modal emergente
%>
    <div id="mensajeModal" class="modal-overlay">
        <div class="modal">
            <h2>¡Éxito!</h2>
            <p><%= msg %></p>
            <div class="actions">
                <button class="btn btn-secondary" id="cerrarBtn">Cerrar</button>
                <button class="btn btn-primary" id="irBtn">Ver mis publicaciones</button>
            </div>
        </div>
    </div>
    <script>
        (function(){
            var modal = document.getElementById('mensajeModal');
            var cerrar = document.getElementById('cerrarBtn');
            var ir = document.getElementById('irBtn');
            // Cerrar modal
            cerrar.addEventListener('click', function(){
                modal.style.display = 'none';
                // si existe redirectUrl, redirigir al cerrar opcionalmente
                <% if (redirectUrl != null && !redirectUrl.isBlank()) { %>
                    window.location.href = '<%= redirectUrl %>';
                <% } %>
            });
            // Ir directamente a mis publicaciones
            ir.addEventListener('click', function(){
                <% if (redirectUrl != null && !redirectUrl.isBlank()) { %>
                    window.location.href = '<%= redirectUrl %>';
                <% } else { %>
                    modal.style.display = 'none';
                <% } %>
            });
            // Auto-redirect después de 2.5 segundos si hay redirectUrl
            <% if (redirectUrl != null && !redirectUrl.isBlank()) { %>
                setTimeout(function(){
                    window.location.href = '<%= redirectUrl %>';
                }, 2500);
            <% } %>
        })();
    </script>
<%
    } else {
        // Si no hay mensaje, mostrar el contenido simple
        mostrarMensaje(msg, out);
    }
%>

</body>
</html>