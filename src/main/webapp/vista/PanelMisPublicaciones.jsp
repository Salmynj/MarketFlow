<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="modelo.Publicacion, java.util.List"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Mis publicaciones - MarketFlow</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/main.css" />
</head>
<body>

<h1>Mis publicaciones</h1>

<p>
    <a href="${pageContext.request.contextPath}/publicaciones">Volver al catálogo</a>
    <a class="btn btn-primary" href="${pageContext.request.contextPath}/crearPublicacion">
        Crear Publicación
    </a>
</p>

<table>
    <thead>
        <tr>
            <th>Título</th>
            <th>Producto</th>
            <th>Precio</th>
            <th>Estado</th>
        </tr>
    </thead>
    <tbody>

<%!
    public void desplegarMisPublicaciones(
    		List<Publicacion> misPublicaciones,
            jakarta.servlet.jsp.JspWriter out) throws java.io.IOException {

        if (misPublicaciones == null || misPublicaciones.isEmpty()) {
            out.println("<tr><td colspan='4'>No tienes publicaciones</td></tr>");
            return;
        }

        for (Publicacion p : misPublicaciones) {
            out.println("<tr>");
            out.println("<td>" + p.getTitulo() + "</td>");
            out.println("<td>" + p.getNombreDelProducto() + "</td>");
            out.println("<td>" + p.getPrecio() + "</td>");
            out.println("<td>" + p.getEstado() + "</td>");
            out.println("</tr>");
        }
    }
%>

<%
   List<Publicacion> misPublicaciones =
    (List<Publicacion>) request.getAttribute("publicaciones");

    desplegarMisPublicaciones(misPublicaciones, out);
%>

    </tbody>
</table>

</body>
</html>
