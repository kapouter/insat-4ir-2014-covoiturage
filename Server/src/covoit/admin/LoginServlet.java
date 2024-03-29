/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package covoit.admin;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import covoit.Admin;
import covoit.lib.BCrypt;
import java.security.InvalidParameterException;
import java.sql.SQLException;
import javax.json.Json;
import javax.json.JsonObject;

@WebServlet(name="LoginServlet", urlPatterns={"http://localhost:8080/MyFirstWebProject/LoginServlet"})
public class LoginServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LoginServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if (!login.isEmpty() && !password.isEmpty()) {
            Admin currentAdmin = null;
            try {
                currentAdmin = Admin.load(login);
            } catch (SQLException e) {
                String erreur = "admin non existant " + e.getMessage();
                request.setAttribute("erreur", erreur);
                request.getRequestDispatcher("FormConnexion.jsp").forward(request, response);
                return;
            }

            if (login != null && password.equals(currentAdmin.getPassword())) {
                request.getSession().setAttribute("admin", currentAdmin);
                request.getRequestDispatcher("content.jsp").forward(request, response);
            } else {
                String erreur = "mauvais mot de passe";
                request.setAttribute("erreur", erreur);
                request.getRequestDispatcher("FormConnexion.jsp").forward(request, response);
            }
        } else {
            response.sendRedirect("FormConnexion.jsp");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
