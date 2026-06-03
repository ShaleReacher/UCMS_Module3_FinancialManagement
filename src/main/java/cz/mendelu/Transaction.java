package cz.mendelu;

public class Transaction {

    private int transactionId;
    private int paymentId;            // Which payment does this belong to?
    private String transactionType;   // "fee_payment", "refund", "adjustment"
    private double amount;            // €50, €-5 (refund)
    private String description;       // "Payment received", "Refund issued"
    private String transactionDate;   // "2026-04-04"
    private String status;            // "completed", "pending", "cancelled"

    // Constructor
    public Transaction(int transactionId, int paymentId, String transactionType, double amount, String description, String transactionDate, String status) {
        this.transactionId = transactionId;
        this.paymentId = paymentId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.description = description;
        this.transactionDate = transactionDate;
        this.status = status;
    }

    // Getters
    public int getTransactionId() {
        return transactionId;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public String getStatus() {
        return status;
    }

    // Setters
    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // toString (for printing)
    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", paymentId=" + paymentId +
                ", transactionType='" + transactionType + '\'' +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", transactionDate='" + transactionDate + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
