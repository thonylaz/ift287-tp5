package CentreSportifServlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;


public class Logout extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // invalider la session pour libérer les ressources associées à la
        // session
        request.getSession().invalidate();
        RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
        dispatcher.forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doGet(request, response);
    }
}
