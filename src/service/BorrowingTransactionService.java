package service;

import domain.entities.Book;
import domain.entities.BookCopy;
import domain.entities.BorrowingTransaction;
import domain.entities.Client;
import domain.enums.BookStatus;
import repository.BookCopyDAO;
import repository.BookDAO;
import repository.BorrowingTransactionDAO;
import repository.ClientDAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

public class BorrowingTransactionService {
    Scanner scanner = new Scanner(System.in);
    private final BorrowingTransactionDAO borrowingTransactionDAO = new BorrowingTransactionDAO();
    private final BookDAO bookDAO = new BookDAO();
    private final ClientDAO clientDAO = new ClientDAO();
    private final BookCopyDAO bookCopyDAO = new BookCopyDAO();

    public BorrowingTransactionService() {
    }

    public void BorrowMenu() {

        while (true) {
            System.out.println("\n**************************************");
            System.out.println("1. Get all Transactions");
            System.out.println("2. Borrow a Book");
            System.out.println("3. Return a Book");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            int choice = Integer.parseInt(scanner.nextLine());

            System.out.println("\n######################################");

            switch (choice) {
                case 1:
                    listAllTransactions();
                    break;
                case 2:
                    borrowBook();
                    break;
                case 3:
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

    public void listAllTransactions() {
        try {
            List<BorrowingTransaction> borrowingTransactionList = borrowingTransactionDAO.getAll();
            for (BorrowingTransaction borrowingTransaction : borrowingTransactionList) {
                System.out.println(borrowingTransaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void borrowBook() {
        System.out.print("Enter ISBN of the book to borrow: ");
        String isbn = scanner.nextLine();

        try {
            Book borrowBook = bookDAO.getByIsbn(isbn);
            if (borrowBook == null) {
                System.out.println("Book with ISBN " + isbn + " not found.");
                return;
            }

            int bookCopyId = bookCopyDAO.getCopyAvailable(isbn);
            if (bookCopyId != 0) {
                BookCopy bookCopy = bookCopyDAO.getById(bookCopyId);

                System.out.print("Enter id of the client to borrow: ");
                int client_id = Integer.parseInt(scanner.nextLine());

                Client client = clientDAO.getById(client_id);

                System.out.print("Enter the number of days to borrow (max 30 days): ");
                int numberOfDaysToBorrow = Integer.parseInt(scanner.nextLine());

                if (client == null || 30 < numberOfDaysToBorrow || numberOfDaysToBorrow <= 0) {
                    System.out.println("Invalid input. Please make sure all fields are filled correctly.");
                    return;
                }

                if (!borrowingTransactionDAO.isClientCanBorrow(bookCopy, client)) {
                    System.out.println("You are already borrowed this book.");
                    return;
                }

                Calendar calendar = Calendar.getInstance();
                Date borrowDate = new Date(calendar.getTime().getTime());
                calendar.add(Calendar.DAY_OF_MONTH, numberOfDaysToBorrow);
                Date dueDate = new Date(calendar.getTime().getTime());

                BorrowingTransaction borrowingTransaction = new BorrowingTransaction();
                borrowingTransaction.setBookCopy(bookCopy);
                borrowingTransaction.setClient(client);
                borrowingTransaction.setBorrowDate(borrowDate);
                borrowingTransaction.setDueDate(dueDate);

                BorrowingTransaction borrow = borrowingTransactionDAO.insert(borrowingTransaction);
                bookCopy.setStatus(BookStatus.ON_LOAN);
                BookCopy updateBookCopy = bookCopyDAO.update(bookCopy);

                if (updateBookCopy != null && borrow != null) {
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
        System.out.print("Enter id of the borrowing transaction: ");
        int id = Integer.parseInt(scanner.nextLine());

        try {
            BorrowingTransaction borrowingTransaction = borrowingTransactionDAO.getById(id);
            if (borrowingTransaction == null) {
                System.out.println("Borrowing with id " + id + " not found.");
                return;
            }

            if (borrowingTransaction.getBookCopy().getStatus() == BookStatus.AVAILABLE && borrowingTransaction.getReturnDate() != null) {
                System.out.println("This Book already returned !.");
                return;
            }

            Calendar calendar = Calendar.getInstance();
            Date returnDate = new Date(calendar.getTime().getTime());

            borrowingTransaction.setReturnDate(returnDate);
            BorrowingTransaction returnBook = borrowingTransactionDAO.update(borrowingTransaction);

            BookCopy bookCopy = borrowingTransaction.getBookCopy();
            bookCopy.setStatus(BookStatus.AVAILABLE);
            BookCopy updateBook = bookCopyDAO.update(bookCopy);

            if (updateBook != null && returnBook != null) {
                System.out.println("Book returned successfully: " + borrowingTransaction);
            } else {
                System.out.println("Failed to return book.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
