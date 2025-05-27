package app.persistence.mappers;

import app.entities.forCalculator.MountForCalculator;
import app.entities.forCalculator.RoofForCalculator;
import app.entities.forCalculator.ScrewForCalculator;
import app.entities.forCalculator.WoodForCalculator;
import app.exceptions.DatabaseException;
import app.persistence.connection.ConnectionPool;
import app.entities.Order;
import org.junit.jupiter.api.*;
import app.entities.Status;
import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;
import static app.persistence.mappers.testSetupForMappers.CreateTestSchemaDatabase.createTestSchemaWithData;
import static app.persistence.mappers.testSetupForMappers.CreateTestSchemaDatabase.insertDataInTestDatabase;
import static org.junit.jupiter.api.Assertions.*;

class OrderMapperTest {
    private static ConnectionPool connectionPool;
    private static OrderMapper orderMapper;

    @BeforeAll
    static void beforeAll() throws SQLException {
        String USER = "postgres";
        String PASSWORD = System.getenv("kudsk_db_password");
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
    void setUp() throws SQLException {
        try (Connection conn = connectionPool.getConnection()) {
            insertDataInTestDatabase(conn);
        }

        orderMapper = new OrderMapper();
    }


    @Test
    void createNewOrder() throws SQLException, DatabaseException {
        // Arrange:
        int offerId = 1;
        int expected = 4;


        // Act
        int actual = orderMapper.createNewOrder(connectionPool, offerId);

        // Assert
        assertEquals(expected, actual);
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
        int orderId = 3;
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

    @Test
    void getWoodListFromOrderId() throws DatabaseException {
        // Arrange
        int testOrderId = 1;
        ArrayList<WoodForCalculator> expectedWoodList = new ArrayList<>();
        expectedWoodList.add(new WoodForCalculator("Spær", 100, 1, 1, 1, "Rem til montering på stolper"));
        expectedWoodList.add(new WoodForCalculator("Spær", 200, 2, 2, 1, "Spær til montering på rem"));
        int positionInArray = 0;
        // Act
        ArrayList<WoodForCalculator> actual = orderMapper.getWoodListFromOrderId(connectionPool, testOrderId);

        // Assert
        assertEquals(expectedWoodList.size(), actual.size());

        for (WoodForCalculator wood : actual) {
            WoodForCalculator expectedWood = expectedWoodList.get(positionInArray);
            assertEquals(expectedWood.getName(), wood.getName());
            assertEquals(expectedWood.getAmount(), wood.getAmount());
            assertEquals(expectedWood.getWoodDimensionId(), wood.getWoodDimensionId());
            assertEquals(expectedWood.getWoodTreatmentId(), wood.getWoodTreatmentId());
            assertEquals(expectedWood.getWoodTypeId(), wood.getWoodTypeId());
            assertEquals(expectedWood.getDescription(), wood.getDescription());
            positionInArray++;
        }
    }

    @Test
    void getRoofListFromOrderId() throws DatabaseException {
        // Arrange
        int testOrderId = 1;
        ArrayList<RoofForCalculator> expectedRoofList = new ArrayList<>();
        expectedRoofList.add(new RoofForCalculator("Plastmo Ecolite blåtonet", 10, 1, "Tagplader monteres på spær"));
        expectedRoofList.add(new RoofForCalculator("Plastmo Ecolite blåtonet", 10, 2, "Tagplader monteres på spær"));
        int positionInArray = 0;
        // Act
        ArrayList<RoofForCalculator> actual = orderMapper.getRoofListFromOrderId(connectionPool, testOrderId);

        // Assert
        assertEquals(expectedRoofList.size(), actual.size());

        for (RoofForCalculator roof : actual) {
            RoofForCalculator expectedRoof = expectedRoofList.get(positionInArray);
            assertEquals(expectedRoof.getName(), roof.getName());
            assertEquals(expectedRoof.getAmount(), roof.getAmount());
            assertEquals(expectedRoof.getRoofId(), roof.getRoofId());
            assertEquals(expectedRoof.getDescription(), roof.getDescription());
            positionInArray++;
        }
    }

    @Test
    void getMountListFromOrderId() throws DatabaseException {
        // Arrange
        int testOrderId = 1;
        ArrayList<MountForCalculator> expectedMountList = new ArrayList<>();
        expectedMountList.add(new MountForCalculator("Universalbeslag 190 mm højre", 10, 1, "Spær monterings beslag"));
        expectedMountList.add(new MountForCalculator("Firkantskiver 40 x 40 x 11 mm", 6, 3, "Firkantskive til montering af rem"));
        int positionInArray = 0;
        // Act
        ArrayList<MountForCalculator> actual = orderMapper.getMountListFromOrderId(connectionPool, testOrderId);

        // Assert
        assertEquals(expectedMountList.size(), actual.size());

        for (MountForCalculator mount : actual) {
            MountForCalculator expectedMount = expectedMountList.get(positionInArray);
            assertEquals(expectedMount.getName(), mount.getName());
            assertEquals(expectedMount.getAmount(), mount.getAmount());
            assertEquals(expectedMount.getMountId(), mount.getMountId());
            assertEquals(expectedMount.getDescription(), mount.getDescription());
            positionInArray++;
        }
    }

    @Test
    void getScrewListFromOrderId() throws DatabaseException {
        // Arrange
        int testOrderId = 1;
        ArrayList<ScrewForCalculator> expectedScrewList = new ArrayList<>();
        expectedScrewList.add(new ScrewForCalculator("Plastmo bundskruer 200 stk.", 50, 1, "Til spær beslag"));
        expectedScrewList.add(new ScrewForCalculator("4,5 x 60 mm. skruer 200 stk.", 30, 2, "Til rem montering på stolpe"));
        int positionInArray = 0;
        // Act
        ArrayList<ScrewForCalculator> actual = orderMapper.getScrewListFromOrderId(connectionPool, testOrderId);

        // Assert
        assertEquals(expectedScrewList.size(), actual.size());

        for (ScrewForCalculator screw : actual) {
            ScrewForCalculator expectedScrew = expectedScrewList.get(positionInArray);
            assertEquals(expectedScrew.getName(), screw.getName());
            assertEquals(expectedScrew.getAmount(), screw.getAmount());
            assertEquals(expectedScrew.getScrewId(), screw.getScrewId());
            assertEquals(expectedScrew.getDescription(), screw.getDescription());
            positionInArray++;
        }
    }
}