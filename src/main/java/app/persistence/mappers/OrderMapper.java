package app.persistence.mappers;

import app.entities.Order;
import app.entities.Status;
import app.entities.forCalculator.MountForCalculator;
import app.entities.forCalculator.RoofForCalculator;
import app.entities.forCalculator.ScrewForCalculator;
import app.entities.forCalculator.WoodForCalculator;
import app.exceptions.DatabaseException;
import app.persistence.connection.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

public class OrderMapper {

    // This method is called when an offer is created
    // Status_id is always 1 when created
    public static int createNewOrder(ConnectionPool connectionPool, int offerId) throws DatabaseException {
        String sql = "INSERT INTO orders (offer_id, status_id, purchase_date) VALUES (?,1,CURRENT_DATE) RETURNING order_id;";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, offerId);
                ResultSet rs = statement.executeQuery();
                if (rs.next()) {
                    int orderId = rs.getInt("order_id");
                    return orderId;
                } else {
                    return 0;
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Could not insert order into database: ");
        }
    }


    //Orderfetcher
    public static Order getOrderDetailsFromOrderId(ConnectionPool connectionPool, int orderId) throws DatabaseException {
        String sql = "SELECT orders.order_id, orders.offer_id, orders.tracking_number, orders.purchase_date, status.status_description " + "FROM orders " + "JOIN status ON orders.status_id = status.status_id " + "WHERE orders.order_id = ?;";
        try (Connection connection = connectionPool.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, orderId);
            try (ResultSet rs = statement.executeQuery()) {

                if (rs.next()) {
                    int retrievedOrderId = rs.getInt("order_id");
                    int offerId = rs.getInt("offer_id");
                    UUID trackingNumber = UUID.fromString(rs.getString("tracking_number"));
                    Date purchaseDate = rs.getDate("purchase_date");
                    String status = rs.getString("status_description");
                    return new Order(retrievedOrderId, offerId, trackingNumber, purchaseDate, status);
                } else {
                    return null;
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Could not retrieve order from database");
        }
    }

    public static UUID getTrackingNumberFromOrderId(ConnectionPool connectionPool, int orderId) throws DatabaseException {
        String sql = "SELECT tracking_number FROM orders WHERE order_id = ?;";

        try (Connection connection = connectionPool.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setObject(1, orderId);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    UUID trackingNumber = UUID.fromString(rs.getString("tracking_number"));
                    return trackingNumber;
                } else {
                    return null;
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Database error while fetching tracking number: ");
        }
    }


    //This method is used, when a customer has bought their product and needs to see the status.
    public static Status getStatusFromTrackingNumber(ConnectionPool connectionPool, UUID trackingNumber) throws DatabaseException {
        String sql = "SELECT status.status_id, status.status_description, status.message_for_mail FROM orders orders " + "JOIN status status ON orders.status_id = orders.status_id " + "WHERE orders.tracking_number = ?";

        try (Connection connection = connectionPool.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setObject(1, trackingNumber);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    int statusId = rs.getInt("status_id");
                    String statusDescription = rs.getString("status_description");
                    String messageForMail = rs.getString("message_for_mail");
                    return new Status(statusId, statusDescription, messageForMail);
                } else {
                    return null;
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Database error while fetching status: ");
        }
    }

    public static ArrayList<WoodForCalculator> getWoodListFromOrderId(ConnectionPool connectionPool, int orderId) throws DatabaseException {
        String sql = "SELECT wood_list.offer_id, wood_list.wood_type_id, wood_list.wood_treatment_id, wood_list.wood_dimension_id, wood_list.wood_amount, wood_list.wood_description, wood_type.wood_type_name FROM orders JOIN offers ON orders.offer_id = offers.offer_id JOIN wood_list ON offers.offer_id = wood_list.offer_id JOIN wood_type ON wood_list.wood_type_id = wood_type.wood_type_id WHERE order_id = ?;";
        ArrayList<WoodForCalculator> woodList = new ArrayList<>();
        String name;
        int amount;
        int woodDimensionId;
        int woodTreatmentId;
        int woodTypeId;
        String description;


        try (Connection connection = connectionPool.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, orderId);

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    name = rs.getString("wood_type_name");
                    amount = rs.getInt("wood_amount");
                    woodDimensionId = rs.getInt("wood_dimension_id");
                    woodTreatmentId = rs.getInt("wood_treatment_id");
                    woodTypeId = rs.getInt("wood_type_id");
                    description = rs.getString("wood_description");
                    woodList.add(new WoodForCalculator(name, amount, woodDimensionId, woodTreatmentId, woodTypeId, description));
                }
                return woodList;
            }

        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Database error while fetching Wood List: ");
        }
    }

    public static ArrayList<RoofForCalculator> getRoofListFromOrderId(ConnectionPool connectionPool, int orderId) throws DatabaseException {
        String sql = "SELECT roof_list.offer_id, roof_list.roof_id, roof_list.roof_amount, roof_list.roof_description, roofs.roof_type_name FROM orders JOIN offers ON orders.offer_id = offers.offer_id JOIN roof_list ON offers.offer_id = roof_list.offer_id JOIN roofs ON roof_list.roof_id = roofs.roof_id WHERE order_id = ?;";
        ArrayList<RoofForCalculator> roofList = new ArrayList<>();
        String name;
        int amount;
        int roofId;
        String description;


        try (Connection connection = connectionPool.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, orderId);

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    name = rs.getString("roof_type_name");
                    amount = rs.getInt("roof_amount");
                    roofId = rs.getInt("roof_id");
                    description = rs.getString("roof_description");
                    roofList.add(new RoofForCalculator(name, amount, roofId, description));
                }
                return roofList;
            }

        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Database error while fetching Wood List: ");
        }
    }

    public static ArrayList<MountForCalculator> getMountListFromOrderId(ConnectionPool connectionPool, int orderId) throws DatabaseException {
        String sql = "SELECT mounts_list.offer_id, mounts_list.mount_id, mounts_list.mount_amount, mounts_list.mount_description, mounts.mount_type_name FROM orders JOIN offers ON orders.offer_id = offers.offer_id JOIN mounts_list ON offers.offer_id = mounts_list.offer_id JOIN mounts ON mounts_list.mount_id = mounts.mount_id WHERE order_id = ?;";
        ArrayList<MountForCalculator> mountList = new ArrayList<>();
        String name;
        int amount;
        int roofId;
        String description;


        try (Connection connection = connectionPool.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, orderId);

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    name = rs.getString("mount_type_name");
                    amount = rs.getInt("mount_amount");
                    roofId = rs.getInt("mount_id");
                    description = rs.getString("mount_description");
                    mountList.add(new MountForCalculator(name, amount, roofId, description));
                }
                return mountList;
            }

        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Database error while fetching Wood List: ");
        }
    }

    public static ArrayList<ScrewForCalculator> getScrewListFromOrderId(ConnectionPool connectionPool, int orderId) throws DatabaseException {
        String sql = "SELECT screws_list.offer_id, screws_list.screw_id, screws_list.screws_amount, screws_list.screw_description, screws.screw_type_name FROM orders JOIN offers ON orders.offer_id = offers.offer_id JOIN screws_list ON offers.offer_id = screws_list.offer_id JOIN screws ON screws_list.screw_id = screws.screw_id WHERE order_id = ?;";
        ArrayList<ScrewForCalculator> screwList = new ArrayList<>();
        String name;
        int amount;
        int roofId;
        String description;


        try (Connection connection = connectionPool.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, orderId);

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    name = rs.getString("screw_type_name");
                    amount = rs.getInt("screws_amount");
                    roofId = rs.getInt("screw_id");
                    description = rs.getString("screw_description");
                    screwList.add(new ScrewForCalculator(name, amount, roofId, description));
                }
                return screwList;
            }

        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Database error while fetching Wood List: ");
        }
    }
}
