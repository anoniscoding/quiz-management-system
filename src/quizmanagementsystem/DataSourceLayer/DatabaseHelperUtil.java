
package quizmanagementsystem.DataSourceLayer;

import quizmanagementsystem.interfaces.DatabaseHelperInterface;
import quizmanagementsystem.interfaces.DbUtilInterface;



/**
 * Perform necessary operations to setup the database
 * @author anonCoding
 */
public class DatabaseHelperUtil implements DatabaseHelperInterface {
    
    private final DbUtilInterface DB_UTILITY;
    
    /**
     * DATABASE VALUES
     */
    public static final String DATABASE_DRIVER = "org.sqlite.JDBC";
    public static final String DATABASE_URI = "jdbc:sqlite:qms.db";
    
    /**
     * Initialize the class instance variables
     * @param dbUtility the database object
     */
    public DatabaseHelperUtil(final DbUtilInterface dbUtility)
    {
        this.DB_UTILITY = dbUtility;
    }
    
    /**
     * Create a new Table
     * @param sql the query to be executed
     * @param log the details of query executed
     */
    private void executeSql(String sql, String log)
    { 
        DB_UTILITY.dbExecuteUpdate(sql);
    }
    
    /**
     * Create tables
     *      This method should only be called if the the database does not 
     * already exist.
     */
    @Override
    public void onCreate()
    {
        executeSql(ENABLE_FOREIGN_KEY_CONSTRAINT,"enabling FOREIGN_KEY_CONSTRAINT");
        executeSql(TABLE_COMPETITIONS,"creating COMPETITIONS table");
        executeSql(TABLE_QUESTIONS,"creating QUESTIONS table");
        executeSql(TABLE_TEAMS,"creating TEAMS table");
        executeSql(TABLE_OPTIONS, "creating options table");
    }
    
    
    
    /**
     * COMPETITIONS_TABLE
     */
    public static final String COMPETITIONS_TABLE_NAME = "COMPETITIONS";
    public static final String COLUMN_COMPETITIONS_ID = "id";
    public static final String COLUMN_COMPETITIONS_NAME = "name";
    public static final String COLUMN_COMPETITIONS_NO_OF_ROUNDS = "noOfRounds";
    public static final String COLUMN_COMPETITIONS_TIME_PER_QUESTION = "timePerQuestion";
    public static final String COLUMN_COMPETITIONS_NO_OF_TEAMS = "noOfTeams";
    
    /**
     * QUESTIONS_TABLE
     */
    public static final String QUESTIONS_TABLE_NAME = "QUESTIONS";
    public static final String COLUMN_QUESTIONS_ID = "id";
    public static final String COLUMN_QUESTION_CONTENT = "content";
    public static final String COLUMN_QUESTIONS_COMPETITION_ID = "competitionId";
    
    /**
     * OPTIONS TABLE
     */
    public static final String OPTIONS_TABLE_NAME = "OPTIONS";
    public static final String COLUMN_OPTIONS_ID = "id";
    public static final String COLUMN_OPTION_IS_CORRECT = "isCorrect";
    public static final String COLUMN_OPTIONS_CONTENT = "content";
    public static final String COLUMN_OPTION_QUESTION_ID = "questionId";
    
    
    /**
     * TEAMS_TABLE
     */
    public static final String TEAMS_TABLE_NAME = "TEAMS";
    public static final String COLUMN_TEAMS_ID = "id";
    public static final String COLUMN_TEAMS_NAME = "name";
    public static final String COLUMN_TEAMS_COMPETITION_ID = "competitionId";
    
    
    
    private static final String TABLE_COMPETITIONS = "CREATE TABLE IF NOT EXISTS "
                +COMPETITIONS_TABLE_NAME+"(\n"
                +COLUMN_COMPETITIONS_ID+" integer PRIMARY KEY NOT NULL,\n"
                +COLUMN_COMPETITIONS_NAME+" text NOT NULL,\n"
                +COLUMN_COMPETITIONS_NO_OF_ROUNDS+" text NOT NULL,\n"
                +COLUMN_COMPETITIONS_NO_OF_TEAMS+" text NOT NULL,\n"
                +COLUMN_COMPETITIONS_TIME_PER_QUESTION+" text NOT NULL\n"
                +");";
    
    private static final String TABLE_QUESTIONS = "CREATE TABLE IF NOT EXISTS "
                +QUESTIONS_TABLE_NAME+"(\n"
                +COLUMN_QUESTIONS_ID+" integer PRIMARY KEY NOT NULL,\n"
                +COLUMN_QUESTION_CONTENT+" text NOT NULL,\n"
                +COLUMN_QUESTIONS_COMPETITION_ID+" integer NOT NULL,\n"
                +"CONSTRAINT fk_competitions FOREIGN KEY("+COLUMN_QUESTIONS_COMPETITION_ID+") REFERENCES COMPETITIONS("
                +COLUMN_COMPETITIONS_ID+")"
                +" ON DELETE CASCADE"     
                +");";
      
    private static final String TABLE_TEAMS = "CREATE TABLE IF NOT EXISTS "
                +TEAMS_TABLE_NAME+"(\n"
                +COLUMN_TEAMS_ID+" integer PRIMARY KEY NOT NULL,\n"
                +COLUMN_TEAMS_NAME+" text NOT NULL,\n"
                +COLUMN_TEAMS_COMPETITION_ID+" integer NOT NULL,\n"
                +"CONSTRAINT fk_competitions FOREIGN KEY("+COLUMN_TEAMS_COMPETITION_ID+") REFERENCES COMPETITIONS("
                +COLUMN_COMPETITIONS_ID+")"
                +" ON DELETE CASCADE"
                +");";
    
    private static final String TABLE_OPTIONS = "CREATE TABLE IF NOT EXISTS "
                +OPTIONS_TABLE_NAME+"(\n"
                +COLUMN_OPTIONS_ID+" integer PRIMARY KEY NOT NULL,\n"
                +COLUMN_OPTIONS_CONTENT+" text NOT NULL,\n"
                +COLUMN_OPTION_IS_CORRECT+" text NOT NULL,\n"
                +COLUMN_OPTION_QUESTION_ID+" integer NOT NULL,\n"
                +"CONSTRAINT fk_questions FOREIGN KEY("+COLUMN_OPTION_QUESTION_ID+") REFERENCES QUESTIONS("
                +COLUMN_QUESTIONS_ID+")"
                +" ON DELETE CASCADE"
                +");";
        
    private static final String ENABLE_FOREIGN_KEY_CONSTRAINT = 
            "PRAGMA foreign_keys =ON;";
    
    //End of DatabaseHelperUtil class
}