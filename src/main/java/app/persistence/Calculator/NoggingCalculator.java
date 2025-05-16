package app.persistence.Calculator;

import app.entities.forCalculator.WoodForCalculator;
import app.exceptions.DatabaseException;
import app.persistence.connection.ConnectionPool;
import app.persistence.mappers.OfferMapper;

public class NoggingCalculator {
    private OfferMapper offerMapper;
    private PoleCalculator poleCalculator;

    //Pole = Stolpe
    //Nogging = Reglar
    //Rafter = Spær
    //RafterBeam = Rem
    //Fascia board = Understernbræt
    //Barge board = Oversternbræt
    //Drip cap = Vandbræt
    //PlumbersTape = Hulbånd

    public WoodForCalculator noggingForShedFrontAndBackCalculator(ConnectionPool connection, int rafterWidthInMm, int rafterHeightInMm, int carportLength, int carportWidthInCm, int shedWidthInCm, String woodTypeName, String treatmentName) throws DatabaseException {
        int poleAmountForWidth;
        int noggingAmount;
        int woodDimensionId;
        int treatmentId;
        int woodTypeId;

        poleAmountForWidth = poleCalculator.poleAmountYCalculator(carportWidthInCm);
        noggingAmount = noggingForShedFrontAndBackAmountCalculator(shedWidthInCm, carportLength, poleAmountForWidth);
        woodDimensionId = offerMapper.getWoodDimensionIdFromLengthWidthHeight(connection, shedWidthInCm, rafterWidthInMm, rafterHeightInMm);
        treatmentId = offerMapper.getTreatmentIdFromTreatmentName(connection, treatmentName);
        woodTypeId = offerMapper.getWoodTypeIdFromWoodTypeName(connection, woodTypeName);

        return new WoodForCalculator(woodTypeName, noggingAmount, woodDimensionId, treatmentId, woodTypeId, "løsholter til forside og bagside af skur.");
    }

    public WoodForCalculator noggingForShedSidesCalculator(ConnectionPool connection, int rafterWidthInMm, int rafterHeightInMm, int carportLengthInCm, int carportWidthInCm, int shedLengthInCm, int shedWidthInCm, String woodTypeName, String treatmentName) throws DatabaseException {
        int poleAmountForWidth;
        int poleAmountForLength;
        int noggingAmount;
        int woodDimensionId;
        int treatmentId;
        int woodTypeId;

        poleAmountForLength = poleCalculator.poleAmountYCalculator(carportLengthInCm);
        poleAmountForWidth = poleCalculator.poleAmountYCalculator(carportWidthInCm);
        noggingAmount = noggingForShedSidesAmountCalculator(shedLengthInCm, shedWidthInCm, carportLengthInCm, carportWidthInCm, poleAmountForLength, poleAmountForWidth);
        woodDimensionId = offerMapper.getWoodDimensionIdFromLengthWidthHeight(connection, shedLengthInCm, rafterWidthInMm, rafterHeightInMm);
        treatmentId = offerMapper.getTreatmentIdFromTreatmentName(connection, treatmentName);
        woodTypeId = offerMapper.getWoodTypeIdFromWoodTypeName(connection, woodTypeName);

        return new WoodForCalculator(woodTypeName, noggingAmount, woodDimensionId, treatmentId, woodTypeId, "løsholter til siderne af skur.");
    }

    public WoodForCalculator noggingForZOnTheDoorCalculator(ConnectionPool connection, int noggingWidthInMm, int noggingHeightInMm, int noggingLengthInCm, String woodTypeName, String treatmentName, int amountOfDoorsForTheShed) throws DatabaseException {
        int noggingAmount;
        int woodDimensionId;
        int treatmentId;
        int woodTypeId;

        noggingAmount = noggingForZOnTheDoorAmountCalculator(amountOfDoorsForTheShed);
        woodDimensionId = offerMapper.getWoodDimensionIdFromLengthWidthHeight(connection, noggingLengthInCm, noggingWidthInMm, noggingHeightInMm);
        treatmentId = offerMapper.getTreatmentIdFromTreatmentName(connection, treatmentName);
        woodTypeId = offerMapper.getWoodTypeIdFromWoodTypeName(connection, woodTypeName);

        return new WoodForCalculator(woodTypeName, noggingAmount, woodDimensionId, treatmentId, woodTypeId, "til z på bagside af dør.");
    }

    public int noggingForShedFrontAndBackAmountCalculator(int shedWidthInCm, int carportWidthInCm, int poleAmountForWidth) {

        if (poleAmountForWidth > 2) {

            if (shedWidthInCm == carportWidthInCm - 70) {
                return 24;
            }
            else if (shedWidthInCm > carportWidthInCm - 70 / (poleAmountForWidth - 1)) {
                return 18;
            }
        }
        else if (shedWidthInCm == carportWidthInCm - 70 / (poleAmountForWidth - 1)) {
            return 12;
        }

        return 6;
    }

    public int noggingForShedSidesAmountCalculator(int shedLengthInCm, int shedWidthInCm, int carportLengthInCm, int carportWidthInCm, int poleAmountForLength, int poleAmountForWidth) {

        if (shedLengthInCm > carportLengthInCm - 70 / (poleAmountForLength - 1)) {

            if (shedWidthInCm == carportWidthInCm - 70 / (poleAmountForWidth - 1)) {
                return 8;
            }
            else {
                return 10;
            }
        }
        else if (shedWidthInCm == carportWidthInCm - 70 / (poleAmountForWidth - 1)) {
            return 4;
        }

        return 5;
    }

    public int noggingForZOnTheDoorAmountCalculator(int amountOfDoorsForTheShed) {
        int amount;
        amount = amountOfDoorsForTheShed;

        return amount;
    }
}
