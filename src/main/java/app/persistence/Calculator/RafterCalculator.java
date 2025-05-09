package app.persistence.Calculator;

import app.entities.forCalculator.WoodForCalculator;

public class RafterCalculator {

    public WoodForCalculator rafterForRoofCalculator(int carportLengthInCm, int carportWidthInCm) {
        int rafterAmount = rafterAmountForRoofCalculator(carportLengthInCm);
        int rafterWidthInMm = totalRafterWidthInMmCalculator(carportWidthInCm);
        int rafterHeightInMm = totalRafterHeightInMmCalculator(carportWidthInCm);
        return new WoodForCalculator("monteres på rem.", rafterAmount, carportWidthInCm, rafterWidthInMm, rafterHeightInMm, 2, woodType);
    }

    public WoodForCalculator rafterForShedWidthCalculator(int shedWidthInCm) {
        int rafterAmount = 2;
        int rafterWidthInMm = totalRafterWidthInMmCalculator(shedWidthInCm);
        int rafterHeightInMm = totalRafterHeightInMmCalculator(shedWidthInCm);
        return new WoodForCalculator("til bagside og forside af skur.", rafterAmount, shedWidthInCm, rafterWidthInMm, rafterHeightInMm, 2, 1);
    }

    public WoodForCalculator rafterForShedLengthCalculator(int shedLengthInCm) {
        int rafterAmount = 2;
        int rafterWidthInMm = totalRafterWidthInMmCalculator(shedLengthInCm);
        int rafterHeightInMm = totalRafterHeightInMmCalculator(shedLengthInCm);
        return new WoodForCalculator("til siderne på skur.", rafterAmount, shedLengthInCm, rafterWidthInMm, rafterHeightInMm);
    }

    public int rafterAmountForRoofCalculator(int carportLengthInCM) {
        int amount = 1;
        amount = amount + (int) Math.ceil (carportLengthInCM/60.0);
        return amount;
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
