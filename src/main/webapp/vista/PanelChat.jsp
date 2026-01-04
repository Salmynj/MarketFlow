<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.List, modelo.Mensaje"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Chat - MarketFlow</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/main.css" />
</head>
<body>

<%!
    public void desplegarInterfaz(
            String usuario,
            String otro,
            List<Mensaje> conversacion,
            jakarta.servlet.jsp.JspWriter out) throws java.io.IOException {

        out.println("<h1>Chat: " + usuario + " ↔ " + otro + "</h1>");

        out.println("<p>");
        out.println("<a href='publicaciones'>Volver al catálogo</a>");
        out.println("</p>");

        out.println("<div id='chat'>");

        if (conversacion == null || conversacion.isEmpty()) {
            out.println("<p>No hay mensajes aún</p>");
        } else {
            for (Mensaje m : conversacion) {
                out.println("<div class='mensaje'>");
                out.println("<strong>" + m.getRemitente() + ":</strong> ");
                out.println(m.getContenido());
                out.println(" <em>(" + m.getFechaDeEnvio() + ")</em>");
                out.println("</div>");
            }
        }

        out.println("</div>");
        out.println("<hr/>");

        // Formulario del chat
        out.println("<form action='chat' method='post'>");
        out.println("<input type='hidden' name='remitente' value='" + usuario + "' />");
        out.println("<input type='hidden' name='destinatario' value='" + otro + "' />");
        out.println("<textarea name='contenido' rows='3' cols='50'></textarea><br/>");
        out.println("<button type='submit'>Enviar</button>");
        out.println("</form>");

    }
%>

<%
    String usuario = (String) request.getAttribute("usuario");
    String otro = (String) request.getAttribute("otro");
    List<Mensaje> conversacion =
            (List<Mensaje>) request.getAttribute("conversacion");

    desplegarInterfaz(usuario, otro, conversacion, out);
%>

</body>
</html>
