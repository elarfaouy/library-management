package model;

import enums.BookStatus;

public class Book {
    private String isbn;
    private String title;
    private BookStatus status;
    private int quantity;
    private int quantityLost;
    private int idAuthor;

    public Book() {
    }

    public Book(String isbn, String title, BookStatus status, int quantity, int quantityLost, int idAuthor) {
        this.isbn = isbn;
        this.title = title;
        this.status = status;
        this.quantity = quantity;
        this.quantityLost = quantityLost;
        this.idAuthor = idAuthor;
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

    public int getIdAuthor() {
        return idAuthor;
    }

    public void setIdAuthor(int idAuthor) {
        this.idAuthor = idAuthor;
    }

    @Override
    public String toString() {
        return "model.Book{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", status='" + status + '\'' +
                ", quantity=" + quantity +
                ", quantityLost=" + quantityLost +
                ", idAuthor=" + idAuthor +
                '}';
    }
}
