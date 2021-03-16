/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tailm.rentingCar;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author DELL INC
 */
public class rentingCarDTO implements Serializable {

    private int rentingID;
    private String userID;
    private Date createDate;
    private String discountID;
    private float totalPrice;
    private boolean status;
    
    public rentingCarDTO(int rentingID, String userID, Date createDate, String discountID, float totalPrice) {
        this.rentingID = rentingID;
        this.userID = userID;
        this.createDate = createDate;
        this.discountID = discountID;
        this.totalPrice = totalPrice;
    }

    public rentingCarDTO(int rentingID, String userID, Date createDate, String discountID, float totalPrice, boolean status) {
        this.rentingID = rentingID;
        this.userID = userID;
        this.createDate = createDate;
        this.discountID = discountID;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    

    public int getRentingID() {
        return rentingID;
    }

    public void setRentingID(int rentingID) {
        this.rentingID = rentingID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getDiscountID() {
        return discountID;
    }

    public void setDiscountID(String discountID) {
        this.discountID = discountID;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    
}
