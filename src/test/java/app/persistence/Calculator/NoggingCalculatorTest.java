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
    void noggingForShedFrontAndBackAmountCalculator() {
        // Arrange:
        int shedWidthInCm = 510;
        int carportWidthInCm = 570;
        int poleAmountForWidth = 2;
        int expected = 12;

        // Act:
        int result = calculator.noggingForShedFrontAndBackAmountCalculator(shedWidthInCm, carportWidthInCm, poleAmountForWidth);

        // Assert:
        assertEquals(expected, result, "Der bør være 12 reglar til fronten og enden af skuret.");
    }

    @Test
    void noggingForShedSidesAmountCalculator() {
        // Arrange:
        int shedLengthInCm = 240;
        int shedWidthInCm = 500;
        int carportLengthInCm = 600;
        int carportWidthInCm = 600;
        int poleAmountForWidth = 2;
        int poleAmountForLength = 3;
        int expected = 5;

        // Act:
        int result = calculator.noggingForShedSidesAmountCalculator(shedLengthInCm, shedWidthInCm, carportLengthInCm, carportWidthInCm, poleAmountForLength, poleAmountForWidth);

        // Assert:
        assertEquals(expected, result, "Der bør være 5 reglar til siderne af skuret.");
    }

    @Test
    void noggingForZOnTheDoorAmountCalculator() {
        // Arrange:
        int amountOfDoorsForTheShed = 2;
        int expected = 2;

        // Act:
        int result = calculator.noggingForZOnTheDoorAmountCalculator(amountOfDoorsForTheShed);
        // Assert:
        assertEquals(expected, result, "Der bør være 2 lægter til skurets døre.");
    }
}