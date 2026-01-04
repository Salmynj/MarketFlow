<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="modelo.Publicacion, java.util.List"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Catálogo - MarketFlow</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/main.css" />
</head>
<body>

<h1>Catálogo</h1>

<p>
    <a href="index.jsp">Inicio</a> |
    <a href="misPublicaciones">Mis publicaciones</a>
</p>

<%!
    public void desplegarPublicaciones(
            List<Publicacion> publicaciones,
            String sessionUser,
            jakarta.servlet.jsp.JspWriter out) throws java.io.IOException {

	
        if (publicaciones == null || publicaciones.isEmpty()) {
            out.println("<div class='no-items'>No hay publicaciones disponibles.</div>");
            return;
        }

        out.println("<div class='cards-row' id='cardsContainer'>");

        for (Publicacion p : publicaciones) {

            out.println("<div class='card'>");

            // Imagen
            if (p.getImagen() != null && !p.getImagen().isEmpty()) {
                out.println("<img class='card-image' src='" + p.getImagen() +
                            "' alt='" + p.getTitulo() + "' />");
            } else {
                out.println("<img class='card-image' src='styles/placeholder.png' alt='placeholder' />");
            }

            out.println("<div class='card-body'>");

            out.println("<h3 class='card-title'>" + p.getTitulo() + "</h3>");
            out.println("<div class='card-sub'>" + p.getNombreDelProducto() + "</div>");
            out.println("<div class='card-sub'>" +
                        p.getCategoria() + " • " + p.getEstado() +
                        "</div>");

            out.println("<div class='card-meta'>");
            out.println("<div class='price'>$" + p.getPrecio() + "</div>");

            out.println("<div class='actions'>");
            out.println("<a class='btn btn-primary' href='chat?usuario=" +
                    sessionUser + "&otro=" + p.getUsuario() +
                    "'>Contactar</a>");

            out.println("<a class='btn btn-ghost' href='#'>Ver</a>");
            out.println("</div>");

            out.println("</div>"); // card-meta
            out.println("</div>"); // card-body
            out.println("</div>"); // card
        }

        out.println("</div>"); // cards-row
    }
%>

<%
    List<Publicacion> publicaciones =
            (List<Publicacion>) request.getAttribute("publicaciones");

    String sessionUser = (String) session.getAttribute("usuario");
    if (sessionUser == null) {
        sessionUser = "usuarioDemo";
    }

    desplegarPublicaciones(publicaciones, sessionUser, out);
%>


</body>
</html>
