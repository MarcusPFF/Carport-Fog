package app.persistence.Calculator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoofCalculatorTest {
    private static RoofCalculator calculator;

    @BeforeAll
    public static void init() {
        calculator = new RoofCalculator();
    }

    @Test
    void roofAmountInWidthCalculator() {
        // Arrange:
        int carportWidthInCm = 500;
        int roofPladeWidthInCm = 120;

        // Act:
        int result = calculator.roofAmountInWidthCalculator(carportWidthInCm, roofPladeWidthInCm);

        // Assert:
        assertEquals(5, result, "Der bør være 5 stolper i længden.");
    }

    @Test
    void roofLengthCalculator() {
        // Arrange:
        int carportLengthInCm = 500;

        // Act:
        int result = calculator.roofLengthCalculator(carportLengthInCm);

        // Assert:
        assertEquals(600, result, "De bør være 600 cm i længden.");
    }
}