package az.edu.turing.booking_management.dao;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface DAO<T> {
    boolean save(List<T> list);

    void update(long id, int amount);

    void delete(long id);

    List<T> getAll();

    Optional<T> getOneBy(Predicate<T> predicate);

    List<T> getAllBy(Predicate<T> predicate);

}