package cz.mendelu;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class FeeDAOImpl implements FeeDAO {

    @Override
    public List<Fee> getAll() {
        List<Fee> fees = new ArrayList<>();
        try {
            Connection conn = DatabaseConnection.getConnection();
            if (conn == null) {
                System.err.println("Failed to get database connection");
                return fees;
            }

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT fee_id, fee_type, amount, description, status, created_date FROM fees");

            while (rs.next()) {
                Fee fee = new Fee(
                        rs.getInt("fee_id"),
                        rs.getString("fee_type"),
                        rs.getDouble("amount"),
                        rs.getString("description"),
                        rs.getString("status"),
                        rs.getString("created_date")
                );
                fees.add(fee);
            }

            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.err.println("Error fetching fees: " + e.getMessage());
            e.printStackTrace();
        }
        return fees;
    }

    @Override
    public Fee getById(int feeId) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            if (conn == null) {
                System.err.println("Failed to get database connection");
                return null;
            }

            String sql = "SELECT fee_id, fee_type, amount, description, status, created_date FROM fees WHERE fee_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, feeId);
            ResultSet rs = stmt.executeQuery();

            Fee fee = null;
            if (rs.next()) {
                fee = new Fee(
                        rs.getInt("fee_id"),
                        rs.getString("fee_type"),
                        rs.getDouble("amount"),
                        rs.getString("description"),
                        rs.getString("status"),
                        rs.getString("created_date")
                );
            }

            rs.close();
            stmt.close();
            return fee;
        } catch (Exception e) {
            System.err.println("Error fetching fee by ID: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Fee save(Fee fee) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            if (conn == null) {
                System.err.println("Failed to get database connection");
                return null;
            }

            String sql = "INSERT INTO fees (fee_type, amount, description, status, created_date) " +
                    "VALUES (?, ?, ?, ?, CAST(? AS DATE))";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, fee.getFeeType());
            stmt.setDouble(2, fee.getAmount());
            stmt.setString(3, fee.getDescription());
            stmt.setString(4, fee.getStatus());
            stmt.setString(5, fee.getCreatedDate());

            stmt.execute();

            // Retrieve the auto-generated ID from PostgreSQL
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int generatedId = generatedKeys.getInt(1);
                fee.setFeeId(generatedId);
                System.out.println("Fee saved successfully with ID: " + generatedId);
            }

            generatedKeys.close();
            stmt.close();
            return fee;
        } catch (Exception e) {
            System.err.println("Error saving fee: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void update(Fee fee) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            if (conn == null) {
                System.err.println("Failed to get database connection");
                return;
            }

            String sql = "UPDATE fees SET fee_type = ?, amount = ?, description = ?, status = ?, created_date = CAST(? AS DATE) WHERE fee_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, fee.getFeeType());
            stmt.setDouble(2, fee.getAmount());
            stmt.setString(3, fee.getDescription());
            stmt.setString(4, fee.getStatus());
            stmt.setString(5, fee.getCreatedDate());
            stmt.setInt(6, fee.getFeeId());

            stmt.execute();
            System.out.println("Fee updated successfully");
            stmt.close();
        } catch (Exception e) {
            System.err.println("Error updating fee: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int feeId) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            if (conn == null) {
                System.err.println("Failed to get database connection");
                return;
            }

            String sql = "DELETE FROM fees WHERE fee_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, feeId);
            stmt.execute();
            System.out.println("Fee deleted successfully");
            stmt.close();
        } catch (Exception e) {
            System.err.println("Error deleting fee: " + e.getMessage());
            e.printStackTrace();
        }
    }
}