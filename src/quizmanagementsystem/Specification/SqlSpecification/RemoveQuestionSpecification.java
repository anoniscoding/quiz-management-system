package quizmanagementsystem.Specification.SqlSpecification;

import quizmanagementsystem.DataSourceLayer.DatabaseHelperUtil;
import quizmanagementsystem.Model.Question;

/**
 *
 * @author anonCoding
 */
public class RemoveQuestionSpecification implements ISqlSpecification {
    
    private final Question _question;

    public RemoveQuestionSpecification(Question question) {
        this._question = question;
    }

    @Override
    public String toSqlQuery() {
        return String.format("PRAGMA foreign_keys =ON; DELETE FROM %s WHERE %s = %d",
                    DatabaseHelperUtil.QUESTIONS_TABLE_NAME,
                    DatabaseHelperUtil.COLUMN_QUESTIONS_ID,
                    _question.getId()
                );
    }
    
}
