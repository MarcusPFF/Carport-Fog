package app.entities;

public class Wood {
    private WoodType woodType;
    private Dimensions dimensions;
    private Treatment treatment;

    public Wood(WoodType woodType, Dimensions dimensions, Treatment treatment) {
        this.woodType = woodType;
        this.dimensions = dimensions;
        this.treatment = treatment;
    }

    public WoodType getWoodType() {
        return woodType;
    }

    public void setWoodType(WoodType woodType) {
        this.woodType = woodType;
    }

    public Dimensions getDimensions() {
        return dimensions;
    }

    public void setDimensions(Dimensions dimensions) {
        this.dimensions = dimensions;
    }

    public Treatment getTreatment() {
        return treatment;
    }

    public void setTreatment(Treatment treatment) {
        this.treatment = treatment;
    }

    @Override
    public String toString() {
        return "Wood{" +
                "woodType=" + woodType +
                ", dimensions=" + dimensions +
                ", treatment=" + treatment +
                '}';
    }
}
