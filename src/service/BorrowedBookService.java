package service;

import domain.entities.Book;
import domain.entities.BorrowingTransaction;
import domain.entities.Client;
import domain.enums.BookStatus;
import repository.BookDAO;
import repository.BorrowingTransactionDAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Scanner;

public class BorrowedBookService {
    Scanner scanner = new Scanner(System.in);
    private final BorrowingTransactionDAO borrowingTransactionDAO;
    private final BookDAO bookDAO;

    public BorrowedBookService(Connection connection) {
        this.borrowingTransactionDAO = new BorrowingTransactionDAO(connection);
        this.bookDAO = new BookDAO(connection);
    }

    public void BorrowMenu() {

        while (true) {
            System.out.println("Library Management System");
            System.out.println("1. Borrow a Book");
            System.out.println("2. Return a Book");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    borrowBook();
                    break;
                case 2:
                    returnBook();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void borrowBook() {
        System.out.print("Enter ISBN of the book to borrow: ");
        String isbn = scanner.nextLine();

        try {
            Book borrowBook = bookDAO.getBookByISBN(isbn);
            if (borrowBook == null) {
                System.out.println("Book with ISBN " + isbn + " not found.");
                return;
            }

            if (borrowBook.getStatus() == BookStatus.AVAILABLE) {
                System.out.print("Enter id of the client to borrow: ");
                int client_id = Integer.parseInt(scanner.nextLine());
                Client client = new Client(client_id, "", "");

                System.out.print("Enter the number of days to borrow: ");
                int numberOfDaysToBorrow = Integer.parseInt(scanner.nextLine());

                Calendar calendar = Calendar.getInstance();
                Date borrowDate = new Date(calendar.getTime().getTime());
                calendar.add(Calendar.DAY_OF_MONTH, numberOfDaysToBorrow);
                Date dueDate = new Date(calendar.getTime().getTime());

                BorrowingTransaction borrowingTransaction = new BorrowingTransaction();
                borrowingTransaction.setBook(borrowBook);
                borrowingTransaction.setClient(client);
                borrowingTransaction.setBorrowDate(borrowDate);
                borrowingTransaction.setDueDate(dueDate);

                BorrowingTransaction borrow = borrowingTransactionDAO.borrowBook(borrowingTransaction);
                borrowBook.setStatus(BookStatus.ON_LOAN);
                Book updateBook = bookDAO.update(borrowBook);

                if (updateBook != null && borrow != null) {
                    System.out.println("Book borrowed successfully: " + borrowingTransaction);
                } else {
                    System.out.println("Failed to borrowed book.");
                }
            } else {
                System.out.println("Book is not available for borrowing.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void returnBook() {
        System.out.print("Enter id of the borrowing: ");
        int id = Integer.parseInt(scanner.nextLine());

        try {
            BorrowingTransaction borrowingTransaction = borrowingTransactionDAO.getBorrowedBookById(id);
            if (borrowingTransaction == null) {
                System.out.println("Borrowing with id " + id + " not found.");
                return;
            }

            Calendar calendar = Calendar.getInstance();
            Date returnDate = new Date(calendar.getTime().getTime());

            borrowingTransaction.setReturnDate(returnDate);
            Boolean borrow = borrowingTransactionDAO.returnBook(borrowingTransaction);

            Book book = borrowingTransaction.getBook();
            book.setStatus(BookStatus.AVAILABLE);
            Book updateBook = bookDAO.update(book);

            if (updateBook != null && borrow) {
                System.out.println("Book returned successfully: " + borrowingTransaction);
            } else {
                System.out.println("Failed to return book.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
