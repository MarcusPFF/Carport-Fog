package app.persistence.Calculator;

import app.entities.forCalculator.RoofForCalculator;
import app.entities.forCalculator.WoodForCalculator;
import app.exceptions.DatabaseException;
import app.persistence.connection.ConnectionPool;
import app.persistence.mappers.OfferMapper;

public class RoofCalculator {
    private OfferMapper offerMapper;

    //10Cm pr bølge
    //overlap med 2 bølger

    //todo test den her
    public RoofForCalculator roofCalculator(ConnectionPool connection, int carportLengthInCm, int carportWidthInCm, int roofPladeWidthInCm, String roofName) throws DatabaseException {

        int amount = roofAmountInWidthCalculator(carportWidthInCm, roofPladeWidthInCm);
        int roofLengthInCm = roofLengthCalculator(carportLengthInCm);

        int roofId = offerMapper.getRoofIdFromRoofLength(connection, roofLengthInCm);

        return new RoofForCalculator(roofName + "monteres på rem.", amount, roofId);
    }


    public int roofAmountInWidthCalculator(int carportWidthInCm, int roofWidthInCm) {
        int amount = 0;
        while (carportWidthInCm > amount * (roofWidthInCm - 20)) {
            amount++;
        }
        return amount;
    }

    public int roofLengthCalculator(int carportLengthInCm) {
        int shortestLengthInCm = 300;
        int mediumLengthInCm = 450;
        int longestLengthInCm = 600;
        int roofLengthInCm;

        if (carportLengthInCm + 5 < shortestLengthInCm) {
            roofLengthInCm = shortestLengthInCm;
        }
        else if (carportLengthInCm + 5 < mediumLengthInCm) {
            roofLengthInCm = mediumLengthInCm;
        }
        else {
            roofLengthInCm = longestLengthInCm;
        }

        return roofLengthInCm;
    }



}
