package CentreSportifServlet;

import javax.servlet.*;
import java.util.*;



public class CentreSportifContextListener implements ServletContextListener
{
    public void contextInitialized(ServletContextEvent sce)
    {
        System.out.println("Contexte du CentreSportif WEB démarré : " + sce.getServletContext().getServletContextName());
        System.out.println("Voici les paramètres du contexte tels que définis dans web.xml");
        Enumeration<String> initParams = sce.getServletContext().getInitParameterNames();
        while (initParams.hasMoreElements())
        {
            String name = (String) initParams.nextElement();
            System.out.println(name + ":" + sce.getServletContext().getInitParameter(name));
        }
    }

    public void contextDestroyed(ServletContextEvent sce)
    {
        System.out.println("Le contexte de l'application GestionCentreSportif vient d'etre détruit.");
    }
}
