import enums.BookStatus;
import model.Author;
import model.Book;
import model.BorrowedBook;
import model.Client;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Author author1 = new Author(1, "John", "Doe");
        Author author2 = new Author(2, "Jane", "Smith");

        Book book1 = new Book("ISBN-12-345", "The Great Novel", BookStatus.AVAILABLE, 50, 0, author1.getId());
        Book book2 = new Book("ISBN-67-890", "Mystery at Midnight", BookStatus.AVAILABLE, 30, 0, author2.getId());
        Book book3 = new Book("ISBN-99-999", "Adventure Quest", BookStatus.ON_LOAN, 10, 2, author1.getId());

        author1.setBooks(Arrays.asList(book1, book3));
        author2.setBooks(List.of(book2));

        Client client1 = new Client(1, "Alice", "Johnson");
        Client client2 = new Client(2, "Bob", "Smith");
        Client client3 = new Client(3, "Eve", "Williams");

        Date currentDate = new Date();
        Date dueDate1 = new Date(currentDate.getTime() + (7 * 24 * 60 * 60 * 1000));
        Date dueDate2 = new Date(currentDate.getTime() + (14 * 24 * 60 * 60 * 1000));

        BorrowedBook borrowedBook1 = new BorrowedBook(currentDate, null, dueDate1, book1, client1);
        BorrowedBook borrowedBook2 = new BorrowedBook(currentDate, null, dueDate2, book2, client2);
        BorrowedBook borrowedBook3 = new BorrowedBook(currentDate, null, dueDate1, book3, client3);

        System.out.println("model.Book 1 Info: " + book1.toString());
        System.out.println("model.Book 2 Info: " + book2.toString());
        System.out.println("model.Book 3 Info: " + book3.toString());
        System.out.println("model.Author 1 Info: " + author1.toString());
        System.out.println("model.Author 2 Info: " + author2.toString());
        System.out.println("model.Client 1 Info: " + client1.toString());
        System.out.println("model.Client 2 Info: " + client2.toString());
        System.out.println("model.Client 3 Info: " + client3.toString());
        System.out.println("Borrowed model.Book 1 Info: " + borrowedBook1.toString());
        System.out.println("Borrowed model.Book 2 Info: " + borrowedBook2.toString());
        System.out.println("Borrowed model.Book 3 Info: " + borrowedBook3.toString());
    }
}