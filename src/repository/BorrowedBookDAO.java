package repository;

import domain.entities.Book;
import domain.entities.BorrowedBook;
import domain.entities.Client;

import java.sql.*;

public class BorrowedBookDAO {
    private final Connection connection;

    public BorrowedBookDAO(Connection connection) {
        this.connection = connection;
    }

    public BorrowedBook borrowBook(BorrowedBook borrowedBook) throws SQLException {
        String query = "INSERT INTO borrowed_books (borrow_date, due_date, book_isbn, client_id) VALUES (?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setDate(1, borrowedBook.getBorrowDate());
        preparedStatement.setDate(2, borrowedBook.getDueDate());
        preparedStatement.setString(3, borrowedBook.getBook().getIsbn());
        preparedStatement.setInt(4, borrowedBook.getClient().getId());

        int rowsAffected = preparedStatement.executeUpdate();

        if (rowsAffected == 1) {
            return borrowedBook;
        }

        return null;
    }

    public Boolean returnBook(BorrowedBook borrowedBook) throws SQLException {
        String query = "UPDATE borrowed_books SET return_date=? WHERE id=?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setDate(1, borrowedBook.getReturnDate());
        preparedStatement.setInt(2, borrowedBook.getId());

        int rowsAffected = preparedStatement.executeUpdate();

        return rowsAffected == 1;
    }

    public BorrowedBook getBorrowedBookById(int id) throws SQLException {
        String query = "SELECT * FROM borrowed_books WHERE id=?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            Date borrowDate = resultSet.getDate(2);
            Date returnDate = resultSet.getDate(3);
            Date dueDate = resultSet.getDate(4);
            String isbn = resultSet.getString(5);
            int client_id = resultSet.getInt(6);

            Book book = new BookDAO(connection).getBookByISBN(isbn);

            Client client = new Client();
            client.setId(client_id);

            return new BorrowedBook(id, borrowDate, returnDate, dueDate, book, client);
        }

        return null;
    }
}
