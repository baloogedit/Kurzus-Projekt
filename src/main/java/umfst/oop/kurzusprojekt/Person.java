package umfst.oop.kurzusprojekt;

/**
 * Absztrakt ősosztály.
 * MÓDOSÍTVA: intro() helyett introString() van, ami String-et ad vissza.
 */
public abstract class Person {
    
    protected String name;
    protected int age;
    
    public Person(String name, int age)
    {
        this.name=name;
        this.age=age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    
    /**
     * Polimorf metódus, ami visszaad egy bemutatkozó szöveget.
     * A GUI fogja ezt megjeleníteni.
     */
    public abstract String introString();
    
}