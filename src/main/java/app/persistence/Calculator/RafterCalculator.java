package app.persistence.Calculator;

import app.entities.forCalculator.WoodForCalculator;
import app.persistence.mappers.OfferMapper;


public class RafterCalculator {
    private OfferMapper offerMapper;
    public WoodForCalculator rafterForRoofCalculator(int carportLengthInCm, int carportWidthInCm, int treatmentId) {
        int rafterAmount = rafterAmountForRoofCalculator(carportLengthInCm);
        int rafterWidthInMm = totalRafterWidthInMmCalculator(carportWidthInCm);
        int rafterHeightInMm = totalRafterHeightInMmCalculator(carportWidthInCm);
        int woodDimensionId = offerMapper.getWoodDimensionIdFromFromLengthWidthHeight(carportWidthInCm, rafterWidthInMm, rafterHeightInMm);
        int woodTypeId = offerMapper.getWoodTypeIdFromWoodTypeName("Spær");
        return new WoodForCalculator("monteres på rem.", rafterAmount, woodDimensionId, treatmentId, woodTypeId);
    }

    public WoodForCalculator rafterForShedWidthCalculator(int shedWidthInCm, int treatmentId) {
        int rafterAmount = 2;
        int rafterWidthInMm = totalRafterWidthInMmCalculator(shedWidthInCm);
        int rafterHeightInMm = totalRafterHeightInMmCalculator(shedWidthInCm);
        int woodDimensionId = offerMapper.getWoodDimensionIdFromFromLengthWidthHeight(shedWidthInCm, rafterWidthInMm, rafterHeightInMm);
        int woodTypeId = offerMapper.getWoodTypeIdFromWoodTypeName("Spær");
        return new WoodForCalculator("til bagside og forside af skur.", rafterAmount, woodDimensionId, treatmentId, woodTypeId);
    }

    public WoodForCalculator rafterForShedLengthCalculator(int shedLengthInCm, int treatmentId) {
        int rafterAmount = 2;
        int rafterWidthInMm = totalRafterWidthInMmCalculator(shedLengthInCm);
        int rafterHeightInMm = totalRafterHeightInMmCalculator(shedLengthInCm);
        int woodDimensionId = offerMapper.getWoodDimensionIdFromFromLengthWidthHeight(shedLengthInCm, rafterWidthInMm, rafterHeightInMm);
        int woodTypeId = offerMapper.getWoodTypeIdFromWoodTypeName("Spær");
        return new WoodForCalculator("til siderne på skur.", rafterAmount, woodDimensionId, treatmentId, woodTypeId);
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
