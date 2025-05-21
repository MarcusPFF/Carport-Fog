package app.entities.forCalculator;

import app.exceptions.DatabaseException;
import app.persistence.calculator.ScrewCalculator;
import app.persistence.connection.ConnectionPool;

import java.util.ArrayList;

public class ScrewForCalculator {

    private String name;
    private int amount;
    private int screwId;
    private String description;
    private ScrewCalculator screwCalculator;

    private static ArrayList<ScrewForCalculator> screwList;

    public ScrewForCalculator(String name, int amount, int screwId,  String description) {
        this.name = name;
        this.amount = amount;
        this.screwId = screwId;
        this.description = description;
    }

    public ScrewForCalculator() {
    }

    public ArrayList<ScrewForCalculator> screwListCalculator(ConnectionPool connection, int carportLengthInCm, int carportWidthInCm, int shedLengthInCm, int shedWidthInCm, int boardForShedWidthInMm, int spareShedBoardAmount) throws DatabaseException {
        screwList = new ArrayList<>();

        //Skruer til at montere tagplader. (regnes i antal bokse)
        screwList.add(screwCalculator.screwForRoofCalculator(connection, carportLengthInCm, carportWidthInCm, "Plastmo bundskruer 200 stk"));

        //Skruer til at montere stern og vandbræt. (regnes i antal bokse)
        screwList.add(screwCalculator.screwForFasciaAndBargeBoardCalculator(connection, carportLengthInCm, carportWidthInCm, "4,5 x 60 mm. skruer 200 stk"));

        //Skruer til beslag for spærs montering på rem. (regnes i antal bokse)
        screwList.add(screwCalculator.screwForRafterMountsCalculator(connection, carportLengthInCm, carportWidthInCm, "4,0 x 50 mm. beslagskruer 250 stk", shedLengthInCm, shedWidthInCm));

        //Skruer til at montere beklædnings brædderne på skur. (regnes i antal bokse)
        screwList.add(screwCalculator.screwsForShedBoardsCalculator(connection, shedLengthInCm, shedWidthInCm, "4,5 x 50 mm. Skruer 300 stk", boardForShedWidthInMm, spareShedBoardAmount));

        //Bolte til at montere remmene på stolper. (regnes i stk)
        screwList.add(screwCalculator.boltsForRafterBeamCalculator(connection, carportLengthInCm, carportWidthInCm, "Bræddebolt 10 x 120 mm"));

        //Hulbånd til vindkryds. (regnes i antal ruller)
        screwList.add(screwCalculator.plumbersTapeCalculator(connection, carportLengthInCm, carportWidthInCm, shedLengthInCm, "Hulbånd 1x20 mm. 10 mtr"));

        return screwList;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public int getScrewId() {
        return screwId;
    }

    public String getDescription() {
        return description;
    }

    public static ArrayList<ScrewForCalculator> getScrewList() {
        return screwList;
    }
}
