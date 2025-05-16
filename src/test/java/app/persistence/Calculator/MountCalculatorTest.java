package app.persistence.Calculator;

import app.persistence.connection.ConnectionPool;
import app.persistence.mappers.OfferMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static app.persistence.mappers.testSetupForMappers.CreateTestSchemaDatabase.createTestSchemaWithData;
import static org.junit.jupiter.api.Assertions.*;

class MountCalculatorTest {
    private static MountCalculator calculator;
    private static ConnectionPool connectionPool;

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
    void setUp() { calculator = new MountCalculator(); }

    @Test
    void leftRafterMountsAmountCalculator() {
        // Arrange
        int rafterAmount = 10;
        int rafterBeamAmount = 3;
        int expected = 20;

        // Act
        int result = calculator.leftRafterMountsAmountCalculator(rafterAmount, rafterBeamAmount);

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void rightRafterMountsAmountCalculator() {
        // Arrange
        int rafterAmount = 10;
        int expected = 10;

        // Act
        int result = calculator.rightRafterMountsAmountCalculator(rafterAmount);

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void squareBracketsForRafterMountsAmountCalculator() {
        // Arrange
        int poleAmount = 6;
        int expected = 12;

        // Act
        int result = calculator.squareBracketsForRafterMountsAmountCalculator(poleAmount);

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void angleMountAmountCalculator() {
        // Arrange
        int noggingAmount = 22;
        int expected = 44;

        // Act
        int result = calculator.angleMountAmountCalculator(noggingAmount);

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void stableDoorHandleAmountCalculator() {
        // Arrange
        int amountOfDoorsForTheShed = 2;
        int expected = 2;

        // Act
        int result = calculator.stableDoorHandleAmountCalculator(amountOfDoorsForTheShed);

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void hingeForDoorAmountCalculator() {
        // Arrange
        int amountOfDoorsForTheShed = 2;
        int expected = 4;

        // Act
        int result = calculator.hingeForDoorAmountCalculator(amountOfDoorsForTheShed);

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void leftRafterMountCalculator() {
    }

    @Test
    void rightRafterMountCalculator() {
    }

    @Test
    void squareBracketsForRafterMountCalculator() {
    }

    @Test
    void angleMountCalculator() {
    }

    @Test
    void stableDoorHandleCalculator() {
    }

    @Test
    void hingeForDoorCalculator() {
    }
}