package app.persistence.Calculator;

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

    @Test
    void totalNoggingLengthCalculator() {
        // Arrange:
        int carportLengthInCm = 700;
        int noggingAmount = 3;
        int expected = 2100;

        // Act:
        int result = calculator.totalNoggingLengthCalculator(noggingAmount, carportLengthInCm);

        // Assert:
        assertEquals(expected, result, "Der bør være 21 meter ragler.");

    }
}