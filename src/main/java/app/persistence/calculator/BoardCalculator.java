package app.persistence.calculator;

import app.entities.forCalculator.WoodForCalculator;
import app.exceptions.DatabaseException;
import app.persistence.connection.ConnectionPool;
import app.persistence.mappers.OfferMapper;

public class BoardCalculator {
    private OfferMapper offerMapper;

    //Pole = Stolpe
    //Nogging = Reglar
    //Rafter = Spær
    //RafterBeam = Rem
    //Fascia board = Understernbræt
    //Barge board = Oversternbræt
    //Drip cap = Vandbræt
    //PlumbersTape = Hulbånd


    public BoardCalculator() {
        offerMapper = new OfferMapper();
    }

    public WoodForCalculator shedBoardCalculator(ConnectionPool connection, int shedLengthInCm, int shedWidthInCm, String woodTypeName, String treatmentName, int boardDepthInMm, int boardWidthInMm, int boardLengthInCM, int spareBoardAmount) throws DatabaseException {
       int amount;
       int woodDimensionId;
       int treatmentId;
       int woodTypeId;

       amount = shedBoardAmountCalculator(shedLengthInCm,shedWidthInCm, boardWidthInMm, spareBoardAmount);
       woodDimensionId = offerMapper.getWoodDimensionIdFromLengthWidthHeight(connection, boardLengthInCM, boardDepthInMm, boardWidthInMm);
       treatmentId = offerMapper.getTreatmentIdFromTreatmentName(connection, treatmentName);
       woodTypeId = offerMapper.getWoodTypeIdFromWoodTypeName(connection, woodTypeName);

       return new WoodForCalculator(woodTypeName, amount, woodDimensionId, treatmentId, woodTypeId, "Til beklædning af skur.");
    }

    public WoodForCalculator fasciaBoardFrontAndBackCalculator(ConnectionPool connection, int carportWidthInCm, String woodTypeName, String treatmentName, int boardDepthInMm, int boardWidthInMm) throws DatabaseException {
        int amount;
        int woodDimensionId;
        int treatmentId;
        int woodTypeId;

        amount = fasciaAndBargeBoardAmountCalculator();
        woodDimensionId = offerMapper.getWoodDimensionIdFromLengthWidthHeight(connection, carportWidthInCm + 30, boardDepthInMm, boardWidthInMm);
        treatmentId = offerMapper.getTreatmentIdFromTreatmentName(connection, treatmentName);
        woodTypeId = offerMapper.getWoodTypeIdFromWoodTypeName(connection, woodTypeName);

        return new WoodForCalculator(woodTypeName, amount, woodDimensionId, treatmentId, woodTypeId, "Understernbrædder til for & bag ende.");
    }

    public WoodForCalculator fasciaBoardSidesCalculator(ConnectionPool connection, int carportLengthInCm,String woodTypeName, String treatmentName, int boardDepthInMm, int boardWidthInMm) throws DatabaseException {
        int amount;
        int woodDimensionId;
        int treatmentId;
        int woodTypeId;

        amount = fasciaAndBargeBoardAmountCalculator();
        woodDimensionId = offerMapper.getWoodDimensionIdFromLengthWidthHeight(connection, carportLengthInCm, boardDepthInMm, boardWidthInMm);
        treatmentId = offerMapper.getTreatmentIdFromTreatmentName(connection, treatmentName);
        woodTypeId = offerMapper.getWoodTypeIdFromWoodTypeName(connection, woodTypeName);

        return new WoodForCalculator(woodTypeName, amount, woodDimensionId, treatmentId, woodTypeId, "Understernbrædder til siderne.");
    }

    public WoodForCalculator bargeBoardFrontCalculator(ConnectionPool connection, int carportWidthInCm,String woodTypeName, String treatmentName, int boardDepthInMm, int boardWidthInMm) throws DatabaseException {
        int amount;
        int woodDimensionId;
        int treatmentId;
        int woodTypeId;

        amount = fasciaAndBargeBoardFrontAmountCalculator();
        woodDimensionId = offerMapper.getWoodDimensionIdFromLengthWidthHeight(connection, carportWidthInCm + 30, boardDepthInMm, boardWidthInMm);
        treatmentId = offerMapper.getTreatmentIdFromTreatmentName(connection, treatmentName);
        woodTypeId = offerMapper.getWoodTypeIdFromWoodTypeName(connection, woodTypeName);

        return new WoodForCalculator(woodTypeName, amount, woodDimensionId, treatmentId, woodTypeId, "Oversternbrædder til forende.");
    }

    public WoodForCalculator bargeBoardSidesCalculator(ConnectionPool connection, int carportLengthInCm,String woodTypeName, String treatmentName, int boardDepthInMm, int boardWidthInMm) throws DatabaseException {
        int amount;
        int woodDimensionId;
        int treatmentId;
        int woodTypeId;

        amount = fasciaAndBargeBoardAmountCalculator();
        woodDimensionId = offerMapper.getWoodDimensionIdFromLengthWidthHeight(connection, carportLengthInCm, boardDepthInMm, boardWidthInMm);
        treatmentId = offerMapper.getTreatmentIdFromTreatmentName(connection, treatmentName);
        woodTypeId = offerMapper.getWoodTypeIdFromWoodTypeName(connection, woodTypeName);

        return new WoodForCalculator(woodTypeName, amount, woodDimensionId, treatmentId, woodTypeId, "Oversternbrædder til siderne.");
    }

    public WoodForCalculator dripCapForBoardFrontCalculator(ConnectionPool connection, int carportWidthInCm,String woodTypeName, String treatmentName, int boardDepthInMm, int boardWidthInMm) throws DatabaseException {
        int amount;
        int woodDimensionId;
        int treatmentId;
        int woodTypeId;

        amount = fasciaAndBargeBoardFrontAmountCalculator();
        woodDimensionId = offerMapper.getWoodDimensionIdFromLengthWidthHeight(connection, carportWidthInCm + 30, boardDepthInMm, boardWidthInMm);
        treatmentId = offerMapper.getTreatmentIdFromTreatmentName(connection, treatmentName);
        woodTypeId = offerMapper.getWoodTypeIdFromWoodTypeName(connection, woodTypeName);

        return new WoodForCalculator(woodTypeName, amount, woodDimensionId, treatmentId, woodTypeId, "Vandbræt til stern i forende.");
    }

    public WoodForCalculator dripCapForBoardSidesCalculator(ConnectionPool connection, int carportLengthInCm, String woodTypeName, String treatmentName, int boardDepthInMm, int boardWidthInMm) throws DatabaseException {
        int amount;
        int woodDimensionId;
        int treatmentId;
        int woodTypeId;

        amount = fasciaAndBargeBoardAmountCalculator();
        woodDimensionId = offerMapper.getWoodDimensionIdFromLengthWidthHeight(connection, carportLengthInCm, boardDepthInMm, boardWidthInMm);
        treatmentId = offerMapper.getTreatmentIdFromTreatmentName(connection, treatmentName);
        woodTypeId = offerMapper.getWoodTypeIdFromWoodTypeName(connection, woodTypeName);

        return new WoodForCalculator(woodTypeName, amount, woodDimensionId, treatmentId, woodTypeId, "Vandbræt til stern i siderne.");
    }

    public int shedBoardAmountCalculator(int shedLengthInCm, int shedWidthInCm, int boardWidthInMm, int spareBoardAmount) {
        int totalCircumferenceInCm;
        int boardCoverageInCm;
        int boardAmount;

        totalCircumferenceInCm = (shedLengthInCm * 2) + (shedWidthInCm * 2);
        boardCoverageInCm = 0;
        boardAmount = 0;

        while (totalCircumferenceInCm > boardCoverageInCm) {
            boardCoverageInCm = boardCoverageInCm + (boardWidthInMm/10);
            boardAmount ++;
        }

        return boardAmount + spareBoardAmount;
    }

    public int fasciaAndBargeBoardAmountCalculator() {
        int boardAmount;
        boardAmount= 2;

        return boardAmount;
    }

    public int fasciaAndBargeBoardFrontAmountCalculator() {
        int boardAmount;
        boardAmount = 1;
        return boardAmount;
    }

    public int  totalFasciaAndBargeBoardAndDripCapLengthCalculator(int carportLengthInCm, int carportWidthInCm) {
        int lengthOfFasciaBoardFrontAndBack;
        int lengthOfFasciaBoardSides;
        int lengthOfBargeBoardFront;
        int lengthOfBargeBoardSides;
        int lengthOfDripCapFront;
        int lengthOfDripCapSides;
        int totalFasciaAndBargeBoardAndDripCapLengthInCm;

        lengthOfFasciaBoardFrontAndBack = fasciaAndBargeBoardAmountCalculator() * carportWidthInCm;
        lengthOfFasciaBoardSides = fasciaAndBargeBoardAmountCalculator() * carportLengthInCm;
        lengthOfBargeBoardFront = fasciaAndBargeBoardFrontAmountCalculator() * carportWidthInCm;
        lengthOfBargeBoardSides = fasciaAndBargeBoardAmountCalculator() * carportLengthInCm;
        lengthOfDripCapFront = fasciaAndBargeBoardFrontAmountCalculator() * carportWidthInCm;
        lengthOfDripCapSides = fasciaAndBargeBoardAmountCalculator() * carportLengthInCm;
        totalFasciaAndBargeBoardAndDripCapLengthInCm = lengthOfFasciaBoardFrontAndBack + lengthOfFasciaBoardSides + lengthOfBargeBoardFront + lengthOfBargeBoardSides + lengthOfDripCapFront + lengthOfDripCapSides;

        return totalFasciaAndBargeBoardAndDripCapLengthInCm;
    }

}
