package dao;

import model.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Types.BOOLEAN;

public class ClientDAO {
    private final Connection connection;

    public ClientDAO(Connection connection) {
        this.connection = connection;
    }

    public Client insert(Client client) throws SQLException {
        String sql = "INSERT INTO clients (name, surname) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, client.getName());
            preparedStatement.setString(2, client.getSurname());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 1) {
                return client;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Client update(Client client) throws SQLException {
        String sql = "UPDATE clients SET name=?, surname=? WHERE id=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, client.getName());
            preparedStatement.setString(2, client.getSurname());
            preparedStatement.setInt(3, client.getId());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 1) {
                return client;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean delete(int clientId) throws SQLException {
        String sql = "DELETE FROM clients WHERE id=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, clientId);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Client> getAllClients() throws SQLException {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM clients";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");

                Client client = new Client(id, name, surname);
                clients.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clients;
    }

    public Client getFirstClient() throws SQLException {
        String sql = "SELECT id, name, surname FROM clients ORDER BY id ASC LIMIT 1";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");

                return new Client(id, name, surname);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean checkClientExists(String clientName, String clientSurname) throws SQLException {
        try (CallableStatement cs = connection.prepareCall("{call isClientExists(?,?,?)}")) {
            cs.setString(1, clientName);
            cs.setString(2, clientSurname);
            cs.registerOutParameter(3, BOOLEAN);
            cs.execute();

            return cs.getBoolean(3);
        }
    }
}
