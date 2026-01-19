<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ page import="java.util.List,modelo.entidad.Publicacion,jakarta.servlet.jsp.JspWriter" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%!

// Declaración de la función que renderiza las publicaciones en HTML
public void desplegarPublicaciones(List<Publicacion> publicaciones, JspWriter out) throws java.io.IOException {
    if (publicaciones == null || publicaciones.isEmpty()) {
        out.write("<p>No hay publicaciones disponibles.</p>");
        return;
    }
    for (Publicacion pub : publicaciones) {
        String estado = (pub.getEstado() != null) ? pub.getEstado().name() : "";
        String estadoClass = "dispo";
        if ("VENDIDO".equals(estado)) {
            estadoClass = "vendido";
        }
        String nombre = escapeHtml(pub.getNombreDelProducto());
        String imagen = (pub.getImagen() != null) ? escapeHtml(pub.getImagen()) : "";
        out.write("<div class=\"tarjeta-producto\">\n");
        out.write("  <div class=\"imagen-container\">\n");
        out.write("    <img src=\"" + imagen + "\" alt=\"" + nombre + "\">\n");
        out.write("    <span class=\"etiqueta-estado " + estadoClass + "\">" + estado + "</span>\n");
        out.write("    <i class=\"fa-regular fa-heart icon-fav\"></i>\n");
        out.write("  </div>\n");
        out.write("  <div class=\"contenido-tarjeta\">\n");
        out.write("    <h3 class=\"titulo\">" + nombre + "</h3>\n");
        out.write("    <p class=\"precio\">$" + pub.getPrecio() + "</p>\n");
        if ("VENDIDO".equals(estado)) {
            out.write("    <button class=\"btn-accion btn-gris btn-block\" disabled>Vendido</button>\n");
        } else {
            out.write("    <a href=\"product-detail.html?id=" + pub.getId() + "\" class=\"btn-accion btn-azul btn-block\">Ver Producto</a>\n");
        }
        out.write("  </div>\n");
        out.write("</div>\n");
    }
}

// Helper básico para escapar texto y evitar inyección de HTML
private String escapeHtml(String s) {
    if (s == null) return "";
    return s.replace("&", "&amp;")
            .replace("<", "&lt;")
            .replace(">", "&gt;")
            .replace("\"", "&quot;")
            .replace("'", "&#x27;");
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>PanelCatalogo</title>
<link rel="stylesheet" href="<c:url value='/assets/css/styles2.css'/>">

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
    <header>
        <div class="cajaLogo">
            <i class="fa-solid fa-handshake fa-2x"></i>
            <h2>MarketFlow</h2>
        </div>
        <div class="cajaBarraBusqueda">
            <input type="text" placeholder="🔍 Buscar productos, marcas...">
        </div>
        
        <div class="cajaLoginRegistro">
            <a href="login.html" class="btn-login">Iniciar Sesión</a>
            <a href="register.html" class="btn-registro">Registrarse</a>
        </div>
    </header>

    <div class="contenedor-principal">
        <aside>
            <ul class="flexColumnaAside listaSinEstilo">
                <li><a href="VisualizarCatalogoController?ruta=cargar"><i class="fa-solid fa-store"></i> Catálogo</a></li>
                <li class="activo"><a href="GestionarPublicacionesController?ruta=listar"><i class="fa-solid fa-box-open"></i> Mis Publicaciones</a></li>
                <li><a href="ContactarVendedorController?ruta=contactar"><i class="fa-solid fa-comments"></i> Mensajes</a></li>
            </ul>
        </aside>

        <div class="cajaContenidoPrincipal">
            <h1>Catálogo de Productos</h1>

           <div class="filtros-container">
    
    <div class="caja-filtro">
        <select class="select-boton estilo-azul">
            <option selected disabled>Categoría</option>
            <option>Electrónica</option>
            <option>Hogar</option>
            <option>Deportes</option>
        </select>
        <i class="fa-solid fa-chevron-down icono-flecha texto-blanco"></i>
    </div>

    <div class="caja-filtro">
        <select class="select-boton">
            <option selected disabled>Rango de Precio</option>
            <option>$0 - $50</option>
            <option>$50 - $100</option>
            <option>+$100</option>
        </select>
        <i class="fa-solid fa-chevron-down icono-flecha"></i>
    </div>

    <div class="caja-filtro">
        <select class="select-boton">
            <option selected disabled>Estado</option>
            <option>Nuevo</option>
            <option>Usado</option>
        </select>
        <i class="fa-solid fa-chevron-down icono-flecha"></i>
    </div>

    <button class="btn btn-secondary btn-pill ml-auto">Aplicar Filtros</button>

    </div>

            <div class="grid-productos">
                
                <!-- Llamada a la función que imprime las tarjetas -->
                <% desplegarPublicaciones((List<Publicacion>) request.getAttribute("publicaciones"), out); %>

            </div>
        </div>
    </div>
</body>
</html>