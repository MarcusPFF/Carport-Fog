package app.persistence.Calculator;

import app.entities.forCalculator.WoodForCalculator;
import app.exceptions.DatabaseException;
import app.persistence.connection.ConnectionPool;
import app.persistence.mappers.OfferMapper;


public class RafterCalculator {
    private OfferMapper offerMapper;

    public WoodForCalculator rafterForRoofCalculator(ConnectionPool connection, int carportLengthInCm, int carportWidthInCm, String woodTypeName, String treatmentName) throws DatabaseException {
        int rafterAmount = rafterAmountForRoofCalculator(carportLengthInCm);
        int rafterWidthInMm = totalRafterWidthInMmCalculator(carportWidthInCm);
        int rafterHeightInMm = totalRafterHeightInMmCalculator(carportWidthInCm);

        int woodDimensionId = offerMapper.getWoodDimensionIdFromLengthWidthHeight(connection, carportWidthInCm, rafterWidthInMm, rafterHeightInMm);
        int treatmentId = offerMapper.getTreatmentIdFromTreatmentName(connection, treatmentName);
        int woodTypeId = offerMapper.getWoodTypeIdFromWoodTypeName(connection, woodTypeName);

        return new WoodForCalculator(woodTypeName + "monteres på rem.", rafterAmount, woodDimensionId, treatmentId, woodTypeId);
    }

    public WoodForCalculator rafterForShedWidthCalculator(ConnectionPool connection, int shedWidthInCm, String woodTypeName, String treatmentName) throws DatabaseException {
        int rafterAmount = 2;
        int rafterWidthInMm = totalRafterWidthInMmCalculator(shedWidthInCm);
        int rafterHeightInMm = totalRafterHeightInMmCalculator(shedWidthInCm);

        int woodDimensionId = offerMapper.getWoodDimensionIdFromLengthWidthHeight(connection, shedWidthInCm, rafterWidthInMm, rafterHeightInMm);
        int treatmentId = offerMapper.getTreatmentIdFromTreatmentName(connection, treatmentName);
        int woodTypeId = offerMapper.getWoodTypeIdFromWoodTypeName(connection, woodTypeName);

        return new WoodForCalculator(woodTypeName + "til bagside og forside af skur.", rafterAmount, woodDimensionId, treatmentId, woodTypeId);
    }

    public WoodForCalculator rafterForShedLengthCalculator(ConnectionPool connection, int shedLengthInCm, String woodTypeName, String treatmentName) throws DatabaseException {
        int rafterAmount = 2;
        int rafterWidthInMm = totalRafterWidthInMmCalculator(shedLengthInCm);
        int rafterHeightInMm = totalRafterHeightInMmCalculator(shedLengthInCm);

        int woodDimensionId = offerMapper.getWoodDimensionIdFromLengthWidthHeight(connection, shedLengthInCm, rafterWidthInMm, rafterHeightInMm);
        int treatmentId = offerMapper.getTreatmentIdFromTreatmentName(connection, treatmentName);
        int woodTypeId = offerMapper.getWoodTypeIdFromWoodTypeName(connection, woodTypeName);

        return new WoodForCalculator(woodTypeName + ", til siderne på skur.", rafterAmount, woodDimensionId, treatmentId, woodTypeId);
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
        } else {
            woodHeightInMm = 245;
        }

        return woodHeightInMm;
    }

    public int totalRafterWidthInMmCalculator( int carportWidthInCM) {
        int woodWidthInMm;

        if ((carportWidthInCM - 70) <= 500) {
            woodWidthInMm = 45;

        } else {
            woodWidthInMm = 60;

        }

        return woodWidthInMm;
    }

}
