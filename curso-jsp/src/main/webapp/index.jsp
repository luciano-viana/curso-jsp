<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

<title>Curso JSP</title>

<style type="text/css">

form {
	position: absolute;
	top:40%;
	left:33%;
	right:33%;
}

h2 {
	position: absolute;
	top:30%;
	left:33%;
}

.msg {
	position: absolute;
	top:10%;
	left:33%;
	font-size: 15px;
	color: #664d03;
    background-color: #fff3cd;
    border-color: #ffecb5;
}

</style>

</head>
<body>

	<h2>Bem vindo ao curso JSP</h2>
	
	<%--Formulário para enviar os dados--%>
	<form action="<%=request.getContextPath()%>/ServletLogin" method="post" class="row g-3 needs-validation" novalidate>
	
	<input type="hidden" value="<%= request.getParameter("url") %>" name="url" >
	
			<div class="mb-3">
				<label class="form-label">Login</label>
				<%--Campos de entrada de dados que são HTML --%>
				<input class="form-control" name="login" type="text" required="required">
				<div class="invalid-feedback">
     			 Obrigatório!
    			</div>
    			<div class="valid-feedback">
     			 ok
    			</div>
    			
			</div>
			
			<div class="mb-3">
				<label class="form-label">Senha</label>
				<input class="form-control" name="senha" type="password" required="required">
				<div class="invalid-feedback">
     			 Obrigatório
    			</div>
    			<div class="valid-feedback">
     			 ok
    			</div>
			</div>
			
				<%--Botão para enviar os dados para a página receber-nome.jsp  --%>
				<input type="submit" value="Acessar" class="btn btn-primary">
		
		
	</form>
	<h5 class="msg">${msg}</h5>
	
	<!-- Option 1: Bootstrap Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    
    
<script type="text/javascript">
//Exemplo de JavaScript inicial para desabilitar envios de formulários se houver campos inválidos
(function () {
  'use strict'

  // Busque todos os formulários aos quais queremos aplicar estilos de validação personalizados do Bootstrap
  var forms = document.querySelectorAll('.needs-validation')

  // Passe por cima deles e evite o envio
  Array.prototype.slice.call(forms)
    .forEach(function (form) {
      form.addEventListener('submit', function (event) {
        if (!form.checkValidity()) {
          event.preventDefault()
          event.stopPropagation()
        }

        form.classList.add('was-validated')
      }, false)
    })
})()
</script>
    
</body>
</html>