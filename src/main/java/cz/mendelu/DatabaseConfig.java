package cz.mendelu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseConfig {
    private static final String DB_URL = "jdbc:h2:./testdb;MODE=MySQL";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";

    public static void initializeDatabase() {
        try {
            Class.forName("org.h2.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement stmt = conn.createStatement();

            // ===== TABLE MANAGEMENT STRATEGY =====
            // If tables exist, they will be reused
            // If tables don't exist, they will be created
            // This prevents error messages while maintaining data integrity

            // ===== STUDENTS TABLE (Phase 1) =====
            createStudentsTableIfNotExists(stmt);

            // ===== FEES TABLE (Phase 2) =====
            createFeesTableIfNotExists(stmt);

            // ===== PAYMENTS TABLE (Phase 2) =====
            createPaymentsTableIfNotExists(stmt);

            // ===== TRANSACTIONS TABLE (Phase 2) =====
            createTransactionsTableIfNotExists(stmt);

            stmt.close();
            conn.close();

            System.out.println("✅ H2 Database initialized successfully - all tables ready");
        } catch (Exception e) {
            System.err.println("❌ Error initializing database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Helper method to create STUDENTS table if it doesn't exist
    private static void createStudentsTableIfNotExists(Statement stmt) {
        try {
            // Check if table exists by querying it
            try {
                stmt.executeQuery("SELECT 1 FROM students LIMIT 1");
                System.out.println("✅ Students table exists - using existing table");
            } catch (Exception e) {
                // Table doesn't exist, create it
                stmt.execute("CREATE TABLE students (" +
                        "id INT PRIMARY KEY AUTO_INCREMENT, " +
                        "name VARCHAR(255) NOT NULL)");
                System.out.println("✅ Students table created");

                // Insert sample data only if table is new
                stmt.execute("INSERT INTO students (name) VALUES ('John Doe')");
                stmt.execute("INSERT INTO students (name) VALUES ('Jane Smith')");
                stmt.execute("INSERT INTO students (name) VALUES ('Bob Johnson')");
                System.out.println("✅ Students table populated with 3 records");
            }
        } catch (Exception e) {
            System.err.println("❌ Error with students table: " + e.getMessage());
        }
    }

    // Helper method to create FEES table if it doesn't exist
    private static void createFeesTableIfNotExists(Statement stmt) {
        try {
            // Check if table exists
            try {
                stmt.executeQuery("SELECT 1 FROM fees LIMIT 1");
                System.out.println("✅ Fees table exists - using existing table");
            } catch (Exception e) {
                // Table doesn't exist, create it
                stmt.execute("CREATE TABLE fees (" +
                        "feeId INT PRIMARY KEY AUTO_INCREMENT, " +
                        "feeType VARCHAR(100) NOT NULL, " +
                        "amount DOUBLE NOT NULL, " +
                        "description VARCHAR(255), " +
                        "status VARCHAR(50), " +
                        "createdDate VARCHAR(50))");
                System.out.println("✅ Fees table created");

                // Insert sample data only if table is new
                stmt.execute("INSERT INTO fees (feeType, amount, description, status, createdDate) VALUES ('Membership', 50.0, 'Annual membership fee', 'unpaid', '2026-04-01')");
                stmt.execute("INSERT INTO fees (feeType, amount, description, status, createdDate) VALUES ('Event', 5.0, 'Concert ticket', 'paid', '2026-04-02')");
                stmt.execute("INSERT INTO fees (feeType, amount, description, status, createdDate) VALUES ('Activity', 20.0, 'Workshop fee', 'unpaid', '2026-04-03')");
                System.out.println("✅ Fees table populated with 3 records");
            }
        } catch (Exception e) {
            System.err.println("❌ Error with fees table: " + e.getMessage());
        }
    }

    // Helper method to create PAYMENTS table if it doesn't exist
    private static void createPaymentsTableIfNotExists(Statement stmt) {
        try {
            // Check if table exists
            try {
                stmt.executeQuery("SELECT 1 FROM payments LIMIT 1");
                System.out.println("✅ Payments table exists - using existing table");
            } catch (Exception e) {
                // Table doesn't exist, create it
                stmt.execute("CREATE TABLE payments (" +
                        "paymentId INT PRIMARY KEY AUTO_INCREMENT, " +
                        "studentId INT NOT NULL, " +
                        "feeId INT NOT NULL, " +
                        "amount DOUBLE NOT NULL, " +
                        "paymentDate VARCHAR(50), " +
                        "paymentMethod VARCHAR(100), " +
                        "status VARCHAR(50), " +
                        "FOREIGN KEY (studentId) REFERENCES students(id), " +
                        "FOREIGN KEY (feeId) REFERENCES fees(feeId))");
                System.out.println("✅ Payments table created");

                // Insert sample data only if table is new
                stmt.execute("INSERT INTO payments (studentId, feeId, amount, paymentDate, paymentMethod, status) VALUES (1, 1, 50.0, '2026-04-04', 'card', 'completed')");
                stmt.execute("INSERT INTO payments (studentId, feeId, amount, paymentDate, paymentMethod, status) VALUES (2, 2, 5.0, '2026-04-04', 'cash', 'completed')");
                stmt.execute("INSERT INTO payments (studentId, feeId, amount, paymentDate, paymentMethod, status) VALUES (3, 3, 20.0, '2026-04-04', 'transfer', 'pending')");
                System.out.println("✅ Payments table populated with 3 records");
            }
        } catch (Exception e) {
            System.err.println("❌ Error with payments table: " + e.getMessage());
        }
    }

    // Helper method to create TRANSACTIONS table if it doesn't exist
    private static void createTransactionsTableIfNotExists(Statement stmt) {
        try {
            // Check if table exists
            try {
                stmt.executeQuery("SELECT 1 FROM transactions LIMIT 1");
                System.out.println("✅ Transactions table exists - using existing table");
            } catch (Exception e) {
                // Table doesn't exist, create it
                stmt.execute("CREATE TABLE transactions (" +
                        "transactionId INT PRIMARY KEY AUTO_INCREMENT, " +
                        "paymentId INT NOT NULL, " +
                        "transactionType VARCHAR(100), " +
                        "amount DOUBLE NOT NULL, " +
                        "description VARCHAR(255), " +
                        "transactionDate VARCHAR(50), " +
                        "status VARCHAR(50), " +
                        "FOREIGN KEY (paymentId) REFERENCES payments(paymentId))");
                System.out.println("✅ Transactions table created");

                // Insert sample data only if table is new
                stmt.execute("INSERT INTO transactions (paymentId, transactionType, amount, description, transactionDate, status) VALUES (1, 'fee_payment', 50.0, 'Payment received for membership', '2026-04-04', 'completed')");
                stmt.execute("INSERT INTO transactions (paymentId, transactionType, amount, description, transactionDate, status) VALUES (2, 'fee_payment', 5.0, 'Payment received for event', '2026-04-04', 'completed')");
                stmt.execute("INSERT INTO transactions (paymentId, transactionType, amount, description, transactionDate, status) VALUES (3, 'fee_payment', 20.0, 'Payment received for activity', '2026-04-04', 'pending')");
                System.out.println("✅ Transactions table populated with 3 records");
            }
        } catch (Exception e) {
            System.err.println("❌ Error with transactions table: " + e.getMessage());
        }
    }
}