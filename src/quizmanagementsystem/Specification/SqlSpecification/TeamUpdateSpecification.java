package quizmanagementsystem.Specification.SqlSpecification;

import quizmanagementsystem.DataSourceLayer.DatabaseHelperUtil;
import quizmanagementsystem.Model.Team;

/**
 *
 * @author anonCoding
 */
public class TeamUpdateSpecification implements ISqlSpecification {
    
    private final Team _team;

    public TeamUpdateSpecification(Team team) {
        this._team = team;
    }

    @Override
    public String toSqlQuery() {
        return String.format("UPDATE %s SET %s = '%s' WHERE %s = %d ",
                    DatabaseHelperUtil.TEAMS_TABLE_NAME,
                    DatabaseHelperUtil.COLUMN_TEAMS_NAME,
                    _team.getName(),
                    DatabaseHelperUtil.COLUMN_TEAMS_ID,
                    _team.getId()
               );
    }
    
}
