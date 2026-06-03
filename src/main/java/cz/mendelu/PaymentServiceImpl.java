package cz.mendelu;

import java.util.List;

public class PaymentServiceImpl implements PaymentService {

    private PaymentDAO paymentDAO = new PaymentDAOImpl();

    @Override
    public List<Payment> getAllPayments() {
        return paymentDAO.getAll();
    }

    @Override
    public Payment getPaymentById(int paymentId) {
        return paymentDAO.getById(paymentId);
    }

    @Override
    public Payment savePayment(Payment payment) {
        return paymentDAO.save(payment);
    }

    @Override
    public void updatePayment(Payment payment) {
        paymentDAO.update(payment);
    }

    @Override
    public void deletePayment(int paymentId) {
        paymentDAO.delete(paymentId);
    }
}