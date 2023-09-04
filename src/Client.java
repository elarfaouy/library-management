import java.util.ArrayList;
import java.util.List;

public class Client {
    private int id;
    private String name;
    private String surname;
    private List<BorrowedBook> borrowedBooks;

    public Client() {
    }

    public Client(int id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.borrowedBooks = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<BorrowedBook> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(List<BorrowedBook> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", borrowedBooks=" + borrowedBooks +
                '}';
    }
}
