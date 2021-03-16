/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tailm.registration;

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
public class RegistrationDAO implements Serializable {

    public boolean registerAccount(RegistrationDTO dto) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "INSERT INTO registration(userID, password, fullName, phone, address, createDate, role) "
                        + "VALUES(?, ?, ?, ?, ?, ?, ?)";
                ps = con.prepareStatement(sql);
                ps.setString(1, dto.getEmail());
                ps.setString(2, dto.getPassword());
                ps.setString(3, dto.getFullName());
                ps.setString(4, dto.getPhone());
                ps.setString(5, dto.getAddress());
                ps.setDate(6, dto.getCreateDate());
                ps.setString(7, dto.getRole());
                
                int rs = ps.executeUpdate();
                if (rs > 0) {
                    return true;
                }
            }
        } finally {
            if (con != null) {
                con.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
        return false;
    }

    public RegistrationDTO checkLogin(String email, String password) throws SQLException, NamingException {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String url = "SELECT userID, fullName, role "
                        + "FROM registration "
                        + "WHERE userID = ? AND password = ? ";
                ps = con.prepareStatement(url);
                ps.setString(1, email);
                ps.setString(2, password);
                rs = ps.executeQuery();
                if(rs.next()){
                    return new RegistrationDTO(rs.getString("userID"), rs.getString("fullName"), rs.getString("role"));
                }
            }
        } finally {
            if (con != null) {
                con.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
        return null;
    }

    public boolean checkDuplicateEmail(String email) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT userID "
                        + "FROM registration "
                        + "WHERE userID = ?";
                ps = con.prepareStatement(sql);
                ps.setString(1, email);
                rs = ps.executeQuery();
                if (rs.next()) {
                    return true;
                }
            }
        } finally {
            if (con != null) {
                con.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
        return false;
    }
    
    public boolean updateRoleAfterVerify(String email) throws NamingException, SQLException{
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "UPDATE registration SET role = ? "
                        + "WHERE userID = ?";
                ps = con.prepareStatement(sql);
                ps.setString(1, "active");
                ps.setString(2, email);
                int rs = ps.executeUpdate();
                if (rs > 0) {
                    return true;
                }
            }
        } finally {
            if (con != null) {
                con.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
        return false;
    }

}
