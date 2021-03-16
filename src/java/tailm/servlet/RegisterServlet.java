/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tailm.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tailm.registration.RegistrationDAO;
import tailm.registration.RegistrationDTO;
import tailm.registration.RegistrationError;

/**
 *
 * @author DELL INC
 */
@WebServlet(name = "RegisterServlet", urlPatterns = {"/RegisterServlet"})
public class RegisterServlet extends HttpServlet {

    private final String REGISTER_PAGE = "registerPage";
    private final String LOGIN_PAGE = "loginPage";

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
        PrintWriter out = response.getWriter();
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String confirm = request.getParameter("confirm");
        String address = request.getParameter("address");
        String url = REGISTER_PAGE;
        try {
            RegistrationDAO dao = new RegistrationDAO();
            RegistrationError error = new RegistrationError();
            boolean foundError = false;
            if (!dao.checkDuplicateEmail(email)) {
                if (password.trim().length() <= 0) {
                    foundError = true;
                    error.setPasswordNotEmpty("Not Empty Password");
                } else {
                    if (password.equals(confirm)) {
                        java.util.Date date = new java.util.Date();
                        Date createDate = new Date(date.getTime());
                        String role = "new";
                        RegistrationDTO dto = new RegistrationDTO(email, password, name, phone, address, createDate, role);
                        dao.registerAccount(dto);
                        url = LOGIN_PAGE;
                    } else {
                        foundError = true;
                        error.setPasswordNotMatched("Password Not Matched!!!");
                    }
                }

            } else {
                foundError = true;
                error.setDuplicateEmail("Email has been existed!!!");
            }
            if (foundError) {
                request.setAttribute("ERROR", error);
            }

        } catch (NamingException ex) {
            log("RegisterServlet_NamingEx: " + ex.getMessage());
        } catch (SQLException ex) {
            log("RegisterServlet_SQLEX: " + ex.getMessage());
        } finally {
            ServletContext context = request.getServletContext();
            Map<String, String> listFilter = (Map<String, String>) context.getAttribute("FILTER");
            RequestDispatcher rd = request.getRequestDispatcher(listFilter.get(url));
            rd.forward(request, response);
            out.close();
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
        processRequest(request, response);
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
