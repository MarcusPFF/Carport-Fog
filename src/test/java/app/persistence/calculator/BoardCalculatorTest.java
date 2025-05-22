package app.persistence.calculator;

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
        int result = calculator.fasciaAndBargeBoardAmountCalculator();

        // Assert:
        assertEquals(expected, result, "Der bør være 2 Brædder.");
    }

    @Test
    void totalFasciaAndBargeBoardAndDripCapLengthCalculator() {
        // Arrange:
        int carportLengthInCm = 500;
        int carportWidthInCm = 500;
        int expected = 5000;

        // Act:
        int result = calculator.totalFasciaAndBargeBoardAndDripCapLengthCalculator(carportLengthInCm, carportWidthInCm);

        // Assert:
        assertEquals(expected, result, "Der bør være 50 meter stern og vandbræt.");
    }

}