package app.persistence.Calculator;

import app.entities.forCalculator.WoodForCalculator;

public class NoggingCalculator {

    public WoodForCalculator noggingCalculator(int carportLengthInCM, int carportWidthInCM) {
        int noggingAmount = noggingAmountCalculator(carportWidthInCM);
        int totalRafterLengthInCM = totalNoggingLengthCalculator(noggingAmount, carportLengthInCM);
        return new WoodForCalculator("Regler", noggingAmount, totalRafterLengthInCM, 45, 95);
    }

    public int noggingAmountCalculator(int carportWidthInCM) {
        int amount = 1;
        amount = amount + (int) Math.ceil ((carportWidthInCM - 70.0)/600.0);
        return amount;
    }

    public int totalNoggingLengthCalculator(int noggingAmount, int carportLengthInCM) {
        int totalNoggingLengthInCm;
        totalNoggingLengthInCm = noggingAmount * carportLengthInCM;
        return totalNoggingLengthInCm;
    }

}
