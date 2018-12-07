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
	        listeMessageErreur.add("Erreur de connexion au serveur de base de donn�e");
	        listeMessageErreur.add(e.getMessage());
	        request.setAttribute("listeMessageErreur", listeMessageErreur);
	        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
	        dispatcher.forward(request, response);
	        // pour d�boggage seulement : afficher tout le contenu de
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
                throw new IFT287Exception("Le nom de l'�quipeA doit �tre sp�cifi�");
            if (scoreEquipeA == null || scoreEquipeA.equals(""))
                throw new IFT287Exception("Le le score de l'�quipeA doit �tre sp�cifi�");
            if (nomEquipeB == null || nomEquipeB.equals(""))
                throw new IFT287Exception("Le nom de l'�quipeB doit �tre sp�cifi�");
            if (scoreEquipeB == null || scoreEquipeB.equals(""))
                throw new IFT287Exception("Le le score de l'�quipeB doit �tre sp�cifi�");
            GestionCentreSportif centreSportifUpdate = (GestionCentreSportif) request.getSession().getAttribute("centreSportifUpdate");
            // ex�cuter la maj en utilisant synchronized pour s'assurer
            // que le thread du servlet est le seul � ex�cuter une transaction
            // sur biblio
            synchronized (centreSportifUpdate) {
            	centreSportifUpdate.getGestionResultat().getResultat().ajouterResultat(nomEquipeA, Integer.parseInt(scoreEquipeA), nomEquipeB, Integer.parseInt(scoreEquipeB));
            	List<String> listeMessageSucces = new LinkedList<String>();
            	listeMessageSucces.add("Ajout des scores au �quipe " + nomEquipeA + " et " + nomEquipeB);
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

    // Dans les formulaires, on utilise la m�thode POST
    // donc, si le servlet est appel� avec la m�thode GET, c'est que 
    // quelqu'un a tap� le nom du servlet dans la barre d'adresse.
    // On redirige vers la bonne page
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Servlet Resultats : GET");
        // Si on a d�j� entr� les informations de connexion valide
        if (CentreSportifHelper.peutProceder(getServletContext(), request, response)) {
        	 System.out.println("Servlet Resultats : GET dispatch vers resultats.jsp");
             RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/resultats.jsp");
             dispatcher.forward(request, response);
        } else {
        	CentreSportifHelper.DispatchToLogin(request, response);
        }
    }
}
