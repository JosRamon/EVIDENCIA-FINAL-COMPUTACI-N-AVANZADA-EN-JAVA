<%@page import="model.ErrorGeneral"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>ERROR</title>

<%
	String mensaje = (String) request.getAttribute("mensaje");
	String url = (String) request.getAttribute("urlFwrd");
%>

</head>
<body bgcolor="greenyellow">
	<h1><%=mensaje %></h1>
	<%
	if(url==null){
	%>
		<input type="button" onclick="history.back()" value="Aceptar">
	<%
	} else {
	%>	
		<input type="button" onclick="window.location.href='<%=request.getContextPath() + url %>'" value="Aceptar">
	<%
	}
	%>
</body>
</html>