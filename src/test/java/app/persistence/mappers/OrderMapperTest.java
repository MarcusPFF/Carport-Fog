package app.persistence.mappers;
import app.exceptions.DatabaseException;
import app.persistence.connection.ConnectionPool;
import app.entities.Order;
import org.junit.jupiter.api.*;
import app.entities.Status;



import java.sql.*;
import java.util.UUID;

import static app.persistence.mappers.testSetupForMappers.CreateTestSchemaDatabase.createTestSchemaWithData;
import static org.junit.jupiter.api.Assertions.*;

class OrderMapperTest {
    private static ConnectionPool connectionPool;
    private static OrderMapper orderMapper;

    @BeforeAll
    static void beforeAll() throws SQLException {
        String USER = "postgres";
        String PASSWORD = "jo221mf411jk513!";
        //TODO FÅ Getenv til at virke
        //String PASSWORD = System.getenv("kudsk_db_password");
        String URL = "jdbc:postgresql://134.122.71.16/%s?currentSchema=test";
        String DB = "Fog_Carport";

        connectionPool = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);

        try (Connection conn = connectionPool.getConnection()) {
            createTestSchemaWithData(conn);
        }
    }

    @AfterAll
    static void afterAll() throws SQLException {
        try (Connection conn = connectionPool.getConnection()) {
            conn.createStatement().execute("ROLLBACK;");
        }
    }

    @BeforeEach
    void setUp() {
        orderMapper = new OrderMapper();
    }





    @Test
    void createNewOrder() throws SQLException, DatabaseException {
        // Arrange:
        int offerId = 1;

        // Act
        orderMapper.createNewOrder(connectionPool, offerId);

        // Assert
        try (Connection conn = connectionPool.getConnection(); PreparedStatement ps = conn.prepareStatement("SELECT order_id, offer_id, tracking_number, status_id, purchase_date FROM test.orders WHERE offer_id = ?")) {
            ps.setInt(1, offerId);
            try (ResultSet rs = ps.executeQuery()) {
                assertTrue(rs.next(), "Der forventes en række med offerId = " + offerId);
                assertEquals(offerId, rs.getInt("offer_id"));
            }
        }
    }

    @Test
    void getOrderDetailsFromOrderId() throws SQLException, DatabaseException {
        // Arrange
        int orderId = 3;
        int offerId = 3;
        int statusId = 3;
        String statusDescription = "Delivered";
        Date purchaseDate = Date.valueOf("2025-05-03");

        // Act
        Order order = orderMapper.getOrderDetailsFromOrderId(connectionPool, orderId);

        // Assert
        assertNotNull(order);
        assertEquals(orderId, order.getOrderId());
        assertEquals(offerId, order.getOfferId());
        assertEquals(purchaseDate, order.getPurchaseDate());
        assertEquals(statusDescription, order.getStatus());
    }

    @Test
    void getTrackingNumberFromOrderId() throws SQLException, DatabaseException {
        int orderId = 4;
        UUID expectedTrackingNumber = UUID.fromString("f47ac10b-58cc-4372-a567-0e02b2c3d479");

        //Act: Get tracking_number from order_id
        UUID trackingNumber = orderMapper.getTrackingNumberFromOrderId(connectionPool, orderId);
        //Assert:
        assertNotNull(trackingNumber);
        assertEquals(expectedTrackingNumber, trackingNumber);
    }

    @Test
    void getStatusFromTrackingNumber() throws SQLException, DatabaseException {
        int orderId = 1;
        UUID trackingNumber = orderMapper.getTrackingNumberFromOrderId(connectionPool, orderId);
        assertNotNull(trackingNumber, "Tracking number should now not be null");

        // Act: Get status via tracking_number
        Status status = orderMapper.getStatusFromTrackingNumber(connectionPool, trackingNumber);
        // Assert:
        assertNotNull(status);
        assertEquals(1, status.getStatusId());
        assertEquals("Pending", status.getStatusDescription());
        assertEquals("Your order is pending.", status.getMessageForMail());
    }
}