package repository;

import domain.entities.Author;
import domain.entities.Book;
import domain.enums.BookStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    private final Connection connection;

    public BookDAO(Connection connection) {
        this.connection = connection;
    }

    public Book getBookByISBN(String ISBN) throws SQLException {
        String query = "SELECT * FROM books WHERE isbn=?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, ISBN);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            String isbn = resultSet.getString(1);
            String title = resultSet.getString(2);
            String statusStr = resultSet.getString(3);
            BookStatus status = BookStatus.fromString(statusStr);
            int quantity = resultSet.getInt(4);
            int quantityLost = resultSet.getInt(5);

            // TODO : when complete author dao, I need to get object of author for that id
            int author_id = resultSet.getInt(6);
            Author author = new Author();

            return new Book(isbn, title, status, quantity, quantityLost, author);
        }

        return null;
    }

    public List<Book> getAllBooks() throws SQLException {
        List<Book> books = new ArrayList<>();

        String query = "SELECT * FROM books";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            String isbn = resultSet.getString(1);
            String title = resultSet.getString(2);
            String statusStr = resultSet.getString(3);
            BookStatus status = BookStatus.fromString(statusStr);
            int quantity = resultSet.getInt(4);
            int quantityLost = resultSet.getInt(5);

            // TODO : when complete author dao, I need to get object of author for that id
            int author_id = resultSet.getInt(6);
            Author author = new Author();

            Book book = new Book(isbn, title, status, quantity, quantityLost, author);

            books.add(book);
        }

        return books;
    }

    public Book insert(Book book) throws SQLException {
        String query = "INSERT INTO books (isbn, title, status, quantity, quantity_lost, author_id) VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, book.getIsbn());
        preparedStatement.setString(2, book.getTitle());
        preparedStatement.setString(3, book.getStatus().toString());
        preparedStatement.setInt(4, book.getQuantity());
        preparedStatement.setInt(5, book.getQuantityLost());
        preparedStatement.setInt(6, book.getAuthor().getId());

        int rowsAffected = preparedStatement.executeUpdate();

        if (rowsAffected == 1) {
            return book;
        }

        return null;
    }

    public Book update(Book book) throws SQLException {
        String query = "UPDATE books SET title=?, status=?, quantity=?, quantity_lost=?, author_id=? WHERE isbn=?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, book.getTitle());
        preparedStatement.setString(2, book.getStatus().toString());
        preparedStatement.setInt(3, book.getQuantity());
        preparedStatement.setInt(4, book.getQuantityLost());
        preparedStatement.setInt(5, book.getAuthor().getId());
        preparedStatement.setString(6, book.getIsbn());

        int rowsAffected = preparedStatement.executeUpdate();

        if (rowsAffected == 1) {
            return book;
        }

        return null;
    }

    public boolean delete(String isbn) throws SQLException {
        String query = "DELETE FROM books WHERE isbn=?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, isbn);

        int rowsAffected = preparedStatement.executeUpdate();

        return rowsAffected == 1;
    }

}
