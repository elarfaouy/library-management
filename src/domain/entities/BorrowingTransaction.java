package domain.entities;

import java.sql.Date;

public class BorrowingTransaction {
    private int id;
    private Date borrowDate;
    private Date returnDate;
    private Date dueDate;
    private BookCopy bookCopy;
    private Client client;

    public BorrowingTransaction() {
    }

    public BorrowingTransaction(int id, Date borrowDate, Date returnDate, Date dueDate, BookCopy bookCopy, Client client) {
        this.id = id;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.dueDate = dueDate;
        this.bookCopy = bookCopy;
        this.client = client;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public BookCopy getBookCopy() {
        return bookCopy;
    }

    public void setBookCopy(BookCopy bookCopy) {
        this.bookCopy = bookCopy;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "BorrowingTransaction{" +
                "id=" + id +
                ", borrowDate=" + borrowDate +
                ", returnDate=" + returnDate +
                ", dueDate=" + dueDate +
                ", bookCopy=" + bookCopy +
                ", client=" + client +
                '}';
    }
}
