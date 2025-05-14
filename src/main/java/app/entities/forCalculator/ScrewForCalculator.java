package app.entities.forCalculator;

import app.exceptions.DatabaseException;
import app.persistence.Calculator.PoleCalculator;
import app.persistence.Calculator.RoofCalculator;
import app.persistence.connection.ConnectionPool;

import java.util.ArrayList;

public class ScrewForCalculator {

    private String name;
    private int amount;
    private int screwId;
    private String description;

    private static ArrayList<ScrewForCalculator> screwList;

    public ScrewForCalculator(String name, int amount, int screwId,  String description) {
        this.name = name;
        this.amount = amount;
        this.screwId = screwId;
        this.description = description;
    }

    public ScrewForCalculator() {
    }
    //TODO test det her, kunne ikke teste pga ingen connectionpool
    public ArrayList<ScrewForCalculator> screwCalculator(ConnectionPool connection, int carportLengthInCm, int carportWidthInCm, int roofPladeWidthInCm, String roofName) throws DatabaseException {
        screwList = new ArrayList<>();



        return screwList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static ArrayList<ScrewForCalculator> getScrewList() {
        return screwList;
    }

    public static void setScrewList(ArrayList<ScrewForCalculator> screwList) {
        ScrewForCalculator.screwList = screwList;
    }
}
