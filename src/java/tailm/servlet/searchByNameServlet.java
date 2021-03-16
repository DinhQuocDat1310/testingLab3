/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tailm.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
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
@WebServlet(name = "searchByNameServlet", urlPatterns = {"/searchByNameServlet"})
public class searchByNameServlet extends HttpServlet {

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
        
        String searchName = request.getParameter("searchName");
        String searchDateRent = request.getParameter("searchDateRent");
        String searchDateReturn = request.getParameter("searchDateReturn");
        int searchQuantity = Integer.parseInt(request.getParameter("searchQuantity"));
        
        
        try {
            CarDAO dao = new CarDAO();
            rentingDetailDAO reDetailDAO = new rentingDetailDAO();
            HttpSession session = request.getSession();

            dao.loadCarByName(searchName);
            List<CarDTO> list = dao.getListCar();

            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    int quantityRented = reDetailDAO.getAllQuantityMinusByCarID(list.get(i).getCarID(),
                            searchDateRent, searchDateReturn);
                    if (list.get(i).getQuantity() - quantityRented < searchQuantity) {
                        list.remove(i);
                        i--;
                    }
                }

                cartObject cart = (cartObject) session.getAttribute("CART");
                if (cart != null) {
                    if (cart.getCartCar() != null) {
                        for (Map.Entry<String, CarDTO> entry : cart.getCartCar().entrySet()) {
                            for (int i = 0; i < list.size(); i++) {
                                if (list.get(i).getCarID().compareToIgnoreCase(entry.getKey()) == 0) {
                                    list.get(i).setOrderStatus(true);
                                }
                            }
                        }
                    }
                }
                int totalPage = dao.countTotalPageByName(list.size() - 1);
                session.setAttribute("LISTSEARCH", list);
                session.setAttribute("TOTALPAGESEARCH", totalPage);
            }

        } catch (SQLException ex) {
            log("searchByNameServlet_SQLEx: " + ex.getMessage());
        } catch (NamingException ex) {
            log("searchByNameServlet_NamingEx: " + ex.getMessage());
        } finally {
            ServletContext context = request.getServletContext();
            Map<String, String> listFilter = (Map<String, String>) context.getAttribute("FILTER");
            RequestDispatcher rd = request.getRequestDispatcher(listFilter.get("loadListBySearch"));
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
