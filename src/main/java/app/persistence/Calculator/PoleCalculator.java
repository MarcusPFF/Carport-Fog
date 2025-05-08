package app.persistence.Calculator;

import app.entities.forCalculator.WoodForCalculator;

public class PoleCalculator {

    public WoodForCalculator poleAmountCalculator(int carportLengthInCM, int carportWidthInCM, int shedLengthInCM, int shedWidthInCM) {
        int lengthAmount = poleAmountXCalculator(carportLengthInCM);
        int widthAmount = poleAmountYCalculator(carportWidthInCM);
        int shedAmount = shedPoleAmountCalculator(carportLengthInCM, carportWidthInCM, shedLengthInCM, shedWidthInCM, lengthAmount, widthAmount);
        int amount = (lengthAmount * widthAmount) + shedAmount;
        return new WoodForCalculator("Stolper", amount, amount*300, 115, 115);
    }

    public int poleAmountXCalculator(int carportLengthInCM) {
        int LengthBetweenFirstAndLastPoleInCM = carportLengthInCM - 130;
        int amount = 2;
        while (true){
            if(LengthBetweenFirstAndLastPoleInCM/(amount - 1) <= 300){
                return amount;
            }
            amount++;
        }
    }

    public int poleAmountYCalculator(int carportWidthInCM) {
        int LengthBetweenRightAndLeftPoleInCM = carportWidthInCM - 70;
        int amount = 2;
        while (true){
            if(LengthBetweenRightAndLeftPoleInCM/(amount - 1) <= 600){
                return amount;
            }
            amount++;
        }
    }

    public int shedPoleAmountCalculator(int carportLengthInCM, int carportWidthInCM, int shedLengthInCM, int shedWidthInCM, int lengthAmount, int widthAmount) {
        if ((carportLengthInCM - 130)/(lengthAmount - 1) == shedLengthInCM && (carportWidthInCM - 70)/(widthAmount - 1) != shedWidthInCM){
            return 3;
        }
        if ((carportLengthInCM - 130)/(lengthAmount - 1) != shedLengthInCM && (carportWidthInCM - 70)/(widthAmount - 1) == shedWidthInCM){
            return 5;
        }
        if (shedWidthInCM == carportWidthInCM - 70 && widthAmount > 2) {
            return 8;
        }
        return 4;
    }
}
