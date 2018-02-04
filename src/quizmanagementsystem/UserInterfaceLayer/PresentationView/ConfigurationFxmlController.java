package quizmanagementsystem.UserInterfaceLayer.PresentationView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import quizmanagementsystem.InfrastructuralLayer.FieldValidator;
import quizmanagementsystem.InfrastructuralLayer.Notifier;
import quizmanagementsystem.Model.Competition;
import quizmanagementsystem.Repository.CompetitionRepository;
import quizmanagementsystem.Repository.IRepository;
import quizmanagementsystem.Specification.CompositeSpecification.CompositeSpecification;
import quizmanagementsystem.Specification.CompositeSpecification.TextIsNotEmptySpecification;
import quizmanagementsystem.Specification.CompositeSpecification.TextIsNumericSpecification;
import quizmanagementsystem.Specification.SqlSpecification.AllCompetitionsSpecification;
import quizmanagementsystem.Specification.SqlSpecification.LastAddedCompetitionSpecification;

/**
 * FXML Controller class
 *
 * @author anonCoding
 */
public class ConfigurationFxmlController implements Initializable {
    @FXML
    private TableView<Competition> competitionsTable;
    @FXML
    private TableColumn<Competition, String> competitionNameColumn;
    @FXML
    private TableColumn<Competition, String> noOfTeamsColumn;
    @FXML
    private TableColumn<Competition, String> timePerQuestionColumn;
    @FXML
    private TableColumn<Competition, String> noOfRoundsColumn;
    @FXML
    private TextField nameField;
    @FXML
    private TextField noOfRoundsField;
    @FXML
    private TextField timePerQuestionField;
    @FXML
    private TextField noOfTeamsField;
    
    private final ObservableList<Competition> competitionsList = FXCollections.observableArrayList();
    
    private final IRepository competitionRepo = new CompetitionRepository();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initTable();
        populateTable();
    }    
    
    private void initTable() {      
        competitionsTable.setEditable(true);
        
        competitionNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        competitionNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        noOfTeamsColumn.setCellValueFactory(new PropertyValueFactory<>("noOfTeams"));
        noOfTeamsColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        
        timePerQuestionColumn.setCellValueFactory(new PropertyValueFactory<>("timePerQuestion"));
        timePerQuestionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        
        noOfRoundsColumn.setCellValueFactory(new PropertyValueFactory<>("noOfRounds"));
        noOfRoundsColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        
        competitionsTable.setItems(competitionsList);
    }
    
    private void populateTable() {
        competitionsList.addAll(competitionRepo.query(new AllCompetitionsSpecification()));
    }

    @FXML
    private void onCompetitionNameEdit(TableColumn.CellEditEvent<Competition, String> event) {
        Competition competition = (Competition) event.getTableView().getItems().get( event.getTablePosition().getRow() );
        competition.setName(event.getNewValue());
        competitionRepo.update(competition);
    }
    
    @FXML
    private void onNoOfTeamsEdit(TableColumn.CellEditEvent<Competition, String> event) {
        Competition competition = (Competition) event.getTableView().getItems().get( event.getTablePosition().getRow() );
        if (new TextIsNumericSpecification().isSatisfiedBy(event.getNewValue())) {
            competition.setNoOfTeams(event.getNewValue());
            competitionRepo.update(competition);
        } else {
            Notifier.error("expected a numeric value");
        }
    }
    
    @FXML
    private void onTimePerQuestionEdit(TableColumn.CellEditEvent<Competition, String> event) {
        Competition competition = (Competition) event.getTableView().getItems().get( event.getTablePosition().getRow() );
        if (new TextIsNumericSpecification().isSatisfiedBy(event.getNewValue())) {
            competition.setTimePerQuestion(event.getNewValue());
            competitionRepo.update(competition);
        } else {
            Notifier.error("expected a numeric value");
        }
    }
    
    @FXML
    private void onNoOfRoundsEdit(TableColumn.CellEditEvent<Competition, String> event) {
        Competition competition = (Competition) event.getTableView().getItems().get( event.getTablePosition().getRow() );
        if (new TextIsNumericSpecification().isSatisfiedBy(event.getNewValue())) {
            competition.setNoOfRounds(event.getNewValue());
            competitionRepo.update(competition);
        } else {
            Notifier.error("expected a numeric value");
        }
    }

    @FXML
    private void onAddClick(ActionEvent event) {
        if (FieldValidator.isFieldsValid(getFieldValidationResults())) {
            Competition competition = new Competition();
            competition.setName(nameField.getText());
            competition.setNoOfRounds(noOfRoundsField.getText());
            competition.setNoOfTeams(noOfTeamsField.getText());
            competition.setTimePerQuestion(timePerQuestionField.getText());
            competitionsList.add(competition);
            competitionRepo.add(competition);
            getLastAddedCompetition().ifPresent( (comp) -> competition.setId(comp.getId()) );
        } 
    }
    
    private List<Boolean> getFieldValidationResults() {
        List<Boolean> fieldValidationResults = new ArrayList<>();
        CompositeSpecification textIsNumericSpec = new TextIsNumericSpecification();
        
        fieldValidationResults.add(new TextIsNotEmptySpecification().isSatisfiedBy(nameField.getText()));
        fieldValidationResults.add(textIsNumericSpec.isSatisfiedBy(noOfRoundsField.getText()));
        fieldValidationResults.add(textIsNumericSpec.isSatisfiedBy(timePerQuestionField.getText()));
        fieldValidationResults.add(textIsNumericSpec.isSatisfiedBy(noOfTeamsField.getText()));
        return fieldValidationResults;
    }
    
    private Optional<Competition> getLastAddedCompetition(){
        return competitionRepo.query(new LastAddedCompetitionSpecification()).stream().findFirst();
    }
    
    @FXML
    private void delete(KeyEvent evt) {
        if (evt.getCode().equals(KeyCode.DELETE)) {   
            
            if (Notifier.confirm("Are you sure you want to delete competition")) {
                Competition competition = competitionsTable.getSelectionModel().getSelectedItem();
                competitionsTable.getItems().remove(competition);
                competitionRepo.remove(competition);
            }
        }
    }
    
}