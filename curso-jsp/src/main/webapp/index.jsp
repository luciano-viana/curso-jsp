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
	<input type="hidden" value="<%= request.getParameter("url") %>" name="url" >
		<table>
			<tr>
				<td><label>Login</label></td>
				<%--Campos de entrada de dados que são HTML --%>
				<td><input name="login" type="text"></td>
			</tr>
			<tr>
				<td><label>Senha</label></td>
				<td><input name="senha" type="password"></td>
			</tr>
			<tr>
				<td />
				<td>
					<%--Botão para enviar os dados para a página receber-nome.jsp  --%>
					<input type="submit" value="Enviar">
				</td>
			</tr>
		</table>
	</form>
	<h4>${msg}</h4>
</body>
</html>