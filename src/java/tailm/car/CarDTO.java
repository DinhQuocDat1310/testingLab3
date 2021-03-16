/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tailm.car;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DELL INC
 */
public class CarDTO implements Serializable {

    private String carID;
    private String name;
    private String color;
    private int year;
    private String category;
    private String image;
    private float price;
    private int quantity;
    private String rentalDate;
    private String returnDate;
    private long numberDay;
    private boolean orderStatus;

    public CarDTO(String carID, String name, String color, int year, String category, String image, float price, int quantity) {
        this.carID = carID;
        this.name = name;
        this.color = color;
        this.year = year;
        this.category = category;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
    }

    public CarDTO(String carID, String name, String color, int year, String category, String image, float price, int quantity, String rentalDate, String returnDate) {
        this.carID = carID;
        this.name = name;
        this.color = color;
        this.year = year;
        this.category = category;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;

    }

    public long getNumberDay() {
        try {

            if (rentalDate != null && this.returnDate != null) {
                Date dateRental = new SimpleDateFormat("yyyy-MM-dd").parse(this.rentalDate);
                Date dateReturn = new SimpleDateFormat("yyyy-MM-dd").parse(this.returnDate);
                numberDay = ((dateReturn.getTime() - dateRental.getTime()) / (60 * 60 * 24 * 1000));
            }
        } catch (ParseException ex) {
            Logger.getLogger(CarDTO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return numberDay;
    }

    public String getCarID() {
        return carID;
    }

    public void setCarID(String carID) {
        this.carID = carID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(String rentalDate) {
        this.rentalDate = rentalDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public boolean isOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(boolean orderStatus) {
        this.orderStatus = orderStatus;
    }

}
