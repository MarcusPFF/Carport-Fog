package app.entities.forCalculator;

import app.exceptions.DatabaseException;
import app.persistence.Calculator.BoardCalculator;
import app.persistence.Calculator.NoggingCalculator;
import app.persistence.Calculator.PoleCalculator;
import app.persistence.Calculator.RafterCalculator;
import app.persistence.connection.ConnectionPool;

import java.util.ArrayList;

public class WoodForCalculator {

    private String name;
    private int amount;
    private int woodDimensionId;
    private int treatmentId;
    private int woodTypeId;
    private PoleCalculator poleCalculator;
    private RafterCalculator rafterCalculator;
    private NoggingCalculator noggingCalculator;
    private BoardCalculator boardCalculator;

    private static ArrayList<WoodForCalculator> woodList;

    public WoodForCalculator(String name, int amount, int woodDimensionId, int treatmentId, int woodTypeId) {
        this.name = name;
        this.amount = amount;
        this.woodDimensionId = woodDimensionId;
        this.treatmentId = treatmentId;
        this.woodTypeId = woodTypeId;
    }

    //Pole = Stolpe
    //Nogging = Ragle
    //Rafter = Spær
    //Fascia board = Understernbræt
    //Barge board = Oversternbræt
    //Drip cap = Vandbræt

    public ArrayList<WoodForCalculator> woodCalculator(ConnectionPool connection, int carportLengthInCm, int carportWidthInCm, int shedLengthInCm, int shedWidthInCm, int carportHeightInCm) throws DatabaseException {
        woodList = new ArrayList<>();

        //stolper
        woodList.add(poleCalculator.poleCalculator(connection, carportLengthInCm, carportWidthInCm, shedLengthInCm, shedWidthInCm, 100, 100, carportHeightInCm,"Stolpe", "Trykimprægneret"));

        //ragler
        woodList.add(noggingCalculator.noggingCalculator(connection, carportLengthInCm, carportWidthInCm, 45, 95, "Spær", "Ubehandlet"));

        //spær
        woodList.add(rafterCalculator.rafterForRoofCalculator(connection, carportLengthInCm, carportWidthInCm, "Spær", "Ubehandlet"));
        woodList.add(rafterCalculator.rafterForShedLengthCalculator(connection, shedLengthInCm,"Spær", "Ubehandlet"));
        woodList.add(rafterCalculator.rafterForShedWidthCalculator(connection, shedWidthInCm,"Spær", "Ubehandlet"));

        //brædder
        woodList.add(boardCalculator.shedBoardCalculator(connection, shedLengthInCm, shedWidthInCm, "Bræt", "Trykimprægneret", 100, 20, carportHeightInCm, 10));
        woodList.add(boardCalculator.fasciaBoardFrontAndBackCalculator(connection, carportWidthInCm,"Bræt", "Trykimprægneret", 200, 25));
        woodList.add(boardCalculator.fasciaBoardSidesCalculator(connection, carportLengthInCm,"Bræt", "Trykimprægneret", 200, 25));
        woodList.add(boardCalculator.bargeBoardFrontCalculator(connection, carportWidthInCm,"Bræt", "Trykimprægneret", 125, 25));
        woodList.add(boardCalculator.bargeBoardSidesCalculator(connection, carportLengthInCm,"Bræt", "Trykimprægneret", 125, 25));
        woodList.add(boardCalculator.dripCapForBoardFrontCalculator(connection, carportWidthInCm,"Bræt", "Trykimprægneret", 100, 20));
        woodList.add(boardCalculator.dripCapForBoardSidesCalculator(connection, carportLengthInCm,"Bræt", "Trykimprægneret", 100, 20));

        return woodList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getWoodDimensionId() {
        return woodDimensionId;
    }

    public void setWoodDimensionId(int woodDimensionId) {
        this.woodDimensionId = woodDimensionId;
    }

    public int getTreatmentId() {
        return treatmentId;
    }

    public void setTreatmentId(int treatmentId) {
        this.treatmentId = treatmentId;
    }

    public int getWoodTypeId() {
        return woodTypeId;
    }

    public void setWoodTypeId(int woodTypeId) {
        this.woodTypeId = woodTypeId;
    }

    public static ArrayList<WoodForCalculator> getWoodList() {
        return woodList;
    }

    public static void setWoosList(ArrayList<WoodForCalculator> woodList) {
        WoodForCalculator.woodList = woodList;
    }
}
