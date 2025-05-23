package app.persistence.calculator;

import app.entities.Offer;
import app.entities.forCalculator.MountForCalculator;
import app.entities.forCalculator.RoofForCalculator;
import app.entities.forCalculator.ScrewForCalculator;
import app.entities.forCalculator.WoodForCalculator;
import app.persistence.mappers.OfferMapper;
import app.exceptions.DatabaseException;
import app.persistence.connection.ConnectionPool;

import java.time.LocalDate;
import java.util.ArrayList;

public class MaterialCalculator {
    private static MountForCalculator mountForCalculator;
    private static RoofForCalculator roofForCalculator;
    private static ScrewForCalculator screwForCalculator;
    private static WoodForCalculator woodForCalculator;
    private static OfferMapper offerMapper;
    private static PriceCalculator priceCalculator;

    public MaterialCalculator() {
        mountForCalculator = new MountForCalculator();
        roofForCalculator = new RoofForCalculator();
        screwForCalculator = new ScrewForCalculator();
        woodForCalculator = new WoodForCalculator();
        offerMapper = new OfferMapper();
        priceCalculator = new PriceCalculator();
    }

    public static int offerCalculator(ConnectionPool connection, int carportLengthInCm, int carportWidthInCm, int carportHeightInCm, int shedLengthInCm, int shedWidthInCm, int amountOfDoorsForTheShed, String customerMail, String customerFirstName, String customerLastName, String customerStreetName, int customerHouseNumber, int customerZipCode, int customerPhoneNumber, int roofSheetWidthInCm, String roofName, int shedBoardWidthInMm, int amountOfSpareBoardsForShed) throws DatabaseException {
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


        customerId = offerMapper.createNewCustomerIfAlreadyExistGetCustomerIdFromMail(connection, customerMail, customerFirstName, customerLastName, customerStreetName, customerHouseNumber, customerZipCode, customerPhoneNumber);
        sellerId = offerMapper.getRandomSellerId(connection);

        offerId = offerMapper.createOffer(connection, totalOfferExpensePrice, totalOfferSalesPrice, sellerId, carportLengthInCm, carportWidthInCm, shedLengthInCm, shedWidthInCm, customerId);

        mountList = mountForCalculator.mountListCalculator(connection, carportLengthInCm, carportWidthInCm, shedLengthInCm, shedWidthInCm, amountOfDoorsForTheShed);
        roofList = roofForCalculator.roofListCalculator(connection,carportLengthInCm, carportWidthInCm, roofSheetWidthInCm, roofName);
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

    public static boolean offerEditCalculator(ConnectionPool connection, int offerId, int carportLengthInCm, int carportWidthInCm, int carportHeightInCm, int shedLengthInCm, int shedWidthInCm, int amountOfDoorsForTheShed, int roofSheetWidthInCm, String roofName, int shedBoardWidthInMm, int amountOfSpareBoardsForShed) throws DatabaseException {
        ArrayList<MountForCalculator> mountList;
        ArrayList<RoofForCalculator> roofList;
        ArrayList<ScrewForCalculator> screwList;
        ArrayList<WoodForCalculator> woodList;
        float totalOfferExpensePrice = 0;
        float totalOfferSalesPrice = 0;
        boolean dimensionsChanced;
        boolean dimensionsChancedBack = false;
        boolean materialListsDeleted;
        Offer offer;

        try {
            offer = offerMapper.getOfferFromOfferId(connection, offerId);
            dimensionsChanced = offerMapper.updateOfferDimensions(connection, offerId, carportLengthInCm, carportWidthInCm, shedLengthInCm, shedWidthInCm);

            if (dimensionsChanced) {
                materialListsDeleted = offerMapper.deleteMaterialListsByOfferId(connection, offerId);

                if (materialListsDeleted) {

                    mountList = mountForCalculator.mountListCalculator(connection, carportLengthInCm, carportWidthInCm, shedLengthInCm, shedWidthInCm, amountOfDoorsForTheShed);
                    roofList = roofForCalculator.roofListCalculator(connection, carportLengthInCm, carportWidthInCm, roofSheetWidthInCm, roofName);
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

                    return true;
                } else {
                    try {
                        dimensionsChancedBack = offerMapper.updateOfferDimensions(connection, offerId, offer.getCarportLength(), offer.getCarportWidth(), offer.getShedLength(), offer.getShedWidth());
                        if (dimensionsChancedBack) {
                            return false;
                        }
                    } catch (DatabaseException ex) {
                        throw new DatabaseException(ex, "Couldn't update offer dimensions back to original.");
                    }

                }

            }
            return false;
        } catch (DatabaseException ex) {
            throw new DatabaseException(ex, "Error fetching offer dimensions from database.");
        }


    }

}