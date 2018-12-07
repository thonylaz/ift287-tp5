package CentreSportifServlet;

import javax.servlet.http.*;

import CentreSportif.GestionCentreSportif;

import java.sql.*;


public class CentreSportifSessionListener implements HttpSessionListener
{
    public void sessionCreated(HttpSessionEvent se)
    {
    }

    public void sessionDestroyed(HttpSessionEvent se)
    {
    	System.out.println("Session d�truite pour l'utilisateur " + se.getSession().getAttribute("userID"));
        
        GestionCentreSportif centreSportifInterrogation = (GestionCentreSportif)se.getSession().getAttribute("centreSportifInterrogation");
        if (centreSportifInterrogation != null)
        {
            try
            {
                System.out.println("Fermeture de la connexion d'interrogation...");
                centreSportifInterrogation.fermer();
            }
            catch (SQLException e)
            {
                System.out.println("Impossible de fermer la connexion");
                e.printStackTrace();
            }
        }
        else
        {
            System.out.println("Aucun gestionnaire d'interrogation n'avait encore �t� cr��.");
        }
        
        GestionCentreSportif centreSportifUpdate = (GestionCentreSportif)se.getSession().getAttribute("centreSportifUpdate");
        if (centreSportifUpdate != null)
        {
            try
            {
                System.out.println("Fermeture de la connexion de mise � jour...");
                centreSportifUpdate.fermer();
            }
            catch (SQLException e)
            {
                System.out.println("Impossible de fermer la connexion");
                e.printStackTrace();
            }
        }
        else
        {
            System.out.println("Aucun gestionnaire de mise � jour n'avait encore �t� cr��.");
        }
    }
}
