package app.persistence.calculator;
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
        assertEquals(3, result, "Der bør være 16 stolper i længden.");
    }

    @Test
    public void testPoleAmountY() {
        // Arrange:
        int carportWidthInCm = 300;

        // Act:
        int result = calculator.poleAmountYCalculator(carportWidthInCm);

        // Assert:
        assertEquals(2, result, "Der bør være 4 stolper i bredden.");
    }

    @Test
    public void testShedPoleAmount() {
        // Arrange:
        int carportLengthInCm = 500;
        int carportWidthInCm = 300;
        int shedLengthInCm = 200;
        int shedWidthInCm = 150;
        int lengthAmount = 16;
        int widthAmount = 2;

        // Act:
        int result = calculator.shedPoleAmountCalculator(carportLengthInCm, carportWidthInCm, shedLengthInCm, shedWidthInCm, lengthAmount, widthAmount);

        // Assert:
        assertEquals(4, result, "Der bør være 4 stolper til skuret.");
    }
}
