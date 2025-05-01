package app.entities;

public class Treatment {
    private int treatmentId;
    private String treatmentType;
    private float treatmentPrice;

    public Treatment(int treatmentId, String treatmentType, float treatmentPrice) {
        this.treatmentId = treatmentId;
        this.treatmentType = treatmentType;
        this.treatmentPrice = treatmentPrice;
    }

    public int getTreatmentId() {
        return treatmentId;
    }

    public void setTreatmentId(int treatmentId) {
        this.treatmentId = treatmentId;
    }

    public String getTreatmentType() {
        return treatmentType;
    }

    public void setTreatmentType(String treatmentType) {
        this.treatmentType = treatmentType;
    }

    public float getTreatmentPrice() {
        return treatmentPrice;
    }

    public void setTreatmentPrice(float treatmentPrice) {
        this.treatmentPrice = treatmentPrice;
    }

    @Override
    public String toString() {
        return "Treatment{" +
                "treatmentId=" + treatmentId +
                ", treatmentType='" + treatmentType + '\'' +
                ", treatmentPrice=" + treatmentPrice +
                '}';
    }
}
