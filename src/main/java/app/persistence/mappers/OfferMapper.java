package app.persistence.mappers;

import app.entities.CustomerInformation;
import app.entities.forCalculator.MountForCalculator;
import app.entities.forCalculator.RoofForCalculator;
import app.entities.forCalculator.ScrewForCalculator;
import app.entities.forCalculator.WoodForCalculator;
import app.persistence.connection.ConnectionPool;
import app.exceptions.DatabaseException;
import app.persistence.connection.ConnectionPool;


import java.sql.*;
import java.util.ArrayList;


public class OfferMapper {

//getter mapper metoder
    public int getTreatmentIdFromTreatmentName(ConnectionPool connectionPool, String treatmentName) throws DatabaseException {
        String sql = "SELECT wood_treatment_id FROM wood_treatment WHERE wood_treatment_type_name = ?;";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, treatmentName);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    int treatmentId = rs.getInt("wood_treatment_id");
                    return treatmentId;
                } else {
                    return 0;
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Database error while fetching treatment ID: ");
        }
    }

    public int getWoodTypeIdFromWoodTypeName(ConnectionPool connectionPool, String woodTypeName) throws DatabaseException {
        String sql = "SELECT wood_type_id FROM wood_type WHERE wood_type_name = ?;";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, woodTypeName);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    int woodTypeId = rs.getInt("wood_type_id");
                    return woodTypeId;
                } else {
                    return 0;
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Database error while fetching wood type ID: ");
        }
    }

    public int getWoodDimensionIdFromLengthWidthHeight(ConnectionPool connectionPool, int woodLenght, int woodWidth, int woodHeight) throws DatabaseException {
        String sql = "SELECT wood_dimension_id FROM wood_dimensions WHERE wood_length = ? AND wood_width = ? AND wood_height = ?;";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, woodLenght);
            statement.setInt(2, woodWidth);
            statement.setInt(3, woodHeight);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    int woodDimensionId = rs.getInt("wood_dimension_id");
                    return woodDimensionId;
                } else {
                    return 0;
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Database error while fetching dimension ID: ");
        }
    }

    public static int getWoodLengthFromWoodDimensionId(ConnectionPool connectionPool, int woodDimensionId) throws DatabaseException {
        String sql = "SELECT wood_length FROM wood_dimensions WHERE wood_dimension_id = ?;";
        int woodLengthInCm;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, woodDimensionId);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    woodLengthInCm = rs.getInt("wood_length");
                    return woodLengthInCm;
                }
            }
            throw new DatabaseException(null, "Wood Length not found for Wood Dimension Id: " + woodDimensionId);

        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Database error while fetching Wood Length: ");
        }
    }

    public static int getRoofIdFromRoofLength(ConnectionPool connectionPool, int roofLengthInCm) throws DatabaseException {
        String sql = "SELECT roof_id FROM roofs WHERE roof_length_cm = ?;";
        int roofId;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, roofLengthInCm);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    roofId = rs.getInt("roof_id");
                    return roofId;
                }
            }
            throw new DatabaseException(null, "Roof Id not found for Roof Length: " + roofLengthInCm);

        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Database error while fetching Roof Id: ");
        }
    }

    public static int getMountIdFromMountName(ConnectionPool connectionPool, String mountName) throws DatabaseException {
        String sql = "SELECT mount_id FROM mounts WHERE mount_type_name = ?;";
        int mountId;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, mountName);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    mountId = rs.getInt("mount_id");
                    return mountId;
                }
            }
            throw new DatabaseException(null, "Mount Id not found for Mount Name: " + mountName);

        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Database error while fetching Mount Id: ");
        }
    }

    public static int getScrewIdFromScrewName(ConnectionPool connectionPool, String screwName) throws DatabaseException {
        String sql = "SELECT screw_id FROM screws WHERE screw_type_name = ?;";
        int screwId;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, screwName);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    screwId = rs.getInt("screw_id");
                    return screwId;
                }
            }
            throw new DatabaseException(null, "Screw Id not found for Screw Name: " + screwName);

        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Database error while fetching Screw Id: ");
        }
    }

    public static int getAmountPrContainerFromScrewName(ConnectionPool connectionPool, String screwName) throws DatabaseException {
        String sql = "SELECT amount_pr_container FROM screws WHERE screw_type_name = ?;";
        int amountPrContainer;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, screwName);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    amountPrContainer = rs.getInt("amount_pr_container");
                    return amountPrContainer;
                }
            }
            throw new DatabaseException(null, "Amount Pr Container not found for Screw Name: " + screwName);

        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Database error while fetching Amount Pr Container: ");
        }
    }

    public static int getRandomSellerId(ConnectionPool connectionPool) throws DatabaseException {
        String sql = "SELECT seller_id FROM sellers ORDER BY RANDOM() LIMIT 1;";
        int sellerId;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    sellerId = rs.getInt("seller_id");
                    return sellerId;
                }
            }
            throw new DatabaseException(null, "Random Seller Id not found");

        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Database error while fetching sellers Id: ");
        }
    }

    public static String getSellerMailFromOfferId(ConnectionPool connectionPool, int offerId) throws DatabaseException {
        String sql = "SELECT seller_mail FROM sellers JOIN offers ON sellers.seller_id = offers.seller_id WHERE offer_id = ?;";
        String sellerMail;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, offerId);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    sellerMail = rs.getString("seller_mail");
                    return sellerMail;
                }
            }
            throw new DatabaseException(null, "Seller Mail not found for Offer Id: " + offerId);

        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Database error while fetching Seller Mail: ");
        }
    }

    public static String getCustomerMailFromOfferId(ConnectionPool connectionPool, int offerId) throws DatabaseException {
        String sql = "SELECT customer_mail FROM customer JOIN offers ON customer.customer_id = offers.customer_id WHERE offer_id = ?;";
        String customerMail;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, offerId);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    customerMail = rs.getString("customer_mail");
                    return customerMail;
                }
            }
            throw new DatabaseException(null, "Customer Mail not found for Offer Id: " + offerId);

        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Database error while fetching Customer Mail: ");
        }
    }

    public static CustomerInformation getCustomerInformationFromOfferId(ConnectionPool connectionPool, int offerId) throws DatabaseException {
        String sql = "SELECT customer_mail, customer_firstname, customer_lastname, street_name, house_number, customer.zip_code, city_name, phone_number FROM cities JOIN customer ON cities.zip_code = customer.zip_code JOIN offers ON customer.customer_id = offers.customer_id WHERE offer_id = ?;";
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, offerId);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    String customerMail = rs.getString("customer_mail");
                    String firstName = rs.getString("customer_firstname");
                    String lastName = rs.getString("customer_lastname");
                    String streetName = rs.getString("street_name");
                    int houseNumber = rs.getInt("house_number");
                    int zipCode = rs.getInt("zip_code");
                    String cityName = rs.getString("city_name");
                    int phoneNumber = rs.getInt("phone_number");
                    return new CustomerInformation(customerMail, firstName, lastName, streetName, houseNumber, zipCode, cityName, phoneNumber);
                }
            }
            throw new DatabaseException(null, "Customer Information not found for Offer Id: " + offerId);

        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Database error while fetching Customer Information: ");
        }
    }

    public static float getSalesPriceFromOfferId(ConnectionPool connectionPool, int offerId) throws DatabaseException {
        String sql = "SELECT total_sales_price FROM offers WHERE offer_id = ?;";
        float totalSalesPrice;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, offerId);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    totalSalesPrice = rs.getFloat("total_sales_price");
                    return totalSalesPrice;
                }
            }
            throw new DatabaseException(null, "Total Sales Price not found for Offer Id: " + offerId);

        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Database error while fetching Total Sales Price: ");
        }
    }

    public static float getTotalExpensesPriceFromOfferId(ConnectionPool connectionPool, int offerId) throws DatabaseException {
        String sql = "SELECT total_expenses_price FROM offers WHERE offer_id = ?;";
        float totalExpensesPrice;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, offerId);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    totalExpensesPrice = rs.getFloat("total_expenses_price");
                    return totalExpensesPrice;
                }
            }
            throw new DatabaseException(null, "Total Expenses Price not found for Offer Id: " + offerId);

        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Database error while fetching Total Expenses Price: ");
        }
    }

//create mapper metoder
    public static int createNewCustomerIfAlreadyExistGetCustomerIdFromMail(ConnectionPool connectionPool, String customerMail, String customerFirstName, String customerLastName, String customerStreetName, int customerHouseNumber, int customerZipCode, int phoneNumber) throws DatabaseException {
        int customerId;
        String sql = "INSERT INTO customer (customer_mail, customer_firstname, customer_lastname, street_name, house_number, zip_code, phone_number) VALUES ( ?, ?, ?, ?, ?, ?, ?) ON CONFLICT (customer_mail) DO NOTHING RETURNING customer_id;";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setString(1, customerMail);
                statement.setString(2, customerFirstName);
                statement.setString(3, customerLastName);
                statement.setString(4, customerStreetName);
                statement.setInt(5, customerHouseNumber);
                statement.setInt(6, customerZipCode);
                statement.setInt(7, phoneNumber);

                try (ResultSet rs = statement.executeQuery();) {
                    if (rs.next()) {
                        customerId = rs.getInt("customer_id");
                        return customerId;
                    }
                    else {
                        String secondSql = "SELECT customer_id FROM customer WHERE customer_mail = ?";
                        try (PreparedStatement secondStmt = connection.prepareStatement(secondSql)) {

                            secondStmt.setString(1, customerMail);
                            try (ResultSet secondRs = secondStmt.executeQuery()) {
                                if (secondRs.next()) {
                                    customerId = secondRs.getInt("customer_id");
                                    return customerId;
                                } else {

                                    throw new DatabaseException(null, "Customer Id not found from: " + customerMail + " and new customer could not be created.");
                                }
                            }
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Could not insert customer into database: ");
        }
    }

    public static int createOffer(ConnectionPool connectionPool, float totalOfferExpensePrice, float totalOfferSalesPrice, int sellerId, int carportLengthInCm, int carportWidthInCm, int shedLengthInCm, int shedWidthInCm, int customerId) throws DatabaseException {
        String sql = "INSERT INTO offers (total_expenses_price, total_sales_price, seller_id, expiration_date, carport_length, carport_width, shed_length, shed_width, customer_id) VALUES (?, ?, ?, CURRENT_DATE + INTERVAL '7 days', ?, ?, ?, ?, ?);";
        int offerId;
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                statement.setFloat(1, totalOfferExpensePrice);
                statement.setFloat(2, totalOfferSalesPrice);
                statement.setInt(3, sellerId);
                statement.setInt(4, carportLengthInCm);
                statement.setInt(5, carportWidthInCm);
                statement.setInt(6, shedLengthInCm);
                statement.setInt(7, shedWidthInCm);
                statement.setInt(8, customerId);

                int affectedRows = statement.executeUpdate();

                if (affectedRows == 0) {
                    throw new DatabaseException(null, "Creating offer failed, no rows affected.");
                }

                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        offerId = generatedKeys.getInt(1);
                        return offerId;
                    } else {
                        throw new DatabaseException(null, "Creating offer failed, no ID obtained.");
                    }
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Could not insert offer into database: ");
        }
    }

    public static boolean createMountsList(ConnectionPool connectionPool, ArrayList<MountForCalculator> mountList, int offerId) throws DatabaseException {
        String sql = "INSERT INTO mounts_list (offer_id, mount_id, mount_amount, mount_description) VALUES (?, ?, ?, ?);";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                int rowsAffected = 0;
                int expectedRowsAffected = 0;

                statement.setInt(1, offerId);

                for (MountForCalculator mountForCalculator : mountList) {
                    statement.setInt(2, mountForCalculator.getMountId());
                    statement.setInt(3, mountForCalculator.getAmount());
                    statement.setString(4, mountForCalculator.getDescription());

                    expectedRowsAffected ++;
                    rowsAffected += statement.executeUpdate();
                }

                if (rowsAffected == expectedRowsAffected) {
                    return true;
                }
                System.out.println("Not alle Rows inserted, Inserted: " + rowsAffected + ", Expected: " + expectedRowsAffected);
                return false;

            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Could not insert Mount List into database for Offer Id: " + offerId);
        }
    }

    public static boolean createRoofList(ConnectionPool connectionPool, ArrayList<RoofForCalculator> roofList, int offerId) throws DatabaseException {
        String sql = "INSERT INTO roof_list (offer_id, roof_id, roof_amount, roof_description) VALUES (?, ?, ?, ?);";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                int rowsAffected = 0;
                int expectedRowsAffected = 0;

                statement.setInt(1, offerId);

                for (RoofForCalculator roofForCalculator : roofList) {
                    statement.setInt(2, roofForCalculator.getRoofId());
                    statement.setInt(3, roofForCalculator.getAmount());
                    statement.setString(4, roofForCalculator.getDescription());

                    expectedRowsAffected ++;
                    rowsAffected += statement.executeUpdate();
                }

                if (rowsAffected == expectedRowsAffected) {
                    return true;
                }
                System.out.println("Not alle Rows inserted, Inserted " + rowsAffected + ", Expected: " + expectedRowsAffected);
                return false;

            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Could not insert Roof List into database for Offer Id: " + offerId);
        }
    }

    public static boolean createScrewsList(ConnectionPool connectionPool, ArrayList<ScrewForCalculator> screwList, int offerId) throws DatabaseException {
        String sql = "INSERT INTO screws_list (offer_id , screw_id, screws_amount, screw_description) VALUES (?, ?, ?, ?);";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                int rowsAffected = 0;
                int expectedRowsAffected = 0;

                statement.setInt(1, offerId);

                for (ScrewForCalculator screwForCalculator : screwList) {
                    statement.setInt(2, screwForCalculator.getScrewId());
                    statement.setInt(3, screwForCalculator.getAmount());
                    statement.setString(4, screwForCalculator.getDescription());

                    expectedRowsAffected ++;
                    rowsAffected += statement.executeUpdate();
                }

                if (rowsAffected == expectedRowsAffected) {
                    return true;
                }
                System.out.println("Not alle Rows inserted, Inserted: " + rowsAffected + ", Expected: " + expectedRowsAffected);
                return false;

            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Could not insert Screw List into database for Offer Id: " + offerId);
        }
    }

    public static boolean createWoodList(ConnectionPool connectionPool, ArrayList<WoodForCalculator> woodList, int offerId) throws DatabaseException {
        String sql = "INSERT INTO wood_list (offer_id , wood_type_id, wood_treatment_id, wood_dimension_id, wood_amount, wood_description) VALUES (?, ?, ?, ?, ?, ?);";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                int rowsAffected = 0;
                int expectedRowsAffected = 0;

                statement.setInt(1, offerId);

                for (WoodForCalculator woodForCalculator : woodList) {
                    statement.setInt(2, woodForCalculator.getWoodTypeId());
                    statement.setInt(3, woodForCalculator.getWoodTreatmentId());
                    statement.setInt(4, woodForCalculator.getWoodDimensionId());
                    statement.setInt(5, woodForCalculator.getAmount());
                    statement.setString(6, woodForCalculator.getDescription());

                    expectedRowsAffected ++;
                    rowsAffected += statement.executeUpdate();
                }

                if (rowsAffected == expectedRowsAffected) {
                    return true;
                }
                System.out.println("Not alle Rows inserted, Inserted: " + rowsAffected + ", Expected: " + expectedRowsAffected);
                return false;

            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Could not insert Wood List into database for Offer Id: " + offerId);
        }
    }

//update metoder
    public static boolean updateTotalExpensesPrice(ConnectionPool connectionPool, float totalOfferExpensePrice, int offerId) throws DatabaseException {
        String sql = "UPDATE offers SET total_expenses_price = ? WHERE offer_id = ?;";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {

                ps.setFloat(1, totalOfferExpensePrice);
                ps.setInt(2, offerId);

                int rowsAffected = ps.executeUpdate();
                if (rowsAffected == 1) {
                    return true;
                }
                return false;

            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Error updating Total Expenses Price for Offer Id: " + offerId + " in database");
        }
    }

    public static boolean updateTotalSalesPrice(ConnectionPool connectionPool, float totalOfferSalesPrice, int offerId) throws DatabaseException {
        String sql = "UPDATE offers SET total_sales_price = ? WHERE offer_id = ?;";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {

                ps.setFloat(1, totalOfferSalesPrice);
                ps.setInt(2, offerId);

                int rowsAffected = ps.executeUpdate();
                if (rowsAffected == 1) {
                    return true;
                }
                return false;

            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Error updating Total Sales Price for Offer Id: " + offerId + " in database");
        }
    }

    public static boolean updateExpirationDate(ConnectionPool connectionPool, int offerId) throws DatabaseException {
        String sql = "UPDATE offers SET expiration_date = CURRENT_DATE + INTERVAL '5 years'  WHERE offer_id = ?;";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {

                ps.setInt(1, offerId);

                int rowsAffected = ps.executeUpdate();
                if (rowsAffected == 1) {
                    return true;
                }
                return false;

            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Error updating Expiration Date for Offer Id: " + offerId + " in database");
        }
    }

//delete mapper metoder
    public static boolean deleteOfferAndEveryThinkLinkedToItByOfferId(ConnectionPool connectionPool, int offerId) throws DatabaseException {
        //den kan delete alle rækker i tabeller som har samme offer_id pga foreign Key constrain on action delete køre den cascade
        String sql = "DELETE FROM offers WHERE offer_id = ?;";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {

                ps.setInt(1, offerId);

                int rowsAffected = ps.executeUpdate();
                if (rowsAffected == 0) {
                    return false;
                }
                return true;

            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Error deleting Offer, Order, Mount List, Roof List, Screw List and Wood List for Offer Id: " + offerId + " in database");
        }
    }

    public static boolean deleteMountListByOfferId(ConnectionPool connectionPool, int offerId) throws DatabaseException {
        String sql = "DELETE FROM mounts_list WHERE offer_id = ?;";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {

                ps.setInt(1, offerId);

                int rowsAffected = ps.executeUpdate();
                if (rowsAffected == 0) {
                    return false;
                }
                return true;

            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Error deleting Mount List for Offer Id: " + offerId + " in database");
        }
    }

    public static boolean deleteRoofListByOfferId(ConnectionPool connectionPool, int offerId) throws DatabaseException {
        String sql = "DELETE FROM roof_list WHERE offer_id = ?;";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {

                ps.setInt(1, offerId);

                int rowsAffected = ps.executeUpdate();
                if (rowsAffected == 0) {
                    return false;
                }
                return true;

            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Error deleting Roof List for Offer Id: " + offerId + " in database");
        }
    }

    public static boolean deleteScrewListByOfferId(ConnectionPool connectionPool, int offerId) throws DatabaseException {
        String sql = "DELETE FROM screws_list WHERE offer_id = ?;";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {

                ps.setInt(1, offerId);

                int rowsAffected = ps.executeUpdate();
                if (rowsAffected == 0) {
                    return false;
                }
                return true;

            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Error deleting Screw List for Offer Id: " + offerId + " in database");
        }
    }

    public static boolean deleteWoodListByOfferId(ConnectionPool connectionPool, int offerId) throws DatabaseException {
        String sql = "DELETE FROM wood_list WHERE offer_id = ?;";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {

                ps.setInt(1, offerId);

                int rowsAffected = ps.executeUpdate();
                if (rowsAffected == 0) {
                    return false;
                }
                return true;

            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Error deleting Wood List for Offer Id: " + offerId + " in database");
        }

    }
}
