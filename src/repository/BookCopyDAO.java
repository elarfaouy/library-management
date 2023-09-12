package repository;

import domain.entities.Book;
import domain.entities.BookCopy;
import domain.enums.BookStatus;
import util.DatabaseConnection;

import java.sql.*;
import java.util.List;

public class BookCopyDAO implements BaseDAO<BookCopy> {
    private final Connection connection = DatabaseConnection.getConnection();

    public BookCopyDAO() {
    }

    @Override
    public BookCopy insert(BookCopy entity) throws SQLException {
        String query = "INSERT INTO book_copies (`serial`, `book_id`) VALUES (?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, entity.getSerial());
        preparedStatement.setInt(2, entity.getBook().getId());

        int rowsAffected = preparedStatement.executeUpdate();

        if (rowsAffected == 1) {
            return entity;
        }

        return null;
    }

    @Override
    public BookCopy update(BookCopy entity) throws SQLException {
        String query = "UPDATE book_copies SET serial=?, status=?, book_id=? WHERE id=?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, entity.getSerial());
        preparedStatement.setString(2, entity.getStatus().toString());
        preparedStatement.setInt(3, entity.getBook().getId());
        preparedStatement.setInt(4, entity.getId());

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
    public List<BookCopy> getAll() throws SQLException {
        return null;
    }

    @Override
    public BookCopy getById(int id) throws SQLException {
        String query = "SELECT * FROM book_copies WHERE id=?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            int serial = resultSet.getInt(2);
            String statusStr = resultSet.getNString(3);
            BookStatus status = BookStatus.fromString(statusStr);
            int book_id = resultSet.getInt(4);

            Book book = new BookDAO().getById(book_id);

            return new BookCopy(id, serial, status, book);
        }

        return null;
    }

    public int getCopyAvailable(String isbn) throws SQLException {
        CallableStatement cs = connection.prepareCall("{call isAnyCopyExist(?,?)}");

        cs.setString(1, isbn);
        cs.registerOutParameter(2, Types.INTEGER);
        cs.execute();

        return cs.getInt(2);
    }
}
