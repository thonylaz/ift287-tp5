<%@ page import="java.util.*,java.text.*, CentreSportif.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>IFT287 - Système de gestion du centre sportif</title>
		<meta name="author" content="Alexandre Beausoleil">
		<meta name="description" content="Page de gestion des participants">
		
		<!-- Required meta tags -->
	    <meta charset="utf-8">
	    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	
	    <!-- Bootstrap CSS -->
	    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
			
	</head>
	<body>
		<div class="container">
			<jsp:include page="/WEB-INF/navigation.jsp" />
			<h1 class="text-center">Participant</h1>
			<h3 class="text-left">Inscrire un participant</h3>
			<hr>
			<div class="col-md-4 pull-left">
			<form action="Participants" method="POST">
				<div class="form-group">
			    	<label for="matricule">Matricule</label>
			    	<input class="form-control" type="NUMBER" name="matricule" min="1" value="<%= (request.getAttribute("matricule") != null ? (String)request.getAttribute("matricule") : "") %>">
			    </div>
			    <div class="form-group">
				    <label for="nom">Nom</label>
				    <input class="form-control" type="TEXT" name="nom" value="<%= (request.getAttribute("nom") != null ? (String)request.getAttribute("nom") : "") %>">
			    </div>
			    <div class="form-group">
				    <label for="prenom">Prenom</label>
				    <input class="form-control" type="TEXT" name="prenom" value="<%= (request.getAttribute("prenom") != null ? (String)request.getAttribute("prenom") : "") %>">
			    </div>
			    <div class="form-group">
				    <label for="motDePasse">Mot de passe</label>
				    <input class="form-control" type="PASSWORD" name="motDePasse" value="<%= (request.getAttribute("motDePasse") != null ? (String)request.getAttribute("motDePasse") : "") %>">
			    </div>	    			    
			    <div class="row">
			    	<div class="col-md-6">
						<input class="btn btn-primary" type="SUBMIT" name="inscrireParticipant" value="Inscrire le participant">
					</div>
				</div>
			</form>
			</div>
			<br>
			<h3 class="text-left">Supprimer un participant</h3>
			<hr>
			<div class="col-md-4 pull-left">
			<form action="Participants" method="POST">
				<div class="form-group">
			    	<label for="matricule">Matricule</label>
			    	<select class="form-control" name="matricule">
					    <option value=""></option>
					    <%  GestionCentreSportif centreSportifInterro = (GestionCentreSportif) request.getSession().getAttribute("centreSportifInterrogation");  
					    ArrayList<String> matricule = centreSportifInterro.getGestionParticipant().getParticipant().getParticipantsMatricule();
				    	for(String s : matricule) {
				    		%><option value="<%= s %>"><%= s %></option><%
			    		}%>
				    </select>
			    </div>
			    <div class="row">
			    	<div class="col-md-6">
						<input class="btn btn-primary" type="SUBMIT" name="supprimerParticipant" value="Supprimer le participant">
					</div>
				</div>
			</form>
			</div>
			<br>
			<h3 class="text-left">Inscrire un participant à une équipe</h3>
			<hr>
			<div class="col-md-4 pull-left">
			<form action="Participants" method="POST">
				<div class="form-group">
			    	<label for="matricule">Matricule</label>
			    	<select class="form-control" name="matricule">
					    <option value=""></option>   
  					    <%for(String s : matricule) {
				    		%><option value="<%= s %>"><%= s %></option><%
			    		}%>
				    </select>
			    </div>
			    <div class="form-group">
			    	<label for="nomEquipe">Nom de léquipe</label>
			    	<select class="form-control" name="nomEquipe">
					    <option value=""></option>   
  					    <%ArrayList<String> equipes = centreSportifInterro.getGestionEquipe().getEquipe().getNomEquipes();
  					    for(String s : equipes) {
				    		%><option value="<%= s %>"><%= s %></option><%
			    		}%>
				    </select>
			    </div>
			    <div class="row">
			    	<div class="col-md-6">
						<input class="btn btn-primary" type="SUBMIT" name="ajouterJoueur" value="S'inscrire dans cette équipe">
					</div>
				</div>
			</form>
			</div>
			<br>
			<h3 class="text-left">Accepter un participant dans l'équipe</h3>
			<hr>
			<div class="col-md-4 pull-left">
			<form action="Participants" method="POST">
				<div class="form-group">
			    	<label for="matricule">Matricule</label>
			    	<select class="form-control" name="matricule">
					    <option value=""></option>   
  					    <% ArrayList<String> matriculeInscrit = centreSportifInterro.getGestionParticipant().getParticipant().getParticipantsInscrit();
  					    for(String s : matriculeInscrit) {
				    		%><option value="<%= s %>"><%= s %></option><%
			    		}%>
				    </select>
			    </div>
			    <div class="form-group">
			    	<label for="nomEquipe">Nom de léquipe</label>
			    	<select class="form-control" name="nomEquipe">
					    <option value=""></option>   
  					    <%for(String s : equipes) {
				    		%><option value="<%= s %>"><%= s %></option><%
			    		}%>
				    </select>
			    </div>
			    <div class="row">
			    	<div class="col-md-6">
						<input class="btn btn-primary" type="SUBMIT" name="accepterJoueur" value="Accepter le joueur">
					</div>
				</div>
			</form>
			</div>
			<br>
			<h3 class="text-left">Refuser un participant dans l'équipe</h3>
			<hr>
			<div class="col-md-4 pull-left">
			<form action="Participants" method="POST">
				<div class="form-group">
			    	<label for="matricule">Matricule</label>
			    	<select class="form-control" name="matricule">
					    <option value=""></option>   
  					    <%for(String s : matriculeInscrit) {
				    		%><option value="<%= s %>"><%= s %></option><%
			    		}%>
				    </select>
			    </div>
			    <div class="form-group">
			    	<label for="nomEquipe">Nom de léquipe</label>
			    	<select class="form-control" name="nomEquipe">
					    <option value=""></option>   
  					    <%for(String s : equipes) {
				    		%><option value="<%= s %>"><%= s %></option><%
			    		}%>
				    </select>
			    </div>
			    <div class="row">
			    	<div class="col-md-6">
						<input class="btn btn-primary" type="SUBMIT" name="refuserJoueur" value="Refuser le joueur">
					</div>
				</div>
			</form>
			</div>
		
			<br>
			<h3 class="text-left">Supprimer un joueur d'une équipe</h3>
			<hr>
			<div class="col-md-4 pull-left">
			<form action="Participants" method="POST">
				<div class="form-group">
			    	<label for="matricule">Matricule</label>
			    	<select class="form-control" name="matricule">
					    <option value=""></option>   
  					    <%ArrayList<String> matriculeEquipe = centreSportifInterro.getGestionParticipant().getParticipant().getParticipantsDansEquipe();
  					    for(String s : matriculeEquipe) {
				    		%><option value="<%= s %>"><%= s %></option><%
			    		}%>
				    </select>
			    </div>
			    <div class="form-group">
			    	<label for="nomEquipe">Nom de léquipe</label>
			    	<select class="form-control" name="nomEquipe">
					    <option value=""></option>   
  					    <%for(String s : equipes) {
				    		%><option value="<%= s %>"><%= s %></option><%
			    		}%>
				    </select>
			    </div>
			    <div class="row">
			    	<div class="col-md-6">
						<input class="btn btn-primary" type="SUBMIT" name="supprimerJoueur" value="Supprimer le joueur de l'équipe">
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
