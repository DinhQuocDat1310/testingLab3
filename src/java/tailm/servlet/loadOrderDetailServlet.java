/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tailm.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;
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
import tailm.feedback.feedbackDAO;
import tailm.feedback.feedbackDTO;
import tailm.registration.RegistrationDTO;
import tailm.rentingDetail.rentingDetailDAO;
import tailm.rentingDetail.rentingDetailDTO;


/**
 *
 * @author DELL INC
 */
@WebServlet(name = "loadOrderDetailServlet", urlPatterns = {"/loadOrderDetailServlet"})
public class loadOrderDetailServlet extends HttpServlet {

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
        int rentingID = Integer.parseInt(request.getParameter("rentingID"));
        HttpSession session = request.getSession();
        try {
            RegistrationDTO user = (RegistrationDTO) session.getAttribute("USER");
            rentingDetailDAO dao = new rentingDetailDAO();
            feedbackDAO feDAO = new  feedbackDAO();
            dao.loadOrderByEmail(rentingID);
            Date currentDate = new Date();
            List<rentingDetailDTO> listDetail =  dao.getListRentingDetail();
            if(listDetail!=null){
                for (rentingDetailDTO detailDTO : listDetail) {
                    feedbackDTO dto = feDAO.loadFeedBackDetailByUserID(user.getEmail(),detailDTO.getRentingDetailID());
                    long a = currentDate.getTime() - detailDTO.getReturnDate().getTime();
                    if(a>=0){
                        detailDTO.setIsFeedBack(true);
                    }else{
                        detailDTO.setIsFeedBack(false);
                    }
                    if(dto!=null){
                        detailDTO.setDesriptionFeedBack(dto.getDescription());
                        detailDTO.setStar(dto.getStar());
                    }
                }
            }
            request.setAttribute("LISTDETAIL", listDetail);
        }catch(SQLException ex){
            log("loadOrderServlet_SQLEx: "+ex.getMessage());
        }catch(NamingException ex){
            log("loadOrderServlet_NamingEx: "+ex.getMessage());
        }finally{
            ServletContext context = request.getServletContext();
            Map<String, String> listFilter = (Map<String, String>) context.getAttribute("FILTER");
            RequestDispatcher rd = request.getRequestDispatcher(listFilter.get("historyOrderDetailPage"));
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
