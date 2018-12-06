<%@ page import="java.util.*,java.text.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>IFT287 - Système de gestion du centre sportif</title>
		<meta name="author" content="Antoine Lazure">
		<meta name="description" content="Page de gestion des résultats">
		
		<!-- Required meta tags -->
	    <meta charset="utf-8">
	    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	
	    <!-- Bootstrap CSS -->
	    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
			
	</head>
	<body>
		<div class="container">
			<jsp:include page="/WEB-INF/navigation.jsp" />
			<h1 class="text-center">RESULTATS</h1>
			<div class="col-md-4 offset-md-4">
			<form action="Resultats" method="POST">
			    <div class="form-group">
				    <label for="nomEquipeA">Nom de l'équipe A</label>
				    <input class="form-control" type="TEXT" name="nomEquipeA" value="<%= (request.getAttribute("nomEquipeA") != null ? (String)request.getAttribute("nomEquipeA") : "") %>">
			    </div>
			    <div class="form-group">
			    	<label for="scoreEquipeA">Score de l'équipe A</label>
			    	<input class="form-control" type="NUMBER" name="scoreEquipeA" min="0" value="<%= (request.getAttribute("scoreEquipeA") != null ? (String)request.getAttribute("scoreEquipeA") : "") %>">
			    </div>
			    <div class="form-group">
				    <label for="nomEquipeB">Nom de l'équipe B</label>
				    <input class="form-control" type="TEXT" name="nomEquipeB" value="<%= (request.getAttribute("nomEquipeB") != null ? (String)request.getAttribute("nomEquipeB") : "") %>">
			    </div>
			    <div class="form-group">
			    	<label for="scoreEquipeB">Score de l'équipe B</label>
			    	<input class="form-control" type="NUMBER" name="scoreEquipeB" min="0" value="<%= (request.getAttribute("scoreEquipeB") != null ? (String)request.getAttribute("scoreEquipeB") : "") %>">
			    </div>			    		    			    
			    <div class="row">
			    	<div class="col-md-6">
						<input class="btn btn-primary" type="SUBMIT" name="ajouterResultat" value="Créer le Résultat des Équipes">
					</div>
				</div>
			</form>
			</div>			
			<br>
			<%-- inclusion d'une autre page pour l'affichage des messages d'erreur--%>
			<jsp:include page="/WEB-INF/messageErreur.jsp" />
			<br>
		</div>		
		<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
		<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
	</body>
</html>
