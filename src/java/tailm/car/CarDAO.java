/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tailm.car;

import java.io.Serializable;
import java.sql.Connection;
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
public class CarDAO implements Serializable {

    private List<CarDTO> listCar;
    private final int PAGE_PAGING = 3;
    private List listCategory;

    public List<CarDTO> getListCar() {
        return listCar;
    }

    public List getListCategory() {
        return listCategory;
    }

    public void loadCarDefault(int paging) throws SQLException, NamingException {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int pageIndex = (paging - 1) * PAGE_PAGING;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String url = "SELECT carID,name, color, year, category, image, price, quantity "
                        + "FROM car WHERE quantity > 0 "
                        + "ORDER BY year OFFSET ? ROWS FETCH NEXT " + PAGE_PAGING + " ROWS ONLY";
                ps = con.prepareStatement(url);
                ps.setInt(1, pageIndex);
                rs = ps.executeQuery();
                while (rs.next()) {
                    if (this.listCar == null) {
                        this.listCar = new ArrayList<>();
                    }
                    this.listCar.add(new CarDTO(rs.getString("carID"), rs.getString("name"),
                            rs.getString("color"), rs.getInt("year"), rs.getString("category"),
                            rs.getString("image"), rs.getFloat("price"), rs.getInt("quantity")));
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
    }

    public void loadCarByCategory(String category) throws SQLException, NamingException {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String url = "SELECT carID,name, color, year, category, image, price, quantity "
                        + "FROM car WHERE quantity > 0 AND category = ? ";
                ps = con.prepareStatement(url);
                ps.setString(1, category);
                rs = ps.executeQuery();
                while (rs.next()) {
                    if (this.listCar == null) {
                        this.listCar = new ArrayList<>();
                    }
                    this.listCar.add(new CarDTO(rs.getString("carID"), rs.getString("name"),
                            rs.getString("color"), rs.getInt("year"), rs.getString("category"),
                            rs.getString("image"), rs.getFloat("price"), rs.getInt("quantity")));
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
    }

    public void loadCarByName(String searchValue) throws SQLException, NamingException {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String url = "SELECT carID,name, color, year, category, image, price, quantity "
                        + "FROM car WHERE quantity > 0 AND name LIKE ? ";
                ps = con.prepareStatement(url);
                ps.setString(1, "%" + searchValue + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    if (this.listCar == null) {
                        this.listCar = new ArrayList<>();
                    }
                    this.listCar.add(new CarDTO(rs.getString("carID"), rs.getString("name"),
                            rs.getString("color"), rs.getInt("year"), rs.getString("category"),
                            rs.getString("image"), rs.getFloat("price"), rs.getInt("quantity")));
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
    }

    public int countTotalPageDefault() throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT COUNT(carID) AS 'totalRecord' FROM car";
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();
                if (rs.next()) {
                    return (int) Math.ceil(rs.getInt("totalRecord") / (double) PAGE_PAGING);
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
        return -1;
    }

    public int countTotalPageByName(int totalRecord) throws NamingException, SQLException {
        if (totalRecord > 0) {
            return (int) Math.ceil(totalRecord / (double) PAGE_PAGING);
        } else {
            return 0;
        }
    }

    public int countTotalPageByCategory(int totalRecord) throws NamingException, SQLException {

        if (totalRecord > 0) {
            return (int) Math.ceil(totalRecord / (double) PAGE_PAGING);
        } else {
            return 0;
        }

    }

    public CarDTO findCarByID(String carID, int quantity) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT carID,name,color,year,category,image,price "
                        + "FROM car WHERE carID = ?";
                ps = con.prepareStatement(sql);
                ps.setString(1, carID);
                rs = ps.executeQuery();
                if (rs.next()) {
                    return new CarDTO(rs.getString("carID"), rs.getString("name"),
                            rs.getString("color"), rs.getInt("year"), rs.getString("category"),
                            rs.getString("image"), rs.getFloat("price"), quantity);
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

    public int countQuantityCarByID(String carID) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT quantity "
                        + "FROM car WHERE carID = ?";
                ps = con.prepareStatement(sql);
                ps.setString(1, carID);
                rs = ps.executeQuery();
                if (rs.next()) {
                    return rs.getInt("quantity");
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
        return -1;
    }

    public void loadAllCategory() throws SQLException, NamingException {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String url = "SELECT DISTINCT category FROM car";
                ps = con.prepareStatement(url);
                rs = ps.executeQuery();
                while (rs.next()) {
                    if (this.listCategory == null) {
                        this.listCategory = new ArrayList<>();
                    }
                    this.listCategory.add(rs.getString("category"));
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
    }
}
