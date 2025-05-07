package app.persistence.Calculator;
import app.entities.forCalculator.WoodForCalculator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PoleCalculatorTest {
    private static PoleCalculator calculator;

    @BeforeAll
    public static void init() {
        calculator = new PoleCalculator();
    }

    @Test
    public void testPoleAmountCalculator() {
        // Arrange:
        int carportLength = 500;
        int carportWidth = 300;
        int shedLength = 200;
        int shedWidth = 150;

        // Act:
        WoodForCalculator result = calculator.poleAmountCalculator(carportLength, carportWidth, shedLength, shedWidth);

        // Assert:
        assertNotNull(result, "Resultatet bør ikke være null");
        assertEquals("Stolpe", result.getName(), "Materialet bør være 'Stolpe'");
        assertEquals(10, result.getAmount(), "Antallet af stolper bør være 6");
        assertEquals(3000, result.getTotalLengthInCm(), "Totalprisen bør være 1800");
    }

    @Test
    public void testPoleAmountX() {
        // Arrange:
        int carportLength = 500;

        // Act:
        int result = calculator.poleAmountX(carportLength);

        // Assert:
        assertEquals(3, result, "Der bør være 16 stolper i længden.");
    }

    @Test
    public void testPoleAmountY() {
        // Arrange:
        int carportWidth = 300;

        // Act:
        int result = calculator.poleAmountY(carportWidth);

        // Assert:
        assertEquals(2, result, "Der bør være 4 stolper i bredden.");
    }

    @Test
    public void testShedPoleAmount() {
        // Arrange:
        int carportLength = 500;
        int carportWidth = 300;
        int shedLength = 200;
        int shedWidth = 150;
        int lengthAmount = 16;
        int widthAmount = 2;

        // Act:
        int result = calculator.shedPoleAmount(carportLength, carportWidth, shedLength, shedWidth, lengthAmount, widthAmount);

        // Assert:
        assertEquals(4, result, "Der bør være 4 stolper til skuret.");
    }
}
