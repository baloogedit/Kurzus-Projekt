/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package umfst.oop.kurzusprojekt;

import java.util.List;

/**
 *
 * @author edite
 */

// unique exception
// only shows if role input is not valid
// used in TancProject.java - creating dancer, changing dancer's role method
public class InvalidRoleException extends Exception{
    
    // specific fields to store error details
    private final String invalidRoleInput;
    private final List<String> allowedRoles;

    // constructor that takes the raw data
    public InvalidRoleException(String invalidRoleInput, List<String> allowedRoles) {
        // informative message
        super("The role '" + invalidRoleInput + "' is not valid. Allowed roles are: " + allowedRoles);
        this.invalidRoleInput = invalidRoleInput;
        this.allowedRoles = allowedRoles;
    }

    public String getInvalidRoleInput() {
        return invalidRoleInput;
    }

    public List<String> getAllowedRoles() {
        return allowedRoles;
    }
}
