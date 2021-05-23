<%@page import="model.ErrorGeneral"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>ERROR</title>

<%
	ErrorGeneral error = (ErrorGeneral) request.getAttribute("error");
%>

</head>
<body>
	<h1>Se produjo un error: <%=error.getTitulo() %></h1>
	<h3>Causa: <%=error.getMensaje() %></h3>
	
	<input type="button" onclick="history.back()" value="Volver">
	
</body>
</html>