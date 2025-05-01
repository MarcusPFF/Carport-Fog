package app.entities;

public class WoodType {
    private int woodId;
    private String woodType;
    private float meterPrice;

    public WoodType(int woodId, String woodType, float meterPrice) {
        this.woodId = woodId;
        this.woodType = woodType;
        this.meterPrice = meterPrice;
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

    public float getMeterPrice() {
        return meterPrice;
    }

    public void setMeterPrice(float meterPrice) {
        this.meterPrice = meterPrice;
    }

    @Override
    public String toString() {
        return "WoodType{" +
                "woodId=" + woodId +
                ", woodType='" + woodType + '\'' +
                ", meterPrice=" + meterPrice +
                '}';
    }
}
