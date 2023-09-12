import service.*;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        BookService bookService = new BookService();
        AuthorService authorService = new AuthorService();
        BorrowingTransactionService borrowingTransactionService = new BorrowingTransactionService();
        ClientService clientService = new ClientService();
        ReportService reportService = new ReportService();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n======================================");
            System.out.println("    Library Management System");
            System.out.println("======================================");
            System.out.println("1. Book Menu");
            System.out.println("2. Author Menu");
            System.out.println("3. Borrowing Menu");
            System.out.println("4. Client Menu");
            System.out.println("5. Report Menu");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    bookService.BookMenu();
                    break;
                case 2:
                    authorService.AuthorMenu();
                    break;
                case 3:
                    borrowingTransactionService.BorrowMenu();
                    break;
                case 4:
                    clientService.ClientMenu();
                    break;
                case 5:
                    reportService.Menu();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}