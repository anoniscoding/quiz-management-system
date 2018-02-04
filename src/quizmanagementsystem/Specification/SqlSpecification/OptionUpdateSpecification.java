package quizmanagementsystem.Specification.SqlSpecification;

import quizmanagementsystem.DataSourceLayer.DatabaseHelperUtil;
import quizmanagementsystem.Model.Option;

/**
 *
 * @author anonCoding
 */
public class OptionUpdateSpecification implements ISqlSpecification {
    
    private final Option _option;

    public OptionUpdateSpecification(Option option) {
        this._option = option;
    }

    @Override
    public String toSqlQuery() {
        return String.format("UPDATE %s SET %s = '%s', %s = '%s' WHERE %s = %d ",
                    DatabaseHelperUtil.OPTIONS_TABLE_NAME,
                    DatabaseHelperUtil.COLUMN_OPTIONS_CONTENT,
                    _option.getContent(),
                    DatabaseHelperUtil.COLUMN_OPTION_IS_CORRECT,
                    String.valueOf(this._option.isCorrect()),
                    DatabaseHelperUtil.COLUMN_OPTIONS_ID,
                    _option.getId()
               );
    }
    
}
