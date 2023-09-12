package repository;

import domain.entities.Author;
import domain.entities.Book;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorDAO implements BaseDAO<Author> {
    private final Connection connection = DatabaseConnection.getConnection();

    public AuthorDAO() {
    }

    @Override
    public Author insert(Author author) throws SQLException {
        String query = "INSERT INTO authors (`name`, `surname`) VALUES (?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, author.getName());
        preparedStatement.setString(2, author.getSurname());

        int rowsAffected = preparedStatement.executeUpdate();

        if (rowsAffected == 1) {
            return author;
        }

        return null;
    }

    @Override
    public Author update(Author author) throws SQLException {
        String query = "UPDATE authors SET name=?, surname=? WHERE id=?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, author.getName());
        preparedStatement.setString(2, author.getSurname());
        preparedStatement.setInt(3, author.getId());

        int rowsAffected = preparedStatement.executeUpdate();

        if (rowsAffected == 1) {
            return author;
        }

        return null;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String query = "DELETE FROM authors WHERE id=?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);

        int rowsAffected = preparedStatement.executeUpdate();

        return rowsAffected == 1;
    }

    @Override
    public List<Author> getAll() throws SQLException {
        List<Author> authors = new ArrayList<>();

        String query = "SELECT * FROM authors LIMIT 10";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            String surname = resultSet.getString(3);

            Author author = new Author(id, name, surname);

            authors.add(author);
        }

        return authors;
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

    public Author isAuthorExist(String name, String surname) throws SQLException {
        String query = "SELECT * FROM authors WHERE name=? AND surname=?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, surname);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            int id = resultSet.getInt(1);

            return new Author(id, name, surname);
        }

        return null;
    }
}
