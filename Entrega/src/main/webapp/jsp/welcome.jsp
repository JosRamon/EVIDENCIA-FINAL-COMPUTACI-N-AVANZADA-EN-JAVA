<%@page import="model.Usuario"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%
	Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
	
%>    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Bienvenido <%=usuario.nombre %></title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/estiloMenu.css" />
<script src="<%=request.getContextPath() %>/js/jquery-3.6.0.min.js"></script>
<script src="<%=request.getContextPath() %>/js/varios.js"></script>

<script type="text/javascript">

$(document).ready(function() {
	obtenerHistoricosIMC(); 
});

//Recupera el historico de calculos de IMN del usuario
function obtenerHistoricosIMC(){
	var usuario = '<%=usuario.usuario %>';
		
	$("#spanAjaxIndicator").show();
	var request = $.ajax({
			url: "<%=request.getContextPath() %>/HistoricosIMC",
			type: "GET",
			cache: false,
			data: {usuario: usuario},
			dataType: "json",
			async: true
	});
	request.done(function(resp) {
		$("#spanAjaxIndicator").hide();
		if (resp.exito){
			if(resp.colObjetos == null || resp.colObjetos.length == 0){
				alert("No se recuperaron calculos de IMC anteriores.");
				
			}else{
				//Cargamos el div con los historicos recuperados
				var strHistoricos = "<table border='1' whith='60%'><tr><th>Fecha</th><th>Peso</th><th>Estatura</th><th>IMC</th></tr>";
				
				for (i = 0; i < resp.colObjetos.length; i++) { 
					//Agregamos cada uno de los calculos recibidos
					strHistoricos += "<tr><td>" + resp.colObjetos[i].fecha + "</td><td>" + resp.colObjetos[i].peso + "</td><td>" + resp.colObjetos[i].estatura + "</td><td>" + resp.colObjetos[i].imc + "</td></tr>";
				}
				strHistoricos += "</table>";
				$("#divHistoricos").html(strHistoricos);
			}	
		}else{
			alert(resp.leyendaError);
		}
	});
	request.fail(function(jqXHR, textStatus) {
		$("#spanAjaxIndicator").hide();
		alert("Falló la recuperación de los historicos. \n" + textStatus);
	});
}

</script>

</head>
<body bgcolor="greenyellow">
	<nav class="menu">
	  <ul>
	     <!--
	    <li><a href="#">Calcular IMC</a></li>
	    <li><a href="#">Historial de c&aacute;lculos</a></li>
	    <li><a href="<%=request.getContextPath() %>/jspg/listaUsuarios.jsp">Ver usuarios registrados</a><br></li>  
	    -->
	    <li><a href="<%=request.getContextPath() %>/Logout">Cerrar Sesion</a><br></li>
	  </ul>
	</nav>
	<section id="idDatosUsuario">
		<hr /><h1>Bienvenido <b><%=usuario.usuario %></b></h1><hr />
		<p>Nombre: <%=usuario.nombre %></p>
		<p>Edad: <%=usuario.edad %></p>
		<p>Estatura: <%=usuario.estatura %></p>
		<p>Sexo: <%=usuario.sexo %></p>
		
		<h2><B>IMC Actual: <%=usuario.imc%></b></h2>
	</section>
	
	<section id="idFormIMC">
		<hr /><h2>Calculo de Indice de Masa Corporal (IMC)</h2><hr />
		<h3>Para realizar el c&aacute;lculo se debe ingresar el peso. Si ya realiz&oacute; un calculo en el mismo d&iacute;a, 
		el nuevo c&aacute;lculo sobreescribir&aacute; al anterior.</h3>
		
		<form id="idForm" action="<%=request.getContextPath() %>/CalcularIMC" method="post">
	           Peso:<br>
	           <input type="text" name="peso" id="peso" value="" required onkeypress="return esNumericoPlus(event);">
	           <br>
	           <br>
	           
	           <input type="submit" value="Calcular"><br><br>
	    </form>
	</section>
	  
	
	<section id="idHistoricos">
		<div style="margin:'0 auto'">
			<hr /><h2>Hist&oacute;ricos de c&aacute;lculos de IMC</h2><hr />
			
			<span id="spanAjaxIndicator" style="display: none;">
				<img border="0" src="<%=request.getContextPath() %>/img/ajax-loader.gif" />
			</span>
			<div id="divHistoricos"></div>
		</div>									
	</section>
	

</body>
</html>