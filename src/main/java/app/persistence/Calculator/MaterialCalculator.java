package app.persistence.Calculator;

import app.entities.forCalculator.MountForCalculator;
import app.entities.forCalculator.RoofForCalculator;
import app.entities.forCalculator.ScrewForCalculator;
import app.entities.forCalculator.WoodForCalculator;
import app.persistence.mappers.OfferMapper;
import app.exceptions.DatabaseException;
import app.persistence.connection.ConnectionPool;

import java.time.LocalDate;
import java.util.Date;
import java.util.ArrayList;

public class MaterialCalculator {
    private MountForCalculator mountForCalculator;
    private RoofForCalculator roofForCalculator;
    private ScrewForCalculator screwForCalculator;
    private WoodForCalculator woodForCalculator;
    private OfferMapper offerMapper;
    private PriceCalculator priceCalculator;

    public MaterialCalculator() {
    }

    public int offerCalculator(ConnectionPool connection, int carportLengthInCm, int carportWidthInCm, int carportHeightInCm, int shedLengthInCm, int shedWidthInCm, int amountOfDoorsForTheShed, String customerMail, String customerFirstName, String customerLastName, String customerStreetName, int customerHouseNumber, int customerZipCode, int roofPlateWidthInCm, String roofName, int shedBoardWidthInMm, int amountOfSpareBoardsForShed) throws DatabaseException {
        ArrayList<MountForCalculator> mountList;
        ArrayList<RoofForCalculator> roofList;
        ArrayList<ScrewForCalculator> screwList;
        ArrayList<WoodForCalculator> woodList;
        int offerId;
        int sellerId;
        int customerId;
        float totalOfferExpensePrice = 0;
        float totalOfferSalesPrice = 0;
        LocalDate expirationDate;


        customerId = offerMapper.createNewCustomerIfAlreadyExistGetCustomerIdFromMail(connection, customerMail, customerFirstName, customerLastName, customerStreetName, customerHouseNumber, customerZipCode);
        sellerId = offerMapper.getRandomSellerId(connection);

        offerId = offerMapper.createOffer(connection, totalOfferExpensePrice, totalOfferSalesPrice, sellerId, carportLengthInCm, carportWidthInCm, shedLengthInCm, shedWidthInCm, customerId);

        mountList = mountForCalculator.mountListCalculator(connection, carportLengthInCm, carportWidthInCm, shedLengthInCm, shedWidthInCm, amountOfDoorsForTheShed);
        roofList = roofForCalculator.roofListCalculator(connection,carportLengthInCm, carportWidthInCm, roofPlateWidthInCm, roofName);
        screwList = screwForCalculator.screwListCalculator(connection, carportLengthInCm, carportWidthInCm, shedLengthInCm, shedWidthInCm, shedBoardWidthInMm, amountOfSpareBoardsForShed);
        woodList = woodForCalculator.woodListCalculator(connection, carportLengthInCm, carportWidthInCm, shedLengthInCm, shedWidthInCm, carportHeightInCm, amountOfDoorsForTheShed);

        offerMapper.createMountsList(connection, mountList, offerId);
        offerMapper.createRoofList(connection, roofList, offerId);
        offerMapper.createScrewsList(connection, screwList, offerId);
        offerMapper.createWoodList(connection, woodList, offerId);

        totalOfferExpensePrice = priceCalculator.calculateTotalOfferExpensePrice(connection, woodList, roofList, mountList, screwList);
        totalOfferSalesPrice = priceCalculator.calculateTotalOfferSalesPrice(connection, totalOfferExpensePrice);

        offerMapper.updateTotalExpensesPrice(connection, totalOfferExpensePrice, offerId);
        offerMapper.updateTotalSalesPrice(connection, totalOfferSalesPrice, offerId);

        return offerId;
    }

}