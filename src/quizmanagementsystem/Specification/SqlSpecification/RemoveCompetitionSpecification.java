package quizmanagementsystem.Specification.SqlSpecification;

import quizmanagementsystem.DataSourceLayer.DatabaseHelperUtil;
import quizmanagementsystem.Model.Competition;

/**
 *
 * @author anonCoding
 */
public class RemoveCompetitionSpecification implements ISqlSpecification {
    
    private final Competition competition;

    public RemoveCompetitionSpecification(Competition competition) {
        this.competition = competition;
    }

    @Override
    public String toSqlQuery() {
        return String.format("PRAGMA foreign_keys =ON; DELETE FROM %s WHERE %s = %d", 
                    DatabaseHelperUtil.COMPETITIONS_TABLE_NAME,
                    DatabaseHelperUtil.COLUMN_COMPETITIONS_ID,
                    competition.getId()
               );
    }
    
}
