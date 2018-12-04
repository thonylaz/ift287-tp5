package CentreSportifServlet;

import java.sql.*;
import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import CentreSportif.IFT287Exception;
import CentreSportif.GestionCentreSportif;
import CentreSportifServlet.CentreSportifConstantes;



public class Login extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            HttpSession session = request.getSession();
            // fermer la session si elle a déjà  été ouverte lors d'un appel
            // précédent
            // survient lorsque l'usager recharge la page login.jsp
            if (session.getAttribute("etat") != null)
            {
                // pour déboggage seulement : afficher no session et information
                System.out.println("GestionCentreSportif: session déjà cr�e; id=" + session.getId());
                // la méthode invalidate appelle le listener
                // CentreSportifSessionListener; cette classe est chargée lors du
                // démarrage de
                // l'application par le serveur (voir le fichier web.xml)
                session.invalidate();
                session = request.getSession();
                System.out.println("GestionCentreSportif: session invalide");
            }

            // lecture des parametres du formulaire login.jsp
            String userId = request.getParameter("userIdBD");
            String motDePasse = request.getParameter("motDePasseBD");
            String serveur = request.getParameter("serveur");
            String bd = request.getParameter("bd");

            if (serveur != null)
            {
                /*
                 * ouvrir une connexion avec la BD et créer les gestionnaires et
                 * stocker dans la session. On ouvre une session en lecture
                 * readcommited pour les interrogations seulement et une autre
                 * en mode serialisable, pour les transactions
                 */
                System.out.println("Login: session id=" + session.getId());
                GestionCentreSportif centreSportifInterrogation = new GestionCentreSportif(serveur, bd, userId, motDePasse);
                //centreSportifInterrogation.getConnexion().setIsolationReadCommited();
                session.setAttribute("biblioInterrogation", centreSportifInterrogation);
                GestionCentreSportif centreSportifUpdate = new GestionCentreSportif(serveur, bd, userId, motDePasse);
                session.setAttribute("biblioUpdate", centreSportifUpdate);

                // afficher le menu membre en appelant la page
                // selectionParticipant.jsp
                // tous les JSP sont dans /WEB-INF/
                // ils ne peuvent pas etre appelés directement par l'utilisateur
                // seulement par un autre JSP ou un servlet
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/selectionParticipant.jsp");
                dispatcher.forward(request, response);
                session.setAttribute("etat", new Integer(CentreSportifConstantes.CONNECTE));
            }
            else
            {
                throw new SQLException("Vous devez vous connecter au serveur.");
            }
        }
        catch (SQLException e)
        {
            List<String> listeMessageErreur = new LinkedList<String>();
            listeMessageErreur.add("Erreur de connexion au serveur");
            listeMessageErreur.add(e.toString());
            request.setAttribute("listeMessageErreur", listeMessageErreur);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
            dispatcher.forward(request, response);
            // pour d�boggage seulement : afficher tout le contenu de
            // l'exception
            e.printStackTrace();
        }
        catch (IFT287Exception e)
        {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
        }
    }

    // Dans les formulaires, on utilise la méthode POST
    // donc, si le servlet est appelé avec la méthode GET
    // on appelle POST
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doPost(request, response);
    }

} // class
