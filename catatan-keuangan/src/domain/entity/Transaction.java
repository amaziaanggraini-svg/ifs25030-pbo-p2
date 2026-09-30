package domain.entity;

public class Transaction {
    private String id;
    private String description;
    private long amount;
    private String type;

    public Transaction(String id, String description, long amount, String type) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.type = type;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public long getAmount() { return amount; }
    public void setAmount(long amount) { this.amount = amount; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}