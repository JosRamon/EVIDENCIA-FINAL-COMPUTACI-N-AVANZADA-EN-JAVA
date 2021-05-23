<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>

<title>LOGIN</title>

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/estiloMenu.css" />

</head>

<body bgcolor="greenyellow">

<nav class="menu">
	  <ul>
	    <li><a href="<%=request.getContextPath() %>/jsp/register.jsp">Registar nuevo usuario</a></li>
	  </ul>
	</nav>
	
	<section>
		<h1>Iniciar sesion</h1>
		
		<form id="idForm" action="<%=request.getContextPath() %>/Login" method="post">
	           Usuario:<br>
	           <input type="text" name="usuario" id="usuario" value=""><br>
	           Contraseña:<br>
	           <input type="password" name="password" id="password" value=""><br><br>
	           
	           <input type="submit" value="Loguin"><br><br>
	    </form>
	
	</section>
	
</body>
</html>