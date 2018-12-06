<%@ page import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	// affichage de la liste des messages d'erreur
	if (request.getAttribute("listeMessageSucces") != null)
	{
		for(String text : (List<String>)request.getAttribute("listeMessageSucces"))
		{
			%>
			<div class="alert alert-success" role="alert">
			  <%= text %>
			</div>
		<%
		}
	}
%>
