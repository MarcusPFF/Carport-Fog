package app.persistence.Calculator;

import app.entities.forCalculator.WoodForCalculator;
import app.exceptions.DatabaseException;
import app.persistence.connection.ConnectionPool;
import app.persistence.mappers.OfferMapper;

public class BoardCalculator {
    private OfferMapper offerMapper;

    public WoodForCalculator shedBoardCalculator(ConnectionPool connection, int shedLengthInCm, int shedWidthInCm, String woodTypeName, String treatmentName, int boardWidthInMm, int boardHeightInMm, int boardLengthInCM, int spareBoardAmount) throws DatabaseException {
       int amount;

       amount = shedBoardAmountCalculator(shedLengthInCm,shedWidthInCm, boardWidthInMm, spareBoardAmount);

       int woodDimensionId = offerMapper.getWoodDimensionIdFromLengthWidthHeight(connection, boardLengthInCM, boardWidthInMm, boardHeightInMm);
       int treatmentId = offerMapper.getTreatmentIdFromTreatmentName(connection, treatmentName);
       int woodTypeId = offerMapper.getWoodTypeIdFromWoodTypeName(connection, woodTypeName);

       return new WoodForCalculator(woodTypeName + ", til beklædning af skur.", amount, woodDimensionId, treatmentId, woodTypeId);
    }

    public WoodForCalculator fasciaBoardFrontAndBackCalculator(ConnectionPool connection, int carportWidthInCm,String woodTypeName, String treatmentName, int boardWidthInMm, int boardHeightInMm) throws DatabaseException {
        int amount;

        amount = fasciaBoardAmountCalculator();

        int woodDimensionId = offerMapper.getWoodDimensionIdFromLengthWidthHeight(connection, carportWidthInCm + 30, boardWidthInMm, boardHeightInMm);
        int treatmentId = offerMapper.getTreatmentIdFromTreatmentName(connection, treatmentName);
        int woodTypeId = offerMapper.getWoodTypeIdFromWoodTypeName(connection, woodTypeName);

        return new WoodForCalculator(woodTypeName + ", understernbrædder til for & bag ende.", amount, woodDimensionId, treatmentId, woodTypeId);
    }

    public WoodForCalculator fasciaBoardSidesCalculator(ConnectionPool connection, int carportLengthInCm,String woodTypeName, String treatmentName, int boardWidthInMm, int boardHeightInMm) throws DatabaseException {
        int amount;

        amount = fasciaBoardAmountCalculator();

        int woodDimensionId = offerMapper.getWoodDimensionIdFromLengthWidthHeight(connection, carportLengthInCm, boardWidthInMm, boardHeightInMm);
        int treatmentId = offerMapper.getTreatmentIdFromTreatmentName(connection, treatmentName);
        int woodTypeId = offerMapper.getWoodTypeIdFromWoodTypeName(connection, woodTypeName);

        return new WoodForCalculator(woodTypeName + ", understernbrædder til siderne.", amount, woodDimensionId, treatmentId, woodTypeId);
    }

    public WoodForCalculator bargeBoardFrontCalculator(ConnectionPool connection, int carportWidthInCm,String woodTypeName, String treatmentName, int boardWidthInMm, int boardHeightInMm) throws DatabaseException {
        int amount;

        amount = 1;

        int woodDimensionId = offerMapper.getWoodDimensionIdFromLengthWidthHeight(connection, carportWidthInCm + 30, boardWidthInMm, boardHeightInMm);
        int treatmentId = offerMapper.getTreatmentIdFromTreatmentName(connection, treatmentName);
        int woodTypeId = offerMapper.getWoodTypeIdFromWoodTypeName(connection, woodTypeName);

        return new WoodForCalculator(woodTypeName + ", oversternbrædder til forende.", amount, woodDimensionId, treatmentId, woodTypeId);
    }

    public WoodForCalculator bargeBoardSidesCalculator(ConnectionPool connection, int carportLengthInCm,String woodTypeName, String treatmentName, int boardWidthInMm, int boardHeightInMm) throws DatabaseException {
        int amount;

        amount = bargeBoardAmountCalculator();

        int woodDimensionId = offerMapper.getWoodDimensionIdFromLengthWidthHeight(connection, carportLengthInCm, boardWidthInMm, boardHeightInMm);
        int treatmentId = offerMapper.getTreatmentIdFromTreatmentName(connection, treatmentName);
        int woodTypeId = offerMapper.getWoodTypeIdFromWoodTypeName(connection, woodTypeName);

        return new WoodForCalculator(woodTypeName + ", oversternbrædder til siderne.", amount, woodDimensionId, treatmentId, woodTypeId);
    }

    public WoodForCalculator dripCapForBoardFrontCalculator(ConnectionPool connection, int carportWidthInCm,String woodTypeName, String treatmentName, int boardWidthInMm, int boardHeightInMm) throws DatabaseException {
        int amount;

        amount = 1;

        int woodDimensionId = offerMapper.getWoodDimensionIdFromLengthWidthHeight(connection, carportWidthInCm + 30, boardWidthInMm, boardHeightInMm);
        int treatmentId = offerMapper.getTreatmentIdFromTreatmentName(connection, treatmentName);
        int woodTypeId = offerMapper.getWoodTypeIdFromWoodTypeName(connection, woodTypeName);

        return new WoodForCalculator(woodTypeName + ", vandbræt til stern i forende.", amount, woodDimensionId, treatmentId, woodTypeId);
    }

    public WoodForCalculator dripCapForBoardSidesCalculator(ConnectionPool connection, int carportLengthInCm, String woodTypeName, String treatmentName, int boardWidthInMm, int boardHeightInMm) throws DatabaseException {
        int amount;

        amount = bargeBoardAmountCalculator();

        int woodDimensionId = offerMapper.getWoodDimensionIdFromLengthWidthHeight(connection, carportLengthInCm, boardWidthInMm, boardHeightInMm);
        int treatmentId = offerMapper.getTreatmentIdFromTreatmentName(connection, treatmentName);
        int woodTypeId = offerMapper.getWoodTypeIdFromWoodTypeName(connection, woodTypeName);

        return new WoodForCalculator(woodTypeName + ", vandbræt til stern i siderne.", amount, woodDimensionId, treatmentId, woodTypeId);
    }



    public int shedBoardAmountCalculator(int shedLengthInCm, int shedWidthInCm, int boardWidthInMm, int spareBoardAmount) {
        int totalCircumferenceInCm = (shedLengthInCm * 2) + (shedWidthInCm * 2);
        int boardCoverageInCm = 0;
        int boardAmount = 0;

        while (totalCircumferenceInCm > boardCoverageInCm) {
            boardCoverageInCm = boardCoverageInCm + (boardWidthInMm/10);
            boardAmount ++;
        }

        return boardAmount + spareBoardAmount;
    }

    public int fasciaBoardAmountCalculator() {
        int boardAmount = 2;
        return boardAmount;
    }

    public int bargeBoardAmountCalculator() {
        int boardAmount = 2;
        return boardAmount;
    }
}
