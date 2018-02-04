package quizmanagementsystem.Model;

/**
 *
 * @author anonCoding
 */
public class Team {
    private int id;
    private String name;
    private int competitonId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCompetitonId() {
        return competitonId;
    }

    public void setCompetitonId(int competitonId) {
        this.competitonId = competitonId;
    }
    
}