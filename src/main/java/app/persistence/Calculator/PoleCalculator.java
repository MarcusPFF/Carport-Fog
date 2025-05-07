package app.persistence.Calculator;

public class PoleCalculator {

    public MaterialCalculator poleAmountCalculator(int carportLength, int carportWidth, int shedLength, int shedWidth) {
        int lengthAmount = poleAmountX(carportLength);
        int widthAmount = poleAmountY(carportWidth);
        int shedAmount = shedPoleAmount(carportLength, carportWidth, shedLength, shedWidth, lengthAmount, widthAmount);
        int amount = (lengthAmount * widthAmount) + shedAmount;
        return new MaterialCalculator("Stolpe", amount, amount*300);
    }

    public int poleAmountX(int carportLength) {
        int LengthBetweenFirstAndLastPole = carportLength - 130;
        int amount = 2;
        while (true){
            if(LengthBetweenFirstAndLastPole/amount<=300){
                return amount;
            }
            amount++;
        }
    }

    public int poleAmountY(int carportWidth) {
        int LengthBetweenRightAndLeftPole = carportWidth - 70;
        int amount = 2;
        while (true){
            if(LengthBetweenRightAndLeftPole/amount<=700){
                return amount;
            }
            amount++;
        }
    }

    public int shedPoleAmount(int carportLength, int carportWidth, int shedLength, int shedWidth, int lengthAmount, int widthAmount) {
        if ((carportLength - 130)/(lengthAmount - 1) == shedLength && (carportWidth - 70)/(widthAmount - 1) != shedWidth){
            return 3;
        }
        if ((carportLength - 130)/(lengthAmount - 1) != shedLength && (carportWidth - 70)/(widthAmount - 1) == shedWidth){
            return 5;
        }
        if (shedWidth == carportWidth - 70 && widthAmount > 2) {
            return 8;
        }
        return 4;
    }
}
