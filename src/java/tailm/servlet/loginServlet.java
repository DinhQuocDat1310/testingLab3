/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tailm.servlet;

import java.io.IOException;
import java.io.PrintWriter;
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
import javax.servlet.http.HttpSession;
import tailm.Verify.VerifyRecaptcha;
import tailm.registration.RegistrationDAO;
import tailm.registration.RegistrationDTO;

/**
 *
 * @author DELL INC
 */
@WebServlet(name = "loginServlet", urlPatterns = {"/loginServlet"})
public class loginServlet extends HttpServlet {

    private final String LOAD_CAR = "loadCarDefault";
    private final String LOGIN_PAGE = "loginPage";
    private static final long serialVersionUID = -6506682026701304964L;

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
        String password = request.getParameter("password");
        String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
        boolean verify = VerifyRecaptcha.verify(gRecaptchaResponse);
        String url = LOGIN_PAGE;
        try {
            RegistrationDAO dao = new RegistrationDAO();
            if (email != null && password != null) {
                RegistrationDTO dto = dao.checkLogin(email, password);
                if (dto != null && verify) {
                    HttpSession session = request.getSession();
                    session.setAttribute("USER", dto);
                    url = LOAD_CAR;
                } else {
                    if (dao.checkDuplicateEmail(email)) {
                        if (dto == null) {
                            request.setAttribute("FAILPASS", "The password you have entered is incorrect");
                        }
                    } else {
                        request.setAttribute("FAILEMAIL", "The email or phone number you entered doesn't match any of the accounts");
                    }
                    if (!verify) {
                        request.setAttribute("CAPTCHA", "You missed the Captcha");
                    }
                }
            }
        } catch (NamingException ex) {
            log("loginServlet_NamingEx: " + ex.getMessage());
        } catch (SQLException ex) {
            log("loginServlet_SQLEx: " + ex.getMessage());
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
