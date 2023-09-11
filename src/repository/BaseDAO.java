package repository;

import java.sql.SQLException;
import java.util.List;

public interface BaseDAO<T> {
    T insert(T entity) throws SQLException;

    T update(T entity) throws SQLException;

    boolean delete(int id) throws SQLException;

    List<T> getAll() throws SQLException;

    T getById(int id) throws SQLException;
}
