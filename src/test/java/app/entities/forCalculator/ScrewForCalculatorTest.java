package app.entities.forCalculator;

import app.exceptions.DatabaseException;
import app.persistence.Calculator.RoofCalculator;
import app.persistence.Calculator.ScrewCalculator;
import app.persistence.connection.ConnectionPool;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static app.persistence.mappers.testSetupForMappers.CreateTestSchemaDatabase.createTestSchemaWithData;
import static org.junit.jupiter.api.Assertions.*;

class ScrewForCalculatorTest {
    private static ConnectionPool connectionPool;
    private static ScrewCalculator screwCalculator;

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
        screwCalculator = new ScrewCalculator();
    }

//Screw Calculator
    @Test
    void screwForRoofCalculator() throws SQLException, DatabaseException {
        // Arrange
        int carportOneLength = 510; // Skrue 1
        int carportOneWidth = 330;  // Skrue 1
        int expectedAmountOne = 2;  // Skrue 1
        int carportTwoLength = 690; // Skrue 2
        int carportTwoWidth = 240;  // Skrue 2
        int expectedAmountTwo = 1;  // Skrue 2
        int expectedScrewId = 1;    // Skrue 1 og 2
        String screwName = "Plastmo bundskruer 200 stk.";
        String description = "Skruer til tagplader.";

        // Act
        ScrewForCalculator screwOne = screwCalculator.screwForRoofCalculator(connectionPool, carportOneLength, carportOneWidth, screwName);
        ScrewForCalculator screwTwo = screwCalculator.screwForRoofCalculator(connectionPool, carportTwoLength, carportTwoWidth, screwName);

        // Assert
        assertEquals(screwName, screwOne.getName());           // Skrue 1
        assertEquals(expectedAmountOne, screwOne.getAmount()); // Skrue 1
        assertEquals(expectedScrewId, screwOne.getScrewId());  // Skrue 1
        assertEquals(description, screwOne.getDescription());  // Skrue 1
        assertEquals(screwName, screwTwo.getName());           // Skrue 2
        assertEquals(expectedAmountTwo, screwTwo.getAmount()); // Skrue 2
        assertEquals(expectedScrewId, screwTwo.getScrewId());  // Skrue 2
        assertEquals(description, screwTwo.getDescription());  // Skrue 2
    }

    @Test
    void screwForFasciaAndBargeBoardCalculator() throws SQLException, DatabaseException {
        // Arrange
        int carportOneLength = 510; // Skrue 1
        int carportOneWidth = 330;  // Skrue 1
        int expectedAmountOne = 2;  // Skrue 1
        int carportTwoLength = 360; // Skrue 2
        int carportTwoWidth = 240;  // Skrue 2
        int expectedAmountTwo = 1;  // Skrue 2
        int expectedScrewId = 2;    // Skrue 1 og 2
        String screwName = "4,5 x 60 mm. skruer 200 stk.";
        String description = "Skruer til montering af stern & vandbræt.";

        // Act
        ScrewForCalculator screwOne = screwCalculator.screwForFasciaAndBargeBoardCalculator(connectionPool, carportOneLength, carportOneWidth, screwName);
        ScrewForCalculator screwTwo = screwCalculator.screwForFasciaAndBargeBoardCalculator(connectionPool, carportTwoLength, carportTwoWidth, screwName);

        // Assert
        assertEquals(screwName, screwOne.getName());           // Skrue 1
        assertEquals(expectedAmountOne, screwOne.getAmount()); // Skrue 1
        assertEquals(expectedScrewId, screwOne.getScrewId());  // Skrue 1
        assertEquals(description, screwOne.getDescription());  // Skrue 1
        assertEquals(screwName, screwTwo.getName());           // Skrue 2
        assertEquals(expectedAmountTwo, screwTwo.getAmount()); // Skrue 2
        assertEquals(expectedScrewId, screwTwo.getScrewId());  // Skrue 2
        assertEquals(description, screwTwo.getDescription());  // Skrue 2
    }

    @Test
    void screwForRafterMountsCalculator() throws SQLException, DatabaseException {
        // Arrange
        int carportOneLength = 510; // Skrue 1
        int carportOneWidth = 330;  // Skrue 1
        int shedOneLength = 210;    // Skrue 1
        int shedOneWidth = 260;     // Skrue 1
        int expectedAmountOne = 2;  // Skrue 1
        int carportTwoLength = 360; // Skrue 2
        int carportTwoWidth = 270;  // Skrue 2
        int shedTwoLength = 150;    // Skrue 2
        int shedTwoWidth = 180;     // Skrue 2
        int expectedAmountTwo = 1;  // Skrue 2
        int expectedScrewId = 3;    // Skrue 1 og 2
        String screwName = "4,0 x 50 mm. beslagskruer 250 stk.";
        String description = "Skruer til montering af universalbeslag, vinkelbeslag og hulbånd.";

        // Act
        ScrewForCalculator screwOne = screwCalculator.screwForRafterMountsCalculator(connectionPool, carportOneLength, carportOneWidth, screwName, shedOneLength, shedOneWidth);
        ScrewForCalculator screwTwo = screwCalculator.screwForRafterMountsCalculator(connectionPool, carportTwoLength, carportTwoWidth, screwName, shedTwoLength, shedTwoWidth);

        // Assert
        assertEquals(screwName, screwOne.getName());           // Skrue 1
        assertEquals(expectedAmountOne, screwOne.getAmount()); // Skrue 1
        assertEquals(expectedScrewId, screwOne.getScrewId());  // Skrue 1
        assertEquals(description, screwOne.getDescription());  // Skrue 1
        assertEquals(screwName, screwTwo.getName());           // Skrue 2
        assertEquals(expectedAmountTwo, screwTwo.getAmount()); // Skrue 2
        assertEquals(expectedScrewId, screwTwo.getScrewId());  // Skrue 2
        assertEquals(description, screwTwo.getDescription());  // Skrue 2
    }

    @Test
    void screwsForShedBoardsCalculator() throws SQLException, DatabaseException {
        // Arrange
        int shedOneLength = 270;    // Skrue 1
        int shedOneWidth = 300;     // Skrue 1
        int expectedAmountOne = 3;  // Skrue 1
        int shedTwoLength = 150;    // Skrue 2
        int shedTwoWidth = 180;     // Skrue 2
        int expectedAmountTwo = 2;  // Skrue 2
        int expectedScrewId = 4;    // Skrue 1 og 2
        int boardWidthInMm = 200;  // Skrue 1 og 2
        int spareBoardAmount = 10; // Skrue 1 og 2
        String screwName = "4,5 x 50 mm. Skruer 300 stk.";
        String description = "Til montering af skurets beklædning.";

        // Act
        ScrewForCalculator screwOne = screwCalculator.screwsForShedBoardsCalculator(connectionPool, shedOneLength, shedOneWidth, screwName,boardWidthInMm, spareBoardAmount);
        ScrewForCalculator screwTwo = screwCalculator.screwsForShedBoardsCalculator(connectionPool, shedTwoLength, shedTwoWidth, screwName, boardWidthInMm, spareBoardAmount);

        // Assert
        assertEquals(screwName, screwOne.getName());           // Skrue 1
        assertEquals(expectedAmountOne, screwOne.getAmount()); // Skrue 1
        assertEquals(expectedScrewId, screwOne.getScrewId());  // Skrue 1
        assertEquals(description, screwOne.getDescription());  // Skrue 1
        assertEquals(screwName, screwTwo.getName());           // Skrue 2
        assertEquals(expectedAmountTwo, screwTwo.getAmount()); // Skrue 2
        assertEquals(expectedScrewId, screwTwo.getScrewId());  // Skrue 2
        assertEquals(description, screwTwo.getDescription());  // Skrue 2
    }


    @Test
    void boltsForRafterBeamCalculator() throws SQLException, DatabaseException {
        // Arrange
        int carportOneLength = 570; // Bolt 1
        int carportOneWidth = 300;  // Bolt 1
        int expectedAmountOne = 18; // Bolt 1
        int carportTwoLength = 270; // Bolt 2
        int carportTwoWidth = 240;  // Bolt 2
        int expectedAmountTwo = 12; // Bolt 2
        int expectedScrewId = 5;    // Bolt 1 og 2
        String screwName = "Bræddebolt 10 x 120 mm.";
        String description = "Bolte til montering af rem på stolper.";

        // Act
        ScrewForCalculator screwOne = screwCalculator.boltsForRafterBeamCalculator(connectionPool, carportOneLength, carportOneWidth, screwName);
        ScrewForCalculator screwTwo = screwCalculator.boltsForRafterBeamCalculator(connectionPool, carportTwoLength, carportTwoWidth, screwName);

        // Assert
        assertEquals(screwName, screwOne.getName());           // Bolt 1
        assertEquals(expectedAmountOne, screwOne.getAmount()); // Bolt 1
        assertEquals(expectedScrewId, screwOne.getScrewId());  // Bolt 1
        assertEquals(description, screwOne.getDescription());  // Bolt 1
        assertEquals(screwName, screwTwo.getName());           // Bolt 2
        assertEquals(expectedAmountTwo, screwTwo.getAmount()); // Bolt 2
        assertEquals(expectedScrewId, screwTwo.getScrewId());  // Bolt 2
        assertEquals(description, screwTwo.getDescription());  // Bolt 2
    }

    @Test
    void plumbersTapeCalculator() throws SQLException, DatabaseException {
        // Arrange
        int carportOneLength = 900; // Hulbånd rulle 1
        int carportOneWidth = 720;  // Hulbånd rulle 1
        int expectedAmountOne = 2;  // Hulbånd rulle 1
        int shedOneLength = 0;      // Hulbånd rulle 1
        int carportTwoLength = 270; // Hulbånd rulle 2
        int carportTwoWidth = 240;  // Hulbånd rulle 2
        int shedTwoLength = 210;    // Hulbånd rulle 2
        int expectedAmountTwo = 1;  // Hulbånd rulle 2
        int expectedScrewId = 6;    // Hulbånd rulle 1 og 2
        String screwName = "Hulbånd 1x20 mm. 10 mtr.";
        String description = "Til vindkryds på spær.";

        // Act
        ScrewForCalculator screwOne = screwCalculator.plumbersTapeCalculator(connectionPool, carportOneLength, carportOneWidth, shedOneLength, screwName);
        ScrewForCalculator screwTwo = screwCalculator.plumbersTapeCalculator(connectionPool, carportTwoLength, carportTwoWidth, shedTwoLength, screwName);

        // Assert
        assertEquals(screwName, screwOne.getName());           // Hulbånd rulle 1
        assertEquals(expectedAmountOne, screwOne.getAmount()); // Hulbånd rulle 1
        assertEquals(expectedScrewId, screwOne.getScrewId());  // Hulbånd rulle 1
        assertEquals(description, screwOne.getDescription());  // Hulbånd rulle 1
        assertEquals(screwName, screwTwo.getName());           // Hulbånd rulle 2
        assertEquals(expectedAmountTwo, screwTwo.getAmount()); // Hulbånd rulle 2
        assertEquals(expectedScrewId, screwTwo.getScrewId());  // Hulbånd rulle 2
        assertEquals(description, screwTwo.getDescription());  // Hulbånd rulle 2
    }
}