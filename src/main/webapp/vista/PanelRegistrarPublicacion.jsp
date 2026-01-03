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
<form action="${pageContext.request.contextPath}/crearPublicacion" method="post">
    <label>Título: <input type="text" name="titulo" required/></label><br/>
    <label>Nombre del producto: <input type="text" name="nombreDelProducto" required/></label><br/>
    <label>Categoría:
        <select name="categoria">
            <% for (Categoria c : Categoria.values()) { %>
                <option value="<%= c.name() %>"><%= c.name() %></option>
            <% } %>
        </select>
    </label><br/>
    <label>Precio: <input type="number" step="0.01" name="precio" required/></label><br/>
    <label>Estado:
        <select name="estado">
            <% for (Estado e : Estado.values()) { %>
                <option value="<%= e.name() %>"><%= e.name() %></option>
            <% } %>
        </select>
    </label><br/>
    <label>Descripción:<br/>
        <textarea name="descripcion" rows="4" cols="50"></textarea>
    </label><br/>
    <label>Imagen (URL): <input type="text" name="imagen"/></label><br/>
    <label>Usuario: <input type="text" name="usuario" required/></label><br/>
    <button type="submit">Guardar</button>
</form>
<p><a href="${pageContext.request.contextPath}/publicaciones">Volver al catálogo</a></p>
</body>
</html>