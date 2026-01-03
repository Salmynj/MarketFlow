<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Mis publicaciones - MarketFlow</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/main.css" />
</head>
<body>
<h1>Mis publicaciones</h1>
<p><a href="${pageContext.request.contextPath}/publicaciones">Volver al catálogo</a>
<a class="btn btn-primary" href="${pageContext.request.contextPath}/crearPublicacion">Crear Publicación</a>
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
        <c:forEach var="p" items="${misPublicaciones}">
            <tr>
                <td>${p.titulo}</td>
                <td>${p.nombreDelProducto}</td>
                <td>${p.precio}</td>
                <td>${p.estado}</td>
            </tr>
        </c:forEach>
    </tbody>
</table>
</body>
</html>