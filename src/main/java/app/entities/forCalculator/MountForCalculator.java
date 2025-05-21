package app.entities.forCalculator;

import app.exceptions.DatabaseException;
import app.persistence.Calculator.MountCalculator;
import app.persistence.connection.ConnectionPool;

import java.util.ArrayList;

public class MountForCalculator {

    private String name;
    private int amount;
    private int mountId;
    private String description;
    private MountCalculator mountCalculator;

    private static ArrayList<MountForCalculator> mountList;

    public MountForCalculator(String name, int amount, int mountId,  String description) {
        this.name = name;
        this.amount = amount;
        this.mountId = mountId;
        this.description = description;
    }

    public MountForCalculator() {
    }

    public ArrayList<MountForCalculator> mountListCalculator(ConnectionPool connection, int carportLengthInCm, int carportWidthInCm, int shedLengthInCm, int shedWidthInCm, int amountOfDoorsForShed) throws DatabaseException {
        mountList = new ArrayList<>();

        //Venstre universalbeslag for spærs montering på rem. (regnes i stk)
        mountList.add(mountCalculator.leftRafterMountCalculator(connection, carportLengthInCm, carportWidthInCm, "Universalbeslag 190 mm venstre"));

        //Højre universalbeslag for spærs montering på rem. (regnes i stk)
        mountList.add(mountCalculator.rightRafterMountCalculator(connection, carportLengthInCm, "Universalbeslag 190 mm højre"));

        //Firkantskiver til hvert side af træet hvor boltene kommer til at spænde ind på. (regnes i stk)
        mountList.add(mountCalculator.squareBracketsForRafterMountCalculator(connection, carportLengthInCm, carportWidthInCm, "Firkantskiver 40 x 40 x 11 mm"));

        //Vinkelbeslag til at montere regler i skuret til stolper. (regnes i stk)
        mountList.add(mountCalculator.angleMountCalculator(connection, carportLengthInCm, carportWidthInCm, shedLengthInCm, shedWidthInCm, "Vinkelbeslag 50 x 50 x 35 mm"));

        //Stalddørsgreb til montering som håndtag på dør til skur. (regnes i stk)
        mountList.add(mountCalculator.stableDoorHandleCalculator(connection, amountOfDoorsForShed, "Stalddørsgreb 50 x 75 mm"));

        //T-Hængsler til montering af dør på skur. (regnes i stk)
        mountList.add(mountCalculator.hingeForDoorCalculator(connection, amountOfDoorsForShed, "T-Hængsel 390 mm"));

        return mountList;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public int getMountId() {
        return mountId;
    }

    public String getDescription() {
        return description;
    }

    public static ArrayList<MountForCalculator> getMountList() {
        return mountList;
    }
}
