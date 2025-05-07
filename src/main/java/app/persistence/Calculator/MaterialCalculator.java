package app.persistence.Calculator;

import java.util.ArrayList;

public class MaterialCalculator {
    private String name;
    private int amount;
    private int totalLengthInCm;
    private PoleCalculator poleCalculator;

    private static ArrayList<MaterialCalculator> materialsList;

    public MaterialCalculator(String name, int amount, int totalLengthInCm) {
        this.name = name;
        this.amount = amount;
        this.totalLengthInCm = totalLengthInCm;
    }


    public ArrayList<MaterialCalculator> materialCalculator(int carportLength, int carportWidth, int shedLength, int shedWidth) {
        materialsList = new ArrayList<>();
        materialsList.add(poleCalculator.poleAmountCalculator(carportLength, carportWidth, shedLength, shedWidth));
        return materialsList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getTotalLengthInCm() {
        return totalLengthInCm;
    }

    public void setTotalLengthInCm(int totalLengthInCm) {
        this.totalLengthInCm = totalLengthInCm;
    }

    public PoleCalculator getPoleCalculator() {
        return poleCalculator;
    }

    public void setPoleCalculator(PoleCalculator poleCalculator) {
        this.poleCalculator = poleCalculator;
    }

    public static ArrayList<MaterialCalculator> getMaterialsList() {
        return materialsList;
    }

    public static void setMaterialsList(ArrayList<MaterialCalculator> materialsList) {
        MaterialCalculator.materialsList = materialsList;
    }
}
