package app.persistence.calculator;

import app.entities.forCalculator.MountForCalculator;
import app.entities.forCalculator.RoofForCalculator;
import app.entities.forCalculator.ScrewForCalculator;
import app.entities.forCalculator.WoodForCalculator;
import app.exceptions.DatabaseException;
import app.persistence.connection.ConnectionPool;
import app.persistence.mappers.OfferMapper;
import app.persistence.mappers.PriceAndMaterialMapper;

import java.util.ArrayList;

public class PriceCalculator {
    private ConnectionPool connectionPool;
    private OfferMapper offerMapper;
    private PriceAndMaterialMapper priceAndMaterialMapper;

    public PriceCalculator() {
        offerMapper = new OfferMapper();
        priceAndMaterialMapper = new PriceAndMaterialMapper();
    }

    public float calculateTotalOfferSalesPrice(ConnectionPool connection, float totalOfferExpensePrice) throws DatabaseException {
        float totalOfferSalesPrice = 0;
        int markupPercentage;

        markupPercentage = priceAndMaterialMapper.getMarkupPercentageFromExpensePrice(connection, totalOfferExpensePrice);

        totalOfferSalesPrice = totalOfferExpensePrice * (float) (markupPercentage / 100.00);

        return totalOfferSalesPrice;
    }

    public float calculateTotalOfferExpensePrice(ConnectionPool connection, ArrayList<WoodForCalculator> woodList, ArrayList<RoofForCalculator> roofList, ArrayList<MountForCalculator> mountList, ArrayList<ScrewForCalculator> screwList) throws DatabaseException {
        float totalOfferExpensePrice = 0;

        totalOfferExpensePrice += calculateTotalWoodListPrice(connection, woodList);
        totalOfferExpensePrice += calculateTotalRoofListPrice(connection, roofList);
        totalOfferExpensePrice += calculateTotalMountListPrice(connection, mountList);
        totalOfferExpensePrice += calculateTotalScrewListPrice(connection, screwList);

        return totalOfferExpensePrice;
    }

    public float calculateTotalWoodListPrice(ConnectionPool connection, ArrayList<WoodForCalculator> woodList) throws DatabaseException {
        float totalWoodExpensePrice = 0;

        for (WoodForCalculator woodForCalculator : woodList) {
            int woodDimensionId;
            int woodTypeId;
            int woodTreatmentId;
            int quantity;
            float pricePrMeter;

            woodDimensionId = woodForCalculator.getWoodDimensionId();
            woodTypeId = woodForCalculator.getWoodTypeId();
            woodTreatmentId = woodForCalculator.getWoodTreatmentId();
            pricePrMeter = priceAndMaterialMapper.getTotalWoodPrice(connection, woodTypeId, woodDimensionId, woodTreatmentId);
            quantity = woodForCalculator.getAmount();

            totalWoodExpensePrice = totalWoodExpensePrice + calculateWoodPrice(connection, pricePrMeter, quantity, woodDimensionId);
        }

        return totalWoodExpensePrice;
    }

    public float calculateTotalRoofListPrice(ConnectionPool connection, ArrayList<RoofForCalculator> roofList) throws DatabaseException {
        float totalRoofExpensePrice = 0;

        for (RoofForCalculator roofForCalculator : roofList) {
            int roofId;
            int quantity;
            float pricePrRoofSheet;

            roofId = roofForCalculator.getRoofId();
            pricePrRoofSheet = priceAndMaterialMapper.getRoofPrice(connection, roofId);
            quantity = roofForCalculator.getAmount();

            totalRoofExpensePrice += calculatePriceForEveryCategoryExceptWood(connection, pricePrRoofSheet, quantity);
        }

        return totalRoofExpensePrice;
    }

    public float calculateTotalMountListPrice(ConnectionPool connection, ArrayList<MountForCalculator> mountList) throws DatabaseException {
        float totalMountExpensePrice = 0;

        for (MountForCalculator mountForCalculator : mountList) {
            int mountId;
            int quantity;
            float pricePrPiece;

            mountId = mountForCalculator.getMountId();
            pricePrPiece = priceAndMaterialMapper.getMountPrice(connection, mountId);
            quantity = mountForCalculator.getAmount();

            totalMountExpensePrice += calculatePriceForEveryCategoryExceptWood(connection, pricePrPiece, quantity);
        }

        return totalMountExpensePrice;
    }

    public float calculateTotalScrewListPrice(ConnectionPool connection, ArrayList<ScrewForCalculator> screwList) throws DatabaseException {
        float totalScrewExpensePrice = 0;

        for (ScrewForCalculator screwForCalculator : screwList) {
            int mountId;
            int quantity;
            float pricePrContainer;

            mountId = screwForCalculator.getScrewId();
            pricePrContainer = priceAndMaterialMapper.getScrewsPrice(connection, mountId);
            quantity = screwForCalculator.getAmount();

            totalScrewExpensePrice += calculatePriceForEveryCategoryExceptWood(connection, pricePrContainer, quantity);
        }

        return totalScrewExpensePrice;
    }

    public float calculateWoodPrice(ConnectionPool connection, float meterPrice, int quantity, int woodDimensionId) throws DatabaseException {
        int woodLengthInCm;
        int totalWoodLengthInCm;
        float totalWoodLengthInMeter;
        float totalPrice;

        woodLengthInCm = offerMapper.getWoodLengthFromWoodDimensionId(connection, woodDimensionId);
        totalWoodLengthInCm = woodLengthInCm * quantity;
        totalWoodLengthInMeter = (float) (totalWoodLengthInCm / 100.0);
        totalPrice = totalWoodLengthInMeter * meterPrice;

        return totalPrice;
    }

    public float calculatePriceForEveryCategoryExceptWood(ConnectionPool connection, float price, int quantity) throws DatabaseException {
        float totalPrice;

        totalPrice = quantity * price;

        return totalPrice;
    }

    public int getMarkupPercentageFromCurrentSalesPrice(float totalSalesPrice, float totalExpensesPrice) {
        int markupPercentage;

        markupPercentage = (int) (((totalSalesPrice / totalExpensesPrice) * 100) - 100);

        return markupPercentage;
    }

}
