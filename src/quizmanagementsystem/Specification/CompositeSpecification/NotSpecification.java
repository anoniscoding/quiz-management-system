package quizmanagementsystem.Specification.CompositeSpecification;

/**
 *
 * @author anonCoding
 * @param <T>
 */
public class NotSpecification<T> implements CompositeSpecification<T> {
    
    private final CompositeSpecification spec;

    public NotSpecification(CompositeSpecification spec) {
        this.spec = spec;
    }
    

    @Override
    public boolean isSatisfiedBy(T item) {
        return !this.spec.isSatisfiedBy(item);
    }
    
}
