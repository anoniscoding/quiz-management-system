package quizmanagementsystem.Model;

import java.util.List;

/**
 *
 * @author anonCoding
 */
public class Question {
    private int id;
    private String content;
    private int competitonId;
    List<Option> options;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCompetitonId() {
        return competitonId;
    }

    public void setCompetitonId(int competitonId) {
        this.competitonId = competitonId;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }
    
}
