
public class Main {
    public static void main(String[] args) {
        Book first = new Book("ISBN-12", "first book", BookStatus.AVAILABLE, 100, 0, 2);

        System.out.println(first.toString());
    }
}