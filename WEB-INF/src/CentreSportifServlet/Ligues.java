package CentreSportifServlet;

import java.util.*;
import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import CentreSportif.IFT287Exception;
import CentreSportif.GestionCentreSportif;
import CentreSportifServlet.CentreSportifHelper;
	
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
              } else if (request.getParameter("ajouterLigue") != null) {
             	 ajouterLigue(request, response);
              } else if (request.getParameter("supprimerLigue") != null) {
            	supprimerLigue(request, response);  
              } else if (request.getParameter("afficherLigue") != null) {
              	afficherLigue(request, response);  
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
    
    public void ajouterLigue(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	try {
    		String nomLigue = request.getParameter("nomLigue");
            String nbJoueur = request.getParameter("nbJoueur");
            if (nomLigue == null || nomLigue.equals(""))
                throw new IFT287Exception("Le nom de la ligue doit être spécifié");
            if (nbJoueur == null || nbJoueur.equals(""))
                throw new IFT287Exception("Le nombre de joueurs de la ligue doit être spécifié");
            GestionCentreSportif centreSportifUpdate = (GestionCentreSportif) request.getSession().getAttribute("centreSportifUpdate");
            // exécuter la maj en utilisant synchronized pour s'assurer
            // que le thread du servlet est le seul à exécuter une transaction
            // sur biblio
            synchronized (centreSportifUpdate) {
            	centreSportifUpdate.getGestionLigue().ajouterLigue(nomLigue, Integer.parseInt(nbJoueur));
            	List<String> listeMessageSucces = new LinkedList<String>();
            	listeMessageSucces.add("Succes de la création de la ligue " + nomLigue);
                request.setAttribute("listeMessageSucces", listeMessageSucces);
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/ligues.jsp");
            dispatcher.forward(request, response);
        } catch (IFT287Exception e) {
            List<String> listeMessageErreur = new LinkedList<String>();
            listeMessageErreur.add(e.toString());
            request.setAttribute("listeMessageErreur", listeMessageErreur);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/ligues.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
        }
    }
    
    public void supprimerLigue(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	try {
    		String nomLigue = request.getParameter("nomLigue");
            if (nomLigue == null || nomLigue.equals(""))
                throw new IFT287Exception("Le nom de la ligue doit être spécifié");
            GestionCentreSportif centreSportifUpdate = (GestionCentreSportif) request.getSession().getAttribute("centreSportifUpdate");
            // exécuter la maj en utilisant synchronized pour s'assurer
            // que le thread du servlet est le seul à exécuter une transaction
            // sur biblio
            synchronized (centreSportifUpdate) {
            	centreSportifUpdate.getGestionLigue().supprimerLigue(nomLigue);
            	List<String> listeMessageSucces = new LinkedList<String>();
            	listeMessageSucces.add("Succes de la suppression de la ligue " + nomLigue);
            	request.setAttribute("listeMessageSucces", listeMessageSucces);
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/ligues.jsp");
            dispatcher.forward(request, response);
        } catch (IFT287Exception e) {
            List<String> listeMessageErreur = new LinkedList<String>();
            listeMessageErreur.add(e.toString());
            request.setAttribute("listeMessageErreur", listeMessageErreur);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/ligues.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
        }
    }
    
    public void afficherLigue(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	try {
    		String nomLigue = request.getParameter("nomLigue");
            if (nomLigue == null || nomLigue.equals(""))
                throw new IFT287Exception("Le nom de la ligue doit être spécifié");
            GestionCentreSportif centreSportifUpdate = (GestionCentreSportif) request.getSession().getAttribute("centreSportifUpdate");
            // exécuter la maj en utilisant synchronized pour s'assurer
            // que le thread du servlet est le seul à exécuter une transaction
            // sur biblio
            synchronized (centreSportifUpdate) {
            	centreSportifUpdate.getGestionLigue().afficherLigue(nomLigue, request);
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/ligues.jsp");
            dispatcher.forward(request, response);
        } catch (IFT287Exception e) {
            List<String> listeMessageErreur = new LinkedList<String>();
            listeMessageErreur.add(e.toString());
            request.setAttribute("listeMessageErreur", listeMessageErreur);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/ligues.jsp");
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
        System.out.println("Servlet Ligues : GET");
        // Si on a déjà entré les informations de connexion valide
        if (CentreSportifHelper.peutProceder(getServletContext(), request, response))
        {
//        	GestionCentreSportif centreSportifUpdate = (GestionCentreSportif) request.getSession().getAttribute("centreSportifUpdate");
//            // exécuter la maj en utilisant synchronized pour s'assurer
//            // que le thread du servlet est le seul à exécuter une transaction
//            // sur biblio
//            synchronized (centreSportifUpdate) {
//            	//centreSportifUpdate.getGestionLigue(request).allerChercherLigues();
//            	List<String> listeMessageSucces = new LinkedList<String>();
//            	listeMessageSucces.add("Succes de la suppression de la ligue " + nomLigue);
//            	request.setAttribute("listeMessageSucces", listeMessageSucces);
//            }
        	
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
