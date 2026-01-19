<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List,modelo.entidad.Publicacion,jakarta.servlet.jsp.JspWriter" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%!
// Declaración de la función para listar las publicaciones propias con botones de Editar/Eliminar
public void desplegarMisPublicaciones(List<Publicacion> publicaciones, JspWriter out) throws java.io.IOException {
    if (publicaciones == null || publicaciones.isEmpty()) {
        out.write("<p>No tienes publicaciones creadas actualmente.</p>");
        return;
    }
    
    for (Publicacion pub : publicaciones) {
        String nombre = escapeHtml(pub.getNombreDelProducto());
        String imagen = (pub.getImagen() != null) ? escapeHtml(pub.getImagen()) : "imagenes/placeholder.png";
        
        out.write("<div class=\"tarjeta-producto\">");
        out.write("  <div class=\"imagen-container\">");
        out.write("    <img src=\"" + imagen + "\" alt=\"" + nombre + "\">");
        out.write("  </div>");
        out.write("  <div class=\"contenido-tarjeta\">");
        out.write("    <h3 class=\"titulo\">" + nombre + "</h3>");
        out.write("    <p class=\"precio\">$" + pub.getPrecio() + "</p>");
        out.write("    <div class=\"btnsProducto\">");
        // Botón Editar
        out.write("      <a href=\"GestionarPublicacionesController?ruta=editar&id=" + pub.getId() + "\" class=\"btn-accionMisProductos btn-azul\" style=\"text-decoration:none; text-align:center;\">");
        out.write("         <i class=\"fa-solid fa-pen\"></i> Editar</a>");
        // Botón Eliminar
        out.write("      <a href=\"GestionarPublicacionesController?ruta=eliminar&id=" + pub.getId() + "\" class=\"btn-accionMisProductos btn-rojo\" style=\"text-decoration:none; text-align:center;\" onclick=\"return confirm('¿Estás seguro de eliminar esta publicación?')\">");
        out.write("         <i class=\"fa-solid fa-trash\"></i> Eliminar</a>");
        out.write("    </div>");
        out.write("  </div>");
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
    <title>Mis Publicaciones - MarketFlow</title>
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
                <li>
                    <%-- <h3>Hola, ${usuarioLogueado.nombre}</h3> --%>
                    
                    <%-- Cambia esto temporalmente --%>
                    <h3>Hola, ${nombreUsuario}</h3>
                </li>
            </ul>
        </div>
        <a href="LoginController?ruta=salir" class="btn-logout"><i class="fa-solid fa-right-from-bracket"></i> Salir</a>
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
            <div class="cajaMisPublicaciones">
                <h1>Mis Publicaciones</h1>
                    <a href="RegistrarPublicacionController?ruta=crear" class="btn btn-secondary">
                       <i class="fa-solid fa-plus"></i> Nueva Publicación
                    </a>   
            </div>

            <div class="grid-productos">
                <% 
                    List<Publicacion> lista = (List<Publicacion>) request.getAttribute("publicaciones");
                    desplegarMisPublicaciones(lista, out); 
                %>
            </div>
        </div>
    </div>

</body>
</html>