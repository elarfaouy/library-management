package domain.entities;

public class Book {
    private int id;
    private String isbn;
    private String title;
    private int quantity;
    private Author author;

    public Book() {
    }

    public Book(int id, String isbn, String title, int quantity, Author author) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.quantity = quantity;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
                "id=" + id +
                ", isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", quantity=" + quantity +
                ", author=" + author +
                '}';
    }
}
