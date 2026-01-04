<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="modelo.Categoria, modelo.Estado"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Registrar publicación - MarketFlow</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/main.css" />
</head>
<body>

<h1>Registrar publicación</h1>

<%!
    public void desplegarFormulario(jakarta.servlet.jsp.JspWriter out) throws java.io.IOException {

        out.println("<form action='crearPublicacion' method='post'>");

        out.println("<label>Título:");
        out.println("<input type='text' name='titulo' required/>");
        out.println("</label><br/>");

        out.println("<label>Nombre del producto:");
        out.println("<input type='text' name='nombreDelProducto' required/>");
        out.println("</label><br/>");

        out.println("<label>Categoría:");
        out.println("<select name='categoria'>");
        for (Categoria c : Categoria.values()) {
            out.println("<option value='" + c.name() + "'>" + c.name() + "</option>");
        }
        out.println("</select>");
        out.println("</label><br/>");

        out.println("<label>Precio:");
        out.println("<input type='number' step='0.01' name='precio' required/>");
        out.println("</label><br/>");

        out.println("<label>Estado:");
        out.println("<select name='estado'>");
        for (Estado e : Estado.values()) {
            out.println("<option value='" + e.name() + "'>" + e.name() + "</option>");
        }
        out.println("</select>");
        out.println("</label><br/>");

        out.println("<label>Descripción:<br/>");
        out.println("<textarea name='descripcion' rows='4' cols='50'></textarea>");
        out.println("</label><br/>");

        out.println("<label>Imagen (URL):");
        out.println("<input type='text' name='imagen'/>");
        out.println("</label><br/>");

        out.println("<label>Usuario:");
        out.println("<input type='text' name='usuario' required/>");
        out.println("</label><br/>");

        out.println("<button type='submit'>Guardar</button>");
        out.println("</form>");
    }
%>

<%
    desplegarFormulario(out);
%>

<p>
    <a href="${pageContext.request.contextPath}/publicaciones">Volver al catálogo</a>
</p>

</body>
</html>
