/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tailm.registration;

import java.io.Serializable;

/**
 *
 * @author DELL INC
 */
public class RegistrationError implements Serializable{
    private String duplicateEmail;
    private String passwordNotEmpty;
    private String passwordNotMatched;
    
    
    
    public RegistrationError() {
    }

    public String getDuplicateEmail() {
        return duplicateEmail;
    }

    public void setDuplicateEmail(String duplicateEmail) {
        this.duplicateEmail = duplicateEmail;
    }

    public String getPasswordNotMatched() {
        return passwordNotMatched;
    }

    public void setPasswordNotMatched(String passwordNotMatched) {
        this.passwordNotMatched = passwordNotMatched;
    }

    public String getPasswordNotEmpty() {
        return passwordNotEmpty;
    }

    public void setPasswordNotEmpty(String passwordNotEmpty) {
        this.passwordNotEmpty = passwordNotEmpty;
    }
    
}
