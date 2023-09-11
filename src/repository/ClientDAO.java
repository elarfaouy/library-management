package repository;

import domain.entities.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ClientDAO implements BaseDAO<Client> {
    private final Connection connection;

    public ClientDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Client insert(Client client) throws SQLException {
        return null;
    }

    @Override
    public Client update(Client client) throws SQLException {
        return null;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return false;
    }

    @Override
    public List<Client> getAll() throws SQLException {
        return null;
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
}
