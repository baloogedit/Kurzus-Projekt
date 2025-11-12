package umfst.oop.kurzusprojekt;

/**
 * MÓDOSÍTVA: toString() implementálva.
 */
public class Clothes {
    
    private String clothName;
    private Size size;
    private Dancer assignedTo;
    
    public Clothes(String name, Size size)
    {
        this.clothName=name;
        this.size=size;
        this.assignedTo=null;
    }

    // A 3 paraméteres konstruktor maradhat, bár a GUI most nem használja.
    public Clothes(String name, Size size, Dancer assignedTo) {
        this.clothName = name;
        this.size = size;
        this.assignedTo = assignedTo; 
    }

    public String getName() {
        return clothName;
    }

    public void setName(String name) {
        this.clothName = name;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Dancer getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(Dancer assignedTo) {
        this.assignedTo = assignedTo;
    }
    
    public void print()
    {
       System.out.println(this.toString());
    }
    
    /**
     * ÚJ: toString() a JList-ben való szép megjelenítéshez.
     */
    @Override
    public String toString() {
        String status = (assignedTo == null) ? "Szabad" : ("Kiadva: " + assignedTo.getName());
        return clothName + " (" + size + ") - " + status;
    }
}