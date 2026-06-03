package cz.mendelu;

public class Fee {

    private int feeId;
    private String feeType;          // "Membership", "Event", "Activity"
    private double amount;           // €50, €5, €20
    private String description;      // "Annual membership", "Concert ticket"
    private String status;           // "paid" or "unpaid"
    private String createdDate;      // "2026-04-04"

    // Constructor
    public Fee(int feeId, String feeType, double amount, String description, String status, String createdDate) {
        this.feeId = feeId;
        this.feeType = feeType;
        this.amount = amount;
        this.description = description;
        this.status = status;
        this.createdDate = createdDate;
    }

    // Getters
    public int getFeeId() {
        return feeId;
    }

    public String getFeeType() {
        return feeType;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    // Setters
    public void setFeeId(int feeId) {
        this.feeId = feeId;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    // toString (for printing)
    @Override
    public String toString() {
        return "Fee{" +
                "feeId=" + feeId +
                ", feeType='" + feeType + '\'' +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", createdDate='" + createdDate + '\'' +
                '}';
    }
}
