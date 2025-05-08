package app.entities.forCalculator;

import app.persistence.Calculator.PoleCalculator;
import app.persistence.Calculator.RafterCalculator;

import java.util.ArrayList;

public class WoodForCalculator {

    private String name;
    private int amount;
    private int totalLengthInCm;
    private int woodWidthInMm;
    private int woodHeightInMm;
    private PoleCalculator poleCalculator;
    private RafterCalculator rafterCalculator;

    private static ArrayList<WoodForCalculator> woodList;

    public WoodForCalculator(String name, int amount, int totalLengthInCm, int woodWidthInMm, int woodHeightInMm) {
        this.name = name;
        this.amount = amount;
        this.totalLengthInCm = totalLengthInCm;
        this.woodWidthInMm = woodWidthInMm;
        this.woodHeightInMm = woodHeightInMm;
    }


    public ArrayList<WoodForCalculator> woodCalculator(int carportLengthInCm, int carportWidthInCm, int shedLengthInCm, int shedWidthInCm) {
        woodList = new ArrayList<>();
        woodList.add(poleCalculator.poleCalculator(carportLengthInCm, carportWidthInCm, shedLengthInCm, shedWidthInCm));
        woodList.add(rafterCalculator.rafterCalculator(carportLengthInCm, carportWidthInCm, shedLengthInCm, shedWidthInCm));
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

    public int getWoodHeightInMm() {
        return woodHeightInMm;
    }

    public void setWoodHeightInMm(int woodHeightInMm) {
        this.woodHeightInMm = woodHeightInMm;
    }

    public int getWoodWidthInMm() {
        return woodWidthInMm;
    }

    public void setWoodWidthInMm(int woodWidthInMm) {
        this.woodWidthInMm = woodWidthInMm;
    }

    public static ArrayList<WoodForCalculator> getWoodList() {
        return woodList;
    }

    public static void setWoosList(ArrayList<WoodForCalculator> woodList) {
        WoodForCalculator.woodList = woodList;
    }
}
