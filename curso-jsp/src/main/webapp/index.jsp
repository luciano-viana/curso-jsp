<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Curso JSP</title>
</head>
<body>
<h1>Bem vindo ao curso JSP</h1>

<%--Formul�rio para enviar os dados--%>
<form action="ServletLogin" method="post">

<%--Campos de entrada de dados que s�o HTML --%>
<input name= "nome">
<input name = "idade">

<%--Bot�o para enviar os dados para a p�gina receber-nome.jsp  --%>
<input type="submit" value="Enviar">

</form>
</body>
</html>