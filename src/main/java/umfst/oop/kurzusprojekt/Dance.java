package umfst.oop.kurzusprojekt;

/**
 * MÓDOSÍTVA: toString() implementálva.
 */
public class Dance implements DanceStructure{
    
    public String danceName;
    public String region;
    public int minutes;

    public Dance(String name, String region, int minutes) {
        this.danceName = name;
        this.region = region;
        this.minutes = minutes;
    }
    
    @Override
    public void print()
    {
        System.out.println(this.toString());
    }
    
    /**
     * ÚJ: toString() a JList-ben való szép megjelenítéshez.
     */
    @Override
    public String toString() {
        return danceName + " (" + region + ", " + minutes + " perc)";
    }
    
    // ... a többi metódus (perform, showOrigin, stb.) változatlan marad ...
    
    @Override
    public void perform() {
        System.out.println("Performing the dance '" + danceName + "' energetically on stage!");
    }

    @Override
    public void showOrigin() {
        System.out.println("This dance originates from the region of " + region + ".");
    }

    @Override
    public void showDuration() {
        System.out.println("The dance '" + danceName + "' lasts about " + minutes + " minutes.");
    }

    @Override
    public void describeStyle() {
        if (minutes < 3) {
            System.out.println(danceName + " is a short, lively dance.");
        } else if (minutes < 7) {
            System.out.println(danceName + " is a traditional medium-length folk dance.");
        } else {
            System.out.println(danceName + " is a long ceremonial performance.");
        }
    }
    
    public String getName() {
        return danceName;
    }

    public void setName(String name) {
        this.danceName = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }
}