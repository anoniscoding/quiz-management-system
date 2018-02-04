
package quizmanagementsystem;

import java.io.File;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import quizmanagementsystem.DataSourceLayer.DatabaseHelperUtil;
import quizmanagementsystem.DataSourceLayer.DbUtil;
import quizmanagementsystem.interfaces.DatabaseHelperInterface;

/**
 *
 * @author anonCoding
 */
public class QuizManagementSystem extends Application {
    
    private static Stage appStage;
    
    @Override
    public void start(Stage stage) throws Exception {
        appStage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("UserInterfaceLayer/PresentationView/HomeFxml.fxml"));      
        Scene scene = new Scene(root);
        setUp();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.sizeToScene();
        stage.show();
    }
    
    public void setUp() throws IOException {
        File dbFile = new File("qms.db");
        dbFile.createNewFile();
        DatabaseHelperInterface databaseHelper = new DatabaseHelperUtil(new DbUtil());
        databaseHelper.onCreate();
    }
    
    public static Stage getAppStage(){
        return appStage; 
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
