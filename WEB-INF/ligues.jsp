<%@ page import="java.util.*,java.text.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>IFT287 - Système de gestion du centre sportif</title>
		<meta name="author" content="Alexandre Beausoleil">
		<meta name="description" content="Page de gestion des ligues">
		
		<!-- Required meta tags -->
	    <meta charset="utf-8">
	    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	
	    <!-- Bootstrap CSS -->
	    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
			
	</head>
	<body>
		<div class="container">
			<jsp:include page="/WEB-INF/navigation.jsp" />
			<h1 class="text-center">Ligues</h1>
			<div class="col-md-4 offset-md-4">
			<form action="Ligues" method="POST">
			    <div class="form-group">
				    <label for="nomLigue">Nom de la ligue</label>
				    <input class="form-control" type="TEXT" name="nomLigue" value="<%= (request.getAttribute("nomLigue") != null ? (String)request.getAttribute("nomLigue") : "") %>">
			    </div>
			    <div class="form-group">
			    	<label for="nbJoueur">Nombre de joueurs</label>
			    	<input class="form-control" type="NUMBER" name="nbJoueur" min="1" value="<%= (request.getAttribute("nbJoueur") != null ? (String)request.getAttribute("nbJoueur") : "") %>">
			    </div>			    
			    <div class="row">
			    	<div class="col-md-6">
						<input class="btn btn-primary" type="SUBMIT" name="ajouterLigue" value="Créer la ligue">
					</div>
				</div>
			</form>
			</div>
			<br>
			<%-- inclusion d'une autre page pour l'affichage des messages d'erreur--%>
			<jsp:include page="/WEB-INF/messageSucces.jsp" />
			<jsp:include page="/WEB-INF/messageErreur.jsp" />
			<br>
		</div>
		<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
		<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
	</body>
</html>
