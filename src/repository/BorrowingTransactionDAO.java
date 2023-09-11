package repository;

import domain.entities.Book;
import domain.entities.BookCopy;
import domain.entities.BorrowingTransaction;
import domain.entities.Client;
import domain.enums.BookStatus;

import java.sql.*;
import java.util.List;

public class BorrowingTransactionDAO implements BaseDAO<BorrowingTransaction> {
    private final Connection connection;

    public BorrowingTransactionDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public BorrowingTransaction insert(BorrowingTransaction entity) throws SQLException {
        String query = "INSERT INTO borrowing_transactions (borrow_date, due_date, book_copy_id, client_id) VALUES (?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setDate(1, entity.getBorrowDate());
        preparedStatement.setDate(2, entity.getDueDate());
        preparedStatement.setInt(3, entity.getBookCopy().getId());
        preparedStatement.setInt(4, entity.getClient().getId());

        int rowsAffected = preparedStatement.executeUpdate();

        if (rowsAffected == 1) {
            return entity;
        }

        return null;
    }

    @Override
    public BorrowingTransaction update(BorrowingTransaction entity) throws SQLException {
        String query = "UPDATE borrowing_transactions SET borrow_date=?, return_date=?, due_date=?, book_copy_id=?, client_id=? WHERE id=?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setDate(1, entity.getBorrowDate());
        preparedStatement.setDate(2, entity.getReturnDate());
        preparedStatement.setDate(3, entity.getDueDate());
        preparedStatement.setInt(4, entity.getBookCopy().getId());
        preparedStatement.setInt(5, entity.getClient().getId());
        preparedStatement.setInt(6, entity.getId());

        int rowsAffected = preparedStatement.executeUpdate();

        if (rowsAffected == 1) {
            return entity;
        }

        return null;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return false;
    }

    @Override
    public List<BorrowingTransaction> getAll() throws SQLException {
        return null;
    }

    @Override
    public BorrowingTransaction getById(int id) throws SQLException {
        String query = "SELECT * FROM borrowing_transactions WHERE id=?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            Date borrowDate = resultSet.getDate(2);
            Date returnDate = resultSet.getDate(3);
            Date dueDate = resultSet.getDate(4);
            int bookCopyId = resultSet.getInt(5);
            int clientId = resultSet.getInt(6);

            BookCopy bookCopy = new BookCopyDAO(connection).getById(bookCopyId);
            Client client = new ClientDAO(connection).getById(clientId);

            return new BorrowingTransaction(id, borrowDate, returnDate, dueDate, bookCopy, client);
        }

        return null;
    }
}
