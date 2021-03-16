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
import javax.servlet.http.HttpSession;
import tailm.discount.discountDAO;
import tailm.discount.discountDTO;

/**
 *
 * @author DELL INC
 */
@WebServlet(name = "applyDiscountServlet", urlPatterns = {"/applyDiscountServlet"})
public class applyDiscountServlet extends HttpServlet {

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
        String discountID = request.getParameter("discountID");
        HttpSession session = request.getSession();
        try {
            java.util.Date date = new java.util.Date();
            Date currentDate = new Date(date.getTime());
            discountDAO dao = new discountDAO();
            discountDTO dto = dao.getPercentDiscountByID(discountID, currentDate);
            if (dto != null) {
                dto.setDiscountPercent((100 - dto.getDiscountPercent()) / 100);
                session.setAttribute("DISCOUNT", dto);
                request.setAttribute("STATUSDIS", "Apply Voucher Successful.");
            }else{
                request.setAttribute("STATUSDIS", "Apply Voucher Failed. Cannot Found Voucher You Input!!!");
            }

        } catch (SQLException ex) {
            log("applyDiscountServlet_SQLEx: " + ex.getMessage());
        } catch (NamingException ex) {
            log("applyDiscountServlet_NamingEx: " + ex.getMessage());
        } finally {
            ServletContext context = request.getServletContext();
            Map<String, String> listFilter = (Map<String, String>) context.getAttribute("FILTER");
            RequestDispatcher rd = request.getRequestDispatcher(listFilter.get("viewcartPage"));
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
