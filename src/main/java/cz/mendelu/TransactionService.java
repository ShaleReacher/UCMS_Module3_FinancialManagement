package cz.mendelu;

import java.util.List;

public interface TransactionService {

    List<Transaction> getAllTransactions();

    Transaction getTransactionById(int transactionId);

    Transaction saveTransaction(Transaction transaction);

    void updateTransaction(Transaction transaction);

    void deleteTransaction(int transactionId);
}