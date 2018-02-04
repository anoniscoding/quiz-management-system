package quizmanagementsystem.InfrastructuralLayer;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;

/**
 *
 * @author anonCoding
 */
public class Notifier {
    
    public static void error(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(msg);
        alert.showAndWait();
    }
    
    public static void alert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(msg);
        alert.showAndWait();
    }
    
    public static boolean confirm(String msg) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(msg);
        Optional<ButtonType> response = alert.showAndWait();
        return response.isPresent() && (response.get() == ButtonType.OK);
    }
    
    public static String prompt(String headerTxt, String msg) {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setHeaderText(headerTxt);
        dialog.setContentText(msg);
        Optional<String> response = dialog.showAndWait();
        
        if (response.isPresent()) {
            return response.get();
        }
        
        return "";
    }
}
