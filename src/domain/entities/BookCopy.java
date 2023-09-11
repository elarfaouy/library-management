package domain.entities;

import domain.enums.BookStatus;

public class BookCopy {
    private int id;
    private int serial;
    private BookStatus status;
    private Book book;

    public BookCopy() {
    }

    public BookCopy(int id, int serial, BookStatus status, Book book) {
        this.id = id;
        this.serial = serial;
        this.status = status;
        this.book = book;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }

    public BookStatus getStatus() {
        return status;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return "BookCopy{" +
                "id=" + id +
                ", serial=" + serial +
                ", bookStatus=" + status +
                ", book=" + book +
                '}';
    }
}
