<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Catálogo - MarketFlow</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/main.css" />
</head>
<body>
<h1>Catálogo</h1>
<!-- Depuración: número de publicaciones que llegan desde el servidor -->
<p id="debugPublicaciones">Publicaciones en request: <strong>${fn:length(publicaciones)}</strong></p>
<p><a href="${pageContext.request.contextPath}/index.jsp">Inicio</a> | <a href="${pageContext.request.contextPath}/misPublicaciones">Mis publicaciones</a></p>

<c:choose>
    <c:when test="${not empty sessionScope.usuario}">
        <c:set var="sessionUser" value="${sessionScope.usuario}" />
    </c:when>
    <c:otherwise>
        <c:set var="sessionUser" value="usuarioDemo" />
    </c:otherwise>
</c:choose>

<c:if test="${fn:length(publicaciones) == 0}">
    <div class="no-items">No hay publicaciones disponibles.</div>
</c:if>

<div class="cards-row" id="cardsContainer">
    <c:forEach var="p" items="${publicaciones}">
        <div class="card">
            <c:choose>
                <c:when test="${not empty p.imagen}">
                    <img class="card-image" src="${p.imagen}" alt="${fn:escapeXml(p.titulo)}" />
                </c:when>
                <c:otherwise>
                    <img class="card-image" src="${pageContext.request.contextPath}/styles/placeholder.png" alt="placeholder" />
                </c:otherwise>
            </c:choose>
            <div class="card-body">
                <h3 class="card-title">${fn:escapeXml(p.titulo)}</h3>
                <div class="card-sub">${fn:escapeXml(p.nombreDelProducto)}</div>
                <div class="card-sub">${fn:escapeXml(p.categoria)} &bull; ${fn:escapeXml(p.estado)}</div>
                <div class="card-meta">
                    <div class="price">$${p.precio}</div>
                    <div class="actions">
                        <a class="btn btn-primary" href="${pageContext.request.contextPath}/chat?usuario=${fn:escapeXml(sessionUser)}&amp;otro=${fn:escapeXml(p.usuario)}">Contactar</a>
                        <a class="btn btn-ghost" href="#">Ver</a>
                    </div>
                </div>
            </div>
        </div>
    </c:forEach>
</div>

</body>
</html>