package quizmanagementsystem.UserInterfaceLayer.PresentationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Random;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import quizmanagementsystem.InfrastructuralLayer.Notifier;
import quizmanagementsystem.Model.Competition;
import quizmanagementsystem.Model.Question;
import quizmanagementsystem.Model.Team;
import quizmanagementsystem.QuizManagementSystem;
import quizmanagementsystem.Repository.IRepository;
import quizmanagementsystem.Repository.OptionRepository;
import quizmanagementsystem.Repository.TeamRepository;
import quizmanagementsystem.Specification.CompositeSpecification.QuestionIsEnoughSpecification;
import quizmanagementsystem.Specification.CompositeSpecification.TextIsNumericSpecification;
import quizmanagementsystem.Specification.SqlSpecification.OptionsByQuestionIdSpecification;
import quizmanagementsystem.Specification.SqlSpecification.TeamsByCompetitionIdSpecification;

/**
 * FXML Controller class
 *
 * @author anonCoding
 */
public class LoadQuizFxmlController {
    @FXML
    private Label teamNameLabel;
    @FXML
    private Label roundLabel;
    @FXML
    private FlowPane flowPane;
    @FXML
    private Button shuffleBtn;
    
    private Competition _competition;  
    private ListIterator<Team> teamsListIterator;   
    private List<Team> teamsList;
    private int currentRound = 0;
    private final Map<String, Integer> teamsToScores = new HashMap();
    private Team currentTeam;
    private final ObservableList<Question> questionsList = FXCollections.observableArrayList();
    private final ObservableList<Question> questionsToBeRemovedList = FXCollections.observableArrayList();
    private final IRepository teamRepo = new TeamRepository();
    private final IRepository optionsRepo = new OptionRepository();
    
    private boolean isCurrentTeamQuestionDisabled = false;
    
    public void setCompetition(Competition competition) {
        this._competition = competition;
        loadArena();
    }
    
    private void loadArena() {
        questionsList.addAll(_competition.getQuestions());
        teamsList = teamRepo.query(new TeamsByCompetitionIdSpecification(_competition.getId()));
        teamsListIterator = teamsList.listIterator();
        this.displayQuestionNumbers();
        this.displayNextTeamName();
        this.displayNextRoundNumber();
        this.setInitialTeamsScore();
    }
    
    private void setInitialTeamsScore() {
        teamsList.stream().forEach((team) -> {
            teamsToScores.put(team.getName(), 0);
        });
    }

    private void displayQuestionNumbers() {
        flowPane.setHgap(10);
        flowPane.setVgap(10);
        flowPane.setPrefWrapLength(1280);
        flowPane.setPadding(new Insets(10,15,10,10));
        
        for (int questionNo = 1; questionNo < questionsList.size(); questionNo++) {
            Label label = new Label(String.valueOf(questionNo));
            label.addEventHandler(MouseEvent.MOUSE_PRESSED, questionNoLabelEvent());
            flowPane.getChildren().add(label);
        }
    }
    
    private void displayNextTeamName() {
        this.currentTeam = teamsListIterator.next();
        this.teamNameLabel.setText(currentTeam.getName());
    }
    
    private void displayNextRoundNumber() {
        this.roundLabel.setText(String.format("Round %d", ++currentRound));
    }
    
    private EventHandler<MouseEvent> questionNoLabelEvent() {
        return (MouseEvent event) -> {
            Label label = (Label)event.getSource();
            int questionPos = Integer.parseInt(label.getText()) - 1;
            label.setStyle("-fx-background-color: red");
            label.setDisable(true);
            shuffleBtn.setDisable(true);
            this.questionsToBeRemovedList.add(questionsList.get(questionPos));
            showQuestion(questionsList.get(questionPos));
        };
    }
    
    private void showQuestion(Question question) {
        question.setOptions(optionsRepo.query(new OptionsByQuestionIdSpecification(question.getId())));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ShowQuestionFXML.fxml"));
        try {
                Stage stage = new Stage();
                Scene viewQuestionScene = new Scene(loader.load());
                ShowQuestionFXMLController showQuestionController = loader.getController();
                showQuestionController.setQuestionAndDuration(question, Integer.valueOf(_competition.getTimePerQuestion()));
                this.gradeTeam(showQuestionController);
                this.isQuestionDisabled(showQuestionController);
                stage.setTitle("View Question");
                stage.setScene(viewQuestionScene);
                stage.setResizable(false);
                stage.sizeToScene();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initOwner(QuizManagementSystem.getAppStage());
                stage.showAndWait();
                this.continueCompetitionProcess();
            } catch (Exception e) {}
    }
    
    private void gradeTeam(ShowQuestionFXMLController showQuestionController) {
        showQuestionController.selectedOptionProperty().addListener((ObservableValue<? extends Boolean> observable, 
                                                                     Boolean oldValue, Boolean isSelectedOptionCorrect) -> {
            if (isSelectedOptionCorrect)
                Notifier.alert("YOU ARE CORRECT");
            else
                Notifier.alert("YOU ARE WRONG");

            int score = isSelectedOptionCorrect ? 1 : 0;
            int teamCurrentScore = teamsToScores.get(currentTeam.getName()) + score;
            teamsToScores.put(currentTeam.getName(), teamCurrentScore);
        });
    }
    
    private void isQuestionDisabled(ShowQuestionFXMLController showQuestionController) {
        showQuestionController.disableQuestionProperty().addListener((ObservableValue<? extends Boolean> observable, 
                                                                     Boolean oldValue, Boolean isQuestionDisabled) -> {
            this.isCurrentTeamQuestionDisabled = isQuestionDisabled;
        });
    }

    private void continueCompetitionProcess() {
        if (this.isCurrentTeamQuestionDisabled) {
            this.isCurrentTeamQuestionDisabled = false;
            return; //allow the current team to try another question
        }   
        
        if (teamsListIterator.hasNext()) {
            displayNextTeamName();
            return;
        }
        
        this.showResults(); //show teams result before moving to the next round
        
        if (currentRound < Integer.parseInt(_competition.getNoOfRounds())) {
            teamsListIterator = teamsList.listIterator();
            displayNextTeamName();
            displayNextRoundNumber();
        } else {
            /* End of Competition */
            flowPane.setDisable(true);
            if (this.getHeighestTeams(getHeighestScore()).size() > 1)
                this.handleTieSituation();
        }
    }

    private void handleTieSituation() {
        Competition competition = new Competition();
        String noOfRounds = getNewNoOfRoundsForTiedTeams();
        questionsList.removeAll(this.questionsToBeRemovedList);
        competition.setNoOfRounds(noOfRounds);
        competition.setNoOfTeams(String.valueOf(getHeighestTeams(getHeighestScore()).size()));
        competition.setQuestions(questionsList);
        
        while (new QuestionIsEnoughSpecification().Not().isSatisfiedBy(competition)) {
            Notifier.error("Questions are not enough for the set no of rounds");
            noOfRounds = getNewNoOfRoundsForTiedTeams();
            competition.setNoOfRounds(noOfRounds);
        }
        
        resetCompetitionStage(noOfRounds);
    }

    private void resetCompetitionStage(String noOfRounds) {
        flowPane.getChildren().clear();
        teamsList = getHeighestTeams(getHeighestScore());
        teamsListIterator = teamsList.listIterator();
        _competition.setNoOfRounds(noOfRounds);
        currentRound = 0;
        this.displayNextTeamName();
        this.displayNextRoundNumber();
        this.displayQuestionNumbers();
        flowPane.setDisable(false);
    }

    private String getNewNoOfRoundsForTiedTeams() {
        String noOfRounds = Notifier.prompt("THERE IS A TIE. PLEASE SET A NEW NO OF ROUNDS FOR THE TIED TEAMS", "Enter no of rounds");
        while (new TextIsNumericSpecification().Not().isSatisfiedBy(noOfRounds)) {
            Notifier.error("No of Rounds Must Be Numeric");
            noOfRounds = Notifier.prompt("THERE IS A TIE. PLEASE SET A NEW NO OF ROUNDS FOR THE TIED TEAMS", "Enter no of rounds");
        } 
        return noOfRounds;
    }
    
    private int getHeighestScore() {
        int maxScore = teamsToScores.get(teamsList.get(0).getName());
        for (int i = 1; i < teamsList.size(); i++) {
            if (teamsToScores.get( teamsList.get(i).getName() ) > maxScore)
                maxScore = teamsToScores.get( teamsList.get(i).getName() );
        }
        return maxScore;
    }
    
    private List<Team> getHeighestTeams(int heighestScore) {
        List<Team> heighestTeams = new ArrayList<>();
        for (Team team : teamsList) {
            if (teamsToScores.get(team.getName()) == heighestScore)
                heighestTeams.add(team);     
        }
        return heighestTeams;
    }

    private void showResults() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewResultFXML.fxml"));
        try {
                Stage stage = new Stage();
                Scene viewResultsScene = new Scene(loader.load());
                ViewResultFXMLController viewResultController = loader.getController();
                viewResultController.setTeamsToScores(_competition.getId(), teamsToScores);
                stage.setTitle("View Results");
                stage.setScene(viewResultsScene);
                stage.setResizable(false);
                stage.sizeToScene();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initOwner(QuizManagementSystem.getAppStage());
                stage.showAndWait();
            } catch (Exception e) {}
    }

    @FXML
    private void onShowResultClick(ActionEvent event) {
        this.showResults();
    }

    @FXML
    private void onShuffleClick(ActionEvent event) {
        FXCollections.shuffle(questionsList, new Random());
        Notifier.alert("Questions shuffled successfully");
    }  
    
}