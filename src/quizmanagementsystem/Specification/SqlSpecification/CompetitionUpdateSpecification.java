package quizmanagementsystem.Specification.SqlSpecification;

import quizmanagementsystem.DataSourceLayer.DatabaseHelperUtil;
import quizmanagementsystem.Model.Competition;

/**
 *
 * @author anonCoding
 */
public class CompetitionUpdateSpecification implements ISqlSpecification {
    
    private final Competition _competition;
    
    public CompetitionUpdateSpecification(final Competition competition) {
        this._competition = competition;
    }
    
    @Override
    public String toSqlQuery() {
        return String.format("UPDATE %s SET %s = '%s', %s = '%s', %s = '%s', %s = '%s' WHERE %s = %d ", 
                    DatabaseHelperUtil.COMPETITIONS_TABLE_NAME,
                    DatabaseHelperUtil.COLUMN_COMPETITIONS_NAME,
                    this._competition.getName(),
                    DatabaseHelperUtil.COLUMN_COMPETITIONS_NO_OF_ROUNDS,
                    this._competition.getNoOfRounds(),
                    DatabaseHelperUtil.COLUMN_COMPETITIONS_NO_OF_TEAMS,
                    this._competition.getNoOfTeams(),
                    DatabaseHelperUtil.COLUMN_COMPETITIONS_TIME_PER_QUESTION,
                    this._competition.getTimePerQuestion(),
                    DatabaseHelperUtil.COLUMN_COMPETITIONS_ID,
                    this._competition.getId()
               );
    }
    
}
