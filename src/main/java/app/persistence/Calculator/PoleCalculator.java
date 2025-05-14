package app.persistence.Calculator;

import app.entities.forCalculator.WoodForCalculator;
import app.exceptions.DatabaseException;
import app.persistence.connection.ConnectionPool;
import app.persistence.mappers.OfferMapper;

public class PoleCalculator {
    private OfferMapper offerMapper;

    public WoodForCalculator poleCalculator(ConnectionPool connection, int carportLengthInCm, int carportWidthInCm, int shedLengthInCm, int shedWidthInCm, int carportHeightInCm, int poleWidthInMm, int poleHeightInMm, String woodTypeName, String treatmentName) throws DatabaseException {
        int amount;
        int lengthAmount;
        int widthAmount;
        int shedAmount;
        int digDepthOfPoleInCm;
        int poleLengthInCm;
        int woodDimensionId;
        int treatmentId;
        int woodTypeId;

        lengthAmount = poleAmountXCalculator(carportLengthInCm);
        widthAmount = poleAmountYCalculator(carportWidthInCm);
        shedAmount = shedPoleAmountCalculator(carportLengthInCm, carportWidthInCm, shedLengthInCm, shedWidthInCm, lengthAmount, widthAmount);

        amount = (lengthAmount * widthAmount) + shedAmount;
        digDepthOfPoleInCm = 90;
        poleLengthInCm = digDepthOfPoleInCm + carportHeightInCm;

        woodDimensionId = offerMapper.getWoodDimensionIdFromLengthWidthHeight(connection, poleLengthInCm, poleWidthInMm, poleHeightInMm);
        treatmentId = offerMapper.getTreatmentIdFromTreatmentName(connection, treatmentName);
        woodTypeId = offerMapper.getWoodTypeIdFromWoodTypeName(connection, woodTypeName);

        return new WoodForCalculator(woodTypeName, amount, woodDimensionId, treatmentId, woodTypeId, "Stolper nedgraves 90-100 cm. i jord");
    }

    public int poleAmountXCalculator(int carportLengthInCM) {
        int amount;
        int LengthBetweenFirstAndLastPoleInCM;

        LengthBetweenFirstAndLastPoleInCM = carportLengthInCM - 130;
        amount = 2;

        while (true) {
            if (LengthBetweenFirstAndLastPoleInCM / (amount - 1) <= 400) {
                return amount;
            }
            amount++;
        }
    }

    public int poleAmountYCalculator(int carportWidthInCM) {
        int lengthBetweenRightAndLeftPoleInCM;
        int amount;

        lengthBetweenRightAndLeftPoleInCM = carportWidthInCM - 70;
        amount = 2;

        while (true) {
            if (lengthBetweenRightAndLeftPoleInCM / (amount - 1) <= 600) {
                return amount;
            }
            amount++;
        }
    }

    public int shedPoleAmountCalculator(int carportLengthInCM, int carportWidthInCM, int shedLengthInCM, int shedWidthInCM, int lengthAmount, int widthAmount) {
        int betweenPolesWidthInCm;
        int betweenPolesLengthInCm;
        int shedPolesYAmount;
        int shedPolesXAmount;
        int totalShedPoles;

        betweenPolesWidthInCm = (carportWidthInCM - 70) / (widthAmount - 1);
        betweenPolesLengthInCm = (carportLengthInCM - 130) / (lengthAmount - 1);
        shedPolesYAmount = 1;
        shedPolesXAmount = 0;
        totalShedPoles = 1;

        while (shedWidthInCM > betweenPolesWidthInCm) {
            shedPolesYAmount++;
            betweenPolesWidthInCm += betweenPolesWidthInCm;
        }

        while (shedLengthInCM > betweenPolesLengthInCm) {
            shedPolesXAmount++;
            betweenPolesLengthInCm += betweenPolesLengthInCm;
        }

        if (shedLengthInCM == betweenPolesLengthInCm && shedWidthInCM == betweenPolesWidthInCm) {
            shedPolesXAmount = 0;
        }

        else if (shedWidthInCM != betweenPolesWidthInCm && shedLengthInCM != betweenPolesLengthInCm) {
            shedPolesXAmount = shedPolesXAmount + shedPolesYAmount;
        }

        else if (shedLengthInCM != betweenPolesLengthInCm) {
            shedPolesXAmount = shedPolesYAmount + 1;
        }

        shedPolesYAmount = shedPolesYAmount * 2;
        totalShedPoles = totalShedPoles + shedPolesXAmount + shedPolesYAmount;

        return totalShedPoles;
    }

}