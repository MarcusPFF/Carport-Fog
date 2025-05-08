package app.persistence.mappers;

import app.persistence.connection.ConnectionPool;
import app.entities.*;
import app.exceptions.DatabaseException;
import java.sql.*;
import java.util.UUID;

public class OrderMapper {

    // This method is called when an offer is created
    // Status_id is always 1 when created
    public boolean createNewOrder(ConnectionPool connectionPool, int offerId) throws DatabaseException {
        String sql = "INSERT INTO orders (offer_id, status_id, purchase_date) VALUES (?,1,CURRENT_DATE);";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, offerId);
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected == 0) {
                    System.out.println("No rows affected");
                    return false;
                } else {
                    System.out.println("Rows affected: " + rowsAffected);
                    return true;
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Could not insert order into database: ");
        }
    }


    //Orderfetcher
    public Order getOrderDetailsFromOrderId(ConnectionPool connectionPool, int orderId) throws DatabaseException {
        String sql = "SELECT orders.order_id, orders.offer_id, orders.tracking_number, orders.purchase_date, status.status_description "
                + "FROM orders "
                + "JOIN status ON orders.status_id = status.status_id "
                + "WHERE orders.order_id = ?;";
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

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
            throw new DatabaseException("Could not retrieve order from database");
        }
    }

    public UUID getTrackingNumberFromOrderId(ConnectionPool connectionPool, int orderId) throws DatabaseException {
        String sql = "SELECT tracking_number FROM orders WHERE order_id = ?;";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setObject(1, orderId);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    UUID trackingNumber = UUID.fromString(rs.getString("tracking_number"));
                    return trackingNumber;
                } else {
                    throw new DatabaseException("No tracking number found for the provided orderId.");
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Database error while fetching tracking number: " + ex.getMessage());
        }
    }



    //This method is used, when a customer has bought their product and needs to see the status.
    public Status getStatusFromTrackingNumber(ConnectionPool connectionPool, UUID trackingNumber) throws DatabaseException {
        String sql = "SELECT status.status_id, status.status_description, status.message_for_mail FROM orders orders " +
                    "JOIN status status ON orders.status_id = orders.status_id " +
                    "WHERE orders.tracking_number = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setObject(1, trackingNumber);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    int statusId = rs.getInt("status_id");
                    String statusDescription = rs.getString("status_description");
                    String messageForMail = rs.getString("message_for_mail");
                    return new Status(statusId, statusDescription, messageForMail);
                } else {
                    throw new DatabaseException("No status found for the provided tracking number.");
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Database error while fetching status: " + ex.getMessage());
        }
    }
}
