<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Chat - MarketFlow</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/main.css" />
</head>
<body>
<h1>Enviar mensaje</h1>
<form action="${pageContext.request.contextPath}/mensaje" method="post">
    <label>Remitente: <input type="text" name="remitente" required/></label><br/>
    <label>Destinatario: <input type="text" name="destinatario" required/></label><br/>
    <label>Contenido:<br/><textarea name="contenido" rows="4" cols="50"></textarea></label><br/>
    <button type="submit">Enviar</button>
</form>
</body>
</html>