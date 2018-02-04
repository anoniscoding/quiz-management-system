package quizmanagementsystem.Specification.SqlSpecification;

import quizmanagementsystem.DataSourceLayer.DatabaseHelperUtil;
import quizmanagementsystem.Model.Question;

/**
 *
 * @author anonCoding
 */
public class NewQuestionSpecification implements ISqlSpecification {
    
    private final Question _question;

    public NewQuestionSpecification(Question question) {
        this._question = question;
    }

    @Override
    public String toSqlQuery() {
        return String.format("INSERT INTO %s(%s, %s) VALUES('%s', '%d')",
                    DatabaseHelperUtil.QUESTIONS_TABLE_NAME,
                    DatabaseHelperUtil.COLUMN_QUESTION_CONTENT,
                    DatabaseHelperUtil.COLUMN_QUESTIONS_COMPETITION_ID,
                    _question.getContent(),
                    _question.getCompetitonId()
                );
    }
    
}
