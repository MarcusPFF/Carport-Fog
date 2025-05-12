package app.persistence.Calculator;

import app.entities.forCalculator.WoodForCalculator;
import app.persistence.mappers.OfferMapper;

public class BoardCalculator {
    private OfferMapper offerMapper;

    public WoodForCalculator shedBoardCalculator(int shedLengthInCm, int shedWidthInCm, String woodTypeName, String treatmentName, int boardWidthInMm, int boardHeightInMm, int boardLengthInCM) {
       int amount;

       amount = shedBoardAmountCalculator(shedLengthInCm,shedWidthInCm, boardWidthInMm);

       int woodDimensionId = offerMapper.getWoodDimensionIdFromFromLengthWidthHeight(boardLengthInCM, boardWidthInMm, boardHeightInMm);
       int treatmentId = offerMapper.getTreatmentIdFromTreatmentName(treatmentName);
       int woodTypeId = offerMapper.getWoodTypeIdFromWoodTypeName(woodTypeName);

       return new WoodForCalculator(woodTypeName + ", til beklædning af skur.", amount, woodDimensionId, treatmentId, woodTypeId);
    }

    public WoodForCalculator fasciaBoardFrontAndBackCalculator(int carportWidthInCm,String woodTypeName, String treatmentName, int boardWidthInMm, int boardHeightInMm) {
        int amount;

        amount = fasciaBoardAmountCalculator(carportWidthInCm);

        int woodDimensionId = offerMapper.getWoodDimensionIdFromFromLengthWidthHeight(carportWidthInCm + 30, boardWidthInMm, boardHeightInMm);
        int treatmentId = offerMapper.getTreatmentIdFromTreatmentName(treatmentName);
        int woodTypeId = offerMapper.getWoodTypeIdFromWoodTypeName(woodTypeName);

        return new WoodForCalculator(woodTypeName + ", understernbrædder til for & bag ende.", amount, woodDimensionId, treatmentId, woodTypeId);
    }

    public WoodForCalculator fasciaBoardSidesCalculator(int carportLengthInCm,String woodTypeName, String treatmentName, int boardWidthInMm, int boardHeightInMm) {
        int amount;

        amount = fasciaBoardAmountCalculator(carportLengthInCm);

        int woodDimensionId = offerMapper.getWoodDimensionIdFromFromLengthWidthHeight(carportLengthInCm, boardWidthInMm, boardHeightInMm);
        int treatmentId = offerMapper.getTreatmentIdFromTreatmentName(treatmentName);
        int woodTypeId = offerMapper.getWoodTypeIdFromWoodTypeName(woodTypeName);

        return new WoodForCalculator(woodTypeName + ", understernbrædder til siderne.", amount, woodDimensionId, treatmentId, woodTypeId);
    }

    public WoodForCalculator bargeBoardFrontCalculator(int carportWidthInCm,String woodTypeName, String treatmentName, int boardWidthInMm, int boardHeightInMm) {
        int amount;

        amount = 1;

        int woodDimensionId = offerMapper.getWoodDimensionIdFromFromLengthWidthHeight(carportWidthInCm + 30, boardWidthInMm, boardHeightInMm);
        int treatmentId = offerMapper.getTreatmentIdFromTreatmentName(treatmentName);
        int woodTypeId = offerMapper.getWoodTypeIdFromWoodTypeName(woodTypeName);

        return new WoodForCalculator(woodTypeName + ", oversternbrædder til forende.", amount, woodDimensionId, treatmentId, woodTypeId);
    }

    public WoodForCalculator bargeBoardSidesCalculator(int carportLengthInCm,String woodTypeName, String treatmentName, int boardWidthInMm, int boardHeightInMm) {
        int amount;

        amount = bargeBoardAmountCalculator(carportLengthInCm);

        int woodDimensionId = offerMapper.getWoodDimensionIdFromFromLengthWidthHeight(carportLengthInCm, boardWidthInMm, boardHeightInMm);
        int treatmentId = offerMapper.getTreatmentIdFromTreatmentName(treatmentName);
        int woodTypeId = offerMapper.getWoodTypeIdFromWoodTypeName(woodTypeName);

        return new WoodForCalculator(woodTypeName + ", oversternbrædder til siderne.", amount, woodDimensionId, treatmentId, woodTypeId);
    }

    public WoodForCalculator dripCapForBoardFrontCalculator(int carportWidthInCm,String woodTypeName, String treatmentName, int boardWidthInMm, int boardHeightInMm) {
        int amount;

        amount = 1;

        int woodDimensionId = offerMapper.getWoodDimensionIdFromFromLengthWidthHeight(carportWidthInCm + 30, boardWidthInMm, boardHeightInMm);
        int treatmentId = offerMapper.getTreatmentIdFromTreatmentName(treatmentName);
        int woodTypeId = offerMapper.getWoodTypeIdFromWoodTypeName(woodTypeName);

        return new WoodForCalculator(woodTypeName + ", vandbræt til stern i forende.", amount, woodDimensionId, treatmentId, woodTypeId);
    }

    public WoodForCalculator dripCapForBoardSidesCalculator(int carportLengthInCm, String woodTypeName, String treatmentName, int boardWidthInMm, int boardHeightInMm) {
        int amount;

        amount = bargeBoardAmountCalculator(carportLengthInCm);

        int woodDimensionId = offerMapper.getWoodDimensionIdFromFromLengthWidthHeight(carportLengthInCm, boardWidthInMm, boardHeightInMm);
        int treatmentId = offerMapper.getTreatmentIdFromTreatmentName(treatmentName);
        int woodTypeId = offerMapper.getWoodTypeIdFromWoodTypeName(woodTypeName);

        return new WoodForCalculator(woodTypeName + ", vandbræt til stern i siderne.", amount, woodDimensionId, treatmentId, woodTypeId);
    }



    public int shedBoardAmountCalculator(int shedLengthInCm, int shedWidthInCm, int boardWidthInMm) {
        int totalCircumferenceInCm = (shedLengthInCm * 2) + (shedWidthInCm * 2);
        int boardCoverageInCm = 0;
        int boardAmount = 0;
        while (totalCircumferenceInCm > boardCoverageInCm) {
            boardCoverageInCm = boardCoverageInCm + (boardWidthInMm/10);
            boardAmount ++;
        }
    return boardAmount;
    }

    public int fasciaBoardAmountCalculator(int LengthInCm) {
        int boardAmount = 2;
        return boardAmount;
    }

    public int bargeBoardAmountCalculator(int LengthInCm) {
        int boardAmount = 2;
        return boardAmount;
    }
}
