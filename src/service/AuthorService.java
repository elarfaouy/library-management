package service;

import domain.entities.Author;
import repository.AuthorDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class AuthorService {
    Scanner scanner = new Scanner(System.in);
    private final AuthorDAO authorDAO;

    public AuthorService(Connection connection) {
        this.authorDAO = new AuthorDAO(connection);
    }

    public void AuthorMenu() throws SQLException {

        while (true) {
            System.out.println("\n**************************************");
            System.out.println("1. List All Authors");
            System.out.println("2. Add a Author");
            System.out.println("3. Update a Author");
            System.out.println("4. Delete a Author");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            int choice = Integer.parseInt(scanner.nextLine());

            System.out.println("\n######################################");

            switch (choice) {
                case 1:
                    listAllAuthors();
                    break;
                case 2:
                    addAuthor();
                    break;
                case 3:
                    updateAuthor();
                    break;
                case 4:
                    deleteAuthor();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void listAllAuthors() {
        try {
            List<Author> Authors = authorDAO.getAll();
            for (Author Author : Authors) {
                System.out.println(Author);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addAuthor() throws SQLException {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        System.out.print("Enter surname: ");
        String surname = scanner.nextLine();

        if (name.isEmpty() || surname.isEmpty()) {
            System.out.println("Invalid input. Please make sure all fields are filled correctly.");
            return;
        }

        if (authorDAO.isAuthorExist(name, surname) != null) {
            System.out.println("Author already exist !.");
            return;
        }

        Author newAuthor = new Author();
        newAuthor.setName(name);
        newAuthor.setSurname(surname);

        try {
            Author insertedAuthor = authorDAO.insert(newAuthor);
            if (insertedAuthor != null) {
                System.out.println("Author added successfully: " + insertedAuthor);
            } else {
                System.out.println("Failed to add Author.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error adding Author: " + e.getMessage());
        }
    }

    public void updateAuthor() {
        System.out.print("Enter id of the Author to update: ");
        int id = Integer.parseInt(scanner.nextLine());

        try {
            Author existingAuthor = authorDAO.getById(id);

            if (existingAuthor == null) {
                System.out.println("Author with id " + id + " not found.");
                return;
            }

            System.out.print("Enter new name (leave empty to keep existing): ");
            String name = scanner.nextLine();
            name = name.isEmpty() ? existingAuthor.getName() : name;

            System.out.print("Enter new surname (leave empty to keep existing): ");
            String surname = scanner.nextLine();
            surname = surname.isEmpty() ? existingAuthor.getSurname() : surname;

            if (name.isEmpty() || surname.isEmpty()) {
                System.out.println("Invalid input. Please make sure all fields are filled correctly.");
                return;
            }

            Author updatedAuthor = new Author(id, name, surname);

            Author result = authorDAO.update(updatedAuthor);
            if (result != null) {
                System.out.println("Author updated successfully: " + result);
            } else {
                System.out.println("Failed to update Author.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error updating Author: " + e.getMessage());
        }
    }

    public void deleteAuthor() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter id of the Author to delete: ");
        int id = Integer.parseInt(scanner.nextLine());

        try {
            boolean deleted = authorDAO.delete(id);
            if (deleted) {
                System.out.println("Author deleted successfully.");
            } else {
                System.out.println("Author not found or failed to delete.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
