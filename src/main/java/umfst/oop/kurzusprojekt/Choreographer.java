package umfst.oop.kurzusprojekt;

/**
 * MÓDOSÍTVA: intro() -> introString()
 */
public class Choreographer extends Person{
    private int yearsOfExperience;
    
    public Choreographer(String name, int age, int yearsOfExperience) {
        super(name, age);
        this.yearsOfExperience = yearsOfExperience;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }
    
    
    // overriding Person's abstract intro method 
    @Override
    public String introString() {
        return "KOREOGRÁFUS: " + name + ", " + age + " éves, " + yearsOfExperience + " év tapasztalattal.";
    }
    
    // override toString method
    @Override
    public String toString() {
        return name + " (Koreográfus)";
    }
    
}