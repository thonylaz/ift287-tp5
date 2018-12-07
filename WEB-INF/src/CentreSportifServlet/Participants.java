package CentreSportifServlet;

import java.util.*;
import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import CentreSportif.IFT287Exception;
import CentreSportif.GestionCentreSportif;
import CentreSportifServlet.CentreSportifHelper;
	
public class Participants extends HttpServlet {
	private static final long serialVersionUID = 1L;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
       try {
    	 Integer etat = (Integer) request.getSession().getAttribute("etat");
         if (etat == null){
             RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/login.jsp");
             dispatcher.forward(request, response);
         } else if (request.getParameter("inscrireParticipant") != null) {
        	 inscrireParticipant(request, response);
         } else if (request.getParameter("supprimerParticipant") != null) {
        	 supprimerParticipant(request, response);  
         } /*else if (request.getParameter("ajouterJoueur") != null) {
        	 ajouterJoueur(request, response);  
         } else if (request.getParameter("supprimerJoueur") != null) {
        	 supprimerJoueur(request, response);  
          } else if (request.getParameter("accepterJoueur") != null) {
        	  accepterJoueur(request, response);  
          } else if (request.getParameter("refuserJoueur") != null) {
        	  refuserJoueur(request, response);  
          }*/

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
    
    public void inscrireParticipant(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	try
        {
    		String matricule = request.getParameter("matricule");
    	    String nom = request.getParameter("nom");
    	    String prenom = request.getParameter("prenom");
    	    String motDePasse = request.getParameter("motDePasse");
    	    
    	    if (matricule == null || matricule.equals(""))
                throw new IFT287Exception("La matricule du participant doit être spécifié"); 
            if (nom == null || nom.equals(""))
                throw new IFT287Exception("Le nom du participant doit être spécifié");          
            if (prenom == null || prenom.equals(""))
                throw new IFT287Exception("Le prenom du participant doit être spécifié");
            if (motDePasse == null || motDePasse.equals(""))
                throw new IFT287Exception("Le mot de passe du participant doit être spécifié");
            
            GestionCentreSportif centreSportifUpdate = (GestionCentreSportif) request.getSession().getAttribute("centreSportifUpdate");
            // exécuter la maj en utilisant synchronized pour s'assurer
            // que le thread du servlet est le seul à exécuter une transaction
            // sur biblio
            synchronized (centreSportifUpdate)
            {
            	centreSportifUpdate.getGestionParticipant().inscrireParticipant(prenom, nom, motDePasse, Integer.parseInt(matricule));
            	
            	 List<String> listeMessageSucces = new LinkedList<String>();
            	 listeMessageSucces.add("Inscription du participant: " + prenom + " " + nom);
                 request.setAttribute("listeMessageSucces", listeMessageSucces);
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/participants.jsp");
            dispatcher.forward(request, response);
        }
        catch (IFT287Exception e)
        {
            List<String> listeMessageErreur = new LinkedList<String>();
            listeMessageErreur.add(e.toString());
            request.setAttribute("listeMessageErreur", listeMessageErreur);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/participants.jsp");
            dispatcher.forward(request, response);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
        }
    }
    
    public void supprimerParticipant(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	try
        {
    		String matricule = request.getParameter("matricule");
            if (matricule == null || matricule.equals(""))
                throw new IFT287Exception("La matricule doit être spécifié");
            GestionCentreSportif centreSportifUpdate = (GestionCentreSportif) request.getSession().getAttribute("centreSportifUpdate");
            // exécuter la maj en utilisant synchronized pour s'assurer
            // que le thread du servlet est le seul à exécuter une transaction
            // sur biblio
            synchronized (centreSportifUpdate) {
            	centreSportifUpdate.getGestionParticipant().supprimerParticipant(Integer.parseInt(matricule));
            	List<String> listeMessageSucces = new LinkedList<String>();
            	listeMessageSucces.add("Succes de la suppression du participant avec la matricule " + matricule);
            	request.setAttribute("listeMessageSucces", listeMessageSucces);
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/participants.jsp");
            dispatcher.forward(request, response);
        }
        catch (IFT287Exception e)
        {
            List<String> listeMessageErreur = new LinkedList<String>();
            listeMessageErreur.add(e.toString());
            request.setAttribute("listeMessageErreur", listeMessageErreur);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/participants.jsp");
            dispatcher.forward(request, response);
        }
        catch (Exception e)
        {
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
        System.out.println("Servlet Participants : GET");
        // Si on a déjà entré les informations de connexion valide
        if (CentreSportifHelper.peutProceder(getServletContext(), request, response))
        {
        	 System.out.println("Servlet Participants : GET dispatch vers participants.jsp");
             RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/participants.jsp");
             dispatcher.forward(request, response);
        }
        else
        {
        	CentreSportifHelper.DispatchToLogin(request, response);
        }
    }
}
