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
    public void testPoleAmountX() {
        // Arrange:
        int carportLengthInCm = 500;

        // Act:
        int result = calculator.poleAmountXCalculator(carportLengthInCm);

        // Assert:
        assertEquals(2, result, "Der bør være 2 stolper i længden.");
    }

    @Test
    public void testPoleAmountY() {
        // Arrange:
        int carportWidthInCm = 300;

        // Act:
        int result = calculator.poleAmountYCalculator(carportWidthInCm);

        // Assert:
        assertEquals(2, result, "Der bør være 2 stolper i bredden.");
    }

    @Test
    public void testShedPoleAmount() {
        // Arrange:
        int carportLengthInCm = 600;
        int carportWidthInCm = 780;
        int shedLengthInCm = 400;
        int shedWidthInCm = 400;
        int lengthAmount = 3;
        int widthAmount = 3;

        // Act:
        int result = calculator.shedPoleAmountCalculator(carportLengthInCm, carportWidthInCm, shedLengthInCm, shedWidthInCm, lengthAmount, widthAmount);

        // Assert:
        assertEquals(8, result, "Der bør være 4 stolper til skuret.");
    }
}
