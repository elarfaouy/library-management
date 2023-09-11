package repository;

import domain.entities.BorrowingTransaction;

import java.sql.SQLException;
import java.util.List;

public class BorrowingTransactionDAO implements BaseDAO<BorrowingTransaction> {
    @Override
    public BorrowingTransaction insert(BorrowingTransaction entity) throws SQLException {
        return null;
    }

    @Override
    public BorrowingTransaction update(BorrowingTransaction entity) throws SQLException {
        return null;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return false;
    }

    @Override
    public List<BorrowingTransaction> getAll() throws SQLException {
        return null;
    }
}
