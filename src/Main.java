import service.ReportService;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {

        Connection connection = DatabaseConnection.getConnection();

        ReportService reportService = new ReportService(connection);
        reportService.Menu();

//        BookService bookService = new BookService(connection);
//        bookService.BookMenu();
    }
}