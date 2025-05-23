package app.entities;

public class Material {
    private String materialName;
    private int materialAmount;

    public Material(String materialName, int materialAmount) {
        this.materialName = materialName;
        this.materialAmount = materialAmount;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public int getMaterialAmount() {
        return materialAmount;
    }

    public void setMaterialAmount(int materialAmount) {
        this.materialAmount = materialAmount;
    }
}
