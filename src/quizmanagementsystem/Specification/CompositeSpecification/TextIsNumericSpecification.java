package quizmanagementsystem.Specification.CompositeSpecification;

/**
 *
 * @author anonCoding
 */
public class TextIsNumericSpecification implements CompositeSpecification<String> {

    @Override
    public boolean isSatisfiedBy(String text) {
       return text.matches("[0-9]+");
    }
    
}
