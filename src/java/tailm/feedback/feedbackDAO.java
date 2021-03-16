/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tailm.feedback;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import tailm.ulties.DBHelpers;

/**
 *
 * @author DELL INC
 */
public class feedbackDAO implements Serializable {

    public boolean insertCarRenting(String userID, int rentingDetailID, float star, String description) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "INSERT INTO feedBack(userID, rentingDetailID,star, description) "
                        + "VALUES(?, ?, ?, ?)";
                ps = con.prepareStatement(sql);
                ps.setString(1, userID);
                ps.setInt(2, rentingDetailID);
                ps.setFloat(3, star);
                ps.setString(4, description);
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

    public boolean updateCarRenting(String userID, int rentingDetailID, float star, String description) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "UPDATE feedBack SET description = ?, star = ? "
                        + "WHERE userID = ? AND rentingDetailID = ? ";
                ps = con.prepareStatement(sql);
                ps.setString(1, description);
                ps.setFloat(2, star);
                ps.setString(3, userID);
                ps.setInt(4, rentingDetailID);
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

    public boolean checkFeedbackDetailByUserID(int rentingDetailID, String userID) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT userID FROM feedBack "
                        + "WHERE userID = ? and rentingDetailID = ?";
                ps = con.prepareStatement(sql);
                ps.setString(1, userID);
                ps.setInt(2, rentingDetailID);
                rs = ps.executeQuery();
                if (rs.next()) {
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

    public feedbackDTO loadFeedBackDetailByUserID(String userID, int rentingDetailID) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT userID,rentingDetailID, star, description  "
                        + "FROM feedBack WHERE userID = ? AND rentingDetailID = ?";
                ps = con.prepareStatement(sql);
                ps.setString(1, userID);
                ps.setInt(2, rentingDetailID);
                rs = ps.executeQuery();
                if (rs.next()) {
                    return (new feedbackDTO(rs.getString("userID"), rs.getInt("rentingDetailID"),
                            rs.getFloat("star"), rs.getString("description")));
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
        return null;
    }
}
