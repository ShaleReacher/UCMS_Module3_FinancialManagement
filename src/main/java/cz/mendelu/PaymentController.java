package cz.mendelu;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private PaymentService paymentService = new PaymentServiceImpl();

    @PostMapping
    public ResponseEntity<?> createPayment(@RequestBody Payment payment) {
        try {
            // Validation
            if (payment.getFeeId() <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse(400, "Fee ID must be greater than 0"));
            }

            if (payment.getAmount() <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse(400, "Amount must be greater than 0"));
            }

            if (payment.getPaymentMethod() == null || payment.getPaymentMethod().trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse(400, "Payment method cannot be empty"));
            }

            Payment savedPayment = paymentService.savePayment(payment);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(convertToDTO(savedPayment));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Error creating payment: " + e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllPayments() {
        try {
            List<Payment> payments = paymentService.getAllPayments();
            List<PaymentDTO> paymentDTOs = payments.stream()
                    .map(this::convertToDTO)
                    .toList();
            return ResponseEntity.status(HttpStatus.OK).body(paymentDTOs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Error fetching payments: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPaymentById(@PathVariable int id) {
        try {
            if (id < 1) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse(400, "Payment ID must be greater than or equal to 1"));
            }

            Payment payment = paymentService.getPaymentById(id);
            if (payment == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse(404, "Payment with ID " + id + " not found"));
            }

            return ResponseEntity.status(HttpStatus.OK).body(convertToDTO(payment));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Error fetching payment: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePayment(@PathVariable int id, @RequestBody Payment payment) {
        try {
            if (id < 1) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse(400, "Payment ID must be greater than or equal to 1"));
            }

            if (payment.getFeeId() <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse(400, "Fee ID must be greater than 0"));
            }

            if (payment.getAmount() <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse(400, "Amount must be greater than 0"));
            }

            if (payment.getPaymentMethod() == null || payment.getPaymentMethod().trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse(400, "Payment method cannot be empty"));
            }

            payment.setPaymentId(id);
            paymentService.updatePayment(payment);

            return ResponseEntity.status(HttpStatus.OK).body(convertToDTO(payment));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Error updating payment: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePayment(@PathVariable int id) {
        try {
            if (id < 1) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse(400, "Payment ID must be greater than or equal to 1"));
            }

            paymentService.deletePayment(id);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ErrorResponse(200, "Payment " + id + " deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Error deleting payment: " + e.getMessage()));
        }
    }

    private PaymentDTO convertToDTO(Payment payment) {
        return new PaymentDTO(
                payment.getPaymentId(),
                payment.getFeeId(),
                payment.getAmount(),
                payment.getPaymentDate(),
                payment.getPaymentMethod(),
                payment.getTransactionId(),
                payment.getStatus()
        );
    }
}