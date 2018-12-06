package CentreSportifServlet;

import java.util.*;
import java.io.*;
import java.sql.Date;

import javax.servlet.*;
import javax.servlet.http.*;

import CentreSportif.IFT287Exception;
import CentreSportif.GestionCentreSportif;
import CentreSportif.Connexion;
import CentreSportifServlet.CentreSportifHelper;
import CentreSportifServlet.CentreSportifConstantes;
	
public class Ligues extends HttpServlet {
	private static final long serialVersionUID = 1L;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    	 try {
         	 Integer etat = (Integer) request.getSession().getAttribute("etat");
              if (etat == null){
                  RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/login.jsp");
                  dispatcher.forward(request, response);
              }
              else if (request.getParameter("ajouterLigue") != null) {
             	 ajouterLigue(request, response);
              }
//              else if (request.getParameter("renouveler") != null)
//                  traiterRenouveler(request, response);
//              else if (request.getParameter("emprunter") != null)
//                  traiterEmprunter(request, response);
//              else if (request.getParameter("selectionMembre") != null)
//                  traiterSelectionMembre(request, response);
//              else
//              {
//                  List<String> listeMessageErreur = new LinkedList<String>();
//                  listeMessageErreur.add("Choix non reconnu");
//                  request.setAttribute("listeMessageErreur", listeMessageErreur);
//                  RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/listePretMembre.jsp");
//                  dispatcher.forward(request, response);
//              }

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
    
    public void ajouterLigue(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	try
        {
    		String nomLigue = request.getParameter("nomLigue");
            String nbJoueur = request.getParameter("nbJoueur");
            if ( nomLigue == null || nomLigue.equals(""))
                throw new IFT287Exception("Le nom de la ligue doit �tre sp�cifi�");
            if (nbJoueur == null || nbJoueur.equals(""))
                throw new IFT287Exception("Le nombre de joueurs de la ligue doit �tre sp�cifi�");
            GestionCentreSportif centreSportifUpdate = (GestionCentreSportif) request.getSession().getAttribute("centreSportifUpdate");
            // ex�cuter la maj en utilisant synchronized pour s'assurer
            // que le thread du servlet est le seul � ex�cuter une transaction
            // sur biblio
            synchronized (centreSportifUpdate)
            {
            	centreSportifUpdate.getGestionLigue().ajouterLigue(nomLigue, Integer.parseInt(nbJoueur));
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/ligues.jsp");
            dispatcher.forward(request, response);
        }
        catch (IFT287Exception e)
        {
            List<String> listeMessageErreur = new LinkedList<String>();
            listeMessageErreur.add(e.toString());
            request.setAttribute("listeMessageErreur", listeMessageErreur);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/listePretMembre.jsp");
            dispatcher.forward(request, response);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
        }
    }

    // Dans les formulaires, on utilise la m�thode POST
    // donc, si le servlet est appel� avec la m�thode GET, c'est que 
    // quelqu'un a tap� le nom du servlet dans la barre d'adresse.
    // On redirige vers la bonne page
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        System.out.println("Servlet Ligues : GET");
        // Si on a d�j� entr� les informations de connexion valide
        if (CentreSportifHelper.peutProceder(getServletContext(), request, response))
        {
        	 System.out.println("Servlet Ligues : GET dispatch vers ligues.jsp");
             RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/ligues.jsp");
             dispatcher.forward(request, response);
        }
        else
        {
        	CentreSportifHelper.DispatchToLogin(request, response);
        }
    }
}
