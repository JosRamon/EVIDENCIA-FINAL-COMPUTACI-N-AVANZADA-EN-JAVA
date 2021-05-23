<%@page import="service.Service"%>
<%@page import="java.util.Collection"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>


<%@page import="model.Usuario"%>
<%@page import="java.util.List"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Lista de Usuarios</title>

<%
	Service servicio = Service.getInstance();
	List<Usuario> usuariosRegistrados = servicio.listarUsuarios();
	
%>

</head>

<body bgcolor="greenyellow">

	<h1>LISTA DE USUARIOS REGISTRADOS</h1>

	<%	for (Usuario usuario : usuariosRegistrados) { %>
		
		<h3>Usuario: <b><%=usuario.usuario %></b></h3><hr />
		<p>Password: <i><%=usuario.password %></i></p>
		<p>Nombre: <%=usuario.nombre %></p>
		<p>Edad: <%=usuario.edad %> años</p>
		<p>Estatura: <%=usuario.estatura %> cent&iacute;metros</p>
		<p>Sexo: <%=usuario.sexo %></p>
		<hr />
		<br />
	
    <% } %>
    
    <br>
    <a href="<%=request.getContextPath() %>/Inicio">Inicio</a><br>
    <a href="<%=request.getContextPath() %>/Logout">Cerrar Sesion</a><br>
    
</body>
</html>