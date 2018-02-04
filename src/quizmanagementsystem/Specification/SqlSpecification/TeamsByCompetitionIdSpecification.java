package quizmanagementsystem.Specification.SqlSpecification;

import quizmanagementsystem.DataSourceLayer.DatabaseHelperUtil;

/**
 *
 * @author anonCoding
 */
public class TeamsByCompetitionIdSpecification implements ISqlSpecification {
    
    private final int _competitionId;

    public TeamsByCompetitionIdSpecification(int id) {
        this._competitionId = id;
    }
    
    @Override
    public String toSqlQuery() {
        return String.format("SELECT * FROM %s WHERE %s = %d", 
                    DatabaseHelperUtil.TEAMS_TABLE_NAME,
                    DatabaseHelperUtil.COLUMN_TEAMS_COMPETITION_ID,
                    _competitionId
               );
    }
    
}
