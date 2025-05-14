package app.entities.forCalculator;

import app.exceptions.DatabaseException;
import app.persistence.connection.ConnectionPool;

import java.util.ArrayList;

public class MountForCalculator {

    private String name;
    private int amount;
    private int mountId;
    private String description;

    private static ArrayList<MountForCalculator> mountList;

    public MountForCalculator(String name, int amount, int mountId,  String description) {
        this.name = name;
        this.amount = amount;
        this.mountId = mountId;
        this.description = description;
    }

    public MountForCalculator() {
    }
    //TODO test det her, kunne ikke teste pga ingen connectionpool
    public ArrayList<MountForCalculator> mountCalculator(ConnectionPool connection, int carportLengthInCm, int carportWidthInCm, int roofPladeWidthInCm, String roofName) throws DatabaseException {
        mountList = new ArrayList<>();



        return mountList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static ArrayList<MountForCalculator> getMountList() {
        return mountList;
    }

    public static void setMountList(ArrayList<MountForCalculator> mountList) {
        MountForCalculator.mountList = mountList;
    }
}
