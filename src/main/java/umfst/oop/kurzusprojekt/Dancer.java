package umfst.oop.kurzusprojekt;

public class Dancer extends Person
{
    private String role;
    private int knownDances;
    
    private static int dancerCount=0;

    public Dancer(String name, int age, String role, int dances) {
        super(name, age);
        this.role = role;
        this.knownDances=dances;
        dancerCount++;
    }

    public static int getDancerCount() {
        return dancerCount;
    }
      
    public String getRole() {
        return role;
    }

    public int getKnownDances() {
        return knownDances;
    }

    public void setKnownDances(int knownDances) {
        this.knownDances = knownDances;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
    // overloaded method
    public void setRole(String role, String reason)
    {
        this.setRole(role);
        // only for debug purposes
        System.out.println("Caution: " + this.name + " 's role changed, because: " + reason);
    }
    
    //overriden abstract method
    @Override
    public String introString()
    {
        return "TÁNCOS: " + name +", " + age + " éves. Szerepkör: "+ role + ", " + knownDances +" táncot ismer.";
    }

    // not used
    public void print()
    {
        System.out.println("Name: "+ name +", age: "+ age + ", role: "+ role + ", known dances: "+ knownDances);
    }
    
     // toString for nice display in Jlist
    @Override
    public String toString() {
        return name + ", " + age +" (" + role + ")";
    }
    
    // overriding equals method
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Dancer dancer = (Dancer) obj;
        return age == dancer.age && name.equalsIgnoreCase(dancer.name);
    }
    
    // override hashcode, when equals is overidden
    @Override
    public int hashCode() {
        int result = name != null ? name.toLowerCase().hashCode() : 0;
        result = 31 * result + age;
        return result;
    }
}