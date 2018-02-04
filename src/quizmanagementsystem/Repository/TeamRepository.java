package quizmanagementsystem.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import quizmanagementsystem.DataSourceLayer.DatabaseHelperUtil;
import quizmanagementsystem.DataSourceLayer.DbUtil;
import quizmanagementsystem.Model.Team;
import quizmanagementsystem.Specification.SqlSpecification.ISqlSpecification;
import quizmanagementsystem.Specification.SqlSpecification.NewTeamSpecification;
import quizmanagementsystem.Specification.SqlSpecification.RemoveTeamSpecification;
import quizmanagementsystem.Specification.SqlSpecification.TeamUpdateSpecification;
import quizmanagementsystem.interfaces.DbUtilInterface;

/**
 *
 * @author anonCoding
 */
public class TeamRepository implements IRepository<Team> {
    
    private final DbUtilInterface dbUtil = new DbUtil();
    
    @Override
    public void add(Team team) {
        dbUtil.dbExecuteUpdate(new NewTeamSpecification(team).toSqlQuery());
    }

    @Override
    public void remove(Team team) {
        dbUtil.dbExecuteUpdate(new RemoveTeamSpecification(team).toSqlQuery());
    }

    @Override
    public void update(Team team) {
        dbUtil.dbExecuteUpdate(new TeamUpdateSpecification(team).toSqlQuery());
    }

    @Override
    public List<Team> query(ISqlSpecification spec) {
        ResultSet rs = dbUtil.dbExecuteQuery(spec.toSqlQuery());
        List<Team> teams = new ArrayList<>();
        try {
            while (rs.next()) {
                Team team = new Team();
                team.setId(rs.getInt(DatabaseHelperUtil.COLUMN_TEAMS_ID));
                team.setName(rs.getString(DatabaseHelperUtil.COLUMN_TEAMS_NAME));
                team.setCompetitonId(rs.getInt(DatabaseHelperUtil.COLUMN_TEAMS_COMPETITION_ID));
                teams.add(team);
            }
        } catch (Exception e) {}
        return teams;
    }
    
}
