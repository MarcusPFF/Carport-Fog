package app.entities;

public class Dimensions {
    private int dimensionId;
    private float width;
    private float height;
    private float length;
    private float meterPrice;

    public Dimensions(int dimensionId, float width, float height, float length, float meterPrice) {
        this.dimensionId = dimensionId;
        this.width = width;
        this.height = height;
        this.length = length;
        this.meterPrice = meterPrice;
    }

    public int getDimensionId() {
        return dimensionId;
    }

    public void setDimensionId(int dimensionId) {
        this.dimensionId = dimensionId;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public float getMeterPrice() {
        return meterPrice;
    }

    public void setMeterPrice(float meterPrice) {
        this.meterPrice = meterPrice;
    }

    @Override
    public String toString() {
        return "Dimensions{" +
                "dimensionId=" + dimensionId +
                ", width=" + width +
                ", height=" + height +
                ", length=" + length +
                ", meterPrice=" + meterPrice +
                '}';
    }
}
