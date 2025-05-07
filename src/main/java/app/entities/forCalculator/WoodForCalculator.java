package app.entities.forCalculator;

import app.persistence.Calculator.PoleCalculator;

import java.util.ArrayList;

public class WoodForCalculator {

    private String name;
    private int amount;
    private int totalLengthInCm;
    private PoleCalculator poleCalculator;

    private static ArrayList<WoodForCalculator> woodList;

    public WoodForCalculator(String name, int amount, int totalLengthInCm) {
        this.name = name;
        this.amount = amount;
        this.totalLengthInCm = totalLengthInCm;
    }


    public ArrayList<WoodForCalculator> woodCalculator(int carportLength, int carportWidth, int shedLength, int shedWidth) {
        woodList = new ArrayList<>();
        woodList.add(poleCalculator.poleAmountCalculator(carportLength, carportWidth, shedLength, shedWidth));
        return woodList;
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


    public static ArrayList<WoodForCalculator> getWoodList() {
        return woodList;
    }

    public static void setWoosList(ArrayList<WoodForCalculator> woodList) {
        WoodForCalculator.woodList = woodList;
    }
}
