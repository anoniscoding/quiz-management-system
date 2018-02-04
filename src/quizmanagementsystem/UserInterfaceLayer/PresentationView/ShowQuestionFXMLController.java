package quizmanagementsystem.UserInterfaceLayer.PresentationView;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;
import quizmanagementsystem.Model.Option;
import quizmanagementsystem.Model.Question;

/**
 * FXML Controller class
 *
 * @author anonCoding
 */
public class ShowQuestionFXMLController {
    @FXML
    private Label timeDurationLabel;
    @FXML
    private TextFlow questionLabel;
    @FXML
    private AnchorPane pageAnchorPane;
    @FXML
    private Button scoreTeamBtn;
    @FXML
    private Button restartBtn;
    
    private final GridPane optionsGridPane = new GridPane();
    private final ToggleGroup optionsToggleGroup = new ToggleGroup();
    
    private Question _question;
    
    private final IntegerProperty timeDuration = new SimpleIntegerProperty(0);
    private final Timeline timeline = new Timeline();
    private int countDownTime;
    
    private final ReadOnlyObjectWrapper<Boolean> selectedOptionObjectWrapper = new ReadOnlyObjectWrapper<>();
    private final ReadOnlyObjectWrapper<Boolean> disableQuestionObjectWrapper = new ReadOnlyObjectWrapper<>();

    @FXML
    private void onScoreTeamBtnClick(ActionEvent event) {
        if (optionsToggleGroup.getSelectedToggle() != null) {
            timeline.stop();
            this.setSelectedOption();
            this.closePage();
            return;
        }
        
        selectedOptionObjectWrapper.set(false); //team got question wrong
        this.closePage();
    }

    private void setSelectedOption() {
        String option = optionsToggleGroup.getSelectedToggle().getUserData().toString();
        selectedOptionObjectWrapper.set(option.equals(this.getQuestionAnswer()));
    }

    @FXML
    private void onDisableBtnClick(ActionEvent event) {
        timeline.stop();
        disableQuestionObjectWrapper.set(true);
        this.closePage();
    }
    
    @FXML
    private void onRestartBtnClick(ActionEvent event) {
        countDownTime = getTimeDuration();
        timeline.playFromStart();
        optionsGridPane.setDisable(false);
        restartBtn.setVisible(false);
        timeDurationLabel.setVisible(true);
    }
    
    public ReadOnlyObjectProperty<Boolean> selectedOptionProperty(){
        return selectedOptionObjectWrapper.getReadOnlyProperty();
    }
    
    public ReadOnlyObjectProperty<Boolean> disableQuestionProperty(){
        return disableQuestionObjectWrapper.getReadOnlyProperty();
    }
    
    public void setQuestionAndDuration(Question question, int duration) {
        this._question = question;
        this.timeDuration.set(duration);
        this.showQuestion();
    }
    
    public void showQuestion() {
        this.displayTimeDuration();
        this.displayQuestion();
        this.displayOptions();
    }
    
    public void displayQuestion() {
        Text questionContent = new Text(_question.getContent());
        questionContent.setStyle("-fx-font-size: 30px");
        this.questionLabel.getChildren().add(questionContent);
    }
    
    public void displayOptions() {
        optionsGridPane.setHgap(200);
        optionsGridPane.setVgap(80);
        optionsGridPane.setPadding(new Insets(270,15,15,100));
        
        addOptionsToGridPane();
        pageAnchorPane.getChildren().add(optionsGridPane);
    }

    private void addOptionsToGridPane() {
        int rowCount = 0;
        int colCount = 0;
        for (int i = 0; i < _question.getOptions().size(); i++) {
            RadioButton rb = new RadioButton();
            rb.setToggleGroup(optionsToggleGroup);
            rb.setText(_question.getOptions().get(i).getContent());
            rb.setUserData(_question.getOptions().get(i).getContent());
            rb.wrapTextProperty().setValue(true);
            rb.setWrapText(true);
            rb.setMaxWidth(500.0);
            boolean nextRow = (i % 2 == 0) && (i != 0);
            if (nextRow) {
                colCount = 0;
                optionsGridPane.add(rb, colCount++, ++rowCount, 1, 1);  
            } else
                optionsGridPane.add(rb, colCount++, rowCount,1 , 1);
        }
    }
    
    public void displayTimeDuration() {
        this.timeDurationLabel.setText(Integer.toString(timeDuration.get()));
        this.timeDurationLabel.setTextFill(Color.RED);
        timer();
    }
    
    public int getTimeDuration() {
        return timeDuration.get();
    }
    
    public void timer() {
        KeyFrame keyframe = new KeyFrame(Duration.seconds(1), (ActionEvent event) -> {
            countDownTime--;
            boolean isSecondsZero = countDownTime == 0;
            
            if (isSecondsZero) {
                timeline.stop();
                this.processUserDecision();
            }
            timeDurationLabel.setText(String.format("%02d sec", countDownTime));
        });
        countDownTime = getTimeDuration();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(keyframe);
        timeline.playFromStart();
    }
    
    private void processUserDecision() {
        if (optionsToggleGroup.getSelectedToggle() != null) {
            setSelectedOption();
            this.closePage();
        } else {
            optionsGridPane.setDisable(true);
            restartBtn.setVisible(true);
            timeDurationLabel.setVisible(false);
        }
    }
    
    private String getQuestionAnswer() {
        String answer = "";
        for (Option option: _question.getOptions()) {
            if (option.isCorrect()) 
                answer = option.getContent();
        }
        return answer;
    }
    
    private void closePage() {
        scoreTeamBtn.getScene().getWindow().hide();
    }
    
}