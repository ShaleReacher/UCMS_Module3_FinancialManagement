package cz.mendelu;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private TransactionService transactionService = new TransactionServiceImpl();

    @PostMapping
    public ResponseEntity<?> createTransaction(@RequestBody Transaction transaction) {
        try {
            // Validation
            if (transaction.getPaymentId() <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse(400, "Payment ID must be greater than 0"));
            }

            if (transaction.getTransactionType() == null || transaction.getTransactionType().trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse(400, "Transaction type cannot be empty"));
            }

            if (transaction.getAmount() <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse(400, "Amount must be greater than 0"));
            }

            Transaction savedTransaction = transactionService.saveTransaction(transaction);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(convertToDTO(savedTransaction));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Error creating transaction: " + e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllTransactions() {
        try {
            List<Transaction> transactions = transactionService.getAllTransactions();
            List<TransactionDTO> transactionDTOs = transactions.stream()
                    .map(this::convertToDTO)
                    .toList();
            return ResponseEntity.status(HttpStatus.OK).body(transactionDTOs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Error fetching transactions: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTransactionById(@PathVariable int id) {
        try {
            if (id < 1) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse(400, "Transaction ID must be greater than or equal to 1"));
            }

            Transaction transaction = transactionService.getTransactionById(id);
            if (transaction == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse(404, "Transaction with ID " + id + " not found"));
            }

            return ResponseEntity.status(HttpStatus.OK).body(convertToDTO(transaction));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Error fetching transaction: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTransaction(@PathVariable int id, @RequestBody Transaction transaction) {
        try {
            if (id < 1) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse(400, "Transaction ID must be greater than or equal to 1"));
            }

            if (transaction.getPaymentId() <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse(400, "Payment ID must be greater than 0"));
            }

            if (transaction.getTransactionType() == null || transaction.getTransactionType().trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse(400, "Transaction type cannot be empty"));
            }

            if (transaction.getAmount() <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse(400, "Amount must be greater than 0"));
            }

            transaction.setTransactionId(id);
            transactionService.updateTransaction(transaction);

            return ResponseEntity.status(HttpStatus.OK).body(convertToDTO(transaction));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Error updating transaction: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTransaction(@PathVariable int id) {
        try {
            if (id < 1) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse(400, "Transaction ID must be greater than or equal to 1"));
            }

            transactionService.deleteTransaction(id);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ErrorResponse(200, "Transaction " + id + " deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Error deleting transaction: " + e.getMessage()));
        }
    }

    private TransactionDTO convertToDTO(Transaction transaction) {
        return new TransactionDTO(
                transaction.getTransactionId(),
                transaction.getPaymentId(),
                transaction.getTransactionType(),
                transaction.getAmount(),
                transaction.getDescription(),
                transaction.getTransactionDate(),
                transaction.getStatus()
        );
    }
}