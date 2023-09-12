package service;

import domain.entities.Client;
import repository.ClientDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class ClientService {
    Scanner scanner = new Scanner(System.in);
    private final ClientDAO clientDAO;

    public ClientService(Connection connection) {
        this.clientDAO = new ClientDAO(connection);
    }

    public void ClientMenu() throws SQLException {

        while (true) {
            System.out.println("\n**************************************");
            System.out.println("1. List All Clients");
            System.out.println("2. Add a Client");
            System.out.println("3. Update a Client");
            System.out.println("4. Delete a Client");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            int choice = Integer.parseInt(scanner.nextLine());

            System.out.println("\n######################################");

            switch (choice) {
                case 1:
                    listAllClients();
                    break;
                case 2:
                    addClient();
                    break;
                case 3:
                    updateClient();
                    break;
                case 4:
                    deleteClient();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void listAllClients() {
        try {
            List<Client> Clients = clientDAO.getAll();
            for (Client Client : Clients) {
                System.out.println(Client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addClient() throws SQLException {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        System.out.print("Enter surname: ");
        String surname = scanner.nextLine();

        if (name.isEmpty() || surname.isEmpty()) {
            System.out.println("Invalid input. Please make sure all fields are filled correctly.");
            return;
        }

        if (clientDAO.isClientExist(name, surname) != null) {
            System.out.println("Client already exist !.");
            return;
        }

        Client newClient = new Client();
        newClient.setName(name);
        newClient.setSurname(surname);

        try {
            Client insertedClient = clientDAO.insert(newClient);
            if (insertedClient != null) {
                System.out.println("Client added successfully: " + insertedClient);
            } else {
                System.out.println("Failed to add Client.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error adding Client: " + e.getMessage());
        }
    }

    public void updateClient() {
        System.out.print("Enter id of the Client to update: ");
        int id = Integer.parseInt(scanner.nextLine());

        try {
            Client existingClient = clientDAO.getById(id);

            if (existingClient == null) {
                System.out.println("Client with id " + id + " not found.");
                return;
            }

            System.out.print("Enter new name (leave empty to keep existing): ");
            String name = scanner.nextLine();
            name = name.isEmpty() ? existingClient.getName() : name;

            System.out.print("Enter new surname (leave empty to keep existing): ");
            String surname = scanner.nextLine();
            surname = surname.isEmpty() ? existingClient.getSurname() : surname;

            if (name.isEmpty() || surname.isEmpty()) {
                System.out.println("Invalid input. Please make sure all fields are filled correctly.");
                return;
            }

            Client updatedClient = new Client(id, name, surname);

            Client result = clientDAO.update(updatedClient);
            if (result != null) {
                System.out.println("Client updated successfully: " + result);
            } else {
                System.out.println("Failed to update Client.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error updating Client: " + e.getMessage());
        }
    }

    public void deleteClient() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter id of the Client to delete: ");
        int id = Integer.parseInt(scanner.nextLine());

        try {
            boolean deleted = clientDAO.delete(id);
            if (deleted) {
                System.out.println("Client deleted successfully.");
            } else {
                System.out.println("Client not found or failed to delete.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
