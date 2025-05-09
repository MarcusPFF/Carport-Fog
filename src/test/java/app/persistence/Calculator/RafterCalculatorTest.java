package app.persistence.Calculator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RafterCalculatorTest {
    private static RafterCalculator calculator;

    @BeforeAll
    public static void init() {
        calculator = new RafterCalculator();
    }


    @Test
    void testRafterAmountForRoofCalculator() {
        // Arrange:
        int carportLengthInCm = 570;
        int expected = 11;

        // Act:
        int result = calculator.rafterAmountForRoofCalculator(carportLengthInCm);

        // Assert:
        assertEquals(expected, result, "Der bør være 10 spær.");

    }


    @Test
    void totalRafterHeightInMmCalculator() {
        // Arrange:
        int carportWidthInCm = 600;
        int expected = 245;

        // Act:
        int result = calculator.totalRafterHeightInMmCalculator(carportWidthInCm);

        // Assert:
        assertEquals(expected, result, "Der bør være en spær højde på 245mm.");

    }

    @Test
    void totalRafterWidthInMmCalculator() {
        // Arrange:
        int carportWidthInCm = 600;
        int expected = 60;

        int carportWidthInCm2 = 400;
        int expected2 = 45;

        // Act:
        int result = calculator.totalRafterWidthInMmCalculator(carportWidthInCm);

        int result2 = calculator.totalRafterWidthInMmCalculator(carportWidthInCm2);

        // Assert:
        assertEquals(expected, result, "Der bør være en spær brede på 60mm.");
        assertEquals(expected2, result2, "Der bør være en spær brede på 45mm.");
    }
}