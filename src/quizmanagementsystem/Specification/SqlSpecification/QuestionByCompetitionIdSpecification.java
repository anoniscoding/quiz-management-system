package quizmanagementsystem.Specification.SqlSpecification;

import quizmanagementsystem.DataSourceLayer.DatabaseHelperUtil;

/**
 *
 * @author anonCoding
 */
public class QuestionByCompetitionIdSpecification implements ISqlSpecification {
    
    private final int _competitionId;

    public QuestionByCompetitionIdSpecification(int competitionId) {
        this._competitionId = competitionId;
    }

    @Override
    public String toSqlQuery() {
        return String.format("SELECT * FROM %s WHERE %s = %d" , 
                    DatabaseHelperUtil.QUESTIONS_TABLE_NAME,
                    DatabaseHelperUtil.COLUMN_QUESTIONS_COMPETITION_ID,
                    this._competitionId
                );
    }
    
}
