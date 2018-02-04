package quizmanagementsystem.Specification.SqlSpecification;

import quizmanagementsystem.DataSourceLayer.DatabaseHelperUtil;

/**
 *
 * @author anonCoding
 */
public class LastAddedTeamSpecification implements ISqlSpecification {

    @Override
    public String toSqlQuery() {
        return String.format("SELECT * FROM %s WHERE %s=(SELECT max(%s) from %s)",
                    DatabaseHelperUtil.TEAMS_TABLE_NAME,
                    DatabaseHelperUtil.COLUMN_TEAMS_ID,
                    DatabaseHelperUtil.COLUMN_TEAMS_ID,
                    DatabaseHelperUtil.TEAMS_TABLE_NAME
                );
                
    }
    
}
