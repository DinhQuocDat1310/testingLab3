/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tailm.discount;

import java.io.Serializable;

/**
 *
 * @author DELL INC
 */
public class discountDTO implements Serializable{
    private String discountID;
    private float discountPercent;
    private String description;

    public discountDTO(String discountID, float discountPercent, String description) {
        this.discountID = discountID;
        this.discountPercent = discountPercent;
        this.description = description;
    }

    public String getDiscountID() {
        return discountID;
    }

    public void setDiscountID(String discountID) {
        this.discountID = discountID;
    }

    public float getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(float discountPercent) {
        this.discountPercent = discountPercent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
