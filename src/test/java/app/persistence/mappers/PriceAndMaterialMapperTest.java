package app.persistence.mappers;

import app.exceptions.DatabaseException;
import app.persistence.connection.ConnectionPool;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.*;
import static app.persistence.mappers.testSetupForMappers.CreateTestSchemaDatabase.createTestSchemaWithData;


class PriceAndMaterialMapperTest {
    private static ConnectionPool connectionPool;
    private static PriceAndMaterialMapper priceAndMaterialMapper;

    @BeforeAll
    static void beforeAll() throws SQLException {
        String USER = "postgres";
        String PASSWORD = System.getenv("kudsk_db_password");
        String URL = "jdbc:postgresql://134.122.71.16/%s?currentSchema=test";
        String DB = "Fog_Carport";

        connectionPool = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);


        try (Connection conn = connectionPool.getConnection()) {
            try (Statement stmt = conn.createStatement()) {
                createTestSchemaWithData(conn);
            }
        }
    }

    @AfterAll
    static void afterAll() throws SQLException {
        try (Connection conn = connectionPool.getConnection()) {
            conn.createStatement().execute("ROLLBACK;");
        }
    }


    @Test
    void updateRoofPrice() throws SQLException, DatabaseException {
        // Arrange:
        String roofName = "Plastmo Ecolite blåtonet";
        int roofLength = 300;
        float updatedPrice = 50;

        // Act
        boolean updated = PriceAndMaterialMapper.updateRoofPrice(connectionPool, updatedPrice, roofName, roofLength);

        // Assert
        try (Connection conn = connectionPool.getConnection(); PreparedStatement ps = conn.prepareStatement("SELECT roof_price FROM roofs WHERE roof_type_name = ? AND roof_length = ?")) {
            ps.setString(1, roofName);
            ps.setInt(2, roofLength);
            try (ResultSet rs = ps.executeQuery()) {
                assertTrue(updated);
            }
        }
    }

    @Test
    void updateScrewsPrice() throws SQLException, DatabaseException {
        // Arrange
        int screwsId = 1;
        float updatedPrice = 15;

        // Act
        boolean updated = PriceAndMaterialMapper.updateScrewsPrice(connectionPool, updatedPrice, screwsId);

        // Assert
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT screw_price FROM screws WHERE screw_id = ?")) {
            ps.setInt(1, screwsId);
            try (ResultSet rs = ps.executeQuery()) {
                assertTrue(updated);
            }
        }
    }

    @Test
    void updateMountPrice() throws SQLException, DatabaseException {
        // Arrange
        int mountId = 1;
        float updatedPrice = 1000;

        // Act
        boolean updated = PriceAndMaterialMapper.updateMountPrice(connectionPool, updatedPrice, mountId);

        // Assert
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT mount_price FROM mounts WHERE mount_id = ?")) {
            ps.setInt(1, mountId);
            try (ResultSet rs = ps.executeQuery()) {
                assertTrue(updated);
            }
        }
    }

    @Test
    void updateWoodTypeMeterPrice() throws SQLException, DatabaseException {
        // Arrange
        int woodTypeId = 1;
        float updatedPrice = 60;

        // Act
        boolean updated = PriceAndMaterialMapper.updateWoodTypeMeterPrice(connectionPool, updatedPrice, woodTypeId);

        // Assert
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT wood_type_meter_price FROM wood_type WHERE wood_type_id = ?")) {
            ps.setInt(1, woodTypeId);
            try (ResultSet rs = ps.executeQuery()) {
                assertTrue(updated);
            }
        }
    }

    @Test
    void updateDimensionMeterPrice() throws SQLException, DatabaseException {
        // Arrange
        int woodWidthInMm = 20;
        int woodHeightInMm = 100;
        float updatedPrice = 14.95f;

        // Act
        boolean updated = PriceAndMaterialMapper.updateDimensionMeterPrice(connectionPool, updatedPrice, woodWidthInMm, woodHeightInMm);

        // Assert
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT wood_dimension_meter_price FROM wood_dimensions WHERE wood_width = ? AND wood_height = ?")) {

            ps.setInt(1, woodWidthInMm);
            ps.setInt(2, woodHeightInMm);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    assertEquals(updatedPrice, rs.getFloat("wood_dimension_meter_price"), 0.001f);
                }
                assertTrue(updated);
            }
        }
    }

    @Test
    void updateTreatmentMeterPrice() throws SQLException, DatabaseException {
        // Arrange
        int treatmentId = 1;
        float updatedPrice = 12;

        // Act
        boolean updated = PriceAndMaterialMapper.updateTreatmentMeterPrice(connectionPool, updatedPrice, treatmentId);

        // Assert
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT wood_treatment_meter_price FROM wood_treatment WHERE wood_treatment_id = ?")) {
            ps.setInt(1, treatmentId);
            try (ResultSet rs = ps.executeQuery()) {
                assertTrue(updated);
            }
        }
    }

    @Test
    void getRoofPrice() throws SQLException, DatabaseException {
        // Arrange
        int roofId = 1;
        double expectedPrice = 349.95;

        // Act
        float actualPrice = PriceAndMaterialMapper.getRoofPrice(connectionPool, roofId);

        // Assert
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT roof_price FROM roofs WHERE roof_id = ?")) {
            ps.setInt(1, roofId);
            try (ResultSet rs = ps.executeQuery()) {
                assertTrue(rs.next(), "Row with roofId = " + roofId + "should exist");
                assertEquals(expectedPrice, actualPrice, 0.0001f,
                        "expectedPrice should match with actualPrice");
            }
        }
    }

    @Test
    void getScrewsPrice() throws SQLException, DatabaseException {
        // Arrange
        int screwsId = 1;
        double expectedPrice = 69.95;

        // Act
        float actualPrice = PriceAndMaterialMapper.getScrewsPrice(connectionPool, screwsId);

        // Assert
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT screw_price FROM screws WHERE screw_id = ?")) {
            ps.setInt(1, screwsId);
            try (ResultSet rs = ps.executeQuery()) {
                assertTrue(rs.next(), "Row with screwsId = " + screwsId + " should exist");
                assertEquals(expectedPrice, actualPrice, 0.0001f,
                        "expectedPrice should match actualPrice");
            }
        }
    }

    @Test
    void getMountPrice() throws SQLException, DatabaseException {
        // Arrange
        int mountId = 1;
        double expectedPrice = 1000.00;

        // Act
        float actualPrice = PriceAndMaterialMapper.getMountPrice(connectionPool, mountId);

        // Assert
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT mount_price FROM mounts WHERE mount_id = ?")) {
            ps.setInt(1, mountId);
            try (ResultSet rs = ps.executeQuery()) {
                assertTrue(rs.next(), "Row with mountId = " + mountId + " should exist");
                assertEquals(expectedPrice, actualPrice, 0.0001f,
                        "expectedPrice should match actualPrice");
            }
        }
    }

    @Test
    void getWoodTypeMeterPrice() throws SQLException, DatabaseException {
        // Arrange
        int woodTypeId = 1;
        double expectedPrice = 10.25;

        // Act
        float actualPrice = PriceAndMaterialMapper.getWoodTypeMeterPrice(connectionPool, woodTypeId);

        // Assert
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT wood_type_meter_price FROM wood_type WHERE wood_type_id = ?")) {
            ps.setInt(1, woodTypeId);
            try (ResultSet rs = ps.executeQuery()) {
                assertTrue(rs.next(), "Row with woodTypeId = " + woodTypeId + " should exist");
                assertEquals(expectedPrice, actualPrice, 0.0001f,
                        "expectedPrice should match actualPrice");
            }
        }
    }

    @Test
    void getDimensionMeterPrice() throws SQLException, DatabaseException {
        // Arrange
        int dimensionId = 1;
        double expectedPrice = 12.95;

        // Act
        float actualPrice = PriceAndMaterialMapper.getDimensionMeterPrice(connectionPool, dimensionId);

        // Assert
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT wood_dimension_meter_price FROM wood_dimensions WHERE wood_dimension_id = ?")) {
            ps.setInt(1, dimensionId);
            try (ResultSet rs = ps.executeQuery()) {
                assertTrue(rs.next(), "Row with dimensionId = " + dimensionId + " should exist");
                assertEquals(expectedPrice, actualPrice, 0.0001f, "expectedPrice should match actualPrice");
            }
        }
    }

    @Test
    void getTreatmentMeterPrice() throws SQLException, DatabaseException {
        // Arrange
        int treatmentId = 1;
        double expectedPrice = 10.00;

        // Act
        float actualPrice = PriceAndMaterialMapper.getTreatmentMeterPrice(connectionPool, treatmentId);

        // Assert
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT wood_treatment_meter_price FROM wood_treatment WHERE wood_treatment_id = ?")) {
            ps.setInt(1, treatmentId);
            try (ResultSet rs = ps.executeQuery()) {
                assertTrue(rs.next(), "Row with treatmentId = " + treatmentId + " should exist");
                assertEquals(expectedPrice, actualPrice, 0.0001f, "expectedPrice should match actualPrice");
            }
        }
    }

    @Test
    void getTotalWoodPrice() throws SQLException, DatabaseException {
        // Arrange
        int woodTypeId = 1;
        int dimensionId = 1;
        int treatmentId = 1;
        double expectedTotal = 12.95 + 10.00 + 10.25;

        // Act
        float actualTotal = PriceAndMaterialMapper.getTotalWoodPrice(connectionPool, woodTypeId, dimensionId, treatmentId);

        // Assert
        assertEquals(expectedTotal, actualTotal, 0.0001f,
                "Sum of woodType + dimension + treatment prices should match");
    }

    @Test
    void getMarkupPercentageFromExpensePrice() throws DatabaseException {
        float expensePrice = 27450.21f;
        int expectedPercentage = 135;

        // Act
        int actualPercentage = PriceAndMaterialMapper.getMarkupPercentageFromExpensePrice(connectionPool, expensePrice);

        // Assert
        assertEquals(expectedPercentage, actualPercentage,
                "expected percentage and actual percentage prices should match");
    }
}