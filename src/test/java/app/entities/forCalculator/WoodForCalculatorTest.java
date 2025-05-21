package app.entities.forCalculator;

import app.exceptions.DatabaseException;
import app.persistence.calculator.*;
import app.persistence.connection.ConnectionPool;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static app.persistence.mappers.testSetupForMappers.CreateTestSchemaDatabase.createTestSchemaWithData;
import static org.junit.jupiter.api.Assertions.*;

class WoodForCalculatorTest {
    private static ConnectionPool connectionPool;
    private static BoardCalculator boardCalculator;
    private static NoggingCalculator noggingCalculator;
    private static PoleCalculator poleCalculator;
    private static RafterCalculator rafterCalculator;

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
        boardCalculator = new BoardCalculator();
        noggingCalculator = new NoggingCalculator();
        poleCalculator = new PoleCalculator();
        rafterCalculator = new RafterCalculator();
    }


//Board calculator
    @Test
    void shedBoardCalculator() throws SQLException, DatabaseException {
        // Arrange
        int shedOneLength = 150;
        int shedOneWidth = 180;
        int boardWidthInMm = 100;
        int boardDepthInMm = 20;
        int boardLengthInCm = 210;
        int spareBoardAmount = 10;
        String woodTypeName = "Bræt";
        String woodTreatmentName = "Trykimprægneret";
        String expectedDescription = "Til beklædning af skur.";
        int expectedAmountOne = 76;
        int expectedDimensionId = 3;
        int expectedTreatmentId = 1;
        int expectedWoodTypeId = 5;

        // Act
        WoodForCalculator wood = boardCalculator.shedBoardCalculator(connectionPool, shedOneLength, shedOneWidth, woodTypeName, woodTreatmentName, boardDepthInMm, boardWidthInMm, boardLengthInCm, spareBoardAmount);

        // Assert
        assertEquals(woodTypeName, wood.getName());
        assertEquals(expectedAmountOne, wood.getAmount());
        assertEquals(expectedDimensionId, wood.getWoodDimensionId());
        assertEquals(expectedTreatmentId, wood.getWoodTreatmentId());
        assertEquals(expectedWoodTypeId, wood.getWoodTypeId());
        assertEquals(expectedDescription, wood.getDescription());
    }

    @Test
    void fasciaBoardFrontAndBackCalculator() throws SQLException, DatabaseException {
        // Arrange
        int carportWidth = 240;
        int boardWidthInMm = 200;
        int boardDepthInMm = 25;
        String woodTypeName = "Bræt";
        String woodTreatmentName = "Trykimprægneret";
        String expectedDescription = "Understernbrædder til for & bag ende.";
        int expectedAmountOne = 2;
        int expectedDimensionId = 203;
        int expectedTreatmentId = 1;
        int expectedWoodTypeId = 5;

        // Act
        WoodForCalculator wood = boardCalculator.fasciaBoardFrontAndBackCalculator(connectionPool, carportWidth, woodTypeName, woodTreatmentName, boardDepthInMm, boardWidthInMm);

        // Assert
        assertEquals(woodTypeName, wood.getName());
        assertEquals(expectedAmountOne, wood.getAmount());
        assertEquals(expectedDimensionId, wood.getWoodDimensionId());
        assertEquals(expectedTreatmentId, wood.getWoodTreatmentId());
        assertEquals(expectedWoodTypeId, wood.getWoodTypeId());
        assertEquals(expectedDescription, wood.getDescription());
    }

    @Test
    void fasciaBoardSidesCalculator() throws SQLException, DatabaseException {
        // Arrange
        int carportLength = 300;
        int boardWidthInMm = 200;
        int boardDepthInMm = 25;
        String woodTypeName = "Bræt";
        String woodTreatmentName = "Trykimprægneret";
        String expectedDescription = "Understernbrædder til siderne.";
        int expectedAmountOne = 2;
        int expectedDimensionId = 204;
        int expectedTreatmentId = 1;
        int expectedWoodTypeId = 5;

        // Act
        WoodForCalculator wood = boardCalculator.fasciaBoardSidesCalculator(connectionPool, carportLength, woodTypeName, woodTreatmentName, boardDepthInMm, boardWidthInMm);

        // Assert
        assertEquals(woodTypeName, wood.getName());
        assertEquals(expectedAmountOne, wood.getAmount());
        assertEquals(expectedDimensionId, wood.getWoodDimensionId());
        assertEquals(expectedTreatmentId, wood.getWoodTreatmentId());
        assertEquals(expectedWoodTypeId, wood.getWoodTypeId());
        assertEquals(expectedDescription, wood.getDescription());
    }

    @Test
    void bargeBoardFrontCalculator() throws SQLException, DatabaseException {
        // Arrange
        int carportWidth = 240;
        int boardWidthInMm = 125;
        int boardDepthInMm = 25;
        String woodTypeName = "Bræt";
        String woodTreatmentName = "Trykimprægneret";
        String expectedDescription = "Oversternbrædder til forende.";
        int expectedAmountOne = 1;
        int expectedDimensionId = 137;
        int expectedTreatmentId = 1;
        int expectedWoodTypeId = 5;

        // Act
        WoodForCalculator wood = boardCalculator.bargeBoardFrontCalculator(connectionPool, carportWidth, woodTypeName, woodTreatmentName, boardDepthInMm, boardWidthInMm);

        // Assert
        assertEquals(woodTypeName, wood.getName());
        assertEquals(expectedAmountOne, wood.getAmount());
        assertEquals(expectedDimensionId, wood.getWoodDimensionId());
        assertEquals(expectedTreatmentId, wood.getWoodTreatmentId());
        assertEquals(expectedWoodTypeId, wood.getWoodTypeId());
        assertEquals(expectedDescription, wood.getDescription());
    }

    @Test
    void bargeBoardSidesCalculator() throws SQLException, DatabaseException {
        // Arrange
        int carportLength = 300;
        int boardWidthInMm = 125;
        int boardDepthInMm = 25;
        String woodTypeName = "Bræt";
        String woodTreatmentName = "Trykimprægneret";
        String expectedDescription = "Oversternbrædder til siderne.";
        int expectedAmountOne = 2;
        int expectedDimensionId = 138;
        int expectedTreatmentId = 1;
        int expectedWoodTypeId = 5;

        // Act
        WoodForCalculator wood = boardCalculator.bargeBoardSidesCalculator(connectionPool, carportLength, woodTypeName, woodTreatmentName, boardDepthInMm, boardWidthInMm);

        // Assert
        assertEquals(woodTypeName, wood.getName());
        assertEquals(expectedAmountOne, wood.getAmount());
        assertEquals(expectedDimensionId, wood.getWoodDimensionId());
        assertEquals(expectedTreatmentId, wood.getWoodTreatmentId());
        assertEquals(expectedWoodTypeId, wood.getWoodTypeId());
        assertEquals(expectedDescription, wood.getDescription());
    }

    @Test
    void dripCapForBoardFrontCalculator() throws SQLException, DatabaseException {
        // Arrange
        int carportWidth = 240;
        int boardWidthInMm = 100;
        int boardDepthInMm = 20;
        String woodTypeName = "Bræt";
        String woodTreatmentName = "Trykimprægneret";
        String expectedDescription = "Vandbræt til stern i forende.";
        int expectedAmountOne = 1;
        int expectedDimensionId = 5;
        int expectedTreatmentId = 1;
        int expectedWoodTypeId = 5;

        // Act
        WoodForCalculator wood = boardCalculator.dripCapForBoardFrontCalculator(connectionPool, carportWidth, woodTypeName, woodTreatmentName, boardDepthInMm, boardWidthInMm);

        // Assert
        assertEquals(woodTypeName, wood.getName());
        assertEquals(expectedAmountOne, wood.getAmount());
        assertEquals(expectedDimensionId, wood.getWoodDimensionId());
        assertEquals(expectedTreatmentId, wood.getWoodTreatmentId());
        assertEquals(expectedWoodTypeId, wood.getWoodTypeId());
        assertEquals(expectedDescription, wood.getDescription());
    }

    @Test
    void dripCapForBoardSidesCalculator() throws SQLException, DatabaseException {
        // Arrange
        int carportLength = 300;
        int boardWidthInMm = 100;
        int boardDepthInMm = 20;
        String woodTypeName = "Bræt";
        String woodTreatmentName = "Trykimprægneret";
        String expectedDescription = "Vandbræt til stern i siderne.";
        int expectedAmountOne = 2;
        int expectedDimensionId = 6;
        int expectedTreatmentId = 1;
        int expectedWoodTypeId = 5;

        // Act
        WoodForCalculator wood = boardCalculator.dripCapForBoardSidesCalculator(connectionPool, carportLength, woodTypeName, woodTreatmentName, boardDepthInMm, boardWidthInMm);

        // Assert
        assertEquals(woodTypeName, wood.getName());
        assertEquals(expectedAmountOne, wood.getAmount());
        assertEquals(expectedDimensionId, wood.getWoodDimensionId());
        assertEquals(expectedTreatmentId, wood.getWoodTreatmentId());
        assertEquals(expectedWoodTypeId, wood.getWoodTypeId());
        assertEquals(expectedDescription, wood.getDescription());
    }


//Nogging calculator
    @Test
    void noggingForShedFrontAndBackCalculator() throws SQLException, DatabaseException {
        // Arrange
        int shedWidth = 180;
        int noggingWidthInMm = 45;
        int noggingHeightInMm = 95;
        String woodTypeName = "Reglar";
        String woodTreatmentName = "Ubehandlet";
        String expectedDescription = "Løsholter til forside og bagside af skur (Skal skæres til).";
        int expectedAmountOne = 6;
        int expectedDimensionId = 354;
        int expectedTreatmentId = 2;
        int expectedWoodTypeId = 3;

        // Act
        WoodForCalculator wood = noggingCalculator.noggingForShedFrontAndBackCalculator(connectionPool, noggingWidthInMm, noggingHeightInMm, shedWidth, woodTypeName, woodTreatmentName);

        // Assert
        assertEquals(woodTypeName, wood.getName());
        assertEquals(expectedAmountOne, wood.getAmount());
        assertEquals(expectedDimensionId, wood.getWoodDimensionId());
        assertEquals(expectedTreatmentId, wood.getWoodTreatmentId());
        assertEquals(expectedWoodTypeId, wood.getWoodTypeId());
        assertEquals(expectedDescription, wood.getDescription());
    }

    @Test
    void noggingForShedSidesCalculator() throws SQLException, DatabaseException {
        // Arrange
        int carportWidth = 240;
        int shedLength = 150;
        int shedWidth = 180;
        int noggingWidthInMm = 45;
        int noggingHeightInMm = 95;
        String woodTypeName = "Reglar";
        String woodTreatmentName = "Ubehandlet";
        String expectedDescription = "Løsholter til siderne af skur (Skal skæres til).";
        int expectedAmountOne = 4;
        int expectedDimensionId = 353;
        int expectedTreatmentId = 2;
        int expectedWoodTypeId = 3;

        // Act
        WoodForCalculator wood = noggingCalculator.noggingForShedSidesCalculator(connectionPool, noggingWidthInMm, noggingHeightInMm, carportWidth, shedLength, shedWidth, woodTypeName, woodTreatmentName);

        // Assert
        assertEquals(woodTypeName, wood.getName());
        assertEquals(expectedAmountOne, wood.getAmount());
        assertEquals(expectedDimensionId, wood.getWoodDimensionId());
        assertEquals(expectedTreatmentId, wood.getWoodTreatmentId());
        assertEquals(expectedWoodTypeId, wood.getWoodTypeId());
        assertEquals(expectedDescription, wood.getDescription());
    }

    @Test
    void noggingForZOnTheDoorCalculator() throws SQLException, DatabaseException {
        // Arrange
        int noggingWidthInMm = 40;
        int noggingHeightInMm = 75;
        int noggingLengthInCm = 420;
        int amountOfDoorsForTheShed = 2;
        String woodTypeName = "Lægte";
        String woodTreatmentName = "Ubehandlet";
        String expectedDescription = "Til z på bagside af dør.";
        int expectedAmountOne = 2;
        int expectedDimensionId = 340;
        int expectedTreatmentId = 2;
        int expectedWoodTypeId = 2;

        // Act
        WoodForCalculator wood = noggingCalculator.noggingForZOnTheDoorCalculator(connectionPool, noggingWidthInMm, noggingHeightInMm, noggingLengthInCm, woodTypeName, woodTreatmentName, amountOfDoorsForTheShed);

        // Assert
        assertEquals(woodTypeName, wood.getName());
        assertEquals(expectedAmountOne, wood.getAmount());
        assertEquals(expectedDimensionId, wood.getWoodDimensionId());
        assertEquals(expectedTreatmentId, wood.getWoodTreatmentId());
        assertEquals(expectedWoodTypeId, wood.getWoodTypeId());
        assertEquals(expectedDescription, wood.getDescription());
    }


//Pole calculator
    @Test
    void poleCalculator() throws SQLException, DatabaseException {
        // Arrange
        int carportLength = 300;
        int carportWidth = 270;
        int shedLength = 150;
        int shedWidth = 180;
        int carportHeightInCm = 210;
        int poleWidthInMm = 100;
        int poleHeightInMm = 100;
        String woodTypeName = "Stolpe";
        String woodTreatmentName = "Trykimprægneret";
        String expectedDescription = "Stolper nedgraves 90-100 cm. i jord.";
        int expectedAmountOne = 8;
        int expectedDimensionId = 1018;
        int expectedTreatmentId = 1;
        int expectedWoodTypeId = 4;

        // Act
        WoodForCalculator wood = poleCalculator.poleCalculator(connectionPool, carportLength, carportWidth, shedLength, shedWidth, carportHeightInCm, poleWidthInMm, poleHeightInMm, woodTypeName, woodTreatmentName);

        // Assert
        assertEquals(woodTypeName, wood.getName());
        assertEquals(expectedAmountOne, wood.getAmount());
        assertEquals(expectedDimensionId, wood.getWoodDimensionId());
        assertEquals(expectedTreatmentId, wood.getWoodTreatmentId());
        assertEquals(expectedWoodTypeId, wood.getWoodTypeId());
        assertEquals(expectedDescription, wood.getDescription());
    }


//Rafter Calculator
    @Test
    void rafterForRoofCalculator() throws SQLException, DatabaseException {
        // Arrange
        int carportLength = 300;
        int carportWidth = 270;
        String woodTypeName = "Spær";
        String woodTreatmentName = "Ubehandlet";
        String expectedDescription = "Monteres på rem.";
        int expectedAmountOne = 6;
        int expectedDimensionId = 401;
        int expectedTreatmentId = 2;
        int expectedWoodTypeId = 1;

        // Act
        WoodForCalculator wood = rafterCalculator.rafterForRoofCalculator(connectionPool, carportLength, carportWidth, woodTypeName, woodTreatmentName);

        // Assert
        assertEquals(woodTypeName, wood.getName());
        assertEquals(expectedAmountOne, wood.getAmount());
        assertEquals(expectedDimensionId, wood.getWoodDimensionId());
        assertEquals(expectedTreatmentId, wood.getWoodTreatmentId());
        assertEquals(expectedWoodTypeId, wood.getWoodTypeId());
        assertEquals(expectedDescription, wood.getDescription());
    }

    @Test
    void rafterBeamCalculator() throws SQLException, DatabaseException {
        // Arrange
        int carportLength = 300;
        int carportWidth = 690;
        String woodTypeName = "Spær";
        String woodTreatmentName = "Ubehandlet";
        String expectedDescription = "Remme, sadles ned i stolper.";
        int expectedAmountOne = 3;
        int expectedDimensionId = 402;
        int expectedTreatmentId = 2;
        int expectedWoodTypeId = 1;

        // Act
        WoodForCalculator wood = rafterCalculator.rafterBeamCalculator(connectionPool, carportLength, carportWidth, woodTypeName, woodTreatmentName);

        // Assert
        assertEquals(woodTypeName, wood.getName());
        assertEquals(expectedAmountOne, wood.getAmount());
        assertEquals(expectedDimensionId, wood.getWoodDimensionId());
        assertEquals(expectedTreatmentId, wood.getWoodTreatmentId());
        assertEquals(expectedWoodTypeId, wood.getWoodTypeId());
        assertEquals(expectedDescription, wood.getDescription());
    }
}