<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Chat</title>
</head>
<body>
<h1>Chat: <c:out value="${usuario}"/> ↔ <c:out value="${otro}"/></h1>
<p>
  <a href="${pageContext.request.contextPath}/publicaciones">Volver al catálogo</a>
</p>
<div id="chat">
    <c:forEach var="m" items="${conversacion}">
        <div class="mensaje">
            <strong><c:out value="${m.remitente}"/>:</strong> <c:out value="${m.contenido}"/> <em>(<c:out value="${m.fechaDeEnvio}"/>)</em>
        </div>
    </c:forEach>
</div>
<hr/>
<form action="${pageContext.request.contextPath}/chat" method="post">
    <input type="hidden" name="remitente" value="${usuario}" />
    <input type="hidden" name="destinatario" value="${otro}" />
    <textarea name="contenido" rows="3" cols="50"></textarea><br/>
    <button type="submit">Enviar</button>
</form>
</body>
</html>
