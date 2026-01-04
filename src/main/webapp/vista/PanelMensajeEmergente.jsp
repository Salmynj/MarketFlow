<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Mensaje - MarketFlow</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/main.css" />
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
    mostrarMensaje(msg, out);
%>

</body>
</html>
