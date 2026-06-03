package cz.mendelu;

public class Payment {

    private int paymentId;
    private int feeId;              // Which fee did they pay for?
    private double amount;          // How much? €50, €20
    private String paymentDate;     // "2026-04-04"
    private String paymentMethod;   // "cash", "card", "transfer"
    private String transactionId;   // Link to transaction
    private String status;          // "pending", "completed", "failed"

    // Constructor
    public Payment(int paymentId, int feeId, double amount, String paymentDate, String paymentMethod, String transactionId, String status) {
        this.paymentId = paymentId;
        this.feeId = feeId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
        this.transactionId = transactionId;
        this.status = status;
    }

    // Getters
    public int getPaymentId() {
        return paymentId;
    }

    public int getFeeId() {
        return feeId;
    }

    public double getAmount() {
        return amount;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getStatus() {
        return status;
    }

    // Setters
    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public void setFeeId(int feeId) {
        this.feeId = feeId;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // toString (for printing)
    @Override
    public String toString() {
        return "Payment{" +
                "paymentId=" + paymentId +
                ", feeId=" + feeId +
                ", amount=" + amount +
                ", paymentDate='" + paymentDate + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}