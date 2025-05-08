package app.persistence.Calculator;

import app.entities.forCalculator.WoodForCalculator;

public class RafterCalculator {

    public WoodForCalculator rafterCalculator(int carportLengthInCM, int carportWidthInCM, int shedLengthInCM, int shedWidthInCm) {
        int rafterAmount = rafterAmountCalculator(carportLengthInCM);
        int totalRafterLengthInCM = totalRafterLengthCalculator(rafterAmount, carportWidthInCM, shedLengthInCM, shedWidthInCm);
        int rafterWidthInMm = totalRafterWidthInMmCalculator(carportWidthInCM);
        int rafterHeightInMm = totalRafterHeightInMmCalculator(carportWidthInCM);
        return new WoodForCalculator("Spær", rafterAmount, totalRafterLengthInCM, rafterWidthInMm, rafterHeightInMm);
    }

    public int rafterAmountCalculator(int carportLengthInCM) {
        int amount = 1;
        amount = amount + (int) Math.ceil (carportLengthInCM/60.0);
        return amount;
    }

    public int totalRafterLengthCalculator(int rafterAmount, int carportWidthInCM, int shedLengthInCM, int shedWidthInCm) {
        int totalRafterLengthInCm;
        totalRafterLengthInCm = (rafterAmount * carportWidthInCM) + (shedLengthInCM * 2) + (shedWidthInCm * 2);
        return totalRafterLengthInCm;
    }

    public int totalRafterHeightInMmCalculator( int carportWidthInCM) {
        int woodHeightInMm;
        if ((carportWidthInCM - 70)  < 300) {
        woodHeightInMm = 145;
        }
        else if ((carportWidthInCM - 70)  < 400) {
            woodHeightInMm = 195;
        }
        else if ((carportWidthInCM - 70)  < 500) {
            woodHeightInMm = 225;
        }
        else {
            woodHeightInMm = 245;
        }
        return woodHeightInMm;
    }

    public int totalRafterWidthInMmCalculator( int carportWidthInCM) {
        int woodWidthInMm;
        if ((carportWidthInCM - 70) <= 500) {
            woodWidthInMm = 45;
        }
        else {
            woodWidthInMm = 60;
        }
        return woodWidthInMm;
    }

}
