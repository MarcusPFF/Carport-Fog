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
    private int woodTreatmentId;
    private int woodTypeId;
    private String description;
    private PoleCalculator poleCalculator;
    private RafterCalculator rafterCalculator;
    private NoggingCalculator noggingCalculator;
    private BoardCalculator boardCalculator;

    private static ArrayList<WoodForCalculator> woodList;

    public WoodForCalculator(String name, int amount, int woodDimensionId, int woodTreatmentId, int woodTypeId, String description) {
        this.name = name;
        this.amount = amount;
        this.woodDimensionId = woodDimensionId;
        this.woodTreatmentId = woodTreatmentId;
        this.woodTypeId = woodTypeId;
        this.description = description;
    }

    public WoodForCalculator() {}

    public ArrayList<WoodForCalculator> woodListCalculator(ConnectionPool connection, int carportLengthInCm, int carportWidthInCm, int shedLengthInCm, int shedWidthInCm, int carportHeightInCm, int amountOfDoorsForTheShed) throws DatabaseException {
        woodList = new ArrayList<>();

        //Stolper. (regnes i stk)
        woodList.add(poleCalculator.poleCalculator(connection, carportLengthInCm, carportWidthInCm, shedLengthInCm, shedWidthInCm, 100, 100, carportHeightInCm,"Stolpe", "Trykimprægneret"));

        //Regler for siderne af skur. (regnes i stk)
        woodList.add(noggingCalculator.noggingForShedSidesCalculator(connection, 45, 95, shedLengthInCm, carportWidthInCm, shedLengthInCm,"Reglar", "Ubehandlet"));

        //Regler for forsiden og bagsiden af skur. (regnes i stk)
        woodList.add(noggingCalculator.noggingForShedFrontAndBackCalculator(connection, 45, 95, carportLengthInCm,"Reglar", "Ubehandlet"));

        //Lægter for z bag på døren til skuret (regnes i stk)
        woodList.add(noggingCalculator.noggingForZOnTheDoorCalculator(connection, 40, 75, 420,"Lægte","Ubehandlet", amountOfDoorsForTheShed));

        //Spær som Taget skal monteres på. (regnes i stk)
        woodList.add(rafterCalculator.rafterForRoofCalculator(connection, carportLengthInCm, carportWidthInCm, "Spær", "Ubehandlet"));

        //Remme som spær skal monteres på. (regnes i stk)
        woodList.add(rafterCalculator.rafterBeamCalculator(connection, carportLengthInCm, carportWidthInCm, "Spær", "Ubehandlet"));

        //Brædder til beklædning af skur. (regnes i stk)
        woodList.add(boardCalculator.shedBoardCalculator(connection, shedLengthInCm, shedWidthInCm, "Bræt", "Trykimprægneret", 100, 20, carportHeightInCm, 10));

        //Understernbrædder til forenden og bagende af carport. (regnes i stk)
        woodList.add(boardCalculator.fasciaBoardFrontAndBackCalculator(connection, carportWidthInCm,"Bræt", "Trykimprægneret", 200, 25));

        //Understernbrædder til siderne af carport. (regnes i stk)
        woodList.add(boardCalculator.fasciaBoardSidesCalculator(connection, carportLengthInCm,"Bræt", "Trykimprægneret", 200, 25));

        //Oversternbrædder til forende af carport. (regnes i stk)
        woodList.add(boardCalculator.bargeBoardFrontCalculator(connection, carportWidthInCm,"Bræt", "Trykimprægneret", 125, 25));

        //Oversternbrædder til siderne af carport. (regnes i stk)
        woodList.add(boardCalculator.bargeBoardSidesCalculator(connection, carportLengthInCm,"Bræt", "Trykimprægneret", 125, 25));

        //Vandbrædder til forenden af carport. (regnes i stk)
        woodList.add(boardCalculator.dripCapForBoardFrontCalculator(connection, carportWidthInCm,"Bræt", "Trykimprægneret", 100, 20));

        //Vandbrædder til siderne af carport. (regnes i stk)
        woodList.add(boardCalculator.dripCapForBoardSidesCalculator(connection, carportLengthInCm,"Bræt", "Trykimprægneret", 100, 20));

        return woodList;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public int getWoodDimensionId() {
        return woodDimensionId;
    }

    public int getWoodTreatmentId() {
        return woodTreatmentId;
    }

    public int getWoodTypeId() {
        return woodTypeId;
    }

    public String getDescription() {
        return description;
    }

    public static ArrayList<WoodForCalculator> getWoodList() {
        return woodList;
    }
}
