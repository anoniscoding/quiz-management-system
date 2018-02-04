
package quizmanagementsystem.DataSourceLayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import quizmanagementsystem.interfaces.DbUtilInterface;

/**
 * Handle Basic Database utility operations
 * @author anonCoding
 */
public class DbUtil implements DbUtilInterface{
    
    private Connection conn = null;
    private Statement stmt = null;
    
    public DbUtil()
    {
        dbConnect();
    }
    
    /**
     * Connect to the database
     */
    private void dbConnect()
    {
        try
        {
            Class.forName(DatabaseHelperUtil.DATABASE_DRIVER);
            conn = DriverManager.getConnection
                    (DatabaseHelperUtil.DATABASE_URI);
        }
        catch(SQLException | ClassNotFoundException e)
        {
            System.out.println("An error occured in dbutil");
        }
        
    }
    
    /**
     * Disconnect the database
     */
    private void dbDisconnect()
    {
        try
        {
            if(conn != null && !conn.isClosed())
            {
                conn.close();
            }
        }
        catch(SQLException e)
        {
            System.out.println("An error occured in dbutil disconnect");
        }
    }
    
    /**
     * Perform db query execution
     * @param queryStmt the statement to executed
     * @return ResultSet
     */
    @Override
    public ResultSet dbExecuteQuery(String queryStmt)
    {
        ResultSet resultSet = null;
        try
        {
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(queryStmt);
        }
        catch(SQLException e)
        {
            System.out.println("An error occured in dbutil exec query"+e.getMessage());
        }
        
        return resultSet;
    }
    
    /**
     * Perform update query operations
     * @param queryStmt the query to be executed
     * @return boolean true/false
     */
    @Override
    public boolean dbExecuteUpdate(String queryStmt)
    {
        try
        {
            stmt = conn.createStatement();
            stmt.executeUpdate(queryStmt);
        }
        catch(SQLException e)
        {
            System.out.println("An error occured in dbutil exec update 1"+e.getMessage());
            return false;
        }
        
        return true;
    }
    
    //End of DbUtil Class
}