package quizmanagementsystem.Model;

import java.util.List;

/**
 *
 * @author anonCoding
 */
public class Competition {
    private int id;
    private String name;
    private String noOfRounds;
    private String noOfTeams;
    private String timePerQuestion;
    List<Question> questions;
    List<Team> teams;

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

    public String getNoOfRounds() {
        return noOfRounds;
    }

    public void setNoOfRounds(String noOfRounds) {
        this.noOfRounds = noOfRounds;
    }

    public String getNoOfTeams() {
        return noOfTeams;
    }

    public void setNoOfTeams(String noOfTeams) {
        this.noOfTeams = noOfTeams;
    }

    public String getTimePerQuestion() {
        return timePerQuestion;
    }

    public void setTimePerQuestion(String timePerQuestion) {
        this.timePerQuestion = timePerQuestion;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    
}