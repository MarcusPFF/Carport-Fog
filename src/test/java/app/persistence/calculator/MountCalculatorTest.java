package app.persistence.calculator;

import app.persistence.connection.ConnectionPool;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MountCalculatorTest {
    private static MountCalculator calculator;
    private static ConnectionPool connectionPool;

    @BeforeAll
    static void beforeAll() {
        calculator = new MountCalculator();
    }


    @Test
    void leftRafterMountsAmountCalculator() {
        // Arrange
        int rafterAmount = 10;
        int rafterBeamAmount = 3;
        int expected = 20;

        // Act
        int result = calculator.leftRafterMountsAmountCalculator(rafterAmount, rafterBeamAmount);

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void rightRafterMountsAmountCalculator() {
        // Arrange
        int rafterAmount = 10;
        int expected = 10;

        // Act
        int result = calculator.rightRafterMountsAmountCalculator(rafterAmount);

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void squareBracketsForRafterMountsAmountCalculator() {
        // Arrange
        int poleAmount = 6;
        int expected = 12;

        // Act
        int result = calculator.squareBracketsForRafterMountsAmountCalculator(poleAmount);

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void angleMountAmountCalculator() {
        // Arrange
        int noggingAmount = 22;
        int expected = 44;

        // Act
        int result = calculator.angleMountAmountCalculator(noggingAmount);

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void stableDoorHandleAmountCalculator() {
        // Arrange
        int amountOfDoorsForTheShed = 2;
        int expected = 2;

        // Act
        int result = calculator.stableDoorHandleAmountCalculator(amountOfDoorsForTheShed);

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void hingeForDoorAmountCalculator() {
        // Arrange
        int amountOfDoorsForTheShed = 2;
        int expected = 4;

        // Act
        int result = calculator.hingeForDoorAmountCalculator(amountOfDoorsForTheShed);

        // Assert
        assertEquals(expected, result);
    }
}