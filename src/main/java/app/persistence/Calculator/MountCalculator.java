package app.persistence.Calculator;

import app.entities.forCalculator.MountForCalculator;
import app.exceptions.DatabaseException;
import app.persistence.connection.ConnectionPool;
import app.persistence.mappers.OfferMapper;

public class MountCalculator {
    private OfferMapper offerMapper;
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

    public MountForCalculator leftRafterMountCalculator(ConnectionPool connection, int carportLengthInCm, int carportWidthInCm, String mountName) throws DatabaseException {
        int amount;
        int rafterAmount;
        int rafterBeamAmount;
        int mountId;

        rafterAmount = rafterCalculator.rafterAmountForRoofCalculator(carportLengthInCm);
        rafterBeamAmount = rafterCalculator.rafterAmountForRoofCalculator(carportWidthInCm);

        amount = leftRafterMountsAmountCalculator(rafterAmount, rafterBeamAmount);
        mountId = offerMapper.getMountIdFromMountName(connection, mountName);

        return new MountForCalculator(mountName, amount, mountId, "Til montering af spær på rem.");
    }

    public MountForCalculator rightRafterMountCalculator(ConnectionPool connection, int carportLengthInCm, int carportWidthInCm, String mountName) throws DatabaseException {
        int amount;
        int rafterAmount;
        int mountId;

        rafterAmount = rafterCalculator.rafterAmountForRoofCalculator(carportLengthInCm);
        amount = rightRafterMountsAmountCalculator(rafterAmount);
        mountId = offerMapper.getMountIdFromMountName(connection, mountName);

        return new MountForCalculator(mountName, amount, mountId, "Til montering af spær på rem.");
    }

    public MountForCalculator squareBracketsForRafterMountCalculator(ConnectionPool connection, int carportLengthInCm, int carportWidthInCm, String mountName) throws DatabaseException {
        int amount;
        int poleAmountY;
        int poleAmountX;
        int poleAmount;

        int mountId;

        poleAmountX = poleCalculator.poleAmountXCalculator(carportLengthInCm);
        poleAmountY = poleCalculator.poleAmountYCalculator(carportWidthInCm);
        poleAmount = poleAmountX * poleAmountY;
        amount = squareBracketsForRafterMountsAmountCalculator(poleAmount);
        mountId = offerMapper.getMountIdFromMountName(connection, mountName);

        return new MountForCalculator(mountName, amount, mountId, "Til montering af rem på stolper.");
    }

    public MountForCalculator angleMountCalculator(ConnectionPool connection, int carportLengthInCm, int carportWidthInCm, int shedLengthInCm, int shedWidthInCm, String mountName) throws DatabaseException {
        int amount;
        int poleAmountX;
        int poleAmountY;
        int noggingAmountSides;
        int noggingAmountFrontAndBack;
        int noggingAmount;
        int mountId;

        poleAmountX = poleCalculator.poleAmountXCalculator(carportLengthInCm);
        poleAmountY = poleCalculator.poleAmountYCalculator(carportWidthInCm);
        noggingAmountSides = noggingCalculator.noggingForShedSidesAmountCalculator(shedLengthInCm, shedWidthInCm, carportLengthInCm, carportWidthInCm, poleAmountX, poleAmountY);
        noggingAmountFrontAndBack = noggingCalculator.noggingForShedFrontAndBackAmountCalculator(shedWidthInCm, carportWidthInCm, poleAmountY);
        noggingAmount = noggingAmountFrontAndBack + noggingAmountSides;

        amount = angleMountAmountCalculator(noggingAmount);
        mountId = offerMapper.getMountIdFromMountName(connection, mountName);

        return new MountForCalculator(mountName, amount, mountId, "Til montering af løsholter i skur.");
    }

    public MountForCalculator stableDoorHandleCalculator(ConnectionPool connection, int amountOfDoorsForShed, String mountName) throws DatabaseException {
        int amount;
        int mountId;

        amount = stableDoorHandleAmountCalculator(amountOfDoorsForShed);
        mountId = offerMapper.getMountIdFromMountName(connection, mountName);

        return new MountForCalculator(mountName, amount, mountId, "Til lås på dør i skur.");
    }

    public MountForCalculator hingeForDoorCalculator(ConnectionPool connection, int amountOfDoorsForShed, String mountName) throws DatabaseException {
        int amount;
        int mountId;

        amount = hingeForDoorAmountCalculator(amountOfDoorsForShed);
        mountId = offerMapper.getMountIdFromMountName(connection, mountName);

        return new MountForCalculator(mountName, amount, mountId, "Til skurdør.");
    }

    public int leftRafterMountsAmountCalculator(int rafterAmount, int rafterBeamAmount) {
        int leftRafterMountAmount;
        leftRafterMountAmount =  rafterAmount * (rafterBeamAmount - 1);

        return leftRafterMountAmount;
    }

    public int rightRafterMountsAmountCalculator(int rafterAmount) {
        int rightRafterMountAmount;
        rightRafterMountAmount =  rafterAmount;

        return rightRafterMountAmount;
    }

    public int squareBracketsForRafterMountsAmountCalculator(int poleAmount) {
        int squareBracketsAmount;
        squareBracketsAmount =  poleAmount * 2;

        return squareBracketsAmount;
    }

    public int angleMountAmountCalculator( int noggingAmount) {
        int angleMountAmount;
        angleMountAmount =  noggingAmount * 2;

        return angleMountAmount;
    }

    public int stableDoorHandleAmountCalculator(int amountOfDoorsForTheShed) {
        int amount;
        amount = amountOfDoorsForTheShed;

        return amount;
    }

    public int hingeForDoorAmountCalculator(int amountOfDoorsForTheShed) {
        int amount;
        amount = amountOfDoorsForTheShed * 2;

        return amount;
    }

}
