package quizmanagementsystem.Specification.CompositeSpecification;

/**
 *
 * @author anonCoding
 * @param <T>
 */
public class AndSpecification<T> implements CompositeSpecification<T> {
    
    private final CompositeSpecification _firstSpec;
    private final CompositeSpecification _secondSpec;

    public AndSpecification(CompositeSpecification firstSpec, CompositeSpecification secondSpec) {
        this._firstSpec = firstSpec;
        this._secondSpec = secondSpec;
    }

    @Override
    public boolean isSatisfiedBy(T item) {
        return this._firstSpec.isSatisfiedBy(item) && this._secondSpec.isSatisfiedBy(item);
    }
}
