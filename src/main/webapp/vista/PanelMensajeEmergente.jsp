<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%!
    // Helper de escape declarado en bloque de declaración para evitar problemas del validador JSP
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
    String ctx = request.getContextPath();

    String titulo = (String) request.getAttribute("titulo");
    String mensaje = (String) request.getAttribute("mensaje");
    String urlConfirmar = (String) request.getAttribute("urlConfirmar");
    String urlCancelar = (String) request.getAttribute("urlCancelar");

    if (titulo == null) titulo = "Mensaje";
    if (mensaje == null) mensaje = "";
    if (urlCancelar == null) urlCancelar = ctx + "/VisualizarCatalogoController?ruta=cargar";

    // Normalizar URLs relativas para que incluyan el contextPath
    if (urlConfirmar != null && !urlConfirmar.trim().isEmpty()) {
        if (!urlConfirmar.startsWith("http://") && !urlConfirmar.startsWith("https://") && !urlConfirmar.startsWith(ctx)) {
            urlConfirmar = ctx + (urlConfirmar.startsWith("/") ? "" : "/") + urlConfirmar;
        }
    }
    if (urlCancelar != null && !urlCancelar.trim().isEmpty()) {
        if (!urlCancelar.startsWith("http://") && !urlCancelar.startsWith("https://") && !urlCancelar.startsWith(ctx)) {
            urlCancelar = ctx + (urlCancelar.startsWith("/") ? "" : "/") + urlCancelar;
        }
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><%= esc(titulo) %></title>
    <link rel="stylesheet" href="<%= ctx %>/assets/css/styles2.css">
    <style>
        .modal-wrap{
            max-width: 720px;
            margin: 60px auto;
            padding: 20px;
        }
        .modal-card{
            background:#fff;
            border-radius:14px;
            padding: 24px;
            box-shadow: 0 2px 14px rgba(0,0,0,0.10);
        }
        .modal-card h2{ margin:0 0 10px 0; }
        .modal-actions{ margin-top: 16px; display:flex; gap:10px; flex-wrap:wrap; }
        .btn{ display:inline-block; padding:10px 14px; border-radius:10px; text-decoration:none; border:1px solid #ccc; }
        .btn-danger{ background:#dc2626; color:#fff; border:none; }
        .btn-secondary{ background:#f3f4f6; color:#111827; border:1px solid #e5e7eb; }
    </style>
</head>
<body>
<div class="modal-wrap">
    <div class="modal-card">
        <h2><%= esc(titulo) %></h2>
        <div style="white-space: pre-wrap;"><%= esc(mensaje) %></div>

        <div class="modal-actions">
            <% if (urlConfirmar != null && !urlConfirmar.trim().isEmpty()) { %>
                <a class="btn btn-danger" href="<%= esc(urlConfirmar) %>">Confirmar</a>
            <% } %>

            <a class="btn btn-secondary" href="<%= esc(urlCancelar) %>">
                <% if (urlConfirmar != null && !urlConfirmar.trim().isEmpty()) { %>
                    Cancelar
                <% } else { %>
                    Volver
                <% } %>
            </a>
        </div>
    </div>
</div>
</body>
</html>