/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tailm.rentingDetail;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import tailm.ulties.DBHelpers;

/**
 *
 * @author DELL INC
 */
public class rentingDetailDAO implements Serializable {

    List<rentingDetailDTO> listRentingDetail;

    public List<rentingDetailDTO> getListRentingDetail() {
        return listRentingDetail;
    }

    public int getAllQuantityMinusByCarID(String carID, String rentalDate, String returnDate) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT SUM(quantity) AS 'minus' FROM rentingDetail "
                        + "WHERE status = 1 AND carID = ? AND "
                        + "(((? >= rentalDate AND ? < returnDate ) OR (? > rentalDate AND ? <= returnDate)) OR "
                        + "((rentalDate >= ? AND rentalDate < ? ) OR ( returnDate > ? AND returnDate <= ? )))";

                ps = con.prepareStatement(sql);
                ps.setString(1, carID);
                ps.setString(2, rentalDate);
                ps.setString(3, rentalDate);
                ps.setString(4, returnDate);
                ps.setString(5, returnDate);
                ps.setString(6, rentalDate);
                ps.setString(7, returnDate);
                ps.setString(8, rentalDate);
                ps.setString(9, returnDate);
                rs = ps.executeQuery();
                if (rs.next()) {
                    return rs.getInt("minus");
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return 0;
    }

    public boolean UpdateCarsRentalExpiresStatus(Date currentDate) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "UPDATE rentingDetail SET status = ? "
                        + "WHERE ? >= returnDate ";
                ps = con.prepareStatement(sql);
                ps.setBoolean(1, false);
                ps.setDate(2, currentDate);
                int rs = ps.executeUpdate();
                if (rs > 0) {
                    return true;
                }
            }
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }

    public boolean inserCarRentingDetail(rentingDetailDTO dto) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "INSERT INTO rentingDetail(rentingID, carID, price, quantity, rentalDate, returnDate, status) "
                        + "VALUES(?,?,?,?,?,?,?)";
                ps = con.prepareStatement(sql);
                ps.setInt(1, dto.getRentingID());
                ps.setString(2, dto.getCarID());
                ps.setFloat(3, dto.getPrice());
                ps.setFloat(4, dto.getQuantity());
                ps.setDate(5, dto.getRentalDate());
                ps.setDate(6, dto.getReturnDate());
                ps.setBoolean(7, true);
                int rs = ps.executeUpdate();
                if (rs > 0) {
                    return true;
                }
            }
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }

    public void loadOrderByEmail(int rentingID) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT rentingDetailID,rentingID,carID,quantity,price,rentalDate,returnDate,status "
                        + "FROM rentingDetail WHERE rentingID = ?";
                ps = con.prepareStatement(sql);
                ps.setInt(1, rentingID);
                rs = ps.executeQuery();
                while (rs.next()) {
                    if (listRentingDetail == null) {
                        listRentingDetail = new ArrayList<>();
                    }
                    listRentingDetail.add(new rentingDetailDTO(rs.getInt("rentingDetailID"),rs.getInt("rentingID"), 
                            rs.getString("carID"), rs.getFloat("price"),
                            rs.getInt("quantity"), rs.getDate("rentalDate"),
                            rs.getDate("returnDate"), rs.getBoolean("status")));
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public List<String> checkDeleteOrder(int rentingID, Date currentDate) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<String> listOrderUnAbleToDelete = new ArrayList<>();
        try {
            con = DBHelpers.makeConnection();
            if (con != null ){
                String sql = "SELECT carID FROM rentingDetail "
                        + "WHERE rentingID = ? AND (? >= rentalDate AND ? < returnDate)";
                ps = con.prepareStatement(sql);
                ps.setInt(1, rentingID);
                ps.setDate(2, currentDate);
                ps.setDate(3, currentDate);
                rs = ps.executeQuery();
                while(rs.next()){
                    if(rs.getString("carID")!=null){
                        listOrderUnAbleToDelete.add(rs.getString("carID"));
                    }
                }
            }

        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return listOrderUnAbleToDelete;
    }
    
    public boolean updateOrderDetailStatusByRetingID(int rentingID) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "UPDATE rentingDetail SET status = ? "
                        + "WHERE rentingID = ? ";
                ps = con.prepareStatement(sql);
                ps.setBoolean(1, false);
                ps.setInt(2, rentingID);
                int rs = ps.executeUpdate();
                if (rs > 0) {
                    return true;
                }
            }
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }
}
