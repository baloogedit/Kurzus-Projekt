/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package umfst.oop.kurzusprojekt;

/**
 *
 * @author edite
 */
public interface DanceStructure {
    
    //start performing the dance
    String getPerform();
    
    //show the dance's origin or region
    String getOrigin();
    
    //display how long the dance lasts
    String getDuration();
    
    // display full details of the dance
    void print();
    
    // describe what type of dance it is
    String getStyleDescription();
}
