package quizmanagementsystem.Specification.SqlSpecification;

import quizmanagementsystem.DataSourceLayer.DatabaseHelperUtil;

/**
 *
 * @author anonCoding
 */
public class AllCompetitionsSpecification implements ISqlSpecification {

    @Override
    public String toSqlQuery() {
        return String.format("SELECT * FROM %s", 
                    DatabaseHelperUtil.COMPETITIONS_TABLE_NAME
               );
    }
    
}
