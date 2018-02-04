package quizmanagementsystem.UserInterfaceLayer.PresentationView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
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
import quizmanagementsystem.Model.Team;
import quizmanagementsystem.Repository.CompetitionRepository;
import quizmanagementsystem.Repository.IRepository;
import quizmanagementsystem.Repository.TeamRepository;
import quizmanagementsystem.Specification.CompositeSpecification.CompositeSpecification;
import quizmanagementsystem.Specification.CompositeSpecification.HasReachedMaximumNoOfTeamsSpecification;
import quizmanagementsystem.Specification.CompositeSpecification.TextIsNotEmptySpecification;
import quizmanagementsystem.Specification.SqlSpecification.AllCompetitionsSpecification;
import quizmanagementsystem.Specification.SqlSpecification.LastAddedTeamSpecification;
import quizmanagementsystem.Specification.SqlSpecification.TeamsByCompetitionIdSpecification;

/**
 * FXML Controller class
 *
 * @author anonCoding
 */
public class RegistrationFxmlController implements Initializable {
    @FXML
    private TextField teamNameField;
    @FXML
    private TableView<Team> teamsTable;
    @FXML
    private TableColumn<Team, String> teamNameColumn;
    @FXML
    private ComboBox competitionComboBox;
    
    private ObservableList<Team> teamsList = FXCollections.observableArrayList();
    
    List<Competition> competitions = new ArrayList();
    
    private final IRepository competitionRepo = new CompetitionRepository();
    private final IRepository teamRepo = new TeamRepository();
    
    private final List<Integer> competitionsId = new ArrayList();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initTable();
        populateCompetitionComboBox();
        onCompetitionsComboBoxValueChange();
    }  
    
    private void initTable() {
        teamsTable.setEditable(true);
        
        teamNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        teamNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        teamsTable.setItems(teamsList);
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
    
    private void onCompetitionsComboBoxValueChange() {
        competitionComboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                populateTable(competitionsId.get(competitionComboBox.getSelectionModel().getSelectedIndex()));
            }
        });
    }
    
    private void populateTable(int competitionId) {
        teamsList.clear();
        teamsList.addAll(teamRepo.query(new TeamsByCompetitionIdSpecification(competitionId)));
    }

    @FXML
    private void onAddClick(ActionEvent event) {
        if (FieldValidator.isFieldsValid(getFieldValidationResults())) {
            Competition competition = competitions.get(competitionComboBox.getSelectionModel().getSelectedIndex());
            if (new HasReachedMaximumNoOfTeamsSpecification(Integer.valueOf(competition.getNoOfTeams())).isSatisfiedBy(teamsList)) {
                Notifier.error("Cannot exceed set no of teams");
                return;
            }
            
            Team team = new Team();
            team.setName(teamNameField.getText());
            team.setCompetitonId(competitionsId.get(competitionComboBox.getSelectionModel().getSelectedIndex()));
            teamsList.add(team);
            teamRepo.add(team);
            getLastAddedTeam().ifPresent( (t) -> team.setId(t.getId()) );
            teamNameField.clear();
        }
    }
    
    private List<Boolean> getFieldValidationResults() {
        List<Boolean> fieldValidationResults = new ArrayList<>();
        CompositeSpecification textIsNotEmptySpec = new TextIsNotEmptySpecification();
        fieldValidationResults.add(textIsNotEmptySpec.isSatisfiedBy(teamNameField.getText()));
        fieldValidationResults.add(textIsNotEmptySpec.isSatisfiedBy(String.valueOf(competitionComboBox.getSelectionModel().getSelectedIndex())));
        return fieldValidationResults;
    }
    
    private Optional<Team> getLastAddedTeam(){
        return teamRepo.query(new LastAddedTeamSpecification()).stream().findFirst();
    }

    @FXML
    private void onTeamNameEdit(TableColumn.CellEditEvent<Team, String> event) {
        Team team = (Team) event.getTableView().getItems().get( event.getTablePosition().getRow() );
        team.setName(event.getNewValue());
        teamRepo.update(team);
    }
    
    @FXML
    private void delete(KeyEvent evt) {
        if (evt.getCode().equals(KeyCode.DELETE)) {   
            
            if (Notifier.confirm("Are you sure you want to delete team")) {
                Team team = teamsTable.getSelectionModel().getSelectedItem();
                teamsTable.getItems().remove(team);
                teamRepo.remove(team);
            }
        }
    }
}
