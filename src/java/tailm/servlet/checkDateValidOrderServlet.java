/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tailm.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author DELL INC
 */
@WebServlet(name = "checkDateValidOrderServlet", urlPatterns = {"/checkDateValidOrderServlet"})
public class checkDateValidOrderServlet extends HttpServlet {

    private final String CHECK_DATE_RENT_POSSIBLE = "checkDateRentPossibleServlet";
    private final String ADD_TO_CART = "addToCartServlet";

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
        String carID = request.getParameter("carID");

        String rental = request.getParameter("dateRental");
        String dateReturnn = request.getParameter("dateReturn");

        String actionSeacrh = request.getParameter("actionSeacrh");
        String searchCategory = request.getParameter("searchCategory");
        String searchName = request.getParameter("searchName");
        String searchDateRent = request.getParameter("searchDateRent");
        String searchDateReturn = request.getParameter("searchDateReturn");
        String searchQuantityString = request.getParameter("searchQuantity");
        int searchQuantity = 0;
        if (searchQuantityString != null) {
            if (searchQuantityString.length() > 0) {
                searchQuantity = Integer.parseInt(searchQuantityString);
            }
        }
        
        int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));

        HttpSession session = request.getSession();
//        cartObject cart = (cartObject) session.getAttribute("CART");
        String url = CHECK_DATE_RENT_POSSIBLE;
        try {
            if (rental != null && dateReturnn != null) {
                Date dateRental = new SimpleDateFormat("yyyy-MM-dd").parse(rental);
                Date dateReturn = new SimpleDateFormat("yyyy-MM-dd").parse(dateReturnn);
                long numberOfDate = (dateReturn.getTime() - dateRental.getTime()) / (60 * 60 * 24 * 1000);
                if (numberOfDate <= 0) {
                    if (dateReturn.getTime() < dateRental.getTime()) {
                        request.setAttribute("ErrorDate", "Date return Must be bigger Date Rental!!!");
                    } else {
                        request.setAttribute("ErrorDate", "The Date is invalid. Rent distance is at least a day!!!");
                    }
                    url = actionSeacrh + "Servlet"
                            + "?pageIndex=" + pageIndex
                            + "&searchName=" + searchName
                            + "&searchCategory=" + searchCategory
                            + "&searchDateRent=" + searchDateRent
                            + "&searchDateReturn=" + searchDateReturn
                            + "&searchQuantity=" + searchQuantity;
                }
            } else {
                url = ADD_TO_CART;
            }
        } catch (ParseException ex) {
            log("checkDateValidOrderServlet_ParseEx: " + ex.getMessage());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
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
