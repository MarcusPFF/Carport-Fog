package app.persistence.Calculator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardCalculatorTest {
    private static BoardCalculator calculator;

    @BeforeAll
    public static void init() {
        calculator = new BoardCalculator();
    }

    @Test
    void shedBoardAmountCalculator() {
        // Arrange:
        int shedLengthInCm = 500;
        int shedWidthInCm = 500;
        int boardWidthInMm = 200;
        int spareBoardAmount = 10;

        // Act:
        int result = calculator.shedBoardAmountCalculator(shedLengthInCm, shedWidthInCm, boardWidthInMm, spareBoardAmount);

        // Assert:
        assertEquals(110, result, "Der bør være 110 Brædder.");
    }

    @Test
    void fasciaBoardAmountCalculator() {
        // Arrange:
        int expected = 2;

        // Act:
        int result = calculator.fasciaBoardAmountCalculator();

        // Assert:
        assertEquals(expected, result, "Der bør være 2 Brædder.");
    }

    @Test
    void bargeBoardAmountCalculator() {
        // Arrange:
        int expected = 2;

        // Act:
        int result = calculator.fasciaBoardAmountCalculator();

        // Assert:
        assertEquals(expected, result, "Der bør være 2 Brædder.");
    }
}