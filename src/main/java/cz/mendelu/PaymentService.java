package cz.mendelu;

import java.util.List;

public interface PaymentService {

    List<Payment> getAllPayments();

    Payment getPaymentById(int paymentId);

    Payment savePayment(Payment payment);

    void updatePayment(Payment payment);

    void deletePayment(int paymentId);
}