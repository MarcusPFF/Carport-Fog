package app.entities.forCalculator;

import app.persistence.Calculator.PoleCalculator;

import java.util.ArrayList;

public class RoofForCalculator {

    private String name;
    private int totalRoofWidthInCm;
    private int totalRoofLengthInCm;

    private static ArrayList<RoofForCalculator> roofList;

    public RoofForCalculator(String name, int totalRoofWidthInCm, int totalRoofLengthInCm) {
        this.name = name;
        this.totalRoofWidthInCm = totalRoofWidthInCm;
        this.totalRoofLengthInCm = totalRoofLengthInCm;

    }


//    public ArrayList<WoodForCalculator> woodCalculator(int carportLengthInCm, int carportWidthInCm, int shedLengthInCm, int shedWidthInCm) {
//        roofList = new ArrayList<>();
//        return roofList;
//    }

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
