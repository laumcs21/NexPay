package Proyecto.Nexpay.Model;

public class Budget {

    private String userId;
    private String id;
    private String name;
    private double amount;
    private String categoryId;
    private double spentAmount;

    public Budget(String userId, String id, String name, double amount, String categoryId) {
        this.userId = userId;
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.categoryId = categoryId;
        this.spentAmount = 0;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public double getSpentAmount() {
        return spentAmount;
    }

    public void setSpentAmount(double spentAmount) {
        this.spentAmount = spentAmount;
    }
}
