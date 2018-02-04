package quizmanagementsystem.UserInterfaceLayer.PresentationView;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import quizmanagementsystem.Model.Team;
import quizmanagementsystem.Repository.IRepository;
import quizmanagementsystem.Repository.TeamRepository;
import quizmanagementsystem.Specification.SqlSpecification.TeamsByCompetitionIdSpecification;

/**
 * FXML Controller class
 *
 * @author anonCoding
 */
public class ViewResultFXMLController implements Initializable {
    @FXML
    private AnchorPane viewResultsAnchorPane;
    
    private final IRepository teamRepo = new TeamRepository();
    private int _competitionId;
    
    private Map<String, Integer> _teamsToScores = new HashMap();
    private List<Team> _teamsList = new ArrayList();
    
    GridPane resultsGridPane = new GridPane();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        resultsGridPane.setHgap(100);
        resultsGridPane.setVgap(20);
        resultsGridPane.setPadding(new Insets(80,15,25,100));
    }    
    
    public void setTeamsToScores(int competitionId, Map<String, Integer> teamsToScores) {
        this._teamsToScores = teamsToScores;
        this._competitionId = competitionId;
        this.setTeamList();
        this.showResults();
    }
    
    private void showResults() {
        int firstCol = 0, secondCol = 1;
        
        for (int rowCount = 0; rowCount < _teamsList.size(); rowCount++){           
            resultsGridPane.add(new Label(_teamsList.get(rowCount).getName()), firstCol, rowCount, 1, 1);
            resultsGridPane.add(new Label( String.valueOf(_teamsToScores.get(_teamsList.get(rowCount).getName())) ), secondCol, rowCount, 1, 1);    
        }
        
        viewResultsAnchorPane.getChildren().add(resultsGridPane);
    }
    
    private void setTeamList() {
        this._teamsList = teamRepo.query(new TeamsByCompetitionIdSpecification(_competitionId));
    }
}
