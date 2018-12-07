<%@ page import="java.util.*,java.text.*, CentreSportif.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>IFT287 - Système de gestion du centre sportif</title>
		<meta name="author" content="Alexandre Beausoleil">
		<meta name="description" content="Page de gestion des équipes">
		
		<!-- Required meta tags -->
	    <meta charset="utf-8">
	    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	
	    <!-- Bootstrap CSS -->
	    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
			
	</head>
	<body>
		<div class="container">
			<jsp:include page="/WEB-INF/navigation.jsp" />
			<h1 class="text-center">Équipes</h1>
			<h3 class="text-left">Créer une équipes</h3>
			<hr>
			<div class="col-md-4 pull-left">
			<form action="Equipes" method="POST">
			    <div class="form-group">
				    <label for="nomEquipe">Nom de l'équipe</label>
				    <input class="form-control" type="TEXT" name="nomEquipe" value="<%= (request.getAttribute("nomEquipe") != null ? (String)request.getAttribute("nomEquipe") : "") %>">
			    </div>
			    <div class="form-group">
			    	<label for="nbJoueur">Nom de la ligue</label>
			    	 <select class="form-control" name="nomLigue">
					    <option value=""></option>
					    <% GestionCentreSportif centreSportifInterro = (GestionCentreSportif) request.getSession().getAttribute("centreSportifInterrogation"); 
						    ArrayList<String> ligues = centreSportifInterro.getGestionLigue().getLigues().getLigues();
					    	for(String s : ligues) {
					    		%><option value="<%= s %>"><%= s %></option><%
				    		}%>
				    </select>
			    </div>
			    <div class="form-group">
			    	<label for="nbJoueur">Matricule</label>
			    	 <select class="form-control" name="matriculeCap">
					    <option value=""></option>
					    <%  ArrayList<String> participants = centreSportifInterro.getGestionParticipant().getParticipant().getParticipantsMatricule();
					    	for(String s : participants) {
					    		%><option value="<%= s %>"><%= s %></option><%
				    		}%>
				    </select>
			    </div>			    
			    <div class="row">
			    	<div class="col-md-6">
						<input class="btn btn-primary" type="SUBMIT" name="ajouterEquipe" value="Créer l'équipe">
					</div>
				</div>
			</form>
			</div>
			<br>
			<h3 class="text-left">Afficher l'équipe</h3>
			<hr>
			<div class="col-md-4 pull-left">
				<form action="Equipes" method="POST">
				    <div class="form-group">
					    <label for="nomEquipe">Nom de l'équipe</label>
					    <select class="form-control" name="nomEquipe">
						    <option value=""></option>
						    <%  ArrayList<String> equipes = centreSportifInterro.getGestionEquipe().getEquipe().getNomEquipes();
						    	for(String s : equipes) {
						    		%><option value="<%= s %>"><%= s %></option><%
					    		}%>
					    </select>
				    </div>			    
				    <div class="row">
				    	<div class="col-md-6">
							<input class="btn btn-primary" type="SUBMIT" name="afficherEquipe" value="Afficher l'équipe">
						</div>
					</div>
				</form>
			</div>
			<br>
			<% if(request.getAttribute("resultatAfficherEquipe_Equipe") != null && request.getAttribute("resultatAfficherEquipe_Capitaine") != null) {
						TupleEquipe tupleEquipe = (TupleEquipe)request.getAttribute("resultatAfficherEquipe_Equipe");
						TupleParticipant capitaine = (TupleParticipant)request.getAttribute("resultatAfficherEquipe_Capitaine");%>
						<table class="table">
							  <thead>
							    <tr>
							      <th scope="col">Nom équipe</th>
							      <th scope="col">Nom de la ligue</th>
							      <th scope="col">Capitaine</th>
							    </tr>
							  </thead>
							  <tbody>
								  <tr>
								      <td><%=tupleEquipe.getNomEquipe()%></td>
								      <td><%=tupleEquipe.getNomLigue()%></td>
								      <td><%=capitaine.getPrenom() + " " + capitaine.getNom()%></td>
								  </tr>
							  </tbody>
						  </table>
						  <br>
						<%ArrayList<TupleParticipant> listParticipants = centreSportifInterro.getGestionParticipant().getParticipant().getJoueursEquipe(tupleEquipe.getNomEquipe());%>
						<table class="table">
						  <thead>
						    <tr>
						      <th scope="col">Matricule</th>
						      <th scope="col">Prénom</th>
						      <th scope="col">Nom</th>
						    </tr>
						  </thead>
						 <tbody>
			     	   <%if(listParticipants.isEmpty()) { %>
							  </tbody>
					 		</table> 
			     	   <%} else {
			                for (TupleParticipant participant : listParticipants) {%>
		                		  <tr>
								      <td><%=Integer.toString(participant.getMatricule())%></td>
								      <td><%=participant.getPrenom()%></td>
								      <td><%=participant.getNom()%></td>
								  </tr>
			                <%}%>
			                	</tbody>
					 		</table>
				 	   <%}%> 
			            	<br>
			            	<table class="table">
							  <thead>
							    <tr>
							      <th scope="col">#</th>
							      <th scope="col">Equipe A</th>
							      <th scope="col">Score</th>
							      <th scope="col">Equipe B</th>
							      <th scope="col">Score</th>
							    </tr>
							  </thead>
							  <tbody>
					  <%ArrayList<TupleResultat> listResultats = centreSportifInterro.getGestionResultat().getResultat().getResultats(tupleEquipe.getNomEquipe());
			            for (TupleResultat resultat: listResultats) {%>
			            	<tr>
						      <td><%=Integer.toString(resultat.getIdResultat())%></td>
						      <td><%=resultat.getNomEquipeA() %></td>
						      <td><%=Integer.toString(resultat.getScoreEquipeA())%></td>
						      <td><%=resultat.getNomEquipeB() %></td>
						      <td><%=Integer.toString(resultat.getScoreEquipeB()) %></td>
			   			    </tr>
			            <%}%>
			          	  </tbody>
				 		</table>
					<%}%>
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
