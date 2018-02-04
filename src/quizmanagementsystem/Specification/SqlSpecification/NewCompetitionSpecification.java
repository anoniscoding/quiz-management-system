package quizmanagementsystem.Specification.SqlSpecification;

import quizmanagementsystem.DataSourceLayer.DatabaseHelperUtil;
import quizmanagementsystem.Model.Competition;

/**
 *
 * @author anonCoding
 */
public class NewCompetitionSpecification implements ISqlSpecification{
    
    private final Competition competition;

    public NewCompetitionSpecification(Competition competition) {
        this.competition = competition;
    }

    @Override
    public String toSqlQuery() {
        return String.format("INSERT INTO %s(%s,%s,%s,%s) VALUES('%s','%s','%s','%s')", 
                    DatabaseHelperUtil.COMPETITIONS_TABLE_NAME,
                    DatabaseHelperUtil.COLUMN_COMPETITIONS_NAME,
                    DatabaseHelperUtil.COLUMN_COMPETITIONS_TIME_PER_QUESTION,
                    DatabaseHelperUtil.COLUMN_COMPETITIONS_NO_OF_ROUNDS,
                    DatabaseHelperUtil.COLUMN_COMPETITIONS_NO_OF_TEAMS,
                    competition.getName(),
                    competition.getTimePerQuestion(),
                    competition.getNoOfRounds(),
                    competition.getNoOfTeams()
                );
    }
    
}
