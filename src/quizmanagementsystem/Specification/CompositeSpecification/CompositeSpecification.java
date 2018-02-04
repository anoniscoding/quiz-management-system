package quizmanagementsystem.Specification.CompositeSpecification;

/**
 *
 * @author anonCoding
 * @param <T>
 */
public interface CompositeSpecification<T> {
    boolean isSatisfiedBy(T item);
    default AndSpecification<T> And(CompositeSpecification spec) {
        return new AndSpecification<>(this,spec);
    }
    
    default NotSpecification<T> Not() {
        return new NotSpecification<>(this);
    }
}
