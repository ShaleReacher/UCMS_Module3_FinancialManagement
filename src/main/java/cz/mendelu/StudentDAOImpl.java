package cz.mendelu;

import cz.mendelu.Student;
import java.util.List;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class StudentDAOImpl implements StudentDAO {

    @Override
    public List<Student> getStudents() {
        List<Student> students = new ArrayList<>();
        try {
            // Use the SAME persistent connection from singleton
            Connection conn = DatabaseConnection.getConnection();

            if (conn == null) {
                System.err.println("❌ Connection is null");
                return students;
            }

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, name FROM students");

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                students.add(new Student(id, name));
            }

            System.out.println("✅ Fetched " + students.size() + " students from H2 database");
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.err.println("❌ Error fetching students: " + e.getMessage());
            e.printStackTrace();
        }
        return students;
    }

    @Override
    public Student getStudentById(int id) {
        try {
            Connection conn = DatabaseConnection.getConnection();

            if (conn == null) {
                System.err.println("❌ Connection is null");
                return null;
            }

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, name FROM students WHERE id = " + id);

            if (rs.next()) {
                Student student = new Student(rs.getInt("id"), rs.getString("name"));
                rs.close();
                stmt.close();
                return student;
            }

            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.err.println("❌ Error fetching student: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(Student student) {
        try {
            Connection conn = DatabaseConnection.getConnection();

            if (conn == null) {
                System.err.println("❌ Connection is null");
                return;
            }

            Statement stmt = conn.createStatement();
            stmt.execute("INSERT INTO students (name) VALUES ('" + student.getName() + "')");
            System.out.println("✅ Saved student: " + student.getName());
            stmt.close();
        } catch (Exception e) {
            System.err.println("❌ Error saving student: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Student student) {
        try {
            Connection conn = DatabaseConnection.getConnection();

            if (conn == null) {
                System.err.println("❌ Connection is null");
                return;
            }

            Statement stmt = conn.createStatement();
            stmt.execute("DELETE FROM students WHERE id = " + student.getId());
            System.out.println("✅ Deleted student: " + student.getName());
            stmt.close();
        } catch (Exception e) {
            System.err.println("❌ Error deleting student: " + e.getMessage());
            e.printStackTrace();
        }
    }
}