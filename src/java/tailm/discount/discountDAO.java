/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tailm.discount;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import tailm.ulties.DBHelpers;

/**
 *
 * @author DELL INC
 */
public class discountDAO implements Serializable{
    public discountDTO getPercentDiscountByID(String discountID, Date CurrentDate) throws NamingException, SQLException{
          Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String url = "SELECT discountID, percentDiscount, description "
                        + "FROM discount WHERE discountID = ? "
                        + "AND ? BETWEEN effectiveDate AND expirationDate ";
                ps = con.prepareStatement(url);
                ps.setString(1, discountID);
                ps.setDate(2, CurrentDate);
                rs = ps.executeQuery();
                if(rs.next()){
                    return new discountDTO(rs.getString("discountID"), 
                            rs.getFloat("percentDiscount"), rs.getString("description"));
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
}
