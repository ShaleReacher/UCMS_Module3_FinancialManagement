package cz.mendelu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String DB_URL = "jdbc:postgresql://postgres:5432/ucms_db";
    private static final String DB_DRIVER = "org.postgresql.Driver";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "postgres";

    static {
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("PostgreSQL JDBC Driver not found. Add postgresql dependency to pom.xml");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}
