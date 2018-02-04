package quizmanagementsystem.UserInterfaceLayer.PresentationView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import quizmanagementsystem.InfrastructuralLayer.Notifier;
import quizmanagementsystem.Model.Competition;
import quizmanagementsystem.Model.Question;
import quizmanagementsystem.QuizManagementSystem;
import quizmanagementsystem.Repository.CompetitionRepository;
import quizmanagementsystem.Repository.IRepository;
import quizmanagementsystem.Repository.QuestionRepository;
import quizmanagementsystem.Specification.CompositeSpecification.QuestionIsEnoughSpecification;
import quizmanagementsystem.Specification.CompositeSpecification.TextIsNotEmptySpecification;
import quizmanagementsystem.Specification.SqlSpecification.AllCompetitionsSpecification;
import quizmanagementsystem.Specification.SqlSpecification.QuestionByCompetitionIdSpecification;

/**
 * FXML Controller class
 *
 * @author anonCoding
 */
public class QuizArenaController implements Initializable {
    @FXML
    private ComboBox competitionComboBox;
    
    List<Competition> competitions = new ArrayList();
    
    private final List<Integer> competitionsId = new ArrayList();
    
    private final IRepository competitionRepo = new CompetitionRepository();
    private final IRepository questionRepo = new QuestionRepository();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populateCompetitionComboBox();
    }    
    
    private void populateCompetitionComboBox() {
        competitions = competitionRepo.query(new AllCompetitionsSpecification());
        ObservableList<String> competitionNames = FXCollections.observableArrayList();
        competitions.stream().forEach((competition) -> {
            competitionNames.add(competition.getName());
            competitionsId.add(competition.getId());
        });
        competitionComboBox.setItems(competitionNames);
    }

    @FXML
    private void onStartQuiz(ActionEvent event) {
        boolean isCompetitionSelected = new TextIsNotEmptySpecification().isSatisfiedBy(
                                                String.valueOf(competitionComboBox.getSelectionModel().getSelectedIndex()));
        if (isCompetitionSelected) {
            int competitionId = competitionsId.get(competitionComboBox.getSelectionModel().getSelectedIndex());
            Competition competition = competitions.get(competitionComboBox.getSelectionModel().getSelectedIndex());
            competition.setQuestions(questionRepo.query(new QuestionByCompetitionIdSpecification(competitionId)));
            if (new QuestionIsEnoughSpecification().isSatisfiedBy(competition)) {
                loadQuiz(competition);
                return;
            }         
            Notifier.error("Questions are not enough for all teams for the set no of rounds");
            return;
        }
        
        Notifier.error("Please select a competition");
    }
    
    private void loadQuiz(Competition competition) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoadQuizFxml.fxml"));
        try {
                Stage stage = new Stage();
                Scene mainQuizScene = new Scene(loader.load());          
                LoadQuizFxmlController loadQuizController = loader.getController();
                loadQuizController.setCompetition(competition);                
                stage.setTitle("View Questions");
                stage.setScene(mainQuizScene);
                stage.setResizable(false);
                stage.sizeToScene();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initOwner(QuizManagementSystem.getAppStage());
                stage.showAndWait();
            } catch (Exception e) {}    
    }
    
}
