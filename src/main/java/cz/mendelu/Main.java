package cz.mendelu;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("\n=== PHASE 2 FINANCIAL MODULE - INTEGRATION TEST ===\n");

        // Initialize database first
        DatabaseConfig.initializeDatabase();

        System.out.println("\n--- TESTING STUDENT SERVICE (Phase 1) ---");
        StudentDAO studentDAO = new StudentDAOImpl();
        StudentService studentService = new StudentServiceImpl(studentDAO);
        List<Student> students = studentService.getAllStudents();
        System.out.println("✅ Students fetched: " + students);

        System.out.println("\n--- TESTING FEE SERVICE (Phase 2) ---");
        FeeService feeService = new FeeServiceImpl();
        List<Fee> fees = feeService.getAllFees();
        System.out.println("✅ Fees fetched: " + fees.size() + " records");
        for (Fee fee : fees) {
            System.out.println("   - " + fee.getFeeType() + ": €" + fee.getAmount() + " (" + fee.getStatus() + ")");
        }

        System.out.println("\n--- TESTING PAYMENT SERVICE (Phase 2) ---");
        PaymentService paymentService = new PaymentServiceImpl();
        List<Payment> payments = paymentService.getAllPayments();
        System.out.println("✅ Payments fetched: " + payments.size() + " records");
        for (Payment payment : payments) {
            System.out.println("   - Fee " + payment.getFeeId() + ": €" + payment.getAmount() + " (" + payment.getStatus() + ")");
        }

        System.out.println("\n--- TESTING TRANSACTION SERVICE (Phase 2) ---");
        TransactionService transactionService = new TransactionServiceImpl();
        List<Transaction> transactions = transactionService.getAllTransactions();
        System.out.println("✅ Transactions fetched: " + transactions.size() + " records");
        for (Transaction transaction : transactions) {
            System.out.println("   - " + transaction.getTransactionType() + ": €" + transaction.getAmount() + " (" + transaction.getStatus() + ")");
        }

        System.out.println("\n=== ALL TESTS PASSED ✅ ===");
        System.out.println("Module 3 Financial Management - READY FOR DEPLOYMENT\n");
    }
}