package app.entities;

public class Treatment {
    private int treatmentId;
    private String treatmentType;
    private float treatmentMeterPrice;

    public Treatment(int treatmentId, String treatmentType, float treatmentMeterPrice) {
        this.treatmentId = treatmentId;
        this.treatmentType = treatmentType;
        this.treatmentMeterPrice = treatmentMeterPrice;
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

    public float getTreatmentMeterPrice() {
        return treatmentMeterPrice;
    }

    public void setTreatmentMeterPrice(float treatmentMeterPrice) {
        this.treatmentMeterPrice = treatmentMeterPrice;
    }

    @Override
    public String toString() {
        return "Treatment{" +
                "treatmentId=" + treatmentId +
                ", treatmentType='" + treatmentType + '\'' +
                ", treatmentMeterPrice=" + treatmentMeterPrice +
                '}';
    }
}
