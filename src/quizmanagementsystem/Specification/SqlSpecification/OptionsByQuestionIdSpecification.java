package quizmanagementsystem.Specification.SqlSpecification;

import quizmanagementsystem.DataSourceLayer.DatabaseHelperUtil;

/**
 *
 * @author anonCoding
 */
public class OptionsByQuestionIdSpecification implements ISqlSpecification {
    
    private final int _questionId;

    public OptionsByQuestionIdSpecification(int id) {
        this._questionId = id;
    }
    
    
    @Override
    public String toSqlQuery() {
        return String.format("SELECT * FROM %s WHERE %s = %d" , 
                    DatabaseHelperUtil.OPTIONS_TABLE_NAME,
                    DatabaseHelperUtil.COLUMN_OPTION_QUESTION_ID,
                    this._questionId
                );
    }
    
}
