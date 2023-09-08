import service.BookService;
import service.BorrowedBookService;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {

        Connection connection = DatabaseConnection.getConnection();

//        BookService bookService = new BookService(connection);
//        bookService.BookMenu();

        BorrowedBookService borrowedBookService = new BorrowedBookService(connection);
        borrowedBookService.BorrowMenu();
    }
}