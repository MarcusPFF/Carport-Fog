package app.entities;

public class WoodType {
    private int woodId;
    private String woodType;
    private float woodTypeMeterPrice;

    public WoodType(int woodId, String woodType, float woodTypeMeterPrice) {
        this.woodId = woodId;
        this.woodType = woodType;
        this.woodTypeMeterPrice = woodTypeMeterPrice;
    }

    public int getWoodId() {
        return woodId;
    }

    public void setWoodId(int woodId) {
        this.woodId = woodId;
    }

    public String getWoodType() {
        return woodType;
    }

    public void setWoodType(String woodType) {
        this.woodType = woodType;
    }

    public float getWoodTypeMeterPrice() {
        return woodTypeMeterPrice;
    }

    public void setWoodTypeMeterPrice(float woodTypeMeterPrice) {
        this.woodTypeMeterPrice = woodTypeMeterPrice;
    }

    @Override
    public String toString() {
        return "WoodType{" +
                "woodId=" + woodId +
                ", woodType='" + woodType + '\'' +
                ", woodTypeMeterPrice=" + woodTypeMeterPrice +
                '}';
    }
}
