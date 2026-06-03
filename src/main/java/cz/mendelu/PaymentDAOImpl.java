package cz.mendelu;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAOImpl implements PaymentDAO {

    @Override
    public List<Payment> getAll() {
        List<Payment> payments = new ArrayList<>();
        try {
            Connection conn = DatabaseConnection.getConnection();
            if (conn == null) {
                System.err.println("Failed to get database connection");
                return payments;
            }

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT payment_id, fee_id, amount, payment_date, payment_method, transaction_id, status FROM payments");

            while (rs.next()) {
                Payment payment = new Payment(
                        rs.getInt("payment_id"),
                        rs.getInt("fee_id"),
                        rs.getDouble("amount"),
                        rs.getString("payment_date"),
                        rs.getString("payment_method"),
                        rs.getString("transaction_id"),
                        rs.getString("status")
                );
                payments.add(payment);
            }

            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.err.println("Error fetching payments: " + e.getMessage());
            e.printStackTrace();
        }
        return payments;
    }

    @Override
    public Payment getById(int paymentId) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            if (conn == null) {
                System.err.println("Failed to get database connection");
                return null;
            }

            String sql = "SELECT payment_id, fee_id, amount, payment_date, payment_method, transaction_id, status FROM payments WHERE payment_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, paymentId);
            ResultSet rs = stmt.executeQuery();

            Payment payment = null;
            if (rs.next()) {
                payment = new Payment(
                        rs.getInt("payment_id"),
                        rs.getInt("fee_id"),
                        rs.getDouble("amount"),
                        rs.getString("payment_date"),
                        rs.getString("payment_method"),
                        rs.getString("transaction_id"),
                        rs.getString("status")
                );
            }

            rs.close();
            stmt.close();
            return payment;
        } catch (Exception e) {
            System.err.println("Error fetching payment by ID: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Payment save(Payment payment) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            if (conn == null) {
                System.err.println("Failed to get database connection");
                return null;
            }

            String sql = "INSERT INTO payments (fee_id, amount, payment_date, payment_method, transaction_id, status) " +
                    "VALUES (?, ?, CAST(? AS DATE), ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, payment.getFeeId());
            stmt.setDouble(2, payment.getAmount());
            stmt.setString(3, payment.getPaymentDate());
            stmt.setString(4, payment.getPaymentMethod());
            stmt.setString(5, payment.getTransactionId());
            stmt.setString(6, payment.getStatus());

            stmt.execute();

            // Retrieve the auto-generated ID from PostgreSQL
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int generatedId = generatedKeys.getInt(1);
                payment.setPaymentId(generatedId);
                System.out.println("Payment saved successfully with ID: " + generatedId);
            }

            generatedKeys.close();
            stmt.close();
            return payment;
        } catch (Exception e) {
            System.err.println("Error saving payment: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void update(Payment payment) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            if (conn == null) {
                System.err.println("Failed to get database connection");
                return;
            }

            String sql = "UPDATE payments SET fee_id = ?, amount = ?, payment_date = CAST(? AS DATE), payment_method = ?, transaction_id = ?, status = ? WHERE payment_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, payment.getFeeId());
            stmt.setDouble(2, payment.getAmount());
            stmt.setString(3, payment.getPaymentDate());
            stmt.setString(4, payment.getPaymentMethod());
            stmt.setString(5, payment.getTransactionId());
            stmt.setString(6, payment.getStatus());
            stmt.setInt(7, payment.getPaymentId());

            stmt.execute();
            System.out.println("Payment updated successfully");
            stmt.close();
        } catch (Exception e) {
            System.err.println("Error updating payment: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int paymentId) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            if (conn == null) {
                System.err.println("Failed to get database connection");
                return;
            }

            String sql = "DELETE FROM payments WHERE payment_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, paymentId);
            stmt.execute();
            System.out.println("Payment deleted successfully");
            stmt.close();
        } catch (Exception e) {
            System.err.println("Error deleting payment: " + e.getMessage());
            e.printStackTrace();
        }
    }
}