package quizmanagementsystem.Specification.SqlSpecification;

import quizmanagementsystem.DataSourceLayer.DatabaseHelperUtil;

/**
 *
 * @author anonCoding
 */
public class LastAddedQuestionSpecification implements ISqlSpecification {

    @Override
    public String toSqlQuery() {
        return String.format("SELECT * FROM %s WHERE %s=(SELECT max(%s) from %s)",
                    DatabaseHelperUtil.QUESTIONS_TABLE_NAME,
                    DatabaseHelperUtil.COLUMN_QUESTIONS_ID,
                    DatabaseHelperUtil.COLUMN_QUESTIONS_ID,
                    DatabaseHelperUtil.QUESTIONS_TABLE_NAME
               );
    }
    
}
