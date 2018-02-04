package quizmanagementsystem.Specification.CompositeSpecification;

/**
 *
 * @author anonCoding
 */
public class TextIsNotEmptySpecification implements CompositeSpecification<String> {

    @Override
    public boolean isSatisfiedBy(String text) {
        return !text.equals("") && !text.equals("-1");
    }
    
}
