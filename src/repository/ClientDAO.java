package repository;

import domain.entities.Client;

import java.sql.SQLException;
import java.util.List;

public class ClientDAO implements BaseDAO<Client> {
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
}
