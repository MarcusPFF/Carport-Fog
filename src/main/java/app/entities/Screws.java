package app.entities;

public class Screws {
    private int screwsId;
    private int amount;
    private float screwPrice;
    private String screwType;

    public Screws(int screwsId, int amount, float screwPrice, String screwType) {
        this.screwsId = screwsId;
        this.amount = amount;
        this.screwPrice = screwPrice;
        this.screwType = screwType;
    }

    public int getScrewsId() {
        return screwsId;
    }

    public void setScrewsId(int screwsId) {
        this.screwsId = screwsId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public float getScrewPrice() {
        return screwPrice;
    }

    public void setScrewPrice(float screwPrice) {
        this.screwPrice = screwPrice;
    }

    public String getScrewType() {
        return screwType;
    }

    public void setScrewType(String screwType) {
        this.screwType = screwType;
    }

    @Override
    public String toString() {
        return "Screws{" +
                "screwsId=" + screwsId +
                ", amount=" + amount +
                ", screwPrice=" + screwPrice +
                ", screwType='" + screwType + '\'' +
                '}';
    }
}
