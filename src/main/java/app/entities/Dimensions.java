package app.entities;

public class Dimensions {
    private int dimensionId;
    private float width;
    private float height;
    private float length;
    private float dimensionsMeterPrice;

    public Dimensions(int dimensionId, float width, float height, float length, float dimensionsMeterPrice) {
        this.dimensionId = dimensionId;
        this.width = width;
        this.height = height;
        this.length = length;
        this.dimensionsMeterPrice = dimensionsMeterPrice;
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

    public float getDimensionsMeterPrice() {
        return dimensionsMeterPrice;
    }

    public void setDimensionsMeterPrice(float dimensionsMeterPrice) {
        this.dimensionsMeterPrice = dimensionsMeterPrice;
    }

    @Override
    public String toString() {
        return "Dimensions{" +
                "dimensionId=" + dimensionId +
                ", width=" + width +
                ", height=" + height +
                ", length=" + length +
                ", dimensionsMeterPrice=" + dimensionsMeterPrice +
                '}';
    }
}
