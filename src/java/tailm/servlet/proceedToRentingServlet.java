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
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import tailm.car.CarDTO;
import tailm.cart.cartObject;
import tailm.rentingCar.rentingCarDAO;
import tailm.rentingCar.rentingCarDTO;
import tailm.rentingDetail.rentingDetailDAO;
import tailm.rentingDetail.rentingDetailDTO;

/**
 *
 * @author DELL INC
 */
@WebServlet(name = "proceedToRentingServlet", urlPatterns = {"/proceedToRentingServlet"})
public class proceedToRentingServlet extends HttpServlet {

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

        String userOrder = request.getParameter("userOrder");
        float totalAllPricePayable = Float.parseFloat(request.getParameter("totalAllPricePayable"));
        String discountID = request.getParameter("discountID");

        HttpSession session = request.getSession();
        try {
            rentingCarDAO rentingDao = new rentingCarDAO();
            rentingDetailDAO rentingDetailDao = new rentingDetailDAO();

            cartObject cart = (cartObject) session.getAttribute("CART");
            java.util.Date date = new java.util.Date();
            Date createRentingDate = new Date(date.getTime());
            rentingDao.insertCarRenting(new rentingCarDTO(0, userOrder, createRentingDate, discountID, totalAllPricePayable));

            int rentingID = rentingDao.getLastRecord();

            for (Map.Entry<String, CarDTO> entry : cart.getCartCar().entrySet()) {
                CarDTO dto = entry.getValue();
                java.util.Date dateRent = new SimpleDateFormat("yyyy-MM-dd").parse(dto.getRentalDate());
                java.util.Date dateRet = new SimpleDateFormat("yyyy-MM-dd").parse(dto.getReturnDate());
                Date rentalDate = new Date(dateRent.getTime());
                Date returnDate = new Date(dateRet.getTime());
                rentingDetailDao.inserCarRentingDetail(new rentingDetailDTO(0, rentingID, entry.getKey(), dto.getPrice(), dto.getQuantity(), rentalDate, returnDate, true));
            }
            cart.getCartCar().clear();
            session.removeAttribute("DISCOUNT");
            session.setAttribute("CART", cart);
        } catch (ParseException ex) {
            log("proceedToRentingServlet_ParseEx: " + ex.getMessage());
        } catch (SQLException ex) {
            log("proceedToRentingServlet_SQLEx: " + ex.getMessage());
        } catch (NamingException ex) {
            log("proceedToRentingServlet_NamingEx: " + ex.getMessage());
        } finally {
            response.sendRedirect("loadOrder");
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
