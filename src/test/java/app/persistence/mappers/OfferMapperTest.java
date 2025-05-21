package app.persistence.mappers;

import app.entities.CustomerInformation;
import app.entities.forCalculator.MountForCalculator;
import app.entities.forCalculator.RoofForCalculator;
import app.entities.forCalculator.ScrewForCalculator;
import app.entities.forCalculator.WoodForCalculator;
import app.exceptions.DatabaseException;
import app.persistence.connection.ConnectionPool;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

import static app.persistence.mappers.testSetupForMappers.CreateTestSchemaDatabase.createTestSchemaWithData;
import static org.junit.jupiter.api.Assertions.*;

class OfferMapperTest {
    private static ConnectionPool connectionPool;
    private static OfferMapper offerMapper;

    @BeforeAll
    static void beforeAll() throws SQLException {
        String USER = "postgres";
        String PASSWORD = System.getenv("kudsk_db_password");
        String URL = "jdbc:postgresql://134.122.71.16/%s?currentSchema=test";
        String DB = "Fog_Carport";

        connectionPool = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);
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
            createTestSchemaWithData(conn);
        }
        offerMapper = new OfferMapper();

    }


    @Test
    void getTreatmentIdFromTreatmentName() throws SQLException, DatabaseException {
        //Arrange
        String treatmentName = "Trykimprægneret";
        int expectedTreatmentId = 1;

        //Act:
        int treatmentId = offerMapper.getTreatmentIdFromTreatmentName(connectionPool, treatmentName);

        //Assert:
        assertNotNull(treatmentId);
        assertEquals(expectedTreatmentId, treatmentId);
    }

    @Test
    void getWoodTypeIdFromWoodTypeName() throws SQLException, DatabaseException {
        //Arrange
        String woodTypeName = "Spær";
        int expectedWoodTypeId = 1;

        //Act:
        int woodTypeId = offerMapper.getWoodTypeIdFromWoodTypeName(connectionPool, woodTypeName);

        //Assert:
        assertNotNull(woodTypeId);
        assertEquals(expectedWoodTypeId, woodTypeId);
    }

    @Test
    void getWoodDimensionIdFromLengthWidthHeight() throws SQLException, DatabaseException {
        //Arrange
        int dimensionLength = 210;
        int dimensionWidth = 60;
        int dimensionHeight = 145;
        int expectedDimensionId = 563;

        //Act:
        int dimensionId = offerMapper.getWoodDimensionIdFromLengthWidthHeight(connectionPool, dimensionLength, dimensionWidth, dimensionHeight);

        //Assert:
        assertNotNull(dimensionId);
        assertEquals(expectedDimensionId, dimensionId);
    }

    @Test
    void getWoodLengthFromWoodDimensionId() throws SQLException, DatabaseException {
    //Arrange
    int dimensionId = 1;
    int expectedLength = 150;

    //Act
    int length = offerMapper.getWoodLengthFromWoodDimensionId(connectionPool, dimensionId);

    //Assert
    assertEquals(expectedLength, length);
}

    @Test
    void getRoofIdFromRoofLength() throws SQLException, DatabaseException {
        //Arrange
        int roofLength = 600;
        int expectedRoofId = 1;

        //Act
        int roofId = offerMapper.getRoofIdFromRoofLength(connectionPool, roofLength);

        //Assert
        assertEquals(expectedRoofId, roofId);
    }

    @Test
    void getMountIdFromMountName() throws SQLException, DatabaseException {
        //Arrange
        String mountName = "Universalbeslag 190 mm højre";
        int expectedMountId = 1;

        //Act
        int mountId = offerMapper.getMountIdFromMountName(connectionPool, mountName);

        //Assert
        assertEquals(expectedMountId, mountId);
    }

    @Test
    void getScrewIdFromScrewName() throws SQLException, DatabaseException {
        //Arrange
        String screwName = "Plastmo bundskruer 200 stk.";
        int expectedScrewId = 1;

        //Act
        int screwId = offerMapper.getScrewIdFromScrewName(connectionPool, screwName);

        //Assert
        assertEquals(expectedScrewId, screwId);
    }

    @Test
    void getAmountPrContainerFromScrewName() throws SQLException, DatabaseException {
        //Arrange
        String screwName = "Plastmo bundskruer 200 stk.";
        int expectedAmount = 200;

        //Act
        int amount = offerMapper.getAmountPrContainerFromScrewName(connectionPool, screwName);

        //Assert
        assertEquals(expectedAmount, amount);
    }

    @Test
    void getRandomSellerId() throws SQLException, DatabaseException {
        //Arrange
        int sellerId;

        //Act
        sellerId = offerMapper.getRandomSellerId(connectionPool);

        //Assert
        assertTrue(sellerId >= 1 && sellerId <= 3, "Random sellerId should be between 1 and 3, but was " + sellerId);
    }

    @Test
    void getSellerMailFromOfferId() throws SQLException, DatabaseException {
        //Arrange
        int offerId = 1;
        String expectedMail = "bob.martin@example.com";

        //Act
        String mail = offerMapper.getSellerMailFromOfferId(connectionPool, offerId);

        //Assert
        assertEquals(expectedMail, mail);
    }

    @Test
    void getCustomerMailFromOfferId() throws SQLException, DatabaseException {
        //Arrange
        int offerId = 1;
        String expectedMail = "john.doe@example.com";

        //Act
        String mail = offerMapper.getCustomerMailFromOfferId(connectionPool, offerId);

        //Assert
        assertEquals(expectedMail, mail);
    }

    @Test
    void getCustomerInformationFromOfferId() throws SQLException, DatabaseException {
        //Arrange
        int offerId = 1;
        CustomerInformation expectedCustomer = new CustomerInformation("john.doe@example.com", "John", "Doe", "Main Street", 123, 1000, "Copenhagen",60606060);

        //Act
        CustomerInformation actualCustomer = offerMapper.getCustomerInformationFromOfferId(connectionPool, offerId);

        //Assert
        assertEquals(expectedCustomer.getCustomerMail(), actualCustomer.getCustomerMail());
        assertEquals(expectedCustomer.getFirstName(), actualCustomer.getFirstName());
        assertEquals(expectedCustomer.getLastName(), actualCustomer.getLastName());
        assertEquals(expectedCustomer.getStreetName(), actualCustomer.getStreetName());
        assertEquals(expectedCustomer.getHouseNumber(), actualCustomer.getHouseNumber());
        assertEquals(expectedCustomer.getZipCode(), actualCustomer.getZipCode());
        assertEquals(expectedCustomer.getCity(), actualCustomer.getCity());
        assertEquals(expectedCustomer.getPhoneNumber(), actualCustomer.getPhoneNumber());
    }

    @Test
    void getSalesPriceFromOfferId() throws SQLException, DatabaseException {
        //Arrange
        int offerId = 1;
        float expectedPrice = 6000.00f;

        //Act
        float price = offerMapper.getSalesPriceFromOfferId(connectionPool, offerId);

        //Assert
        assertEquals(expectedPrice, price, 0.001f);
    }

    @Test
    void getTotalExpensesPriceFromOfferId() throws SQLException, DatabaseException {
        //Arrange
        int offerId = 1;
        float expectedExpenses = 5000.00f;

        //Act
        float expenses = offerMapper.getTotalExpensesPriceFromOfferId(connectionPool, offerId);

        //Assert
        assertEquals(expectedExpenses, expenses, 0.001f);
    }

    @Test
    void createNewCustomerIfAlreadyExist() throws SQLException, DatabaseException {
        //Arrange
        String mail = "test@example.com";


        //Act
        int newId = OfferMapper.createNewCustomerIfAlreadyExistGetCustomerIdFromMail(connectionPool, mail, "test", "test", "test", 1, 1000, 50505050);
        int existingId = OfferMapper.createNewCustomerIfAlreadyExistGetCustomerIdFromMail(connectionPool, mail, "test", "test", "test", 1, 1000, 60606060);


        //Assert
        assertEquals(4, existingId, "Skal hente id=1 for eksisterende john.doe@example.com");
        System.out.println(newId);
        assertTrue(newId == 4, "Forventes at ny customer_id = 4, var " + newId);
    }

    @Test
    void createOffer() throws SQLException, DatabaseException {
        //Arrange
        float expensePrice = 1234.50f;
        float salesPrice = 2345.75f;
        int sellerId = 1;
        int customerId = 1;
        int carpLength = 500;
        int carpWidth = 300;
        int shedLength = 200;
        int shedWidth = 150;
        String sql = "SELECT total_expense_price, total_sales_price, expiration_date FROM test.offers WHERE offer_id = ?;";
        LocalDate expectedExp = LocalDate.now().plusDays(7);

        //Act
        int offerId = OfferMapper.createOffer(connectionPool, expensePrice, salesPrice, sellerId, carpLength, carpWidth, shedLength, shedWidth, customerId);

        //Assert
        assertTrue(offerId > 3, "Nyt offer_id skal være >3, var " + offerId);
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, offerId);
            try (ResultSet rs = ps.executeQuery()) {
                assertTrue(rs.next(), "Kunne ikke finde det nye tilbud i DB");
                assertEquals(expensePrice, rs.getFloat("total_expense_price"), 0.001f);
                assertEquals(salesPrice, rs.getFloat("total_sales_price"), 0.001f);
                assertEquals(Date.valueOf(expectedExp), rs.getDate("expiration_date"));
            }
        }
    }

    @Test
    void createMountsList() throws SQLException, DatabaseException {
        // Arrange
        int offerId = 2;
        ArrayList<MountForCalculator> mounts = new ArrayList<>();
        mounts.add(new MountForCalculator("Spær monterings beslag", 10, 1, ""));
        mounts.add(new MountForCalculator("Firkantskive til montering af rem", 6, 4, ""));
        String sql = "SELECT COUNT(offer_id) FROM test.mounts_list WHERE offer_id = ?;";

        // Act
        boolean ok = OfferMapper.createMountsList(connectionPool, mounts, offerId);

        // Assert
        assertTrue(ok, "createMountsList skal returnere true når alt lykkes");
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, offerId);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                assertEquals(2, rs.getInt(1));
            }
        }
    }

    @Test
    void createRoofList() throws SQLException, DatabaseException {
        //Arrange
        int offerId = 2;
        ArrayList<RoofForCalculator> roofs = new ArrayList<>();
        roofs.add(new RoofForCalculator("Tagplader monteres på spær", 10, 1, ""));
        roofs.add(new RoofForCalculator("Tagplader monteres på spær", 10, 2, ""));
        String sql = "SELECT COUNT(offer_id) FROM test.roof_list WHERE offer_id = ?;";

        //Act
        boolean ok = OfferMapper.createRoofList(connectionPool, roofs, offerId);

        //Assert
        assertTrue(ok);
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, offerId);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                assertEquals(2, rs.getInt(1));
            }
        }
    }

    @Test
    void createScrewsList() throws SQLException, DatabaseException {
        //Arrange
        int offerId = 2;
        ArrayList<ScrewForCalculator> screws = new ArrayList<>();
        screws.add(new ScrewForCalculator("Til spær beslag", 50, 1, ""));
        screws.add(new ScrewForCalculator("Til rem montering på stolpe", 30, 2, ""));
        String sql = "SELECT COUNT(offer_id) FROM test.screws_list WHERE offer_id = ?;";

        //Act
        boolean ok = OfferMapper.createScrewsList(connectionPool, screws, offerId);

        //Assert
        assertTrue(ok);
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, offerId);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                assertEquals(2, rs.getInt(1));
            }
        }
    }

    @Test
    void createWoodList() throws SQLException, DatabaseException {
        //Arrange
        int offerId = 2;
        ArrayList<WoodForCalculator> woods = new ArrayList<>();
        woods.add(new WoodForCalculator("Rem til montering på stolper", 100, 1, 1, 1, ""));
        woods.add(new WoodForCalculator("Spær til montering på rem", 200, 2, 2, 1, ""));
        String sql = "SELECT COUNT(offer_id) FROM test.wood_list WHERE offer_id = ?;";

        //Act
        boolean ok = OfferMapper.createWoodList(connectionPool, woods, offerId);

        //Assert
        assertTrue(ok);
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, offerId);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                assertEquals(2, rs.getInt(1));
            }
        }
    }

    @Test
    void updateTotalExpensesPrice() throws SQLException, DatabaseException {
        //Arrange
        int offerId = 1;
        float newExpenses = 12345.67f;
        String sql = "SELECT total_expense_price FROM test.offers WHERE offer_id = ?;";

        //Act
        boolean ok = OfferMapper.updateTotalExpensesPrice(connectionPool, newExpenses, offerId);

        //Assert
        assertTrue(ok, "updateTotalExpensesPrice should return true");
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, offerId);
            try (ResultSet rs = ps.executeQuery()) {
                assertTrue(rs.next());
                assertEquals(newExpenses, rs.getFloat("total_expense_price"), 0.1f);
            }
        }
    }

    @Test
    void updateTotalSalesPrice() throws SQLException, DatabaseException {
        //Arrange
        int offerId = 1;
        float newSales = 54321.99f;
        String sql = "SELECT total_sales_price FROM test.offers WHERE offer_id = ?;";

        //Act
        boolean ok = OfferMapper.updateTotalSalesPrice(connectionPool, newSales, offerId);

        //Assert
        assertTrue(ok, "updateTotalSalesPrice should return true");
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, offerId);
            try (ResultSet rs = ps.executeQuery()) {
                assertTrue(rs.next());
                assertEquals(newSales, rs.getFloat("total_sales_price"), 0.1f);
            }
        }
    }

    @Test
    void updateExpirationDate() throws SQLException, DatabaseException {
        //Arrange
        int offerId = 1;
        LocalDate expected = LocalDate.now().plusYears(5);
        String sql = "SELECT expiration_date FROM test.offers WHERE offer_id = ?;";

        //Act
        boolean ok = OfferMapper.updateExpirationDate(connectionPool, offerId);

        //Assert
        assertTrue(ok, "updateExpirationDate should return true");
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, offerId);
            try (ResultSet rs = ps.executeQuery()) {
                assertTrue(rs.next());
                assertEquals(Date.valueOf(expected), rs.getDate("expiration_date"));
            }
        }
    }

    @Test
    void deleteMountListByOfferId() throws SQLException, DatabaseException {
        //Arrange
        int offerId = 1;
        String sql = "SELECT COUNT(offer_id) FROM test.mounts_list WHERE offer_id = ?;";

        //Act
        boolean ok = OfferMapper.deleteMountListByOfferId(connectionPool, offerId);

        //Assert
        assertTrue(ok);
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, offerId);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                assertEquals(0, rs.getInt(1));
            }
        }
    }

    @Test
    void deleteRoofListByOfferId() throws SQLException, DatabaseException {
        //Arrange
        int offerId = 1;
        String sql = "SELECT COUNT(offer_id) FROM test.roof_list WHERE offer_id = ?;";

        //Act
        boolean ok = OfferMapper.deleteRoofListByOfferId(connectionPool, offerId);

        //Assert
        assertTrue(ok);
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, offerId);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                assertEquals(0, rs.getInt(1));
            }
        }
    }

    @Test
    void deleteScrewsListByOfferId() throws SQLException, DatabaseException {
        //Arrange
        int offerId = 1;
        String sql = "SELECT COUNT(offer_id) FROM test.screws_list WHERE offer_id = ?;";

        //Act
        boolean ok = OfferMapper.deleteScrewListByOfferId(connectionPool, offerId);

        //Assert
        assertTrue(ok);
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, offerId);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                assertEquals(0, rs.getInt(1));
            }
        }
    }

    @Test
    void deleteWoodListByOfferId() throws SQLException, DatabaseException {
        //Arrange
        int offerId = 1;
        String sql = "SELECT COUNT(offer_id) FROM test.wood_list WHERE offer_id = ?;";

        //Act
        boolean ok = OfferMapper.deleteWoodListByOfferId(connectionPool, offerId);

        //Assert
        assertTrue(ok);
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, offerId);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                assertEquals(0, rs.getInt(1));
            }
        }
    }

    @Test
    void deleteOfferAndEverythingLinked() throws SQLException, DatabaseException {
        //Arrange
        int offerId = 1;
        String offerSql = "SELECT COUNT(offer_id) FROM test.offers WHERE offer_id = ?;";
        String orderSql = "SELECT COUNT(offer_id) FROM test.offers WHERE offer_id = ?;";
        String mountSql = "SELECT COUNT(offer_id) FROM test.mounts_list WHERE offer_id = ?;";
        String roofSql = "SELECT COUNT(offer_id) FROM roof_list WHERE offer_id = ?;";
        String screwSql = "SELECT COUNT(offer_id) FROM test.screws_list WHERE offer_id = ?;";
        String woodSql = "SELECT COUNT(offer_id) FROM test.wood_list WHERE offer_id = ?;";

        //Act
        boolean ok = OfferMapper.deleteOfferAndEveryThinkLinkedToItByOfferId(connectionPool, offerId);

        //Assert
        assertTrue(ok);
        try (Connection conn = connectionPool.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(offerSql)) {
                ps.setInt(1, offerId);
                try (ResultSet rs = ps.executeQuery()) {
                    rs.next();
                    assertEquals(0, rs.getInt(1));
                }
            }
            try (PreparedStatement ps = conn.prepareStatement(orderSql)) {
                ps.setInt(1, offerId);
                try (ResultSet rs = ps.executeQuery()) {
                    rs.next();
                    assertEquals(0, rs.getInt(1));
                }
            }
            try (PreparedStatement ps = conn.prepareStatement(mountSql)) {
                ps.setInt(1, offerId);
                try (ResultSet rs = ps.executeQuery()) {
                    rs.next();
                    assertEquals(0, rs.getInt(1));
                }
            }
            try (PreparedStatement ps = conn.prepareStatement(roofSql)) {
                ps.setInt(1, offerId);
                try (ResultSet rs = ps.executeQuery()) {
                    rs.next();
                    assertEquals(0, rs.getInt(1));
                }
            }
            try (PreparedStatement ps = conn.prepareStatement(screwSql)) {
                ps.setInt(1, offerId);
                try (ResultSet rs = ps.executeQuery()) {
                    rs.next();
                    assertEquals(0, rs.getInt(1));
                }
            }
            try (PreparedStatement ps = conn.prepareStatement(woodSql)) {
                ps.setInt(1, offerId);
                try (ResultSet rs = ps.executeQuery()) {
                    rs.next();
                    assertEquals(0, rs.getInt(1));
                }
            }
        }
    }
}