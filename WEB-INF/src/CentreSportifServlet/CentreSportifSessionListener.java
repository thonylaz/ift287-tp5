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
        System.out.println("CentreSportifSessionListener " + se.getSession().getId());
        
        GestionCentreSportif centreSportifInterrogation = (GestionCentreSportif)se.getSession().getAttribute("centreSportifInterrogation");
        if (centreSportifInterrogation != null)
        {
           //System.out.println("connexion =" + centreSportifInterrogation.getConnexion());
            try
            {
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
            System.out.println("CentreSportif inaccessible.");
        }
        
        GestionCentreSportif centreSportifUpdate = (GestionCentreSportif)se.getSession().getAttribute("centreSportifUpdate");
        if (centreSportifUpdate != null)
        {
            //System.out.println("connexion = " + centreSportifUpdate.getConnexion());
            try
            {
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
            System.out.println("CentreSportif inaccessible.");
        }
    }
}
