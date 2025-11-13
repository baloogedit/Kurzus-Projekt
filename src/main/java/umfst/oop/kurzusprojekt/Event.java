package umfst.oop.kurzusprojekt;

import java.sql.Date;

public class Event {
    
    private String eventName;
    private Date date;
    private String place;
    
    public Event (String name, Date date, String place)
    {
        this.eventName=name;
        this.date=date;
        this.place=place;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void print()
    {
        System.out.println(this.toString());
    }
    
    
    @Override
    public String toString() {
        return eventName + " [" + place + ", " + date.toString() + "]";
    }
}