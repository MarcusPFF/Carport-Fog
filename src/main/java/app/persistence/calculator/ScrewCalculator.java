package app.persistence.calculator;

import app.entities.forCalculator.ScrewForCalculator;
import app.exceptions.DatabaseException;
import app.persistence.connection.ConnectionPool;
import app.persistence.mappers.OfferMapper;

public class ScrewCalculator {
    private OfferMapper offerMapper;
    private BoardCalculator boardCalculator;
    private MountCalculator mountCalculator;
    private RafterCalculator rafterCalculator;
    private PoleCalculator poleCalculator;
    private NoggingCalculator noggingCalculator;

    //Pole = Stolpe
    //Nogging = Reglar
    //Rafter = Spær
    //RafterBeam = Rem
    //Fascia board = Understernbræt
    //Barge board = Oversternbræt
    //Drip cap = Vandbræt
    //PlumbersTape = Hulbånd

    public ScrewCalculator() {
        offerMapper = new OfferMapper();
        boardCalculator = new BoardCalculator();
        mountCalculator = new MountCalculator();
        rafterCalculator = new RafterCalculator();
        poleCalculator = new PoleCalculator();
        noggingCalculator = new NoggingCalculator();
    }

    public ScrewForCalculator screwForRoofCalculator(ConnectionPool connection, int carportLengthInCm, int carportWidthInCm, String screwName) throws DatabaseException {
        int amount;
        int amountPrContainer;
        int screwId;

        amountPrContainer = offerMapper.getAmountPrContainerFromScrewName(connection, screwName);
        amount = screwsForRoofPackAmountCalculator(carportLengthInCm, carportWidthInCm, amountPrContainer);
        screwId = offerMapper.getScrewIdFromScrewName(connection, screwName);

        return new ScrewForCalculator(screwName, amount, screwId, "Skruer til tagplader.");
    }

    public ScrewForCalculator screwForFasciaAndBargeBoardCalculator(ConnectionPool connection, int carportLengthInCm, int carportWidthInCm, String screwName) throws DatabaseException {
        int amount;
        int amountPrContainer;
        int totalFasciaAndBargeBoardAndDripCapLengthInCm;
        int screwId;

        amountPrContainer = offerMapper.getAmountPrContainerFromScrewName(connection, screwName);
        screwId = offerMapper.getScrewIdFromScrewName(connection, screwName);
        amount = screwsForFasciaAndBargeBoardPackAmountCalculator(carportLengthInCm, carportWidthInCm, amountPrContainer);

        return new ScrewForCalculator(screwName, amount, screwId, "Skruer til montering af stern & vandbræt.");
    }

    public ScrewForCalculator screwForRafterMountsCalculator(ConnectionPool connection, int carportLengthInCm, int carportWidthInCm, String screwName, int shedLengthInCm, int shedWidthInCm) throws DatabaseException {
        int amount;
        int amountPrContainer;
        int screwId;
        int rafterAmount;
        int rafterBeamAmount;
        int rafterMountsAmount;
        int noggingAmountSides;
        int noggingAmountFrontAndBack;
        int noggingAmount;
        int poleAmountX;
        int poleAmountY;

        rafterAmount = rafterCalculator.rafterAmountForRoofCalculator(carportLengthInCm);
        rafterBeamAmount = rafterCalculator.rafterBeamAmountCalculator(carportWidthInCm);
        rafterMountsAmount = mountCalculator.leftRafterMountsAmountCalculator(rafterAmount, rafterBeamAmount) + mountCalculator.rightRafterMountsAmountCalculator(rafterAmount);
        poleAmountX = poleCalculator.poleAmountXCalculator(carportLengthInCm);
        poleAmountY = poleCalculator.poleAmountYCalculator(carportWidthInCm);
        noggingAmountSides = noggingCalculator.noggingForShedSidesAmountCalculator(shedLengthInCm, shedWidthInCm, carportLengthInCm, carportWidthInCm, poleAmountX, poleAmountY);
        noggingAmountFrontAndBack = noggingCalculator.noggingForShedFrontAndBackAmountCalculator(shedWidthInCm, carportWidthInCm, poleAmountY);
        noggingAmount = noggingAmountFrontAndBack + noggingAmountSides;

        amountPrContainer = offerMapper.getAmountPrContainerFromScrewName(connection, screwName);
        screwId = offerMapper.getScrewIdFromScrewName(connection, screwName);
        amount = screwsForMountsPackAmountCalculator(rafterMountsAmount, noggingAmount, amountPrContainer);

        return new ScrewForCalculator(screwName, amount, screwId, "Skruer til montering af universalbeslag, vinkelbeslag og hulbånd.");
    }

    public ScrewForCalculator screwsForShedBoardsCalculator(ConnectionPool connection, int shedLengthInCm, int shedWidthInCm, String screwName, int boardWidthInMm, int spareBoardAmount) throws DatabaseException {
        int amount;
        int screwId;
        int amountPrContainer;
        int boardAmount;

        boardAmount = boardCalculator.shedBoardAmountCalculator(shedLengthInCm, shedWidthInCm, boardWidthInMm, spareBoardAmount);
        amountPrContainer = offerMapper.getAmountPrContainerFromScrewName(connection, screwName);
        screwId = offerMapper.getScrewIdFromScrewName(connection, screwName);
        amount = screwsForShedBoardsPackAmountCalculator(boardAmount, amountPrContainer);

        return new ScrewForCalculator(screwName, amount, screwId, "Til montering af skurets beklædning.");
    }

    public ScrewForCalculator boltsForRafterBeamCalculator(ConnectionPool connection, int carportLengthInCm, int carportWidthInCm, String screwName) throws DatabaseException {
        int amount;
        int screwId;
        int poleXAmount;
        int rafterBeamAmount;

        poleXAmount = poleCalculator.poleAmountXCalculator(carportLengthInCm);
        rafterBeamAmount = rafterCalculator.rafterBeamAmountCalculator(carportWidthInCm);
        amount = boltsForRafterBeamAmountCalculator(rafterBeamAmount, poleXAmount);
        screwId = offerMapper.getScrewIdFromScrewName(connection, screwName);

        return new ScrewForCalculator(screwName, amount, screwId, "Bolte til montering af rem på stolper.");
    }

    public ScrewForCalculator plumbersTapeCalculator(ConnectionPool connection, int carportLengthInCm, int carportWidthInCm, int shedLengthInCm, String screwName) throws DatabaseException {
        int amount;
        int screwId;
        int amountPrContainer;

        amountPrContainer = offerMapper.getAmountPrContainerFromScrewName(connection, screwName);
        screwId = offerMapper.getScrewIdFromScrewName(connection, screwName);
        amount = plumbersTapeAmountCalculator(shedLengthInCm, carportLengthInCm, carportWidthInCm, amountPrContainer);

        return new ScrewForCalculator(screwName, amount, screwId, "Til vindkryds på spær.");
    }

    public int screwsForRoofPackAmountCalculator(int carportLengthInCm, int carportWidthInCm, float amountPrContainer) {
        int amountOfScrews = (int) Math.ceil(((carportWidthInCm / 100.0f) * (carportLengthInCm / 100.0f)) * 12);
        int amountOfPacks = (int) Math.ceil(amountOfScrews / amountPrContainer);

        return amountOfPacks;
    }

    public int screwsForFasciaAndBargeBoardPackAmountCalculator(int carportLengthInCm, int carportWidthInCm, float amountPrContainer) {
        int amountOfScrews = 0;
        int amountOfPacks;

        amountOfScrews += 6 * ((1 + (int) Math.floor((carportLengthInCm / 60.0)) * 3)); //Skruer til sidderne
        amountOfScrews += 4 * ((1 + (int) Math.floor((carportWidthInCm / 60.0)) * 3));  //Skruer til foran og bagved

        amountOfPacks = (int) Math.ceil(amountOfScrews / amountPrContainer);

        return amountOfPacks;
    }

    public int screwsForMountsPackAmountCalculator(int rafterMountsAmount, int noggingAmount, float amountPrContainer) {
        int amountOfScrews;
        int amountOfPacks;

        amountOfScrews = (rafterMountsAmount * 9) + 8;
        amountOfScrews += ((noggingAmount * 2) * 4);
        amountOfPacks = (int) Math.ceil(amountOfScrews / amountPrContainer);

        return amountOfPacks;
    }

    public int screwsForShedBoardsPackAmountCalculator(int boardAmount, float amountPrContainer) {
        int amountOfScrews = boardAmount * 9;
        int amountOfPacks = (int) Math.ceil(amountOfScrews / amountPrContainer);

        return amountOfPacks;
    }

    public int boltsForRafterBeamAmountCalculator(int rafterBeamAmount, int poleAmount) {
        int amountOfBolts = (rafterBeamAmount * poleAmount) * 3;

        return amountOfBolts;
    }

    public int plumbersTapeAmountCalculator(int shedLengthInCm, int carportLengthInCm, int carportWidthInCm, int metersPrRollInCm) {
        int a;
        int b;
        float c;
        int amountOfRolls;

        a = carportLengthInCm - 75 - shedLengthInCm;
        b = carportWidthInCm - 60;
        c = (a * a) + (b * b);
        c = (float) Math.sqrt(c);

        amountOfRolls = (int) Math.ceil(c / metersPrRollInCm);
        return amountOfRolls;
    }
}
