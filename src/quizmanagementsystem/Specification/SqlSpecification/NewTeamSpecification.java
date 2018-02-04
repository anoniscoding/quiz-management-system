package quizmanagementsystem.Specification.SqlSpecification;

import quizmanagementsystem.DataSourceLayer.DatabaseHelperUtil;
import quizmanagementsystem.Model.Team;

/**
 *
 * @author anonCoding
 */
public class NewTeamSpecification implements ISqlSpecification {
    
    private final Team _team;

    public NewTeamSpecification(Team team) {
        this._team = team;
    }
    
    @Override
    public String toSqlQuery() {
        return String.format("INSERT INTO %s(%s, %s) VALUES('%s', '%s')",
                    DatabaseHelperUtil.TEAMS_TABLE_NAME,
                    DatabaseHelperUtil.COLUMN_TEAMS_NAME,
                    DatabaseHelperUtil.COLUMN_TEAMS_COMPETITION_ID,
                    _team.getName(),
                    _team.getCompetitonId()
                );
    }
    
}
