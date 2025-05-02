package app.entities;

public class Roof {
    private int roofId;
    private String roofType;
    private float roofPrice;

    public Roof(int roofId, String roofType, float roofPrice) {
        this.roofId = roofId;
        this.roofType = roofType;
        this.roofPrice = roofPrice;
    }

    public int getRoofId() {
        return roofId;
    }

    public void setRoofId(int roofId) {
        this.roofId = roofId;
    }

    public String getRoofType() {
        return roofType;
    }

    public void setRoofType(String roofType) {
        this.roofType = roofType;
    }

    public float getRoofPrice() {
        return roofPrice;
    }

    public void setRoofPrice(float roofPrice) {
        this.roofPrice = roofPrice;
    }

    @Override
    public String toString() {
        return "Roof{" +
                "roofId=" + roofId +
                ", roofType='" + roofType + '\'' +
                ", roofPrice=" + roofPrice +
                '}';
    }
}
