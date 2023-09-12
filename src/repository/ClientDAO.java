package repository;

import domain.entities.Client;
import domain.entities.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO implements BaseDAO<Client> {
    private final Connection connection;

    public ClientDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Client insert(Client client) throws SQLException {
        String query = "INSERT INTO clients (`name`, `surname`) VALUES (?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, client.getName());
        preparedStatement.setString(2, client.getSurname());

        int rowsAffected = preparedStatement.executeUpdate();

        if (rowsAffected == 1) {
            return client;
        }

        return null;
    }

    @Override
    public Client update(Client client) throws SQLException {
        String query = "UPDATE clients SET name=?, surname=? WHERE id=?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, client.getName());
        preparedStatement.setString(2, client.getSurname());
        preparedStatement.setInt(3, client.getId());

        int rowsAffected = preparedStatement.executeUpdate();

        if (rowsAffected == 1) {
            return client;
        }

        return null;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String query = "DELETE FROM clients WHERE id=?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);

        int rowsAffected = preparedStatement.executeUpdate();

        return rowsAffected == 1;
    }

    @Override
    public List<Client> getAll() throws SQLException {
        List<Client> clients = new ArrayList<>();

        String query = "SELECT * FROM clients LIMIT 10";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            String surname = resultSet.getString(3);

            Client client = new Client(id, name, surname);

            clients.add(client);
        }

        return clients;
    }

    @Override
    public Client getById(int id) throws SQLException {
        String query = "SELECT * FROM clients WHERE id=?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            String name = resultSet.getString(2);
            String surname = resultSet.getString(3);

            return new Client(id, name, surname);
        }

        return null;
    }

    public Client isClientExist(String name, String surname) throws SQLException {
        String query = "SELECT * FROM clients WHERE name=? AND surname=?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, surname);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            int id = resultSet.getInt(1);

            return new Client(id, name, surname);
        }

        return null;
    }

}
