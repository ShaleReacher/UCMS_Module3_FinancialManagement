package cz.mendelu;

import java.util.List;

public interface TransactionDAO {

    // Retrieve all transactions from database
    List<Transaction> getAll();

    // Retrieve one transaction by ID
    Transaction getById(int transactionId);

    // Insert a new transaction into database and return it with generated ID
    Transaction save(Transaction transaction);

    // Update an existing transaction in database
    void update(Transaction transaction);

    // Delete a transaction from database
    void delete(int transactionId);
}