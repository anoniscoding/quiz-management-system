package quizmanagementsystem.UserInterfaceLayer.PresentationView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import quizmanagementsystem.InfrastructuralLayer.FieldValidator;
import quizmanagementsystem.InfrastructuralLayer.Notifier;
import quizmanagementsystem.Model.Competition;
import quizmanagementsystem.Model.Option;
import quizmanagementsystem.Model.Question;
import quizmanagementsystem.QuizManagementSystem;
import quizmanagementsystem.Repository.CompetitionRepository;
import quizmanagementsystem.Repository.IRepository;
import quizmanagementsystem.Repository.OptionRepository;
import quizmanagementsystem.Repository.QuestionRepository;
import quizmanagementsystem.Specification.CompositeSpecification.CompositeSpecification;
import quizmanagementsystem.Specification.CompositeSpecification.TextIsNotEmptySpecification;
import quizmanagementsystem.Specification.SqlSpecification.AllCompetitionsSpecification;
import quizmanagementsystem.Specification.SqlSpecification.LastAddedQuestionSpecification;
import quizmanagementsystem.Specification.CompositeSpecification.OptionsIsValidSpecification;

/**
 * FXML Controller class
 *
 * @author anonCoding
 */
public class QuestionsFxmlController implements Initializable {
    @FXML
    private ComboBox competitionsComboBox;
    @FXML
    private TextArea questionContentTextArea;
    @FXML
    private TextArea questionOptionsTextArea;
    @FXML
    private ImageView loaderImageView;
    
    private final IRepository competitionRepo = new CompetitionRepository();
    private final IRepository questionRepo = new QuestionRepository();
    private final IRepository optionRepo = new OptionRepository();
    
    private final List<Integer> competitionsId = new ArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populateCompetitionComboBox();
    }    
    
    private void populateCompetitionComboBox() {
        List<Competition> competitions = competitionRepo.query(new AllCompetitionsSpecification());
        ObservableList<String> competitionNames = FXCollections.observableArrayList();
        competitions.stream().forEach((competition) -> {
            competitionNames.add(competition.getName());
            competitionsId.add(competition.getId());
        });
        competitionsComboBox.setItems(competitionNames);
    }

    @FXML
    private void onLoadFileClick(ActionEvent event) {   
        boolean isCompetitionSelected = new TextIsNotEmptySpecification().isSatisfiedBy(
                                                String.valueOf(competitionsComboBox.getSelectionModel().getSelectedIndex()));
        if (isCompetitionSelected) {
            try {
                BufferedReader questionsReader = new BufferedReader(new FileReader(getQuestionFile()));
                new Thread(() -> {
                    Platform.runLater(() -> loaderImageView.setVisible(true));
                    try {
                        parseReader(questionsReader); 
                    } catch (Exception e) {}
                    Platform.runLater(() -> {
                        loaderImageView.setVisible(false);
                        Notifier.alert("File has been completely analysed.\nThe valid questions have been saved");
                    });
                }).start();
            } catch (Exception e){}
        } else
            Notifier.error("Please select a competition"); 
    }

    private void parseReader(BufferedReader questionsReader) throws IOException {
        String line;
        List<String> options = new ArrayList<>();
        Optional<Question> question = Optional.empty();
        while ((line = questionsReader.readLine()) != null) {
            if (line.length() > 0 && Character.isDigit(line.charAt(0))) {       
                question = Optional.of(new Question());
                question.get().setContent(line.substring(line.indexOf(" ")));
                question.get().setCompetitonId(competitionsId.get(competitionsComboBox.getSelectionModel().getSelectedIndex()));
                continue;
            } 
            
            if (line.length() == 0 && question.isPresent()) {
                addQuestion(question, options);
                options.clear(); //clear options of previous question
                question = Optional.empty();
            } else if (question.isPresent())
                       options.add(line);
            
        }
        addQuestion(question, options); //add the last question in the file
    }

    private void addQuestion(Optional<Question> question, List<String> options) {
        question.ifPresent( (questn) -> {
            if (new OptionsIsValidSpecification().isSatisfiedBy(options)) {
                questionRepo.add(questn);
                getLastAddedQuestion().ifPresent( (q) -> questn.setId(q.getId()) );
                addOptions(getOptions(options), questn.getId());
            }
        });
    }

    private File getQuestionFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Questions File");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File questionFile = fileChooser.showOpenDialog(QuizManagementSystem.getAppStage());
        return questionFile;
    }

    @FXML
    private void onAddClick(ActionEvent event) {
        if (FieldValidator.isFieldsValid(getFieldValidationResults())) {
            Question question = new Question();
            question.setCompetitonId(competitionsId.get(competitionsComboBox.getSelectionModel().getSelectedIndex()));
            question.setContent(questionContentTextArea.getText());
            question.setOptions(getOptions(Arrays.asList(questionOptionsTextArea.getText().split("\n"))));
            questionRepo.add(question);
            getLastAddedQuestion().ifPresent( (q) -> question.setId(q.getId()) ); 
            addOptions(question.getOptions(), question.getId());
            Notifier.alert("Question added successfully");
        } 
    }

    private void addOptions(List<Option> options, int questionId) {
        options.stream().forEach( (option) -> {
            option.setQuestionId(questionId);
            optionRepo.add(option);
        });
    }
    
    private List<Boolean> getFieldValidationResults() {
        List<Boolean> fieldValidationResults = new ArrayList<>();
        CompositeSpecification optionsAreValidSpec = new OptionsIsValidSpecification();
        CompositeSpecification textIsNotEmptySpec = new TextIsNotEmptySpecification();
        
        fieldValidationResults.add(textIsNotEmptySpec.isSatisfiedBy(questionContentTextArea.getText()));
        fieldValidationResults.add(optionsAreValidSpec.isSatisfiedBy(Arrays.asList(questionOptionsTextArea.getText().split("\n"))));
        fieldValidationResults.add(textIsNotEmptySpec.isSatisfiedBy(String.valueOf(competitionsComboBox.getSelectionModel().getSelectedIndex())));
        return fieldValidationResults;
    }
    
    private List<Option> getOptions(List<String> options) {
        List<Option> optionsList = new ArrayList<>();
        options.stream().forEach( (option) -> {
            Option op = new Option();
            if (option.startsWith("*")) {
                op.setIsCorrect(true);
                op.setContent(option.substring(1));
            } else {
                op.setIsCorrect(false);
                op.setContent(option);
            }
            optionsList.add(op);
        });
        return optionsList;
    }
    
    private Optional<Question> getLastAddedQuestion() {
        return questionRepo.query(new LastAddedQuestionSpecification()).stream().findFirst();
    }

    @FXML
    private void onViewQuestionsClick(MouseEvent event) {
        boolean isCompetitionSelected = new TextIsNotEmptySpecification().isSatisfiedBy(
                                                String.valueOf(competitionsComboBox.getSelectionModel().getSelectedIndex()));
        if (isCompetitionSelected) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewQuestionsFxml.fxml"));
            try {
                    Scene homeScene = new Scene(loader.load());
                    ViewQuestionsFxmlController viewQuestionsController = loader.getController();
                    viewQuestionsController.setCompetitionId(competitionsId.get(competitionsComboBox.getSelectionModel().getSelectedIndex()));
                    Stage stage = new Stage();
                    stage.setTitle("View Questions");
                    stage.setScene(homeScene);
                    stage.setResizable(false);
                    stage.sizeToScene();
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.initOwner(QuizManagementSystem.getAppStage());
                    stage.showAndWait();
            } catch (Exception e) {}
        } else { 
            Notifier.error("Please select a competition"); 
        } 
    }
    
}