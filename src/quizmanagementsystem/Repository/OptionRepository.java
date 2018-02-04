package quizmanagementsystem.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import quizmanagementsystem.DataSourceLayer.DatabaseHelperUtil;
import quizmanagementsystem.DataSourceLayer.DbUtil;
import quizmanagementsystem.Model.Option;
import quizmanagementsystem.Specification.SqlSpecification.ISqlSpecification;
import quizmanagementsystem.Specification.SqlSpecification.NewOptionSpecification;
import quizmanagementsystem.Specification.SqlSpecification.OptionUpdateSpecification;
import quizmanagementsystem.Specification.SqlSpecification.RemoveOptionSpecification;
import quizmanagementsystem.interfaces.DbUtilInterface;

/**
 *
 * @author anonCoding
 */
public class OptionRepository implements IRepository<Option> {
    
    private final DbUtilInterface dbUtil = new DbUtil();

    @Override
    public void add(Option option) {
        dbUtil.dbExecuteUpdate(new NewOptionSpecification(option).toSqlQuery());
    }

    @Override
    public void remove(Option option) {
        dbUtil.dbExecuteUpdate(new RemoveOptionSpecification(option).toSqlQuery());
    }

    @Override
    public void update(Option option) {
        dbUtil.dbExecuteUpdate(new OptionUpdateSpecification(option).toSqlQuery());
    }

    @Override
    public List<Option> query(ISqlSpecification spec) {
        ResultSet rs = dbUtil.dbExecuteQuery(spec.toSqlQuery());
        List<Option> options = new ArrayList<>();
        try {
            while (rs.next()) {
                Option option = new Option();
                option.setId(rs.getInt(DatabaseHelperUtil.COLUMN_OPTIONS_ID));
                option.setContent(rs.getString(DatabaseHelperUtil.COLUMN_OPTIONS_CONTENT));
                option.setQuestionId(rs.getInt(DatabaseHelperUtil.COLUMN_OPTION_QUESTION_ID));
                option.setIsCorrect( Boolean.valueOf(rs.getString(DatabaseHelperUtil.COLUMN_OPTION_IS_CORRECT)) );
                options.add(option); 
            }
        } catch (Exception e) {}
        return options;
    }
    
}
