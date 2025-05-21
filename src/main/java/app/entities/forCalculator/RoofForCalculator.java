package app.entities.forCalculator;

import app.exceptions.DatabaseException;
import app.persistence.Calculator.PoleCalculator;
import app.persistence.Calculator.RoofCalculator;
import app.persistence.connection.ConnectionPool;

import java.util.ArrayList;

public class RoofForCalculator {

    private String name;
    private int amount;
    private int roofId;
    private String description;
    private RoofCalculator roofCalculator;

    private static ArrayList<RoofForCalculator> roofList;

    public RoofForCalculator(String name, int amount, int roofId, String description) {
        this.name = name;
        this.amount = amount;
        this.roofId = roofId;
        this.description = description;
    }

    public RoofForCalculator() {
        roofCalculator = new RoofCalculator();
    }

    public ArrayList<RoofForCalculator> roofListCalculator(ConnectionPool connection, int carportLengthInCm, int carportWidthInCm, int roofSheetWidthInCm, String roofName) throws DatabaseException {
        roofList = new ArrayList<>();
        int carportLengthLeftInCm = carportLengthInCm + 5;

        while (carportLengthLeftInCm > 0) {
            //Tagplader beregnes her til taget er dækket. (regnes i stk)
            roofList.add(roofCalculator.roofCalculator(connection, carportLengthLeftInCm, carportWidthInCm, roofSheetWidthInCm, roofName));
            carportLengthLeftInCm = carportLengthLeftInCm - roofCalculator.roofLengthCalculator(carportLengthLeftInCm) + 20;
        }

        return roofList;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public int getRoofId() {
        return roofId;
    }

    public String getDescription() {
        return description;
    }

    public static ArrayList<RoofForCalculator> getRoofList() {
        return roofList;
    }
}
