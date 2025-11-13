package umfst.oop.kurzusprojekt;


public class Clothes {
    
    private String clothName;
    private Size size;
    private Dancer assignedTo;
    
    //2 parameter constructor
    public Clothes(String name, Size size)
    {
        this.clothName=name;
        this.size=size;
        this.assignedTo=null;
    }

    // 3 parameter contructor
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
    
    // toString for nice display in Jlist
    @Override
    public String toString() {
        String status = (assignedTo == null) ? "Szabad" : ("Kiadva: " + assignedTo.getName());
        return clothName + " (" + size + ") - " + status;
    }
}