package cz.mendelu;

public class FeeDTO {
    private int id;
    private double amount;
    private String feeType;
    private String description;

    // Constructor
    public FeeDTO(int id, double amount, String feeType, String description) {
        this.id = id;
        this.amount = amount;
        this.feeType = feeType;
        this.description = description;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}