package quizmanagementsystem.Specification.SqlSpecification;

import quizmanagementsystem.DataSourceLayer.DatabaseHelperUtil;
import quizmanagementsystem.Model.Option;

/**
 *
 * @author anonCoding
 */
public class RemoveOptionSpecification implements ISqlSpecification {
    
    private final Option _option;

    public RemoveOptionSpecification(Option option) {
        this._option = option;
    }

    @Override
    public String toSqlQuery() {
        return String.format("DELETE FROM %s WHERE %s = %d",
                    DatabaseHelperUtil.OPTIONS_TABLE_NAME,
                    DatabaseHelperUtil.COLUMN_OPTIONS_ID,
                    _option.getId()
                );
    }
    
}
