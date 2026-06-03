package cz.mendelu;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fees")
public class FeeController {

    private FeeService feeService = new FeeServiceImpl();

    @PostMapping
    public ResponseEntity<?> createFee(@RequestBody Fee fee) {
        try {
            // Validation: feeType should not be empty
            if (fee.getFeeType() == null || fee.getFeeType().trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse(400, "Fee type cannot be empty"));
            }

            // Validation: amount must be greater than 0
            if (fee.getAmount() <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse(400, "Amount must be greater than 0"));
            }

            Fee savedFee = feeService.saveFee(fee);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(convertToDTO(savedFee));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Error creating fee: " + e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllFees() {
        try {
            List<Fee> fees = feeService.getAllFees();
            List<FeeDTO> feeDTOs = fees.stream()
                    .map(this::convertToDTO)
                    .toList();
            return ResponseEntity.status(HttpStatus.OK).body(feeDTOs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Error fetching fees: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFeeById(@PathVariable int id) {
        try {
            // Only validate that id is positive (database auto-generates starting from 1)
            if (id < 1) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse(400, "Fee ID must be greater than or equal to 1"));
            }

            Fee fee = feeService.getFeeById(id);
            if (fee == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse(404, "Fee with ID " + id + " not found"));
            }

            return ResponseEntity.status(HttpStatus.OK).body(convertToDTO(fee));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Error fetching fee: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateFee(@PathVariable int id, @RequestBody Fee fee) {
        try {
            // Validation
            if (id < 1) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse(400, "Fee ID must be greater than or equal to 1"));
            }

            if (fee.getFeeType() == null || fee.getFeeType().trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse(400, "Fee type cannot be empty"));
            }

            if (fee.getAmount() <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse(400, "Amount must be greater than 0"));
            }

            fee.setFeeId(id);
            feeService.updateFee(fee);

            return ResponseEntity.status(HttpStatus.OK).body(convertToDTO(fee));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Error updating fee: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFee(@PathVariable int id) {
        try {
            // Validation
            if (id < 1) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse(400, "Fee ID must be greater than or equal to 1"));
            }

            feeService.deleteFee(id);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ErrorResponse(200, "Fee " + id + " deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Error deleting fee: " + e.getMessage()));
        }
    }

    // Helper method to convert Fee entity to FeeDTO
    private FeeDTO convertToDTO(Fee fee) {
        return new FeeDTO(
                fee.getFeeId(),
                fee.getAmount(),
                fee.getFeeType(),
                fee.getDescription()
        );
    }
}