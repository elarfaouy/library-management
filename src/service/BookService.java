package service;

import domain.entities.Author;
import domain.entities.Book;
import domain.enums.BookStatus;
import repository.AuthorDAO;
import repository.BookDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class BookService {
    Scanner scanner = new Scanner(System.in);
    private final BookDAO bookDAO;
    private final AuthorDAO authorDAO;

    public BookService(Connection connection) {
        this.bookDAO = new BookDAO(connection);
        this.authorDAO = new AuthorDAO(connection);
    }

    public void BookMenu() throws SQLException {

        while (true) {
            System.out.println("\n**************************************");
            System.out.println("1. List All Books");
            System.out.println("2. Add a Book");
            System.out.println("3. Update a Book");
            System.out.println("4. Delete a Book");
            System.out.println("5. Search a Book");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            int choice = Integer.parseInt(scanner.nextLine());

            System.out.println("\n######################################");

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
                    searchBook();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void listAllBooks() {
        try {
            List<Book> books = bookDAO.getAll();
            for (Book book : books) {
                System.out.println(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void searchBook() {
        try {
            System.out.print("Enter book titre or author name : ");
            String string = scanner.nextLine();

            List<Book> books = bookDAO.searchByBookOrAuthor(string);
            for (Book book : books) {
                System.out.println(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addBook() throws SQLException {
        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine();

        System.out.print("Enter title: ");
        String title = scanner.nextLine();

        System.out.print("Enter quantity: ");
        int quantity = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter author id: ");
        int author_id = Integer.parseInt(scanner.nextLine());

        Author author = authorDAO.getById(author_id);

        Book newBook = new Book();
        newBook.setIsbn(isbn);
        newBook.setTitle(title);
        newBook.setQuantity(quantity);
        newBook.setAuthor(author);

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
        System.out.print("Enter id of the book to update: ");
        int id = Integer.parseInt(scanner.nextLine());

        try {
            Book existingBook = bookDAO.getById(id);

            if (existingBook == null) {
                System.out.println("Book with id " + id + " not found.");
                return;
            }

            System.out.print("Enter new ISBN (leave empty to keep existing): ");
            String isbn = scanner.nextLine();
            isbn = isbn.isEmpty() ? existingBook.getIsbn() : isbn;

            System.out.print("Enter new title (leave empty to keep existing): ");
            String title = scanner.nextLine();
            title = title.isEmpty() ? existingBook.getTitle() : title;

            System.out.print("Enter new quantity (leave empty to keep existing): ");
            String quantityStr = scanner.nextLine();
            int quantity = quantityStr.isEmpty() ? existingBook.getQuantity() : Integer.parseInt(quantityStr);

            System.out.print("Enter new author id (leave empty to keep existing): ");
            String authorStr = scanner.nextLine();
            int author_id = authorStr.isEmpty() ? existingBook.getAuthor().getId() : Integer.parseInt(authorStr);

            Author author = authorDAO.getById(author_id);

            Book updatedBook = new Book(id, isbn, title, quantity, author);

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
        System.out.print("Enter id of the book to delete: ");
        int id = Integer.parseInt(scanner.nextLine());

        try {
            boolean deleted = bookDAO.delete(id);
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
