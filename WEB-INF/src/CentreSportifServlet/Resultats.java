package CentreSportifServlet;

import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import CentreSportif.IFT287Exception;
import CentreSportif.Connexion;
import CentreSportif.GestionCentreSportif;
import CentreSportifServlet.CentreSportifHelper;
	
public class Resultats extends HttpServlet {
	private static final long serialVersionUID = 1L;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	try {
	    	 Integer etat = (Integer) request.getSession().getAttribute("etat");
	         if (etat == null){
	             RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/login.jsp");
	             dispatcher.forward(request, response);
	         } else if (request.getParameter("ajouterResultat") != null) {
	         	ajouterResultat(request, response);
	         }
	    } catch(Exception e) {
	        List<String> listeMessageErreur = new LinkedList<String>();
	        listeMessageErreur.add("Erreur de connexion au serveur de base de donnée");
	        listeMessageErreur.add(e.getMessage());
	        request.setAttribute("listeMessageErreur", listeMessageErreur);
	        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
	        dispatcher.forward(request, response);
	        // pour déboggage seulement : afficher tout le contenu de
	        // l'exception
	        e.printStackTrace();
	    }
    }
    
    public void ajouterResultat(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	try {
    		String nomEquipeA = request.getParameter("nomEquipeA");
            String scoreEquipeA = request.getParameter("scoreEquipeA");
            String nomEquipeB = request.getParameter("nomEquipeB");
            String scoreEquipeB = request.getParameter("scoreEquipeB");
            if (nomEquipeA == null || nomEquipeA.equals(""))
                throw new IFT287Exception("Le nom de l'équipeA doit être spécifié");
            if (scoreEquipeA == null || scoreEquipeA.equals(""))
                throw new IFT287Exception("Le le score de l'équipeA doit être spécifié");
            if (nomEquipeB == null || nomEquipeB.equals(""))
                throw new IFT287Exception("Le nom de l'équipeB doit être spécifié");
            if (scoreEquipeB == null || scoreEquipeB.equals(""))
                throw new IFT287Exception("Le le score de l'équipeB doit être spécifié");
            GestionCentreSportif centreSportifUpdate = (GestionCentreSportif) request.getSession().getAttribute("centreSportifUpdate");
            // exécuter la maj en utilisant synchronized pour s'assurer
            // que le thread du servlet est le seul à exécuter une transaction
            // sur biblio
            synchronized (centreSportifUpdate) {
            	centreSportifUpdate.getGestionResultat().ajouterResultat(nomEquipeA, Integer.parseInt(scoreEquipeA), nomEquipeB, Integer.parseInt(scoreEquipeB));
            	List<String> listeMessageSucces = new LinkedList<String>();
            	listeMessageSucces.add("Ajout des scores au équipe " + nomEquipeA + " et " + nomEquipeB);
                request.setAttribute("listeMessageSucces", listeMessageSucces);
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/resultats.jsp");
            dispatcher.forward(request, response);
        } catch (IFT287Exception e) {
            List<String> listeMessageErreur = new LinkedList<String>();
            listeMessageErreur.add(e.toString());
            request.setAttribute("listeMessageErreur", listeMessageErreur);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/resultats.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
        }
    }

    // Dans les formulaires, on utilise la méthode POST
    // donc, si le servlet est appelé avec la méthode GET, c'est que 
    // quelqu'un a tapé le nom du servlet dans la barre d'adresse.
    // On redirige vers la bonne page
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Servlet Resultats : GET");
        // Si on a déjà entré les informations de connexion valide
        if (CentreSportifHelper.peutProceder(getServletContext(), request, response)) {
        	 System.out.println("Servlet Resultats : GET dispatch vers resultats.jsp");
             RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/resultats.jsp");
             dispatcher.forward(request, response);
        } else {
        	CentreSportifHelper.DispatchToLogin(request, response);
        }
    }
}
