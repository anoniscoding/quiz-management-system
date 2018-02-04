package quizmanagementsystem.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import quizmanagementsystem.DataSourceLayer.DatabaseHelperUtil;
import quizmanagementsystem.DataSourceLayer.DbUtil;
import quizmanagementsystem.Model.Competition;
import quizmanagementsystem.Specification.SqlSpecification.CompetitionUpdateSpecification;
import quizmanagementsystem.Specification.SqlSpecification.ISqlSpecification;
import quizmanagementsystem.Specification.SqlSpecification.NewCompetitionSpecification;
import quizmanagementsystem.Specification.SqlSpecification.RemoveCompetitionSpecification;
import quizmanagementsystem.interfaces.DbUtilInterface;

/**
 *
 * @author anonCoding
 */
public class CompetitionRepository implements IRepository<Competition> {
    
    private final DbUtilInterface dbUtil = new DbUtil();

    @Override
    public void add(Competition competition) {
        dbUtil.dbExecuteUpdate(new NewCompetitionSpecification(competition).toSqlQuery());
    }

    @Override
    public void remove(Competition competition) {
        dbUtil.dbExecuteUpdate(new RemoveCompetitionSpecification(competition).toSqlQuery());
    }

    @Override
    public void update(Competition competition) {
        dbUtil.dbExecuteUpdate(new CompetitionUpdateSpecification(competition).toSqlQuery());
    }

    @Override
    public List<Competition> query(ISqlSpecification spec) {
        ResultSet rs = dbUtil.dbExecuteQuery(spec.toSqlQuery());
        List<Competition> competitions = new ArrayList<>();
        try {
            while (rs.next()) {
                Competition competition = new Competition();
                competition.setId(rs.getInt(DatabaseHelperUtil.COLUMN_COMPETITIONS_ID));
                competition.setName(rs.getString(DatabaseHelperUtil.COLUMN_COMPETITIONS_NAME));
                competition.setNoOfRounds(rs.getString(DatabaseHelperUtil.COLUMN_COMPETITIONS_NO_OF_ROUNDS));
                competition.setNoOfTeams(rs.getString(DatabaseHelperUtil.COLUMN_COMPETITIONS_NO_OF_TEAMS));
                competition.setTimePerQuestion(rs.getString(DatabaseHelperUtil.COLUMN_COMPETITIONS_TIME_PER_QUESTION));
                competitions.add(competition);
            }
        } catch (Exception e) {}
        return competitions;
    }
    
}
