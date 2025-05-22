package app.persistence.calculator;

import app.entities.forCalculator.MountForCalculator;
import app.entities.forCalculator.RoofForCalculator;
import app.entities.forCalculator.ScrewForCalculator;
import app.entities.forCalculator.WoodForCalculator;
import app.exceptions.DatabaseException;
import app.persistence.connection.ConnectionPool;
import app.persistence.mappers.PriceAndMaterialMapper;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import static app.persistence.mappers.testSetupForMappers.CreateTestSchemaDatabase.createTestSchemaWithData;
import static org.junit.jupiter.api.Assertions.*;

class PriceCalculatorTest {
    private static ConnectionPool connectionPool;
    private PriceCalculator priceCalculator;

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
        priceCalculator = new PriceCalculator();
    }


    @Test
    void calculateTotalOfferSalesPrice() throws DatabaseException {
        //Arrange
        float expense = 100.0f;
        float expected = 150.0f;

        //Act
        float actual = priceCalculator.calculateTotalOfferSalesPrice(connectionPool, expense);

        //Assert
        assertEquals(expected, actual, 0.001f);
    }

    @Test
    void calculateTotalOfferExpensePrice() throws DatabaseException {
        //Arrange
        ArrayList<WoodForCalculator> woods = new ArrayList<>();
        woods.add(new WoodForCalculator("wood", 2, 1, 1, 1, ""));
        ArrayList<RoofForCalculator> roofs = new ArrayList<>();
        roofs.add(new RoofForCalculator("roof", 3, 1, ""));
        ArrayList<MountForCalculator> mounts = new ArrayList<>();
        mounts.add(new MountForCalculator("mount", 5, 1, ""));
        ArrayList<ScrewForCalculator> screws = new ArrayList<>();
        screws.add(new ScrewForCalculator("screw", 10, 1, ""));
        float expected = 1923.90f;

        //Act
        float actual = priceCalculator.calculateTotalOfferExpensePrice(connectionPool, woods, roofs, mounts, screws);

        //Assert
        assertEquals(expected, actual, 0.1f);
    }

    @Test
    void calculateTotalWoodListPrice() throws DatabaseException {
        //Arrange
        ArrayList<WoodForCalculator> list = new ArrayList<>();
        list.add(new WoodForCalculator("wood", 2, 1, 1, 1, ""));
        list.add(new WoodForCalculator("wood", 3, 1, 1, 1, ""));
        float expected = 249.00f;

        //Act
        float actual = priceCalculator.calculateTotalWoodListPrice(connectionPool, list);

        //Assert
        assertEquals(expected, actual, 0.001f);
    }

    @Test
    void calculateTotalRoofListPrice() throws DatabaseException {
        //Arrange
        ArrayList<RoofForCalculator> list = new ArrayList<>();
        list.add(new RoofForCalculator("roof", 3, 1, ""));
        list.add(new RoofForCalculator("roof", 3, 1, ""));
        float expected = 2099.60f;

        //Act
        float actual = priceCalculator.calculateTotalRoofListPrice(connectionPool, list);

        //Assert
        assertEquals(expected, actual, 0.11f);
    }

    @Test
    void calculateTotalMountListPrice() throws DatabaseException {
        //Arrange
        ArrayList<MountForCalculator> list = new ArrayList<>();
        list.add(new MountForCalculator("mount", 5, 1, ""));
        float expected = 75.00f;

        //Act
        float actual = priceCalculator.calculateTotalMountListPrice(connectionPool, list);

        //Assert
        assertEquals(expected, actual, 0.001f);
    }

    @Test
    void calculateTotalScrewListPrice() throws DatabaseException {
        //Arrange
        ArrayList<ScrewForCalculator> list = new ArrayList<>();
        list.add(new ScrewForCalculator("screw", 10, 1, ""));
        float expected = 699.50f;

        //Act
        float actual = priceCalculator.calculateTotalScrewListPrice(connectionPool, list);

        //Assert
        assertEquals(expected, actual, 0.001f);
    }

    @Test
    void calculateWoodPrice() throws DatabaseException {
        //Arrange
        int dimensionId = 1;
        float meterPrice = new PriceAndMaterialMapper().getTotalWoodPrice(connectionPool, 1, 1, 1);
        int quantity = 2;
        float expected = 99.60f;

        //Act
        float actual = priceCalculator.calculateWoodPrice(connectionPool, meterPrice, quantity, dimensionId);
        //Assert
        assertEquals(expected, actual, 0.001f);
    }

    @Test
    void calculatePriceForEveryCategoryExceptWood() throws DatabaseException {
        //Arrange
        float price = 7.5f;
        int quantity = 4;
        float expected = 30.0f;

        //Act
        float actual = priceCalculator.calculatePriceForEveryCategoryExceptWood(connectionPool, price, quantity);

        //Assert
        assertEquals(expected, actual, 0.0001f);
    }

    @Test
    void getMarkupPercentageFromCurrentSalesPrice() {
        //Arrange
        float sales = 25000;
        float expenses = 10000;
        int expected = 150;

        //Act
        int actual = priceCalculator.getMarkupPercentageFromCurrentSalesPrice(sales, expenses);

        //Assert
        assertEquals(expected, actual);
    }
}