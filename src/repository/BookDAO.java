package repository;

import domain.entities.Book;

import java.sql.SQLException;
import java.util.List;

public class BookDAO implements BaseDAO<Book> {
    @Override
    public Book insert(Book entity) throws SQLException {
        return null;
    }

    @Override
    public Book update(Book entity) throws SQLException {
        return null;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return false;
    }

    @Override
    public List<Book> getAll() throws SQLException {
        return null;
    }
}
