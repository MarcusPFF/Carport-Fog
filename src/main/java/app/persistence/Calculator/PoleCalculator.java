package app.persistence.Calculator;

import app.entities.forCalculator.WoodForCalculator;
import app.exceptions.DatabaseException;
import app.persistence.connection.ConnectionPool;
import app.persistence.mappers.OfferMapper;

public class PoleCalculator {
    private OfferMapper offerMapper;

    public WoodForCalculator poleCalculator(ConnectionPool connection, int carportLengthInCm, int carportWidthInCm, int shedLengthInCm, int shedWidthInCm, int carportHeightInCm, int poleWidthInMm, int poleHeightInMm, String woodTypeName, String treatmentName) throws DatabaseException {
        int lengthAmount = poleAmountXCalculator(carportLengthInCm);
        int widthAmount = poleAmountYCalculator(carportWidthInCm);
        int shedAmount = shedPoleAmountCalculator(carportLengthInCm, carportWidthInCm, shedLengthInCm, shedWidthInCm, lengthAmount, widthAmount);

        int amount = (lengthAmount * widthAmount) + shedAmount;
        int digDepthOfPoleInCm = 90;
        int poleLengthInCm = digDepthOfPoleInCm + carportHeightInCm;

        int woodDimensionId = offerMapper.getWoodDimensionIdFromLengthWidthHeight(connection, poleLengthInCm, poleWidthInMm, poleHeightInMm);
        int treatmentId = offerMapper.getTreatmentIdFromTreatmentName(connection, treatmentName);
        int woodTypeId = offerMapper.getWoodTypeIdFromWoodTypeName(connection, woodTypeName);

        return new WoodForCalculator("Stolper", amount, woodDimensionId, treatmentId, woodTypeId);
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
