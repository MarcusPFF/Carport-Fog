package app.persistence.Calculator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScrewCalculatorTest {
    private static ScrewCalculator calculator;

    @BeforeAll
    public static void init() {
        calculator = new ScrewCalculator();
    }


    @Test
    void screwsForRoofPackAmountCalculator() {
        // Arrange:
        int carportLengthInCm = 500;
        int carportWidthInCm = 500;
        int amountPrContainer = 200;
        int expected = 2;

        // Act:
        int result = calculator.screwsForRoofPackAmountCalculator(carportLengthInCm, carportWidthInCm, amountPrContainer);

        // Assert:
        assertEquals(expected, result, "Der bør være parker skruer med 200stk i hver.");
    }

    @Test
    void screwsForFasciaAndBargeBoardPackAmountCalculator() {
        // Arrange:
        int carportLengthInCm = 500;
        int carportWidthInCm = 250;
        float amountPrContainer = 200;
        int expected = 2;

        // Act:
        int result = calculator.screwsForFasciaAndBargeBoardPackAmountCalculator(carportLengthInCm, carportWidthInCm, amountPrContainer);

        // Assert:
        assertEquals(expected, result);
    }

    @Test
    void screwsForMountsPackAmountCalculator() {
        // Arrange:
        int rafterMountsAmount = 20;
        int noggingAmount = 16;
        float amountPrContainer = 250;
        int expected = 2;

        // Act:
        int result = calculator.screwsForMountsPackAmountCalculator(rafterMountsAmount, noggingAmount, amountPrContainer);

        // Assert:
        assertEquals(expected, result);
    }

    @Test
    void screwsForShedBoardsPackAmountCalculator() {
        // Arrange:
        int boardAmount = 70;
        float amountPrContainer = 300;
        int expected = 3;

        // Act:
        int result = calculator.screwsForShedBoardsPackAmountCalculator(boardAmount, amountPrContainer);

        // Assert:
        assertEquals(expected, result);
    }

    @Test
    void boltsForRafterBeamAmountCalculator() {
        // Arrange:
        int rafterBeamAmount = 2;
        int poleAmount = 4;
        int expected = 24;

        // Act:
        int result = calculator.boltsForRafterBeamAmountCalculator(rafterBeamAmount, poleAmount);

        // Assert:
        assertEquals(expected, result);
    }

    @Test
    void plumbersTapeAmountCalculator() {
        // Arrange:
        int shedLengthInCm = 200;
        int carportLengthInCm = 780;
        int carportWidthInCm = 600;
        int metersPrRollInCm = 1000;
        int expected = 1;

        // Act:
        int result = calculator.plumbersTapeAmountCalculator(shedLengthInCm, carportLengthInCm, carportWidthInCm, metersPrRollInCm);

        // Assert:
        assertEquals(expected, result);
    }
}