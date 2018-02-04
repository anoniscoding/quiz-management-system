package quizmanagementsystem.Specification.CompositeSpecification;

import javafx.collections.ObservableList;
import quizmanagementsystem.Model.Team;

/**
 *
 * @author anonCoding
 */
public class HasReachedMaximumNoOfTeamsSpecification implements CompositeSpecification<ObservableList<Team>> {
    
    private final int maxNoOfTeams;

    public HasReachedMaximumNoOfTeamsSpecification(int maxNoOfTeams) {
        this.maxNoOfTeams = maxNoOfTeams;
    }
    

    @Override
    public boolean isSatisfiedBy(ObservableList<Team> teamList) {
        return teamList.size() == maxNoOfTeams;
    }
    
}
