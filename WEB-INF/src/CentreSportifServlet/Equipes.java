package CentreSportifServlet;

import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import CentreSportif.IFT287Exception;
import CentreSportif.Connexion;
import CentreSportif.GestionCentreSportif;
import CentreSportifServlet.CentreSportifHelper;
	
public class Equipes extends HttpServlet {
	private static final long serialVersionUID = 1L;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	try {
	    	 Integer etat = (Integer) request.getSession().getAttribute("etat");
	         if (etat == null){
	             RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/login.jsp");
	             dispatcher.forward(request, response);
	         } else if (request.getParameter("ajouterEquipe") != null) {
	        	 ajouterEquipe(request, response);
	         } else if (request.getParameter("afficherEquipe") != null) {
	        	 afficherEquipe(request, response);
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
    
    public void ajouterEquipe(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	try {
    		String nomEquipe = request.getParameter("nomEquipe");
            String nomLigue = request.getParameter("nomLigue");
            String matriculeCap = request.getParameter("matriculeCap");
            if (nomEquipe == null || nomEquipe.equals(""))
                throw new IFT287Exception("Le nom de l'équipe doit être spécifié");
            if (nomLigue == null || nomLigue.equals(""))
                throw new IFT287Exception("Le nom de la ligue doit être spécifié");
            if (matriculeCap == null || matriculeCap.equals(""))
                throw new IFT287Exception("Le matricule du capitaine doit être spécifié");
            GestionCentreSportif centreSportifUpdate = (GestionCentreSportif) request.getSession().getAttribute("centreSportifUpdate");
            // exécuter la maj en utilisant synchronized pour s'assurer
            // que le thread du servlet est le seul à exécuter une transaction
            // sur biblio
            synchronized (centreSportifUpdate) {
            	centreSportifUpdate.getGestionEquipe().ajouterEquipe(nomLigue, nomEquipe, Integer.parseInt(matriculeCap));
            	List<String> listeMessageSucces = new LinkedList<String>();
            	listeMessageSucces.add("Succes de la création de l'équipe " + nomEquipe);
                request.setAttribute("listeMessageSucces", listeMessageSucces);
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/equipes.jsp");
            dispatcher.forward(request, response);
        } catch (IFT287Exception e) {
            List<String> listeMessageErreur = new LinkedList<String>();
            listeMessageErreur.add(e.toString());
            request.setAttribute("listeMessageErreur", listeMessageErreur);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/equipes.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
        }
    }
    
    public void afficherEquipe(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	try {
    		String nomEquipe = request.getParameter("nomEquipe");
            if (nomEquipe == null || nomEquipe.equals(""))
                throw new IFT287Exception("Le nom de l'équipe doit être spécifié");
            GestionCentreSportif centreSportifUpdate = (GestionCentreSportif) request.getSession().getAttribute("centreSportifUpdate");
            // exécuter la maj en utilisant synchronized pour s'assurer
            // que le thread du servlet est le seul à exécuter une transaction
            // sur biblio
            synchronized (centreSportifUpdate) {
            	centreSportifUpdate.getGestionEquipe().afficherEquipe(nomEquipe, request);
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/equipes.jsp");
            dispatcher.forward(request, response);
        } catch (IFT287Exception e) {
            List<String> listeMessageErreur = new LinkedList<String>();
            listeMessageErreur.add(e.toString());
            request.setAttribute("listeMessageErreur", listeMessageErreur);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/equipes.jsp");
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
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        System.out.println("Servlet Equipes : GET");
        // Si on a déjà entré les informations de connexion valide
        if (CentreSportifHelper.peutProceder(getServletContext(), request, response))
        {
        	 System.out.println("Servlet Equipes : GET dispatch vers equipes.jsp");
             RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/equipes.jsp");
             dispatcher.forward(request, response);
        }
        else
        {
        	CentreSportifHelper.DispatchToLogin(request, response);
        }
    }
}
