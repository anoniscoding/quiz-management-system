
package quizmanagementsystem.interfaces;

import java.sql.ResultSet;

/**
 * An interface for directly interacting with the db
 * @author anonCoding
 */
public interface DbUtilInterface {
    public boolean dbExecuteUpdate(String queryStmt);
    public ResultSet dbExecuteQuery(String queryStmt);
}
