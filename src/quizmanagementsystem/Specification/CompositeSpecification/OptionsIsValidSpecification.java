package quizmanagementsystem.Specification.CompositeSpecification;

import java.util.List;

/**
 *
 * @author anonCoding
 */
public class OptionsIsValidSpecification implements CompositeSpecification<List<String>> {
    
    private final int EXPECTED_NO_OF_CORRECT_OPTIONS = 1;

    @Override
    public boolean isSatisfiedBy(List<String> options) {
        int correctOptionCount = 0;
        
        for (String option: options) {  
            if (option.startsWith("*")) {
                correctOptionCount += 1;
                if(!optionMatchesRegex(option.substring(1)))
                    return false;
            } else {
                if (!optionMatchesRegex(option))
                    return false;
            }
        }
        
        return correctOptionCount == EXPECTED_NO_OF_CORRECT_OPTIONS && options.size() > 1;
    }

    private boolean optionMatchesRegex(String option) {
        String optionBeginsWithCharPeriodSpaceRegex = "^[a-zA-Z]\\)\\s.*";
        return option.matches(optionBeginsWithCharPeriodSpaceRegex);     
    }
    
}
