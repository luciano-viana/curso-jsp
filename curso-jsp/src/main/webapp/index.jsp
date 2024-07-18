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

<%--Formulário para enviar os dados--%>
<form action="ServletLogin" method="post">

<%--Campos de entrada de dados que são HTML --%>
<input name= "nome">
<input name = "idade">

<%--Botão para enviar os dados para a página receber-nome.jsp  --%>
<input type="submit" value="Enviar">

</form>
</body>
</html>