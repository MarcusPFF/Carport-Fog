package app.persistence.mappers;

import app.exceptions.DatabaseException;
import app.persistence.connection.ConnectionPool;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.sql.*;
import static app.persistence.mappers.testSetupForMappers.CreateTestSchemaDatabase.createTestSchemaWithData;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PriceAndMaterialMapperTest {
    private static ConnectionPool connectionPool;
    private static PriceAndMaterialMapper priceAndMaterialMapper;

    @BeforeAll
    static void beforeAll() throws SQLException {
        String USER = "postgres";
        String PASSWORD = "jo221mf411jk513!";
       // String PASSWORD = System.getenv("kudsk_db_password");
        String URL = "jdbc:postgresql://134.122.71.16/%s?currentSchema=test";
        String DB = "Fog_Carport";

        connectionPool = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);


        try (Connection conn = connectionPool.getConnection()) {
            try (Statement stmt = conn.createStatement()) {
               // stmt.execute("SET search_path TO test, public;");
                createTestSchemaWithData(conn);
            }
        }
    }

    @AfterAll
    static void afterAll() throws SQLException {
        try (Connection conn = connectionPool.getConnection()) {
            //conn.createStatement().execute("ROLLBACK;");
        }
    }

    @Test
    void updateRoofPrice() throws SQLException, DatabaseException {
        // Arrange:
        int roofId = 1;
        float updatedPrice = 50;

        // Act
        boolean updated = PriceAndMaterialMapper.updateRoofPrice(connectionPool, updatedPrice, roofId);

        // Assert
        try (Connection conn = connectionPool.getConnection(); PreparedStatement ps = conn.prepareStatement("SELECT roof_price FROM roofs WHERE roof_id = ?")) {
            ps.setInt(1, roofId);
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
        int dimensionId = 1;
        float updatedPrice = 25;

        // Act
        boolean updated = PriceAndMaterialMapper.updateDimensionMeterPrice(connectionPool, updatedPrice, dimensionId);

        // Assert
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT wood_dimension_meter_price FROM wood_dimensions WHERE wood_dimension_id = ?")) {
            ps.setInt(1, dimensionId);
            try (ResultSet rs = ps.executeQuery()) {
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

}