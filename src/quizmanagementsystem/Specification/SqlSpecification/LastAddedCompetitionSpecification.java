package quizmanagementsystem.Specification.SqlSpecification;

import quizmanagementsystem.DataSourceLayer.DatabaseHelperUtil;

/**
 *
 * @author anonCoding
 */
public class LastAddedCompetitionSpecification implements ISqlSpecification {

    @Override
    public String toSqlQuery() {
        return String.format("SELECT * FROM %s WHERE %s=(SELECT max(%s) from %s)",
                    DatabaseHelperUtil.COMPETITIONS_TABLE_NAME,
                    DatabaseHelperUtil.COLUMN_COMPETITIONS_ID,
                    DatabaseHelperUtil.COLUMN_COMPETITIONS_ID,
                    DatabaseHelperUtil.COMPETITIONS_TABLE_NAME
               );
    }
    
}
