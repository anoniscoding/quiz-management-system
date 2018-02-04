/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quizmanagementsystem.UserInterfaceLayer.PresentationView;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import quizmanagementsystem.InfrastructuralLayer.Notifier;
import quizmanagementsystem.Model.Option;
import quizmanagementsystem.Model.Question;
import quizmanagementsystem.Repository.IRepository;
import quizmanagementsystem.Repository.OptionRepository;
import quizmanagementsystem.Repository.QuestionRepository;
import quizmanagementsystem.Specification.CompositeSpecification.TextIsNotEmptySpecification;
import quizmanagementsystem.Specification.SqlSpecification.OptionsByQuestionIdSpecification;
import quizmanagementsystem.Specification.SqlSpecification.QuestionByCompetitionIdSpecification;

/**
 * FXML Controller class
 *
 * @author anonCoding
 */
public class ViewQuestionsFxmlController implements Initializable {
    @FXML
    private TableView<Question> questionsTable;
    @FXML
    private TableColumn<Question, String> questionColumn;
    @FXML
    private TableView<Option> optionsTable;
    @FXML
    private TableColumn<Option, String> optionContentColumn;
    @FXML
    private TableColumn<Option, Boolean> isCorrectColumn;
    @FXML
    private TextField optionTextField;
    
    private int _competitionId;
    
    private final ObservableList<Question> questionsList = FXCollections.observableArrayList();
    private final ObservableList<Option> optionsList = FXCollections.observableArrayList();
    
    private final IRepository questionRepo = new QuestionRepository();
    private final IRepository optionsRepo = new OptionRepository();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initQuestionTable();
        initOptionsTable();
    }   
    
    private void initQuestionTable() {
        questionsTable.setEditable(true);
        
        questionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        questionColumn.setCellValueFactory(new PropertyValueFactory<>("content"));
        
        questionsTable.setItems(questionsList);
    }
    
    private void initOptionsTable() {
        optionsTable.setEditable(true);
        
        optionContentColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        optionContentColumn.setCellValueFactory(new PropertyValueFactory<>("content"));
        
        isCorrectColumn.setCellValueFactory((TableColumn.CellDataFeatures<Option, Boolean> param) -> {
            Option option = param.getValue();
            SimpleBooleanProperty booleanProp = new SimpleBooleanProperty( option.isCorrect() );
            booleanProp.addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                onIsCorrectEdit(option, newValue);
            });
            return booleanProp;
        });      
        isCorrectColumn.setCellFactory((TableColumn<Option, Boolean> p) -> {
            CheckBoxTableCell<Option, Boolean> cell = new CheckBoxTableCell<>();
            cell.setAlignment(Pos.CENTER);
            return cell;
        });
        
        optionsTable.setItems(optionsList);
    }

    private void onIsCorrectEdit(Option option, Boolean newValue) {
        option.setIsCorrect(newValue);
        optionsList.stream().forEach( (op) -> {
            if (op == option) {
                optionsRepo.update(option);
            } else if (op.isCorrect() == true) {
                    op.setIsCorrect(false); //sets previously correct option to false
                    optionsRepo.update(op);
            }
        });
        displayOptions(); //to refresh the options table
    }
    
    public void setCompetitionId(int id){
        this._competitionId = id;
        populateQuestionTable();
    }
    
    private void populateQuestionTable() {
        questionsList.addAll(questionRepo.query(new QuestionByCompetitionIdSpecification(_competitionId)));
    }
    

    @FXML
    private void onQuestionEdit(TableColumn.CellEditEvent<Question, String> event) {
        Question question = (Question) event.getTableView().getItems().get( event.getTablePosition().getRow() );
        question.setContent(event.getNewValue());
        questionRepo.update(question);
    }

    @FXML
    private void onOptionContentEdit(TableColumn.CellEditEvent<Option, String> event) {
        Option option = (Option) event.getTableView().getItems().get( event.getTablePosition().getRow() );
        option.setContent(event.getNewValue());
        optionsRepo.update(option);
    }

    @FXML
    private void onOptionTableKeyPress(KeyEvent evt) {
        if (evt.getCode().equals(KeyCode.DELETE)) { 
            Option option = optionsTable.getSelectionModel().getSelectedItem();
            optionsTable.getItems().remove(option);
            optionsRepo.remove(option);
        }
    }
    
    @FXML
    private void onQuestionTableKeyPress(KeyEvent evt) {
        if (evt.getCode().equals(KeyCode.DELETE)) { 
            Question question = questionsTable.getSelectionModel().getSelectedItem();
            questionsTable.getItems().remove(question);
            questionRepo.remove(question);
            
            if(questionsList.size() > 0){
                displayOptions(); //display options for the next automatically selected row
            } else {
                optionsList.clear();
            }
        }
    }
    
    @FXML
    private void onQuestionTableKeyRelease(KeyEvent evt) {
        if (evt.getCode().equals(KeyCode.UP) || evt.getCode().equals(KeyCode.DOWN)) {
            displayOptions(); 
        }
    }
    
    @FXML
    private void onQuestionTableClick(MouseEvent evt) {
        displayOptions();
    }
    
    @FXML
    private void onAddOptionClick(ActionEvent event) {
        if (questionsTable.getSelectionModel().getSelectedItem() != null) {
            
            if (new TextIsNotEmptySpecification().isSatisfiedBy(optionTextField.getText())) {
                optionsRepo.add(getNewOption());
                displayOptions(); //to refresh the options table
                return;
            }
            Notifier.alert("New option cannot be an empty string");    
            return;
        }
        Notifier.alert("Please select a question");
    }

    private Option getNewOption() {
        Option option = new Option();
        option.setIsCorrect(false);
        option.setContent(optionTextField.getText());
        
        if (optionTextField.getText().startsWith("*")) {
            optionsList.stream().forEach( (op) -> {
                if (op.isCorrect() == true) {
                    op.setIsCorrect(false); //sets previously correct option to false
                    optionsRepo.update(op);
                }
            });
            option.setIsCorrect(true);
            option.setContent(optionTextField.getText().substring(1));
        }
        
        option.setQuestionId(questionsTable.getSelectionModel().getSelectedItem().getId());
        return option;
    }

    private void displayOptions() {
        optionsList.clear();
        Question question = questionsTable.getSelectionModel().getSelectedItem();
        optionsList.addAll(optionsRepo.query(new OptionsByQuestionIdSpecification(question.getId())));
    }
}