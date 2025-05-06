package app.persistence.mappers;

import app.persistence.connection.ConnectionPool;
import app.entities.*;
import app.exceptions.DatabaseException;
import java.sql.*;
import java.util.UUID;

public class OrderMapper {

    //Skal kaldes når man et offer laves
    // husk at setStaatusId = 1
    public boolean createNewOrder(ConnectionPool connectionPool, int offerId) throws DatabaseException {
        String sql = "INSERT INTO orders (offer_id, status_id, purchase_date) VALUES (?,1,current_date());";
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
            throw new DatabaseException("Could not insert order into database");
        }
    }

    public Order getOrderDetailsFromOrderId(ConnectionPool connectionPool, int orderId) throws DatabaseException {
        String sql = "SELECT orders.order_id, orders.offer_id, orders.tracking_number, orders.purchase_date, status.status_description "
                    + "FROM orders "
                    + "JOIN status ON orders.status_id = status.id "
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
}
