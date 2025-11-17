/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package umfst.oop.kurzusprojekt;

/**
 *
 * @author edite
 */

// unique exception
// only shows if role input is not valid
// used in TancProject.java - creating dancer, changing dancer's role method
public class InvalidRoleException extends Exception{
    
    public InvalidRoleException(String message)
    { 
        super(message);    
    }
}
