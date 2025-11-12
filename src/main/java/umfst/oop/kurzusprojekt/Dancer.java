package umfst.oop.kurzusprojekt;

/**
 * MÓDOSÍTVA: introString() és toString() implementálva.
 */
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
        // A konzolra írás helyett ezt is egy logba kellene menteni,
        // de egyelőre maradhat debug célból.
        System.out.println("Caution: " + this.name + " 's role changed, because: " + reason);
    }
    
    
    @Override
    public String introString()
    {
        return "TÁNCOS: " + name +", " + age + " éves. Szerepkör: "+ role + ", " + knownDances +" táncot ismer.";
    }

    /**
     * Ez a metódus már nem használatos a GUI-ban, de maradhat.
     */
    public void print()
    {
        System.out.println("Name: "+ name +", age: "+ age + ", role: "+ role + ", known dances: "+ knownDances);
    }
    
    /**
     * ÚJ: toString() a JList-ben való szép megjelenítéshez.
     */
    @Override
    public String toString() {
        return name + " (" + role + ")";
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