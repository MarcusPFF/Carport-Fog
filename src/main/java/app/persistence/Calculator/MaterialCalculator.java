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

}
