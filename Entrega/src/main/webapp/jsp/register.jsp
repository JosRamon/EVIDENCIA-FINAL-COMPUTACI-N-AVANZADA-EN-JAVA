<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>

<!DOCTYPE html>
<html>
<head>

<title>Registro de usuario</title>

<script src="<%=request.getContextPath() %>/js/jquery-3.6.0.min.js"></script>
<script src="<%=request.getContextPath() %>/js/varios.js"></script>

<script type="text/javascript">

	$(document).ready(function() {
	  
	});

	//Permite validar los datos cargados en el formulario
	function validarFormulario(){
		var estatura = $("#estatura").val();
		if(estatura < 100 || estatura > 250){
			alert("Solo se permiten estaturas mayores a 1 metro y menores a 2,5 metros");
			return false;
		}
		var edad = $("#edad").val();
		
	}

</script>

</head>

<body bgcolor="greenyellow">	
	<h1>Registro de nuevo usuario</h1>
	<h3>Cargue los datos necesarios</h3>
	<hr />
	
	<form id="idForm" action="<%=request.getContextPath() %>/RegistrarUsuario" method="post" onsubmit="return validarFormulario()">
         Usuario:<br><input type="text" name="usuario" id="usuario" value="" required><br>
         Contraseña:<br><input type="password" name="password" id="password" value="" required><br>
         Nombre:<br><input type="text" name="nombre" id="nombre" value="" required><br>
         Edad:<br><input type="text" name="edad" id="edad" value="" required onkeypress="return esNumericoPlus(event);"><br>
         Sexo:<br>
         <select name="sexo" id="sexo">
		  <option value="masculino" selected>Masculino</option>
		  <option value="femenino">Femenino</option>
		  <option value="otros">Otros</option>
		</select>
         <br>
         Estatura:<br><input type="text" name="estatura" id="estatura" value="" required onkeypress="return esNumericoPlus(event);"> (en cent&iacute;metros)<br><br>
         
         <input type="submit" value="Registrar"><br><br>

     </form>
	
	<br>
	<input type="button" onclick="history.back()" value="Volver">


</body>
</html>