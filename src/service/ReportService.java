package service;

import repository.ReportDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class ReportService {
    Scanner scanner = new Scanner(System.in);
    private final ReportDAO reportDAO;

    public ReportService(Connection connection) {
        this.reportDAO = new ReportDAO(connection);
    }

    public void Menu() throws SQLException {

        while (true) {
            System.out.println("\n**************************************");
            System.out.println("1. Show Report");
            System.out.println("2. later");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            int choice = Integer.parseInt(scanner.nextLine());

            System.out.println("\n######################################");

            switch (choice) {
                case 1:
                    showReport();
                    break;
                case 2:
                    break;
                case 0:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void showReport() {
        try {
            List<Integer> reportData = reportDAO.generateReport();

            if (reportData != null) {
                int booksAll = reportData.get(0);
                int booksAvailable = reportData.get(1);
                int booksOnLoan = reportData.get(2);
                int booksLost = reportData.get(3);

                System.out.println("Library Report:");
                System.out.println("Total number of books in the library: " + booksAll);
                System.out.println("Number of available books: " + booksAvailable);
                System.out.println("Number of books on loan: " + booksOnLoan);
                System.out.println("Number of lost books: " + booksLost);
            } else {
                System.out.println("Failed to generate the report.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error generating the report: " + e.getMessage());
        }
    }
}
