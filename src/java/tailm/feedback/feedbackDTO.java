/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tailm.feedback;

/**
 *
 * @author DELL INC
 */
public class feedbackDTO {
    
    private String userID;
    private int rentingDetailID;
    private float star;
    private String description;

    public feedbackDTO(String userID, int rentingDetailID, float star, String description) {
        this.userID = userID;
        this.rentingDetailID = rentingDetailID;
        this.star = star;
        this.description = description;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getRentingDetailID() {
        return rentingDetailID;
    }

    public void setRentingDetailID(int rentingDetailID) {
        this.rentingDetailID = rentingDetailID;
    }

    public float getStar() {
        return star;
    }

    public void setStar(float star) {
        this.star = star;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
