package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReportDAO {
    private final Connection connection;

    public ReportDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Integer> generateReport() throws SQLException {
        List<Integer> list = new ArrayList<Integer>();

        String query = "SELECT\n" +
                "COUNT(*),\n" +
                "SUM(status = 'available'),\n" +
                "SUM(status = 'on loan'),\n" +
                "SUM(status = 'lost')\n" +
                "FROM book_copies;";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            int booksAll = resultSet.getInt(1);
            int booksAvailable = resultSet.getInt(2);
            int booksOnLoan = resultSet.getInt(3);
            int booksLost = resultSet.getInt(4);

            return List.of(booksAll, booksAvailable, booksOnLoan, booksLost);
        }

        return null;
    }
}
