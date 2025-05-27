package app.persistence.calculator;

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

    public NoggingCalculator() {
        offerMapper = new OfferMapper();
        poleCalculator = new PoleCalculator();
    }

    public WoodForCalculator noggingForShedFrontAndBackCalculator(ConnectionPool connection, int noggingWidthInMm, int noggingHeightInMm, int shedWidthInCm, String woodTypeName, String treatmentName) throws DatabaseException {
        int noggingAmount;
        int woodDimensionId;
        int treatmentId;
        int woodTypeId;

        noggingAmount = 6;
        woodDimensionId = offerMapper.getWoodDimensionIdFromLengthWidthHeight(connection, shedWidthInCm, noggingWidthInMm, noggingHeightInMm);
        treatmentId = offerMapper.getTreatmentIdFromTreatmentName(connection, treatmentName);
        woodTypeId = offerMapper.getWoodTypeIdFromWoodTypeName(connection, woodTypeName);

        return new WoodForCalculator(woodTypeName, noggingAmount, woodDimensionId, treatmentId, woodTypeId, "Løsholter til forside og bagside af skur (Skal skæres til).");
    }

    public WoodForCalculator noggingForShedSidesCalculator(ConnectionPool connection, int noggingWidthInMm, int noggingHeightInMm, int carportWidthInCm, int shedLengthInCm, int shedWidthInCm, String woodTypeName, String treatmentName) throws DatabaseException {
        int poleAmountForWidth;
        int noggingAmount;
        int woodDimensionId;
        int treatmentId;
        int woodTypeId;

        poleAmountForWidth = poleCalculator.poleAmountYCalculator(carportWidthInCm);
        if (shedWidthInCm == (carportWidthInCm - 60) / (poleAmountForWidth - 1)) {
            noggingAmount = 4;
        } else {
            noggingAmount = 5;
        }
        woodDimensionId = offerMapper.getWoodDimensionIdFromLengthWidthHeight(connection, shedLengthInCm, noggingWidthInMm, noggingHeightInMm);
        treatmentId = offerMapper.getTreatmentIdFromTreatmentName(connection, treatmentName);
        woodTypeId = offerMapper.getWoodTypeIdFromWoodTypeName(connection, woodTypeName);

        return new WoodForCalculator(woodTypeName, noggingAmount, woodDimensionId, treatmentId, woodTypeId, "Løsholter til siderne af skur (Skal skæres til).");
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

        return new WoodForCalculator(woodTypeName, noggingAmount, woodDimensionId, treatmentId, woodTypeId, "Til z på bagside af dør.");
    }

    public int noggingForShedFrontAndBackAmountCalculator(int shedWidthInCm, int carportWidthInCm, int poleAmountForWidth) {
        int poleAmountWidthForDividing = poleAmountForWidth - 1;
        int carportWidthWithoutRoofOverhang = carportWidthInCm - 60;

        if (poleAmountForWidth > 2) {

            if (shedWidthInCm == carportWidthInCm) {
                return 24;
            } else if (shedWidthInCm > carportWidthWithoutRoofOverhang / poleAmountWidthForDividing) {
                return 18;
            }
        }

        if (shedWidthInCm == carportWidthWithoutRoofOverhang / poleAmountWidthForDividing) {
            return 12;
        }

        return 6;
    }

    public int noggingForShedSidesAmountCalculator(int shedLengthInCm, int shedWidthInCm, int carportLengthInCm, int carportWidthInCm, int poleAmountForLength, int poleAmountForWidth) {

        if (shedLengthInCm > (carportLengthInCm - 130) / (poleAmountForLength - 1)) {

            if (shedWidthInCm == (carportWidthInCm - 60) / (poleAmountForWidth - 1)) {
                return 8;
            } else {
                return 10;
            }
        }
        if (shedWidthInCm == (carportWidthInCm - 60) / (poleAmountForWidth - 1)) {
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
