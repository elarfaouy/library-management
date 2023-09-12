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

public class BookDAO implements BaseDAO<Book> {
    private final Connection connection = DatabaseConnection.getConnection();

    public BookDAO() {
    }

    @Override
    public Book insert(Book entity) throws SQLException {
        String query = "INSERT INTO books (`isbn`, `title`, `quantity`, `author_id`) VALUES (?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, entity.getIsbn());
        preparedStatement.setString(2, entity.getTitle());
        preparedStatement.setInt(3, entity.getQuantity());
        preparedStatement.setInt(4, entity.getAuthor().getId());

        int rowsAffected = preparedStatement.executeUpdate();

        if (rowsAffected == 1) {
            return entity;
        }

        return null;
    }

    @Override
    public Book update(Book entity) throws SQLException {
        String query = "UPDATE books SET isbn=?, title=?, quantity=?, author_id=? WHERE id=?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, entity.getIsbn());
        preparedStatement.setString(2, entity.getTitle());
        preparedStatement.setInt(3, entity.getQuantity());
        preparedStatement.setInt(4, entity.getAuthor().getId());
        preparedStatement.setInt(5, entity.getId());

        int rowsAffected = preparedStatement.executeUpdate();

        if (rowsAffected == 1) {
            return entity;
        }

        return null;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String query = "DELETE FROM books WHERE id=?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);

        int rowsAffected = preparedStatement.executeUpdate();

        return rowsAffected == 1;
    }

    @Override
    public List<Book> getAll() throws SQLException {
        List<Book> books = new ArrayList<>();

        String query = "SELECT * FROM books LIMIT 10";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String isbn = resultSet.getString(2);
            String title = resultSet.getString(3);
            int quantity = resultSet.getInt(4);
            int author_id = resultSet.getInt(5);

            Author author = new AuthorDAO().getById(author_id);

            Book book = new Book(id, isbn, title, quantity, author);

            books.add(book);
        }

        return books;
    }

    @Override
    public Book getById(int id) throws SQLException {
        String query = "SELECT * FROM books WHERE id=?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            String isbn = resultSet.getString(2);
            String title = resultSet.getString(3);
            int quantity = resultSet.getInt(4);
            int author_id = resultSet.getInt(5);

            Author author = new AuthorDAO().getById(author_id);

            return new Book(id, isbn, title, quantity, author);
        }

        return null;
    }

    public List<Book> searchByBookOrAuthor(String string) throws SQLException {
        List<Book> books = new ArrayList<>();

        String query = "SELECT b.* " +
                "FROM books AS b " +
                "INNER JOIN authors AS a " +
                "ON b.author_id = a.id " +
                "WHERE b.title LIKE ?" +
                "OR CONCAT(a.name, a.surname) LIKE ?" +
                "LIMIT 10";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, "%" + string + "%");
        preparedStatement.setString(2, "%" + string + "%");
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String isbn = resultSet.getString(2);
            String title = resultSet.getString(3);
            int quantity = resultSet.getInt(4);
            int author_id = resultSet.getInt(5);

            Author author = new AuthorDAO().getById(author_id);

            Book book = new Book(id, isbn, title, quantity, author);

            books.add(book);
        }

        return books;
    }

    public Book getByIsbn(String isbn) throws SQLException {
        String query = "SELECT * FROM books WHERE isbn=?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, isbn);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            int id = resultSet.getInt(1);
            String title = resultSet.getString(3);
            int quantity = resultSet.getInt(4);
            int author_id = resultSet.getInt(5);

            Author author = new AuthorDAO().getById(author_id);

            return new Book(id, isbn, title, quantity, author);
        }

        return null;
    }
}
