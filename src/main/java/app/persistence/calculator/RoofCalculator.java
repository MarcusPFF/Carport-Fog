package app.persistence.calculator;

import app.entities.forCalculator.RoofForCalculator;
import app.exceptions.DatabaseException;
import app.persistence.connection.ConnectionPool;
import app.persistence.mappers.OfferMapper;

public class RoofCalculator {
    private OfferMapper offerMapper;

    //Pole = Stolpe
    //Nogging = Reglar
    //Rafter = Spær
    //RafterBeam = Rem
    //Fascia board = Understernbræt
    //Barge board = Oversternbræt
    //Drip cap = Vandbræt
    //PlumbersTape = Hulbånd

    //10Cm pr bølge
    //overlap med 2 bølger

    public RoofForCalculator roofCalculator(ConnectionPool connection, int carportLengthInCm, int carportWidthInCm, int roofSheetWidthInCm, String roofName) throws DatabaseException {
        int amount;
        int roofLengthInCm;
        int roofId;

        amount = roofAmountInWidthCalculator(carportWidthInCm, roofSheetWidthInCm);
        roofLengthInCm = roofLengthCalculator(carportLengthInCm);
        roofId = offerMapper.getRoofIdFromRoofLength(connection, roofLengthInCm);

        return new RoofForCalculator(roofName, amount, roofId, "Tagplader monteres på spær.");
    }

    public int roofAmountInWidthCalculator(int carportWidthInCm, int roofSheetWidthInCm) {
        int amount;
        amount = 0;

        while (carportWidthInCm > 20 + (amount * (roofSheetWidthInCm - 20))) {
            amount++;
        }

        return amount;
    }

    public int roofLengthCalculator(int carportLengthInCm) {
        int shortestLengthInCm;
        int mediumLengthInCm;
        int longestLengthInCm;
        int roofLengthInCm;

        shortestLengthInCm = 300;
        mediumLengthInCm = 450;
        longestLengthInCm = 600;

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
