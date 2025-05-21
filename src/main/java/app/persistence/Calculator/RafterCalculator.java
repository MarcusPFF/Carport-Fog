package app.persistence.Calculator;

import app.entities.forCalculator.WoodForCalculator;
import app.exceptions.DatabaseException;
import app.persistence.connection.ConnectionPool;
import app.persistence.mappers.OfferMapper;


public class RafterCalculator {
    private OfferMapper offerMapper;

    //Pole = Stolpe
    //Nogging = Reglar
    //Rafter = Spær
    //RafterBeam = Rem
    //Fascia board = Understernbræt
    //Barge board = Oversternbræt
    //Drip cap = Vandbræt
    //PlumbersTape = Hulbånd


    public RafterCalculator() {
        offerMapper = new OfferMapper();
    }

    public WoodForCalculator rafterForRoofCalculator(ConnectionPool connection, int carportLengthInCm, int carportWidthInCm, String woodTypeName, String treatmentName) throws DatabaseException {
        int rafterAmount;
        int rafterWidthInMm;
        int rafterHeightInMm;
        int woodDimensionId;
        int treatmentId;
        int woodTypeId;

        rafterAmount = rafterAmountForRoofCalculator(carportLengthInCm);
        rafterWidthInMm = totalRafterWidthInMmCalculator(carportWidthInCm);
        rafterHeightInMm = totalRafterHeightInMmCalculator(carportWidthInCm);

        woodDimensionId = offerMapper.getWoodDimensionIdFromLengthWidthHeight(connection, carportWidthInCm, rafterWidthInMm, rafterHeightInMm);
        treatmentId = offerMapper.getTreatmentIdFromTreatmentName(connection, treatmentName);
        woodTypeId = offerMapper.getWoodTypeIdFromWoodTypeName(connection, woodTypeName);

        return new WoodForCalculator(woodTypeName, rafterAmount, woodDimensionId, treatmentId, woodTypeId, "Monteres på rem.");
    }

    public WoodForCalculator rafterBeamCalculator(ConnectionPool connection, int carportLengthInCm, int carportWidthInCm, String woodTypeName, String treatmentName) throws DatabaseException {
        int rafterBeamAmount;
        int rafterWidthInMm;
        int rafterHeightInMm;
        int woodDimensionId;
        int treatmentId;
        int woodTypeId;

        rafterWidthInMm = totalRafterWidthInMmCalculator(carportLengthInCm);
        rafterHeightInMm = totalRafterHeightInMmCalculator(carportLengthInCm);
        rafterBeamAmount = rafterBeamAmountCalculator(carportWidthInCm);

        woodDimensionId = offerMapper.getWoodDimensionIdFromLengthWidthHeight(connection, carportLengthInCm, rafterWidthInMm, rafterHeightInMm);
        treatmentId = offerMapper.getTreatmentIdFromTreatmentName(connection, treatmentName);
        woodTypeId = offerMapper.getWoodTypeIdFromWoodTypeName(connection, woodTypeName);

        return new WoodForCalculator(woodTypeName, rafterBeamAmount, woodDimensionId, treatmentId, woodTypeId, "Remme, sadles ned i stolper.");
    }

    public int rafterAmountForRoofCalculator(int carportLengthInCM) {
        int amount;
        amount = 1 + (int) Math.ceil (carportLengthInCM/60.0);

        return amount;
    }

    public int totalRafterHeightInMmCalculator( int carportWidthInCM) {
        int woodHeightInMm;

        if ((carportWidthInCM - 60)  < 300) {
            woodHeightInMm = 145;
        }
        else if ((carportWidthInCM - 60)  < 400) {
            woodHeightInMm = 195;
        }

        else if ((carportWidthInCM - 60)  < 500) {
            woodHeightInMm = 220;
        }
        else if ((carportWidthInCM - 60)  < 600) {
            woodHeightInMm = 245;
        }
        else {
            woodHeightInMm = 275;
        }

        return woodHeightInMm;
    }

    public int totalRafterWidthInMmCalculator( int carportWidthInCM) {
        int woodWidthInMm;

        if ((carportWidthInCM - 60) <= 500) {
            woodWidthInMm = 45;
        }
        else if ((carportWidthInCM - 60) <= 600) {
            woodWidthInMm = 60;
        }
        else {
            woodWidthInMm = 75;
        }

        return woodWidthInMm;
    }

    public int rafterBeamAmountCalculator(int carportWidthInCM) {
        int amount;
        amount = 1 + (int) Math.ceil ((carportWidthInCM - 60.0)/600.0);

        return amount;
    }
}
