package quizmanagementsystem.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import quizmanagementsystem.DataSourceLayer.DatabaseHelperUtil;
import quizmanagementsystem.DataSourceLayer.DbUtil;
import quizmanagementsystem.Model.Question;
import quizmanagementsystem.Specification.SqlSpecification.ISqlSpecification;
import quizmanagementsystem.Specification.SqlSpecification.NewQuestionSpecification;
import quizmanagementsystem.Specification.SqlSpecification.QuestionUpdateSpecification;
import quizmanagementsystem.Specification.SqlSpecification.RemoveQuestionSpecification;
import quizmanagementsystem.interfaces.DbUtilInterface;

/**
 *
 * @author anonCoding
 */
public class QuestionRepository implements IRepository<Question> {
    
    private final DbUtilInterface dbUtil = new DbUtil();

    @Override
    public void add(Question question) {
        dbUtil.dbExecuteUpdate(new NewQuestionSpecification(question).toSqlQuery());
    }

    @Override
    public void remove(Question question) {
        dbUtil.dbExecuteUpdate(new RemoveQuestionSpecification(question).toSqlQuery());
    }

    @Override
    public void update(Question question) {
        dbUtil.dbExecuteUpdate(new QuestionUpdateSpecification(question).toSqlQuery());
    }

    @Override
    public List<Question> query(ISqlSpecification spec) {
        ResultSet rs = dbUtil.dbExecuteQuery(spec.toSqlQuery());
        List<Question> questions = new ArrayList<>();
        try {
            while (rs.next()) {
                Question question = new Question();
                question.setId(rs.getInt(DatabaseHelperUtil.COLUMN_QUESTIONS_ID));
                question.setContent(rs.getString(DatabaseHelperUtil.COLUMN_QUESTION_CONTENT));
                question.setCompetitonId(rs.getInt(DatabaseHelperUtil.COLUMN_QUESTIONS_COMPETITION_ID));
                questions.add(question);
            }
        } catch (Exception e) {}
        return questions;
    }
    
}
