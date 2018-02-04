package quizmanagementsystem.Specification.SqlSpecification;

import quizmanagementsystem.DataSourceLayer.DatabaseHelperUtil;
import quizmanagementsystem.Model.Option;

/**
 *
 * @author anonCoding
 */
public class NewOptionSpecification implements ISqlSpecification {
    
    private Option _option;

    public NewOptionSpecification(Option option) {
        this._option = option;
    }

    @Override
    public String toSqlQuery() {
        return String.format("INSERT INTO %s(%s, %s, %s) VALUES('%s', '%s', %d)",
                    DatabaseHelperUtil.OPTIONS_TABLE_NAME,
                    DatabaseHelperUtil.COLUMN_OPTIONS_CONTENT,
                    DatabaseHelperUtil.COLUMN_OPTION_IS_CORRECT,
                    DatabaseHelperUtil.COLUMN_OPTION_QUESTION_ID,
                    _option.getContent(),
                    String.valueOf(_option.isCorrect()),
                    _option.getQuestionId()
                );
    }
    
}
