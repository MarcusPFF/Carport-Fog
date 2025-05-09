package app.persistence.Calculator;

import app.entities.forCalculator.WoodForCalculator;
import app.persistence.mappers.OfferMapper;

public class NoggingCalculator {
    private OfferMapper offerMapper;
    public WoodForCalculator noggingCalculator(int carportLengthInCm, int carportWidthInCm, int rafterWidthInMm, int rafterHeightInMm, String woodTypeName, String treatmentName) {
        int noggingAmount = noggingAmountCalculator(carportWidthInCm);
        int woodDimensionId = offerMapper.getWoodDimensionIdFromFromLengthWidthHeight(carportLengthInCm, rafterWidthInMm, rafterHeightInMm);
        int treatmentId = offerMapper.getTreatmentIdFromTreatmentName(treatmentName);
        int woodTypeId = offerMapper.getWoodTypeIdFromWoodTypeName(woodTypeName);

        return new WoodForCalculator("Ragler", noggingAmount, woodDimensionId, treatmentId, woodTypeId);
    }

    public int noggingAmountCalculator(int carportWidthInCM) {
        int amount = 1;
        amount = amount + (int) Math.ceil ((carportWidthInCM - 70.0)/600.0);

        return amount;
    }

}
