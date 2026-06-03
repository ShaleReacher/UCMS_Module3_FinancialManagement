package cz.mendelu;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAOImpl implements TransactionDAO {

    @Override
    public List<Transaction> getAll() {
        List<Transaction> transactions = new ArrayList<>();
        try {
            Connection conn = DatabaseConnection.getConnection();
            if (conn == null) {
                System.err.println("Failed to get database connection");
                return transactions;
            }

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT transaction_id, payment_id, transaction_type, amount, description, transaction_date, status FROM transactions");

            while (rs.next()) {
                Transaction transaction = new Transaction(
                        rs.getInt("transaction_id"),
                        rs.getInt("payment_id"),
                        rs.getString("transaction_type"),
                        rs.getDouble("amount"),
                        rs.getString("description"),
                        rs.getString("transaction_date"),
                        rs.getString("status")
                );
                transactions.add(transaction);
            }

            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.err.println("Error fetching transactions: " + e.getMessage());
            e.printStackTrace();
        }
        return transactions;
    }

    @Override
    public Transaction getById(int transactionId) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            if (conn == null) {
                System.err.println("Failed to get database connection");
                return null;
            }

            String sql = "SELECT transaction_id, payment_id, transaction_type, amount, description, transaction_date, status FROM transactions WHERE transaction_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, transactionId);
            ResultSet rs = stmt.executeQuery();

            Transaction transaction = null;
            if (rs.next()) {
                transaction = new Transaction(
                        rs.getInt("transaction_id"),
                        rs.getInt("payment_id"),
                        rs.getString("transaction_type"),
                        rs.getDouble("amount"),
                        rs.getString("description"),
                        rs.getString("transaction_date"),
                        rs.getString("status")
                );
            }

            rs.close();
            stmt.close();
            return transaction;
        } catch (Exception e) {
            System.err.println("Error fetching transaction by ID: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Transaction save(Transaction transaction) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            if (conn == null) {
                System.err.println("Failed to get database connection");
                return null;
            }

            String sql = "INSERT INTO transactions (payment_id, transaction_type, amount, description, transaction_date, status) " +
                    "VALUES (?, ?, ?, ?, CAST(? AS DATE), ?)";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, transaction.getPaymentId());
            stmt.setString(2, transaction.getTransactionType());
            stmt.setDouble(3, transaction.getAmount());
            stmt.setString(4, transaction.getDescription());
            stmt.setString(5, transaction.getTransactionDate());
            stmt.setString(6, transaction.getStatus());

            stmt.execute();

            // Retrieve the auto-generated ID from PostgreSQL
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int generatedId = generatedKeys.getInt(1);
                transaction.setTransactionId(generatedId);
                System.out.println("Transaction saved successfully with ID: " + generatedId);
            }

            generatedKeys.close();
            stmt.close();
            return transaction;
        } catch (Exception e) {
            System.err.println("Error saving transaction: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void update(Transaction transaction) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            if (conn == null) {
                System.err.println("Failed to get database connection");
                return;
            }

            String sql = "UPDATE transactions SET payment_id = ?, transaction_type = ?, amount = ?, description = ?, transaction_date = CAST(? AS DATE), status = ? WHERE transaction_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, transaction.getPaymentId());
            stmt.setString(2, transaction.getTransactionType());
            stmt.setDouble(3, transaction.getAmount());
            stmt.setString(4, transaction.getDescription());
            stmt.setString(5, transaction.getTransactionDate());
            stmt.setString(6, transaction.getStatus());
            stmt.setInt(7, transaction.getTransactionId());

            stmt.execute();
            System.out.println("Transaction updated successfully");
            stmt.close();
        } catch (Exception e) {
            System.err.println("Error updating transaction: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int transactionId) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            if (conn == null) {
                System.err.println("Failed to get database connection");
                return;
            }

            String sql = "DELETE FROM transactions WHERE transaction_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, transactionId);
            stmt.execute();
            System.out.println("Transaction deleted successfully");
            stmt.close();
        } catch (Exception e) {
            System.err.println("Error deleting transaction: " + e.getMessage());
            e.printStackTrace();
        }
    }
}