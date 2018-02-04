package quizmanagementsystem.Repository;

import java.util.List;
import quizmanagementsystem.Specification.SqlSpecification.ISqlSpecification;

/**
 *
 * @author anonCoding
 * @param <T>
 */
public interface IRepository<T> {
    void add(T item);
    void remove(T item);
    void update(T item);
    List<T> query(ISqlSpecification spec);
}
