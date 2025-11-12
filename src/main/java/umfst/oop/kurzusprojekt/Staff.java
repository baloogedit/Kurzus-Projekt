package umfst.oop.kurzusprojekt;

/**
 * MÓDOSÍTVA: intro() -> introString()
 */
public class Staff extends Person{
    
    private String department;
    
    public Staff(String name, int age, String department) {
        super(name, age);
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
    
    // overriding Person's abstract intro method
    @Override
    public String introString() {
        return "STÁBTAG: " + name + ", " + age + " éves. Részleg: " + department + ".";
    }
    
    public void print()
    {
        System.out.println(this.toString());
    }
    
    //override toString method
    @Override
    public String toString() {
        return name + " (" + department + ")";
    }

}