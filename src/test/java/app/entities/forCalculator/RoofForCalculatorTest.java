package app.entities.forCalculator;

import app.exceptions.DatabaseException;
import app.persistence.Calculator.RoofCalculator;
import app.persistence.connection.ConnectionPool;
import app.persistence.mappers.OfferMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import static app.persistence.mappers.testSetupForMappers.CreateTestSchemaDatabase.createTestSchemaWithData;
import static org.junit.jupiter.api.Assertions.*;

class RoofForCalculatorTest {
    private static ConnectionPool connectionPool;
    private static RoofCalculator roofCalculator;
    private static RoofForCalculator roofForCalculator;

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
        roofCalculator = new RoofCalculator();
        roofForCalculator = new RoofForCalculator();

    }

    @Test
    void roofListCalculator() throws DatabaseException {
        // Arrange
        int carportLength = 800;
        int carportWidth = 320;
        int sheetWidth = 120;
        int expectedRoofId = 1;
        String roofName = "MyRoof";

        // Act
        ArrayList<RoofForCalculator> roofList = roofForCalculator.roofListCalculator(connectionPool, carportLength, carportWidth, sheetWidth, roofName);

        // Assert
        for (RoofForCalculator roof : roofList) {
            assertEquals(roofName, roof.getName());
            assertEquals(3, roof.getAmount());
            assertEquals(expectedRoofId, roof.getRoofId());
            assertEquals("Tagplader monteres på spær.", roof.getDescription());
            expectedRoofId += 2;
        }

    }


//Roof Calculator
    @Test
    void roofCalculator() throws DatabaseException {
        // Arrange
        int carportLength = 600;
        int carportWidth = 320;
        int sheetWidth = 120;
        int expectedAmount = 3;
        int expectedRoofId = 1;
        String roofName = "Plastmo Ecolite blåtonet";
        String description = "Tagplader monteres på spær.";

        // Act
        RoofForCalculator roof = roofCalculator.roofCalculator(connectionPool, carportLength, carportWidth, sheetWidth, roofName);

        // Assert
        assertEquals(roofName, roof.getName());
        assertEquals(expectedAmount, roof.getAmount());
        assertEquals(expectedRoofId, roof.getRoofId());
        assertEquals(description, roof.getDescription());
    }
}