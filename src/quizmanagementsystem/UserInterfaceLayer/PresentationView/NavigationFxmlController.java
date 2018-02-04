package quizmanagementsystem.UserInterfaceLayer.PresentationView;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import quizmanagementsystem.QuizManagementSystem;

/**
 * FXML Controller class
 *
 * @author anonCoding
 */
public class NavigationFxmlController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void onHomeClick(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("HomeFxml.fxml"));
        try {
                Scene homeScene = new Scene(loader.load());
                QuizManagementSystem.getAppStage().setTitle("Quiz Management System");
                QuizManagementSystem.getAppStage().setScene(homeScene);
                QuizManagementSystem.getAppStage().setResizable(false);
                QuizManagementSystem.getAppStage().sizeToScene();
                QuizManagementSystem.getAppStage().showAndWait();
        } catch (Exception e) {}
    }
    
    @FXML
    private void onConfigurationClick(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ConfigurationFxml.fxml"));
        try {
                Scene configurationScene = new Scene(loader.load());
                QuizManagementSystem.getAppStage().setTitle("Configurations");
                QuizManagementSystem.getAppStage().setScene(configurationScene);
                QuizManagementSystem.getAppStage().setResizable(false);
                QuizManagementSystem.getAppStage().sizeToScene();
                QuizManagementSystem.getAppStage().showAndWait();
        } catch (Exception e) {}
    }
    
    @FXML
    private void onQuestionsClick(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("QuestionsFxml.fxml"));
        try {
                Scene questionScene = new Scene(loader.load());
                QuizManagementSystem.getAppStage().setTitle("Questions");
                QuizManagementSystem.getAppStage().setScene(questionScene);
                QuizManagementSystem.getAppStage().setResizable(false);
                QuizManagementSystem.getAppStage().sizeToScene();
                QuizManagementSystem.getAppStage().showAndWait();
        } catch (Exception e) {}
    }
    
    @FXML
    private void onRegistrationClick(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("RegistrationFxml.fxml"));
        try {
                Scene registrationScene = new Scene(loader.load());
                QuizManagementSystem.getAppStage().setTitle("Registration");
                QuizManagementSystem.getAppStage().setScene(registrationScene);
                QuizManagementSystem.getAppStage().setResizable(false);
                QuizManagementSystem.getAppStage().sizeToScene();
                QuizManagementSystem.getAppStage().showAndWait();
        } catch (Exception e) {}
    }
    
    @FXML
    private void onQuizArenaClick(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("QuizArena.fxml"));
        try {
                Scene quizArenaScene = new Scene(loader.load());
                QuizManagementSystem.getAppStage().setTitle("Quiz Arena");
                QuizManagementSystem.getAppStage().setScene(quizArenaScene);
                QuizManagementSystem.getAppStage().setResizable(false);
                QuizManagementSystem.getAppStage().sizeToScene();
                QuizManagementSystem.getAppStage().showAndWait();
        } catch (Exception e) {}
    }
    
}
