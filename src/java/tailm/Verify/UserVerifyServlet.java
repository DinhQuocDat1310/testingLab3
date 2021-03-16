/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tailm.Verify;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import tailm.registration.RegistrationDTO;

/**
 *
 * @author DELL INC
 */
@WebServlet(name = "UserVerifyServlet", urlPatterns = {"/UserVerifyServlet"})
public class UserVerifyServlet extends HttpServlet {

    private final String FAIL_VERIFY = "failVerifyPage";
    private final String VERIFY_PAGE = "VerifyPage";

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
        HttpSession session = request.getSession();
        RegistrationDTO dto = (RegistrationDTO) session.getAttribute("USER");
        String url = FAIL_VERIFY;
        try {
            //feth form value
            String name = dto.getFullName();
            String email = dto.getEmail();
            //create instance object of the SendEmail Class
            sendEmail sm = new sendEmail();
            //get the 6-digit code
            String code = sm.getRandom();

            //craete new user using all information
            userVerify user = new userVerify(email, code, name);
            //call the send email method
            boolean test = sm.sendEmail(user);

            //check if the email send successfully
            if (test) {
                session.setAttribute("authcode", user);
                url = VERIFY_PAGE;
            }
        } finally {
            response.sendRedirect(url);
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
