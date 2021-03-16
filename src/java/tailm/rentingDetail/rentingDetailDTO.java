/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tailm.rentingDetail;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author DELL INC
 */
public class rentingDetailDTO implements Serializable {

    private int rentingDetailID;
    private int rentingID;
    private String carID;
    private float price;
    private int quantity;
    private Date rentalDate;
    private Date returnDate;
    private boolean status;
    private String desriptionFeedBack;
    private float star;
    private boolean isFeedBack;
    
    public rentingDetailDTO(int rentingDetailID, int rentingID, String carID, float price, int quantity, Date rentalDate, Date returnDate, boolean status) {
        this.rentingDetailID = rentingDetailID;
        this.rentingID = rentingID;
        this.carID = carID;
        this.price = price;
        this.quantity = quantity;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
        this.status = status;
    }

    public rentingDetailDTO(String carID, int quantity, Date rentalDate, Date returnDate) {
        this.carID = carID;
        this.quantity = quantity;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
    }

 
    public int getRentingDetailID() {
        return rentingDetailID;
    }

    public void setRentingDetailID(int rentingDetailID) {
        this.rentingDetailID = rentingDetailID;
    }

    public int getRentingID() {
        return rentingID;
    }

    public void setRentingID(int rentingID) {
        this.rentingID = rentingID;
    }

    public String getCarID() {
        return carID;
    }

    public void setCarID(String carID) {
        this.carID = carID;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(Date rentalDate) {
        this.rentalDate = rentalDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public String getDesriptionFeedBack() {
        return desriptionFeedBack;
    }

    public void setDesriptionFeedBack(String desriptionFeedBack) {
        this.desriptionFeedBack = desriptionFeedBack;
    }

    public float getStar() {
        return star;
    }

    public void setStar(float star) {
        this.star = star;
    }

    public boolean isIsFeedBack() {
        return isFeedBack;
    }

    public void setIsFeedBack(boolean isFeedBack) {
        this.isFeedBack = isFeedBack;
    }
    

}
