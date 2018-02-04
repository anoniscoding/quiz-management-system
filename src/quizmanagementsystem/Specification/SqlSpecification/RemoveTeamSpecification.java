package quizmanagementsystem.Specification.SqlSpecification;

import quizmanagementsystem.DataSourceLayer.DatabaseHelperUtil;
import quizmanagementsystem.Model.Team;

/**
 *
 * @author anonCoding
 */
public class RemoveTeamSpecification implements ISqlSpecification {
    
    private final Team _team;

    public RemoveTeamSpecification(Team team) {
        this._team = team;
    }
    
    
    @Override
    public String toSqlQuery() {
        return String.format("DELETE FROM %s WHERE %s = %d",
                    DatabaseHelperUtil.TEAMS_TABLE_NAME,
                    DatabaseHelperUtil.COLUMN_TEAMS_ID,
                    _team.getId()
                );
    }
    
}
