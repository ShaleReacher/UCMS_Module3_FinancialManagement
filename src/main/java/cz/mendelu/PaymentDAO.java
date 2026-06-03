package cz.mendelu;

import java.util.List;

public interface PaymentDAO {

    // Retrieve all payments from database
    List<Payment> getAll();

    // Retrieve one payment by ID
    Payment getById(int paymentId);

    // Insert a new payment into database and return it with generated ID
    Payment save(Payment payment);

    // Update an existing payment in database
    void update(Payment payment);

    // Delete a payment from database
    void delete(int paymentId);
}