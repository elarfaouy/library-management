package domain.entities;

import java.util.Date;

public class BorrowedBook {
    private Date borrowDate;
    private Date returnDate;
    private Date dueDate;
    private Book book;
    private Client client;

    public BorrowedBook() {
    }

    public BorrowedBook(Date borrowDate, Date returnDate, Date dueDate, Book book, Client client) {
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.dueDate = dueDate;
        this.book = book;
        this.client = client;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "domain.model.BorrowedBook{" +
                "borrowDate=" + borrowDate +
                ", returnDate=" + returnDate +
                ", dueDate=" + dueDate +
                ", book=" + book +
                ", client=" + client +
                '}';
    }
}
