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
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import tailm.car.CarDAO;
import tailm.car.CarDTO;
import tailm.cart.cartObject;

/**
 *
 * @author DELL INC
 */
@WebServlet(name = "addToCartServlet", urlPatterns = {"/addToCartServlet"})
public class addToCartServlet extends HttpServlet {

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
        String rental = request.getParameter("dateRental");
        String dateReturnn = request.getParameter("dateReturn");

        String carID = request.getParameter("carID");
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

        cartObject cart = (cartObject) session.getAttribute("CART");
        try {
            if (cart == null) {
                cart = new cartObject();
            }

            CarDAO dao = new CarDAO();
            CarDTO dto = dao.findCarByID(carID, 1);
            dto.setRentalDate(rental);
            dto.setReturnDate(dateReturnn);
            Map<String, Integer> mapQuantityPossible = (Map<String, Integer>) session.getAttribute("MAPPOSSIBLE");
            if (mapQuantityPossible != null) {
                if (mapQuantityPossible.containsKey(carID)) {
                    int quantityPossible = mapQuantityPossible.get(carID);
                    if (cart.addToCart(dto, quantityPossible)) {
                        request.setAttribute("STOCK", "This Car have CarID: " + dto.getCarID() + " And Name: " + dto.getName() + " is out of stock. Please Choose Another Product");
                    }
                }
            }
            session.setAttribute("CART", cart);
        } catch (SQLException ex) {
            log("addToCartServlet_SQLEx: " + ex.getMessage());
        } catch (NamingException ex) {
            log("addToCartServlet_NamingEx: " + ex.getMessage());
        } finally {
            String url = actionSeacrh + "Servlet"
                    + "?pageIndex=" + pageIndex
                    + "&searchName=" + searchName
                    + "&searchCategory=" + searchCategory
                    + "&searchDateRent=" + searchDateRent
                    + "&searchDateReturn=" + searchDateReturn
                    + "&searchQuantity=" + searchQuantity;
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
