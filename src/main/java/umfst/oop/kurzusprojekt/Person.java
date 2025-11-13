package umfst.oop.kurzusprojekt;

// abstract parent class

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
    
    
    // the polimorph, abstract method that needs to be overridden
    // it will be the introduction text
    // this is what the GUI will show
    public abstract String introString();
    
}