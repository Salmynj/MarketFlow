<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="modelo.entidad.Publicacion" %>
<%!
    // Helper para escapar HTML en la vista
    private String esc(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
%>
<%
    Publicacion p = (Publicacion) request.getAttribute("publicacion");
    if (p == null) {
        response.sendRedirect(request.getContextPath() + "/VisualizarCatalogoController?ruta=cargar");
        return;
    }

    String ctx = request.getContextPath();

    String titulo = esc(p.getTitulo());
    String producto = esc(p.getNombreDelProducto());
    String categoria = esc(p.getCategoria());
    String estado = (p.getEstado() != null) ? esc(p.getEstado().name()) : "";
    String desc = esc(p.getDescripcion());
    String usuario = esc(p.getUsuario());
    String img = esc(p.getImagen());
    String precio = String.format("%.2f", p.getPrecio());

    // calcular estado legible y seguro (evita substring en cadena vacía)
    String displayEstado = "";
    if (p.getEstado() != null) {
        String s = p.getEstado().name();
        displayEstado = s.substring(0,1).toUpperCase() + (s.length()>1 ? s.substring(1).toLowerCase() : "");
    }

    // comprobar permiso de edición: atributo request "permitirEditar" o sesión usuarioLogueado == propietario
    boolean permitirEditar = false;
    Object permitirAttr = request.getAttribute("permitirEditar");
    if (permitirAttr != null && Boolean.TRUE.equals(permitirAttr)) {
        permitirEditar = true;
    } else {
        Object u = request.getSession().getAttribute("usuarioLogueado");
        if (u != null && u.toString().equals(p.getUsuario())) permitirEditar = true;
    }

    // Usuario param codificado para URL
    String usuarioParam = "";
    try { usuarioParam = java.net.URLEncoder.encode(p.getUsuario() == null ? "" : p.getUsuario(), "UTF-8"); } catch (Exception e) { usuarioParam = p.getUsuario() == null ? "" : p.getUsuario(); }
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><%= titulo %> — MarketFlow</title>
<link rel="stylesheet" href="<%= ctx %>/assets/css/styles2.css">
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
                <li><img src="<%= ctx %>/assets/img/fotoAdmin.png" alt="Usuario"></li>
                <li>
                    <h3>Hola, <%= esc(request.getSession().getAttribute("usuarioLogueado") == null ? "Invitado" : request.getSession().getAttribute("usuarioLogueado").toString()) %></h3>
                </li>
            </ul>
        </div>
        <a href="<%= ctx %>/login.html" class="btn-logout"><i class="fa-solid fa-right-from-bracket"></i> Salir</a>
    </header>

    <div class="contenedor-principal">
        <aside>
            <ul class="flexColumnaAside listaSinEstilo">
                <li><a href="<%= ctx %>/VisualizarCatalogoController?ruta=cargar"><i class="fa-solid fa-store"></i> Catálogo</a></li>
                <li><a href="<%= ctx %>/GestionarPublicacionesController?ruta=listar"><i class="fa-solid fa-box-open"></i> Mis Publicaciones</a>
                </li>
                <li><a href="<%= ctx %>/ContactarVendedorController?ruta=contactar"><i class="fa-solid fa-comments"></i> Mensajes</a></li>
            </ul>
        </aside>

        <div class="cajaContenidoPrincipal">
            <a class="btn-volver btn" href="<%= ctx %>/VisualizarCatalogoController?ruta=cargar">
                <i class="fa-solid fa-arrow-left"></i> Volver al Catálogo
            </a>

            <div class="detalle-flex">
                <div class="detalle-imagen">
                    <% if (img != null && !img.isEmpty()) { %>
                        <img src="<%= img %>" alt="Imagen de <%= titulo %>">
                    <% } else { %>
                        <img src="<%= ctx %>/assets/img/placeholder-380x260.png" alt="Sin imagen">
                    <% } %>
                </div>
                <div class="detalle-info">
                    <div class="detalle-header">
                        <h1 class="detalle-titulo"><%= titulo %></h1>
                        <i class="fa-regular fa-heart fa-2x btn-favorito" title="Añadir a favoritos"></i>
                    </div>
                    <%-- Mostrar el nombre comercial del producto si existe --%>
                    <% if (producto != null && !producto.isEmpty()) { %>
                        <h2 class="detalle-subtitulo" style="margin-top:6px; color:#555; font-weight:500;"><%= producto %></h2>
                    <% } %>
                    <p class="detalle-precio">$<%= precio %></p>

                    <div class="detalle-meta">
                        <span><i class="fa-solid fa-box"></i> Estado: <strong><%= displayEstado %></strong></span>
                        <span><i class="fa-solid fa-tag"></i> Categoría: <strong><%= categoria %></strong></span>
                    </div>

                    <div class="detalle-descripcion">
                        <h3>Descripción</h3>
                        <p style="white-space:pre-wrap;"><%= desc %></p>
                    </div>

                    <div class="vendedor-info">
                        <img src="https://i.pravatar.cc/150?u=<%= usuarioParam %>" class="vendedor-avatar" alt="Vendedor">
                        <div class="vendedor-datos">
                            <h4>Vendido por: <%= usuario %></h4>
                            <small><i class="fa-solid fa-star icono-estrella"></i> 5.0 (45 ventas)</small>
                        </div>
                        <div style="margin-left:auto; display:flex; gap:8px; align-items:center;">
                            <a href="<%= ctx %>/ContactarVendedorController?ruta=contactar&usuario=<%= usuarioParam %>" class="btn btn-primary btn-contactar"><i class="fa-solid fa-comment"></i> Contactar Vendedor</a>
                            <% if (permitirEditar) { %>
                                <a href="<%= ctx %>/EditarPublicacionController?ruta=editar&id=<%= p.getId() %>" class="btn btn-edit"><i class="fa-solid fa-pen-to-square"></i> Editar</a>
                                <a href="<%= ctx %>/EliminarPublicacionController?ruta=eliminar&idPublicacion=<%= p.getId() %>" class="btn btn-danger"><i class="fa-solid fa-trash"></i> Eliminar</a>
                            <% } %>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>