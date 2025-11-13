package umfst.oop.kurzusprojekt;


public class Dance implements DanceStructure{
    
    private String danceName;
    private String region;
    private int minutes;

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
    
    
    @Override
    public String toString() {
        return danceName + " (" + region + ", " + minutes + " perc)";
    }
 
    
    // string returning methods for GUI
    @Override
    public String getPerform() {
        return "Performing the dance '" + danceName + "' energetically on stage!";
    }
    
    @Override
    public String getOrigin() {
        return "This dance originates from the region of " + region + ".";
    }

    @Override
    public String getDuration() {
        return "The dance '" + danceName + "' lasts about " + minutes + " minutes.";
    }
    
    @Override
    public String getStyleDescription() {
        if (minutes < 3) {
            return danceName + " is a short, lively dance.";
        } else if (minutes < 7) {
            return danceName + " is a traditional medium-length folk dance.";
        } else {
            return danceName + " is a long ceremonial performance.";
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