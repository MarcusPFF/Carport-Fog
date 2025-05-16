package app.persistence.mappers;

import app.exceptions.DatabaseException;
import app.persistence.connection.ConnectionPool;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;

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
        offerMapper = new OfferMapper();
    }

    @Test
    void getTreatmentIdFromTreatmentName() throws SQLException, DatabaseException {
        //Arange
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
        //Arange
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
        //Arange
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
}