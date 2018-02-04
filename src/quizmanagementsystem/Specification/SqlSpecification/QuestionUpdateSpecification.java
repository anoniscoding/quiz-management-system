package quizmanagementsystem.Specification.SqlSpecification;

import quizmanagementsystem.DataSourceLayer.DatabaseHelperUtil;
import quizmanagementsystem.Model.Question;

/**
 *
 * @author anonCoding
 */
public class QuestionUpdateSpecification implements ISqlSpecification {
    
    private final Question _question;

    public QuestionUpdateSpecification(Question question) {
        this._question = question;
    }
    
    @Override
    public String toSqlQuery() {
        return String.format("UPDATE %s SET %s = '%s' WHERE %s = %d ",
                    DatabaseHelperUtil.QUESTIONS_TABLE_NAME,
                    DatabaseHelperUtil.COLUMN_QUESTION_CONTENT,
                    _question.getContent(),
                    DatabaseHelperUtil.COLUMN_QUESTIONS_ID,
                    _question.getId()
               );
    }
    
}
