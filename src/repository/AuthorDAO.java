package repository;

import domain.entities.Author;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AuthorDAO implements BaseDAO<Author> {
    private final Connection connection;

    public AuthorDAO(Connection connection) {
        this.connection = connection;
    }
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

    @Override
    public Author getById(int id) throws SQLException {
        String query = "SELECT * FROM authors WHERE id=?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            String name = resultSet.getString(2);
            String surname = resultSet.getString(3);

            return new Author(id, name, surname);
        }

        return null;
    }
}
