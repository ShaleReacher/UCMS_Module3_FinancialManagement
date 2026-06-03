package cz.mendelu;

import java.util.List;

public class TransactionServiceImpl implements TransactionService {

    private TransactionDAO transactionDAO = new TransactionDAOImpl();

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionDAO.getAll();
    }

    @Override
    public Transaction getTransactionById(int transactionId) {
        return transactionDAO.getById(transactionId);
    }

    @Override
    public Transaction saveTransaction(Transaction transaction) {
        return transactionDAO.save(transaction);
    }

    @Override
    public void updateTransaction(Transaction transaction) {
        transactionDAO.update(transaction);
    }

    @Override
    public void deleteTransaction(int transactionId) {
        transactionDAO.delete(transactionId);
    }
}