package umfst.oop.kurzusprojekt;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


// it has the logic of the application (data management, saving, loading)
// the GUI calls for its methods

public class TancProjekt {

    // Adattároló listák
    private static ArrayList<Dancer> dancers = new ArrayList<>();
    private static ArrayList<Dance> dances = new ArrayList<>();
    private static ArrayList<Clothes> costumes = new ArrayList<>();
    private static ArrayList<Event> events = new ArrayList<>();
    private static ArrayList<Choreographer> choreographers = new ArrayList<>();
    private static ArrayList<Staff> staff = new ArrayList<>();
    
    // polimorph list
    private static ArrayList<Person> allPeople = new ArrayList<>();

    // valid roles list
    private static final List<String> VALID_ROLES = Arrays.asList("leader", "member", "beginner", "expert");
    
    // getters for the GUI
    // through these does the GUI accesses the data

    public static ArrayList<Dancer> getDancers() { return dancers; }
    public static ArrayList<Dance> getDances() { return dances; }
    public static ArrayList<Clothes> getCostumes() { return costumes; }
    public static ArrayList<Event> getEvents() { return events; }
    public static ArrayList<Choreographer> getChoreographers() { return choreographers; }
    public static ArrayList<Staff> getStaff() { return staff; }
    public static ArrayList<Person> getAllPeople() { return allPeople; }

    
    // create methods
    // GUI calls these after collecting data

    // create new dancer
    public static void createDancer(String name, int age, String role, int dances) throws InvalidRoleException {
        if (!VALID_ROLES.contains(role.toLowerCase())) {
            throw new InvalidRoleException("Invalid role: '" + role + "'. Must be one of: " + VALID_ROLES);
        }
        Dancer newDancer = new Dancer(name, age, role, dances);
        dancers.add(newDancer);
        allPeople.add(newDancer);
        saveDataToJson(); // Automatikus mentés
    }

    // create new dance
    public static void createDance(String name, String region, int minutes) {
        Dance newDance = new Dance(name, region, minutes);
        dances.add(newDance);
        saveDataToJson();
    }
    
    //create new clothes
    public static void createCostume(String name, Size size) {
        Clothes newCostume = new Clothes(name, size);
        costumes.add(newCostume);
        saveDataToJson();
    }
    
    // create new event
    public static void createEvent(String name, Date date, String place) {
        Event newEvent = new Event(name, date, place);
        events.add(newEvent);
        saveDataToJson();
    }
    
    // create new choreographer
    public static void createChoreographer(String name, int age, int experience) {
        Choreographer newChoreo = new Choreographer(name, age, experience);
        choreographers.add(newChoreo);
        allPeople.add(newChoreo);
        saveDataToJson();
    }
    
    //create new staff
    public static void createStaff(String name, int age, String department) {
        Staff newStaff = new Staff(name, age, department);
        staff.add(newStaff);
        allPeople.add(newStaff);
        saveDataToJson();
    }

    
    // operational methods

    //assigning costume to dancer
    public static void assignCostumeLogic(Clothes costume, Dancer dancer) {
        if (costume != null && dancer != null) {
            costume.setAssignedTo(dancer);
            saveDataToJson();
        }
    }
    
    // returns the intro of all Person based objects
    public static String getAllPeopleIntros() {
        if (allPeople.isEmpty()) {
            return "Nincsenek személyek a rendszerben.";
        }
        StringBuilder sb = new StringBuilder("--- Minden Személy Listája ---\n");
        for (Person p : allPeople) {
            sb.append(p.introString()).append("\n"); // introString() metódust használunk!
        }
        return sb.toString();
    }


    // JSON data handling

    // load the data from json file
    public static void loadDataFromJson() {
        // clear old data so there are no duplicates
        dancers.clear();
        dances.clear();
        costumes.clear();
        events.clear();
        choreographers.clear();
        staff.clear();
        allPeople.clear();

        try {
            String jsonText = Files.readString(Paths.get("data.json"));
            JSONObject jsonObj = new JSONObject(jsonText);

            // Dancers
            JSONArray dancerArr = jsonObj.getJSONArray("dancers");
            for (int i = 0; i < dancerArr.length(); i++) {
                JSONObject d = dancerArr.getJSONObject(i);
                Dancer newDancer = new Dancer(
                        d.getString("name"),
                        d.getInt("age"),
                        d.getString("role"),
                        d.getInt("dances"));
                dancers.add(newDancer);
                allPeople.add(newDancer);
            }

            // Dances
            JSONArray danceArr = jsonObj.getJSONArray("dances");
            for (int i = 0; i < danceArr.length(); i++) {
                JSONObject d = danceArr.getJSONObject(i);
                dances.add(new Dance(
                        d.getString("name"),
                        d.getString("region"),
                        d.getInt("minutes")));
            }

            // Costumes
            JSONArray costumeArr = jsonObj.getJSONArray("costumes");
            for (int i = 0; i < costumeArr.length(); i++) {
                JSONObject c = costumeArr.getJSONObject(i);
                Size size;
                try {
                    size = Size.valueOf(c.getString("size").toUpperCase());
                } catch (Exception e) {
                    size = Size.M;
                }
                Clothes newCostume = new Clothes(c.getString("name"), size);
                costumes.add(newCostume);

                if (c.has("assignedTo") && !c.isNull("assignedTo")) {
                    String dancerName = c.getString("assignedTo");
                    for (Dancer d : dancers) {
                        if (d.getName().equalsIgnoreCase(dancerName)) {
                            newCostume.setAssignedTo(d);
                            break;
                        }
                    }
                }
            }

            // Events
            JSONArray eventArr = jsonObj.getJSONArray("events");
            for (int i = 0; i < eventArr.length(); i++) {
                JSONObject e = eventArr.getJSONObject(i);
                Date date = Date.valueOf(e.getString("date"));
                events.add(new Event(
                        e.getString("name"),
                        date,
                        e.getString("place")));
            }

            // Choreographers
            if (jsonObj.has("choreographers")) {
                JSONArray chArr = jsonObj.getJSONArray("choreographers");
                for (int i = 0; i < chArr.length(); i++) {
                    JSONObject chObj = chArr.getJSONObject(i);
                    Choreographer ch = new Choreographer(
                            chObj.getString("name"),
                            chObj.getInt("age"),
                            chObj.getInt("yearsOfExperience"));
                    choreographers.add(ch);
                    allPeople.add(ch);
                }
            }

            // Staff
            if (jsonObj.has("staff")) {
                JSONArray sArr = jsonObj.getJSONArray("staff");
                for (int i = 0; i < sArr.length(); i++) {
                    JSONObject sObj = sArr.getJSONObject(i);
                    Staff s = new Staff(
                            sObj.getString("name"),
                            sObj.getInt("age"),
                            sObj.getString("department"));
                    staff.add(s);
                    allPeople.add(s);
                }
            }
        } catch (IOException e) {
            System.err.println("Cannot read data.json: " + e.getMessage());
            // GUI-n ezt egy felugró ablakban kellene jelezni
        } catch (JSONException e) {
            System.err.println(" Error parsing JSON: " + e.getMessage());
        }
    }

    // save all data to json file
    public static void saveDataToJson() {
        try {
            JSONObject root = new JSONObject();

            // Dancers
            JSONArray dancerArr = new JSONArray();
            for (Dancer d : dancers) {
                JSONObject obj = new JSONObject();
                obj.put("name", d.getName());
                obj.put("age", d.getAge());
                obj.put("role", d.getRole());
                obj.put("dances", d.getKnownDances());
                dancerArr.put(obj);
            }
            root.put("dancers", dancerArr);

            // Dances
            JSONArray danceArr = new JSONArray();
            for (Dance d : dances) {
                JSONObject obj = new JSONObject();
                obj.put("name", d.getName());
                obj.put("region", d.getRegion());
                obj.put("minutes", d.getMinutes());
                danceArr.put(obj);
            }
            root.put("dances", danceArr);

            // Costumes
            JSONArray costumeArr = new JSONArray();
            for (Clothes c : costumes) {
                JSONObject obj = new JSONObject();
                obj.put("name", c.getName());
                obj.put("size", c.getSize().toString());
                obj.put("assignedTo", (c.getAssignedTo() != null) ? c.getAssignedTo().getName() : JSONObject.NULL);
                costumeArr.put(obj);
            }
            root.put("costumes", costumeArr);

            // Events
            JSONArray eventArr = new JSONArray();
            for (Event e : events) {
                JSONObject obj = new JSONObject();
                obj.put("name", e.getEventName());
                obj.put("date", e.getDate().toString());
                obj.put("place", e.getPlace());
                eventArr.put(obj);
            }
            root.put("events", eventArr);

            // Choreographers
            JSONArray choreographerArr = new JSONArray();
            for (Choreographer ch : choreographers) {
                JSONObject obj = new JSONObject();
                obj.put("name", ch.getName());
                obj.put("age", ch.getAge());
                obj.put("yearsOfExperience", ch.getYearsOfExperience());
                choreographerArr.put(obj);
            }
            root.put("choreographers", choreographerArr);

            // Staff
            JSONArray staffArr = new JSONArray();
            for (Staff s : staff) {
                JSONObject obj = new JSONObject();
                obj.put("name", s.getName());
                obj.put("age", s.getAge());
                obj.put("department", s.getDepartment());
                staffArr.put(obj);
            }
            root.put("staff", staffArr);

            // write in the file
            Files.writeString(Paths.get("data.json"), root.toString(4));
            System.out.println("Data saved successfully to data.json!"); // dedugging purposes

        } catch (IOException e) {
            System.err.println("Error writing to data.json: " + e.getMessage());
        }
    }
}