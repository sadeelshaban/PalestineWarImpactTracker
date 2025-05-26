package First;

/**
 * Represents a general war victim (Martyr, Wounded, Prisoner).
 * This replaces the need for separate classes.
 */
public class WarVictim {
    private String id; // unique national ID
    private String name;
    private int age;
    private String dateOfDeath;
    private String causeOfDeath;
    private String type; // "Martyr", "Wounded", or "Prisoner"

    public WarVictim(String id, String name, int age, String dateOfDeath, String causeOfDeath, String type) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.dateOfDeath = dateOfDeath;
        this.causeOfDeath = causeOfDeath;
        this.type = type;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getDateOfDeath() { return dateOfDeath; }
    public String getCauseOfDeath() { return causeOfDeath; }
    public String getType() { return type; }

    public String getSummary() {
        return "[" + type + "] " + name + " (ID: " + id + "), age " + age +
                ", died: " + dateOfDeath +
                ", cause: " + causeOfDeath;
    }
}
