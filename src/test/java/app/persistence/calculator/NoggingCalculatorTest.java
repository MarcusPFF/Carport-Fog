package app.persistence.calculator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoggingCalculatorTest {
    private static NoggingCalculator calculator;

    @BeforeAll
    public static void init() {
        calculator = new NoggingCalculator();
    }

    @Test
    void noggingAmountCalculator() {
        // Arrange:
        int carportWidthInCm = 700;
        int expected = 3;

        // Act:
        int result = calculator.noggingAmountCalculator(carportWidthInCm);

        // Assert:
        assertEquals(expected, result, "Der bør være 3 ragler.");
    }

}