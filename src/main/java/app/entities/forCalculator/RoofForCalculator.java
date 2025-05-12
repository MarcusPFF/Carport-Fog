package app.entities.forCalculator;

import app.exceptions.DatabaseException;
import app.persistence.Calculator.PoleCalculator;
import app.persistence.Calculator.RoofCalculator;
import app.persistence.connection.ConnectionPool;

import java.util.ArrayList;

public class RoofForCalculator {
private RoofCalculator roofCalculator;

    private String name;
    private int amount;
    private int roofId;

    private static ArrayList<RoofForCalculator> roofList;

    public RoofForCalculator(String name, int amount, int roofId) {
        this.name = name;
        this.amount = amount;
        this.roofId = roofId;
    }

    public RoofForCalculator() {
    }
//TODO test det her, kunne ikke teste pga ingen connectionpool
    public ArrayList<RoofForCalculator> roofCalculator(ConnectionPool connection, int carportLengthInCm, int carportWidthInCm, int roofPladeWidthInCm, String roofName) throws DatabaseException {
        roofList = new ArrayList<>();
        int carportLengthLeftInCm = carportLengthInCm + 5;

        while (carportLengthLeftInCm > 0) {
            roofList.add(roofCalculator.roofCalculator(connection, carportLengthLeftInCm, carportWidthInCm, roofPladeWidthInCm, roofName));
            carportLengthLeftInCm = carportLengthLeftInCm - roofCalculator.roofLengthCalculator(carportLengthLeftInCm) + 20;
        }

        return roofList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static ArrayList<RoofForCalculator> getRoofList() {
        return roofList;
    }

    public static void setRoofList(ArrayList<RoofForCalculator> roofList) {
        RoofForCalculator.roofList = roofList;
    }
}
