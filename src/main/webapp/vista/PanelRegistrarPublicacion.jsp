<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ page import="modelo.entidad.Publicacion,jakarta.servlet.jsp.JspWriter" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%!
    // Helper para escapar HTML
    private String escapeHtml(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#x27;");
    }

    // Función que dibuja el formulario de publicación. Si pub es null, usa valores vacíos.
    // Ahora recibe el contextPath para construir URLs públicas desde la vista
    public void desplegarFormulario(Publicacion pub, JspWriter out, String contextPath) throws java.io.IOException {
        String titulo = (pub != null && pub.getTitulo() != null) ? escapeHtml(pub.getTitulo()) : "";
        String categoria = (pub != null && pub.getCategoria() != null) ? escapeHtml(pub.getCategoria()) : "";
        String nombreProducto = (pub != null && pub.getNombreDelProducto() != null) ? escapeHtml(pub.getNombreDelProducto()) : "";
        String precio = (pub != null) ? String.valueOf(pub.getPrecio()) : "";
        String estado = (pub != null && pub.getEstado() != null) ? pub.getEstado().name() : "";
        String descripcion = (pub != null && pub.getDescripcion() != null) ? escapeHtml(pub.getDescripcion()) : "";
        String imagen = (pub != null && pub.getImagen() != null) ? escapeHtml(pub.getImagen()) : "";
        String usuario = (pub != null && pub.getUsuario() != null) ? escapeHtml(pub.getUsuario()) : "";

        out.write("<div class=\"form-container-largo\">\n");
        out.write("    <form method=\"post\" action=\"" + contextPath + "/RegistrarPublicacionController\" enctype=\"multipart/form-data\">\n");
        out.write("        <input type=\"hidden\" name=\"ruta\" value=\"guardar\">\n");
        out.write("        <div class=\"form-flex-row\">\n");
        out.write("            <div class=\"form-col\">\n");
        out.write("                <div class=\"form-group\">\n");
        out.write("                    <label>Título del Producto</label>\n");
        out.write("                    <input type=\"text\" name=\"titulo\" value=\"" + titulo + "\" placeholder=\"Ej: Bicicleta de montaña...\">\n");
        out.write("                </div>\n");
        out.write("                <div class=\"form-group\">\n");
        out.write("                    <label>Precio ($)</label>\n");
        out.write("                    <input type=\"number\" step=\"0.01\" name=\"precio\" value=\"" + precio + "\" placeholder=\"Ej: 150.00\">\n");
        out.write("                </div>\n");
        out.write("                <div class=\"form-flex-row gap-small\">\n");
        out.write("                    <div class=\"form-group form-col\">\n");
        out.write("                        <label>Categoría</label>\n");
        out.write("                        <select name=\"categoria\">\n");
        // opciones de categoría
        String[] categorias = new String[]{"Electrónica","Hogar","Deportes","Belleza","Otros"};
        for (String cat : categorias) {
            String sel = cat.equals(categoria) ? " selected" : "";
            out.write("                            <option value=\"" + escapeHtml(cat) + "\"" + sel + ">" + escapeHtml(cat) + "</option>\n");
        }
        out.write("                        </select>\n");
        out.write("                    </div>\n");
        out.write("                    <div class=\"form-group form-col\">\n");
        out.write("                        <label>Estado</label>\n");
        out.write("                        <select name=\"estado\">\n");
        String[] estados = new String[]{"NUEVO","USADO","VENDIDO"};
        for (String est : estados) {
            String display = est.substring(0,1) + est.substring(1).toLowerCase();
            String sel = est.equals(estado) ? " selected" : "";
            out.write("                            <option value=\"" + est + "\"" + sel + ">" + display + "</option>\n");
        }
        out.write("                        </select>\n");
        out.write("                    </div>\n");
        out.write("                </div>\n");
        out.write("            </div>\n");

        out.write("            <div class=\"form-col\">\n");
        out.write("                <div class=\"form-group\">\n");
        out.write("                    <label>Descripción</label>\n");
        out.write("                    <textarea name=\"descripcion\" placeholder=\"Detalles del producto, estado, motivo de venta...\">" + descripcion + "</textarea>\n");
        out.write("                </div>\n");
        out.write("                <div class=\"form-group\">\n");
        out.write("                    <label>Imagen Principal</label>\n");
        out.write("                    <div class=\"image-options\">\n");
        out.write("                        <label><input type=\"radio\" name=\"imgSource\" value=\"file\" checked> Subir archivo</label>\n");
        out.write("                        <label style=\"margin-left:12px;\"><input type=\"radio\" name=\"imgSource\" value=\"url\"> Usar URL externa</label>\n");
        out.write("                    </div>\n");
        out.write("                    <div class=\"upload-box\" id=\"uploadBox\">\n");
        out.write("                        <i class=\"fa-solid fa-cloud-arrow-up fa-2x\"></i>\n");
        out.write("                        <span>Arrastra o haz clic para subir imagen</span>\n");
        out.write("                        <input type=\"file\" name=\"imagen\" id=\"imagenFile\" accept=\"image/*\">\n");
        out.write("                    </div>\n");
        out.write("                    <div class=\"form-group\" id=\"urlInputGroup\" style=\"display:none; margin-top:8px;\">\n");
        out.write("                        <input type=\"text\" name=\"imagenUrl\" id=\"imagenUrl\" placeholder=\"https://ejemplo.com/imagen.jpg\" style=\"width:100%;\">\n");
        out.write("                    </div>\n");
        out.write("                    <div class=\"image-preview\" style=\"margin-top:8px;\">\n");
        out.write("                        <img id=\"previewImagen\" src=\"" + imagen + "\" alt=\"Previsualización\" style=\"max-width:200px; max-height:150px;" + (imagen.isEmpty() ? "display:none;" : "") + "\">\n");
        out.write("                    </div>\n");
        out.write("                </div>\n");
        out.write("            </div>\n");
        out.write("        </div>\n");

        // campo usuario
        out.write("        <div class=\"form-group\">\n");
        out.write("            <label>Usuario</label>\n");
        out.write("            <input type=\"text\" name=\"usuario\" value=\"" + usuario + "\" placeholder=\"Nombre de usuario\">\n");
        out.write("        </div>\n");

        out.write("        <div class=\"form-footer\">\n");
        out.write("            <a href=\"GestionarPublicacionesController?ruta=listar\" class=\"btn btn-gris\">Cancelar</a>\n");
        out.write("            <button type=\"submit\" class=\"btn btn-secondary\"><i class=\"fa-solid fa-check\"></i> Publicar Ahora</button>\n");
        out.write("        </div>\n");

        out.write("    </form>\n");
        out.write("</div>\n");
    }
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>PanelRegistrarPublicacion</title>
<c:url var="cssUrl" value="/assets/css/styles2.css" />
<link rel="stylesheet" href="${cssUrl}">

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>

    <div id="alerta-futuro" class="alerta-futuro">
        <i class="fa-solid fa-rocket"></i> Esta funcionalidad será implementada en el futuro.
    </div>

    <header>
        <div class="cajaLogo">
            <i class="fa-solid fa-handshake fa-2x"></i>
            <h2>MarketFlow</h2>
        </div>
        <div class="cajaBarraBusqueda"><input type="text" placeholder="🔍 Buscar productos..."></div>
        <div class="cajaPerfil">
            <ul>
                <li><img src="imagenes/fotoAdmin.png" alt="Usuario"></li>
                <li>
                    <h3>Hola, Admin</h3>
                </li>
            </ul>
        </div>
        <a href="login.html" class="btn-logout"><i class="fa-solid fa-right-from-bracket"></i> Salir</a>
    </header>

    <div class="contenedor-principal">
        <aside>
            <ul class="flexColumnaAside listaSinEstilo">
                <li><a href="VisualizarCatalogoController?ruta=cargar"><i class="fa-solid fa-store"></i> Catálogo</a></li>
                <li><a href="GestionarPublicacionesController?ruta=listar"><i class="fa-solid fa-box-open"></i> Mis Publicaciones</a>
                </li>
                <li><a href="ContactarVendedorController?ruta=contactar"><i class="fa-solid fa-comments"></i> Mensajes</a></li>
            </ul>
        </aside>

        <div class="cajaContenidoPrincipal">
            <h1 class="titulo-pagina">Publicar Nuevo Producto</h1>

            <%-- reemplazo del formulario estático por la función --%>
            <% desplegarFormulario((Publicacion) request.getAttribute("publicacion"), out, request.getContextPath()); %>
        </div>
    </div>

    <script>
        function mostrarAlerta() {
            const alerta = document.getElementById('alerta-futuro');
            alerta.style.display = 'flex'; // Usamos flex para alinear icono y texto
            setTimeout(() => { alerta.style.display = 'none'; }, 3000);
        }
    </script>

    <script>
        // Manejo de toggle entre archivo y URL, drag & drop y previsualización
        document.addEventListener('DOMContentLoaded', function() {
            const radios = document.getElementsByName('imgSource');
            const uploadBox = document.getElementById('uploadBox');
            const urlGroup = document.getElementById('urlInputGroup');
            const fileInput = document.getElementById('imagenFile');
            const urlInput = document.getElementById('imagenUrl');
            const preview = document.getElementById('previewImagen');
            let currentObjectUrl = null;

            function updateMode() {
                const mode = Array.from(radios).find(r => r.checked).value;
                if (mode === 'file') {
                    uploadBox.style.display = 'flex';
                    urlGroup.style.display = 'none';
                } else {
                    uploadBox.style.display = 'none';
                    urlGroup.style.display = 'block';
                }
            }

            radios.forEach(r => r.addEventListener('change', updateMode));
            updateMode();

            // Drag & Drop
            ['dragenter', 'dragover'].forEach(ev => {
                uploadBox.addEventListener(ev, function(e) { e.preventDefault(); e.stopPropagation(); uploadBox.classList.add('dragover'); });
            });
            ['dragleave', 'drop'].forEach(ev => {
                uploadBox.addEventListener(ev, function(e) { e.preventDefault(); e.stopPropagation(); uploadBox.classList.remove('dragover'); });
            });

            uploadBox.addEventListener('drop', function(e) {
                const files = e.dataTransfer.files;
                if (files && files.length) {
                    fileInput.files = files;
                    previewFile(files[0]);
                }
            });

            fileInput.addEventListener('change', function(e) {
                const f = e.target.files && e.target.files[0];
                if (f) previewFile(f);
            });

            urlInput && urlInput.addEventListener('input', function(e) {
                const v = e.target.value.trim();
                if (v.length === 0) { preview.style.display = 'none'; return; }
                // Mostrar preview si es URL (http/https) o data URI
                if (v.startsWith('http://') || v.startsWith('https://') || v.startsWith('data:')) {
                    preview.src = v;
                    preview.style.display = 'block';
                } else {
                    preview.style.display = 'none';
                }
            });

            function previewFile(file) {
                if (!file) return;
                if (currentObjectUrl) URL.revokeObjectURL(currentObjectUrl);
                currentObjectUrl = URL.createObjectURL(file);
                preview.src = currentObjectUrl;
                preview.style.display = 'block';
            }
        });
    </script>
</body>

</html>