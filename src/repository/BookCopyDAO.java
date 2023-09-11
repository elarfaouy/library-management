package repository;

import domain.entities.BookCopy;

import java.sql.SQLException;
import java.util.List;

public class BookCopyDAO implements BaseDAO<BookCopy> {
    @Override
    public BookCopy insert(BookCopy entity) throws SQLException {
        return null;
    }

    @Override
    public BookCopy update(BookCopy entity) throws SQLException {
        return null;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return false;
    }

    @Override
    public List<BookCopy> getAll() throws SQLException {
        return null;
    }

    @Override
    public BookCopy getById(int id) throws SQLException {
        return null;
    }
}
