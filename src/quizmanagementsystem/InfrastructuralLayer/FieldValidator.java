package quizmanagementsystem.InfrastructuralLayer;

import java.util.List;

/**
 *
 * @author anonCoding
 */
public class FieldValidator {
    
    public static boolean isFieldsValid(List<Boolean> fieldValidationResults) {
        for (Boolean isFieldValid: fieldValidationResults) {
            if (!isFieldValid) {
                Notifier.error("Make sure all fields are filled correctly.");
                return false;
            } 
        }
        return true;
    }
}
