package domain.entities;

import domain.enums.BookStatus;

public class Book {
    private String isbn;
    private String title;
    private BookStatus status;
    private int quantity;
    private int quantityLost;
    private Author author;

    public Book() {
    }

    public Book(String isbn, String title, BookStatus status, int quantity, int quantityLost, Author author) {
        this.isbn = isbn;
        this.title = title;
        this.status = status;
        this.quantity = quantity;
        this.quantityLost = quantityLost;
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BookStatus getStatus() {
        return status;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantityLost() {
        return quantityLost;
    }

    public void setQuantityLost(int quantityLost) {
        this.quantityLost = quantityLost;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", status=" + status +
                ", quantity=" + quantity +
                ", quantityLost=" + quantityLost +
                ", author=" + author +
                '}';
    }
}
