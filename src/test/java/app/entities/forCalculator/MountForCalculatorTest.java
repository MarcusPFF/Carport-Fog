package app.entities.forCalculator;

import app.exceptions.DatabaseException;
import app.persistence.calculator.MountCalculator;
import app.persistence.connection.ConnectionPool;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static app.persistence.mappers.testSetupForMappers.CreateTestSchemaDatabase.createTestSchemaWithData;
import static app.persistence.mappers.testSetupForMappers.CreateTestSchemaDatabase.insertDataInTestDatabase;
import static org.junit.jupiter.api.Assertions.*;

class MountForCalculatorTest {
    private static ConnectionPool connectionPool;
    private static MountCalculator mountCalculator;

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

        mountCalculator = new MountCalculator();
    }


//Mounts calculator
    @Test
    void leftRafterMountCalculator() throws SQLException, DatabaseException {
        // Arrange
        int carportLength = 600;
        int carportWidth = 320;
        int expectedAmount = 11;
        int expectedRoofId = 2;
        String mountName = "Universalbeslag 190 mm venstre";
        String description = "Til montering af spær på rem.";

        // Act
        MountForCalculator mount = mountCalculator.leftRafterMountCalculator(connectionPool, carportLength, carportWidth, mountName);

        // Assert
        assertEquals(mountName, mount.getName());
        assertEquals(expectedAmount, mount.getAmount());
        assertEquals(expectedRoofId, mount.getMountId());
        assertEquals(description, mount.getDescription());
    }

    @Test
    void rightRafterMountCalculator() throws SQLException, DatabaseException {
        // Arrange
        int carportLength = 540;
        int expectedAmount = 10;
        int expectedRoofId = 1;
        String mountName = "Universalbeslag 190 mm højre";
        String description = "Til montering af spær på rem.";

        // Act
        MountForCalculator mount = mountCalculator.rightRafterMountCalculator(connectionPool, carportLength, mountName);

        // Assert
        assertEquals(mountName, mount.getName());
        assertEquals(expectedAmount, mount.getAmount());
        assertEquals(expectedRoofId, mount.getMountId());
        assertEquals(description, mount.getDescription());
    }

    @Test
    void squareBracketsForRafterMountCalculator() throws SQLException, DatabaseException {
        // Arrange
        int carportLength = 600;
        int carportWidth = 320;
        int expectedAmount = 12;
        int expectedRoofId = 3;
        String mountName = "Firkantskiver 40 x 40 x 11 mm";
        String description = "Til montering af rem på stolper.";

        // Act
        MountForCalculator mount = mountCalculator.squareBracketsForRafterMountCalculator(connectionPool, carportLength, carportWidth, mountName);

        // Assert
        assertEquals(mountName, mount.getName());
        assertEquals(expectedAmount, mount.getAmount());
        assertEquals(expectedRoofId, mount.getMountId());
        assertEquals(description, mount.getDescription());
    }

    @Test
    void angleMountCalculator() throws SQLException, DatabaseException {
        // Arrange
        int carportLength = 510;
        int carportWidth = 330;
        int shedLength = 210;
        int shedWidth = 270;
        int expectedAmount = 32;
        int expectedRoofId = 6;
        String mountName = "Vinkelbeslag 50 x 50 x 35 mm";
        String description = "Til montering af løsholter i skur.";

        // Act
        MountForCalculator mount = mountCalculator.angleMountCalculator(connectionPool, carportLength, carportWidth, shedLength, shedWidth, mountName);

        // Assert
        assertEquals(mountName, mount.getName());
        assertEquals(expectedAmount, mount.getAmount());
        assertEquals(expectedRoofId, mount.getMountId());
        assertEquals(description, mount.getDescription());
    }

    @Test
    void stableDoorHandleCalculator() throws SQLException, DatabaseException {
        // Arrange
        int amountOfDoorsForShed = 2;
        int expectedAmount = 2;
        int expectedRoofId = 4;
        String mountName = "Stalddørsgreb 50 x 75 mm";
        String description = "Til lås på dør i skur.";

        // Act
        MountForCalculator mount = mountCalculator.stableDoorHandleCalculator(connectionPool, amountOfDoorsForShed, mountName);

        // Assert
        assertEquals(mountName, mount.getName());
        assertEquals(expectedAmount, mount.getAmount());
        assertEquals(expectedRoofId, mount.getMountId());
        assertEquals(description, mount.getDescription());
    }

    @Test
    void hingeForDoorCalculator() throws SQLException, DatabaseException {
        // Arrange
        int amountOfDoorsForShed = 2;
        int expectedAmount = 4;
        int expectedRoofId = 5;
        String mountName = "T-Hængsel 390 mm";
        String description = "Til skurets dør.";

        // Act
        MountForCalculator mount = mountCalculator.hingeForDoorCalculator(connectionPool, amountOfDoorsForShed, mountName);

        // Assert
        assertEquals(mountName, mount.getName());
        assertEquals(expectedAmount, mount.getAmount());
        assertEquals(expectedRoofId, mount.getMountId());
        assertEquals(description, mount.getDescription());
    }
}