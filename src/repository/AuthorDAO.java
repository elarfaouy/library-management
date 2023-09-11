package repository;

import domain.entities.Author;

import java.sql.SQLException;
import java.util.List;

public class AuthorDAO implements BaseDAO<Author> {
    @Override
    public Author insert(Author author) throws SQLException {
        return null;
    }

    @Override
    public Author update(Author author) throws SQLException {
        return null;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return false;
    }

    @Override
    public List<Author> getAll() throws SQLException {
        return null;
    }
}
