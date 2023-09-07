package service;

import domain.entities.Author;
import domain.entities.Book;
import domain.enums.BookStatus;
import repository.BookDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class BookService {
    Scanner scanner = new Scanner(System.in);
    private final BookDAO bookDAO;

    public BookService(Connection connection) {
        this.bookDAO = new BookDAO(connection);
    }

    public void BookMenu() {

        while (true) {
            System.out.println("Library Management System");
            System.out.println("1. List All Books");
            System.out.println("2. Add a Book");
            System.out.println("3. Update a Book");
            System.out.println("4. Delete a Book");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    listAllBooks();
                    break;
                case 2:
                    addBook();
                    break;
                case 3:
                    updateBook();
                    break;
                case 4:
                    deleteBook();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void listAllBooks() {
        try {
            List<Book> books = bookDAO.getAllBooks();
            for (Book book : books) {
                System.out.println(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addBook() {
        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine();

        System.out.print("Enter title: ");
        String title = scanner.nextLine();

        System.out.print("Enter status (available/on loan/lost): ");
        String statusStr = scanner.nextLine();
        BookStatus status = BookStatus.fromString(statusStr);

        System.out.print("Enter quantity: ");
        int quantity = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter quantity lost: ");
        int quantityLost = Integer.parseInt(scanner.nextLine());

        // TODO : Author author = createAuthor();
        Author author = new Author(1, "", "");

        Book newBook = new Book(isbn, title, status, quantity, quantityLost, author);

        try {
            Book insertedBook = bookDAO.insert(newBook);
            if (insertedBook != null) {
                System.out.println("Book added successfully: " + insertedBook);
            } else {
                System.out.println("Failed to add book.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error adding book: " + e.getMessage());
        }
    }

    public void updateBook() {
        System.out.print("Enter ISBN of the book to update: ");
        String isbn = scanner.nextLine();

        try {
            Book existingBook = bookDAO.getBookByISBN(isbn);
            if (existingBook == null) {
                System.out.println("Book with ISBN " + isbn + " not found.");
                return;
            }

            System.out.print("Enter new title (leave empty to keep existing): ");
            String title = scanner.nextLine();
            title = title.isEmpty() ? existingBook.getTitle() : title;

            System.out.print("Enter new status (available/on loan/lost, leave empty to keep existing): ");
            String statusStr = scanner.nextLine();
            BookStatus status = statusStr.isEmpty() ? existingBook.getStatus() : BookStatus.fromString(statusStr);

            System.out.print("Enter new quantity (leave empty to keep existing): ");
            String quantityStr = scanner.nextLine();
            int quantity = quantityStr.isEmpty() ? existingBook.getQuantity() : Integer.parseInt(quantityStr);

            System.out.print("Enter new quantity lost (leave empty to keep existing): ");
            String quantityLostStr = scanner.nextLine();
            int quantityLost = quantityLostStr.isEmpty() ? existingBook.getQuantityLost() : Integer.parseInt(quantityLostStr);

            // TODO : Author author = createAuthor();
            Author author = new Author(1, "", "");

            Book updatedBook = new Book(isbn, title, status, quantity, quantityLost, author);

            Book result = bookDAO.update(updatedBook);
            if (result != null) {
                System.out.println("Book updated successfully: " + result);
            } else {
                System.out.println("Failed to update book.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error updating book: " + e.getMessage());
        }
    }

    public void deleteBook() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter ISBN of the book to delete: ");
        String isbn = scanner.nextLine();

        try {
            boolean deleted = bookDAO.delete(isbn);
            if (deleted) {
                System.out.println("Book deleted successfully.");
            } else {
                System.out.println("Book not found or failed to delete.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
