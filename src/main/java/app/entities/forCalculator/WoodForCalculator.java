package app.entities.forCalculator;

import app.persistence.Calculator.BoardCalculator;
import app.persistence.Calculator.NoggingCalculator;
import app.persistence.Calculator.PoleCalculator;
import app.persistence.Calculator.RafterCalculator;

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


    public ArrayList<WoodForCalculator> woodCalculator(int carportLengthInCm, int carportWidthInCm, int shedLengthInCm, int shedWidthInCm, int carportHeightInCm) {
        woodList = new ArrayList<>();

        //stolper
        woodList.add(poleCalculator.poleCalculator(carportLengthInCm, carportWidthInCm, shedLengthInCm, shedWidthInCm, 100, 100, carportHeightInCm,"Stolpe", "Trykimprægneret"));

        //ragler
        woodList.add(noggingCalculator.noggingCalculator(carportLengthInCm, carportWidthInCm, 45, 95, "Spær", "Ubehandlet"));

        //spær
        woodList.add(rafterCalculator.rafterForRoofCalculator(carportLengthInCm, carportWidthInCm, "Spær", "Ubehandlet"));
        woodList.add(rafterCalculator.rafterForShedLengthCalculator(shedLengthInCm,"Spær", "Ubehandlet"));
        woodList.add(rafterCalculator.rafterForShedWidthCalculator(shedWidthInCm,"Spær", "Ubehandlet"));

        //brædder
        woodList.add(boardCalculator.shedBoardCalculator(shedLengthInCm, shedWidthInCm, "Bræt", "Trykimprægneret", 100, 20, carportHeightInCm));
        woodList.add(boardCalculator.fasciaBoardFrontAndBackCalculator(carportWidthInCm,"Bræt", "Trykimprægneret", 200, 25));
        woodList.add(boardCalculator.fasciaBoardSidesCalculator(carportLengthInCm,"Bræt", "Trykimprægneret", 200, 25));
        woodList.add(boardCalculator.bargeBoardFrontCalculator(carportWidthInCm,"Bræt", "Trykimprægneret", 125, 25));
        woodList.add(boardCalculator.bargeBoardSidesCalculator(carportLengthInCm,"Bræt", "Trykimprægneret", 125, 25));
        woodList.add(boardCalculator.dripCapForBoardFrontCalculator(carportWidthInCm,"Bræt", "Trykimprægneret", 100, 20));
        woodList.add(boardCalculator.dripCapForBoardSidesCalculator(carportLengthInCm,"Bræt", "Trykimprægneret", 100, 20));


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
