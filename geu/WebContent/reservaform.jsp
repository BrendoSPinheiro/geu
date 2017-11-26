<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html >
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Gerenciador de Espaços</title>

<link href="/geu/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="/geu/bootstrap/css/bootstrap-theme.min.css" rel="stylesheet">
<link href="/geu//css/app.css" rel="stylesheet">


</head>

<body>
	<c:import url="topo.jsp"></c:import>
	<div class="container">
		<div class="page-header">
			<h1>Inserir Reserva</h1>
		</div>
		<form action="reservas" method="post">
		Espaço: 
		<select name="espaco" >
			<option value="" selected>Selecione</option>
			<c:forEach var="espaco" items="${lista}">
				<option value="${espaco.id}">${espaco.identificacao}</option>
			</c:forEach>
		</select>
		<br>
			Titulo: <input type="text" name="titulo" /><br> 
			Descrição: <input type="text" name="descricao" /><br> 
			Justificativa:<input type="text" name="justificativa" /><br>
			Solicitante:<input type="text" name="solicitante" /><br>
						Telefone:<input type="text" name="telefone" /><br>
						Data (dd/MM/yyyy):<input type="text" name="data" /><br>
						Inicio (Hora HH:MM):<input type="text" name="inicio" /><br>
						Fim (Hora HH:MM):<input type="text" name="fim" /><br>			
			<input type="submit" value="Salvar" />
		</form>
	</div>

	<c:import url="rodape.jsp"></c:import>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>

	<script src="/geu/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>