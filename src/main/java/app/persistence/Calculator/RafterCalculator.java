package app.persistence.Calculator;

import app.entities.forCalculator.WoodForCalculator;
import app.exceptions.DatabaseException;
import app.persistence.connection.ConnectionPool;
import app.persistence.mappers.OfferMapper;


public class RafterCalculator {
    private OfferMapper offerMapper;

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

        return new WoodForCalculator(woodTypeName, rafterAmount, woodDimensionId, treatmentId, woodTypeId, "monteres på rem.");
    }

    public WoodForCalculator rafterBeamCalculator(ConnectionPool connection, int carportLengthInCm, int carportWidthInCm, int rafterWidthInMm, int rafterHeightInMm, String woodTypeName, String treatmentName) throws DatabaseException {
        int rafterBeamAmount;
        int woodDimensionId;
        int treatmentId;
        int woodTypeId;

        rafterBeamAmount = rafterBeamAmountCalculator(carportWidthInCm);
        woodDimensionId = offerMapper.getWoodDimensionIdFromLengthWidthHeight(connection, carportLengthInCm, rafterWidthInMm, rafterHeightInMm);
        treatmentId = offerMapper.getTreatmentIdFromTreatmentName(connection, treatmentName);
        woodTypeId = offerMapper.getWoodTypeIdFromWoodTypeName(connection, woodTypeName);

        return new WoodForCalculator(woodTypeName, rafterBeamAmount, woodDimensionId, treatmentId, woodTypeId, "Remme, sadles ned i stolper");
    }

    public int rafterAmountForRoofCalculator(int carportLengthInCM) {
        int amount;
        amount = 1 + (int) Math.ceil (carportLengthInCM/60.0);

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

    public int rafterBeamAmountCalculator(int carportWidthInCM) {
        int amount;
        amount = 1 + (int) Math.ceil ((carportWidthInCM - 70.0)/600.0);

        return amount;
    }
}
