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
import tailm.car.CarDAO;
import tailm.car.CarDTO;
import tailm.cart.cartObject;
import tailm.rentingDetail.rentingDetailDAO;

/**
 *
 * @author DELL INC
 */
@WebServlet(name = "checkDateRentServlet", urlPatterns = {"/checkDateRentServlet"})
public class checkDateRentServlet extends HttpServlet {

    private final String VIEW_CART_PAGE = "viewcartPage";
    private final String CHECK_OUT = "proceedToRenting";
    
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
        String url = VIEW_CART_PAGE;
        try  {
            cartObject cart = (cartObject) session.getAttribute("CART");
            rentingDetailDAO reDetailDAO = new rentingDetailDAO();
            CarDAO carDao = new CarDAO();

            boolean success = true;
            for (Map.Entry<String, CarDTO> entry : cart.getCartCar().entrySet()) {
                CarDTO dto = entry.getValue();
                int totalQuanity = carDao.countQuantityCarByID(entry.getKey());
                int quantityRented = reDetailDAO.getAllQuantityMinusByCarID(entry.getKey(), 
                        dto.getRentalDate(), dto.getReturnDate());
                int quantity = totalQuanity - quantityRented;
                if(quantity<=0){
                    success = false;
                    request.setAttribute("OUTSTOCK", "SOME CAR IS OUT OF STOCK. PLEASE CHECK YOUR ORDER AGAIN!!!");
                    cart.deleCarFormCart(entry.getKey());
                }
            }
            if(success){
                url = CHECK_OUT;
            }
            session.setAttribute("CART", cart);
        }catch(SQLException ex){
            log("checkDateRentServlet_SQLEx: "+ex.getMessage());
        }catch(NamingException ex){
            log("checkDateRentServlet_NamingEx: "+ex.getMessage());
        }finally{
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
