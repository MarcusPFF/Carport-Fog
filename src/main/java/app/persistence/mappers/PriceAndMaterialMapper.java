package app.persistence.mappers;

import app.entities.*;
import app.exceptions.DatabaseException;
import app.persistence.connection.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PriceAndMaterialMapper {
    private static Wood wood;
    private static Roof roof;
    private static Screws screws;
    private static Mount mount;
    private static WoodType woodType;
    private static Dimensions dimensions;
    private static Treatment treatment;

    public static boolean updateRoofPrice(ConnectionPool connectionPool, float roofPrice, String roofName, int roofLength) throws DatabaseException {
        String sql = "UPDATE roofs SET roof_price = ? WHERE roof_type_name = ? AND roof_length_cm = ?;";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setFloat(1, roofPrice);
                ps.setString(2, roofName);
                ps.setInt(3, roofLength);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected == 1) {
                    return true;
                }
                return false;
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Error getting roofs price from database");
        }
    }

    public static boolean updateScrewsPrice(ConnectionPool connectionPool, float screwsPrice, String screwName) throws DatabaseException {
        String sql = "UPDATE screws SET screw_price = ? WHERE screw_type_name = ?;";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setFloat(1, screwsPrice);
                ps.setString(2, screwName);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected == 1) {
                    return true;
                }
                return false;
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Error getting screws price from database");
        }
    }

    public static boolean updateMountPrice(ConnectionPool connectionPool, float mountPrice, String mountName) throws DatabaseException {
        String sql = "UPDATE mounts SET mount_price = ? WHERE mount_type_name = ?;";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setFloat(1, mountPrice);
                ps.setString(2, mountName);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected == 1) {
                    return true;
                }
                return false;
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Error getting mounts price from database");
        }
    }

    public static boolean updateWoodTypeMeterPrice(ConnectionPool connectionPool, float newPrice, int woodTypeId) throws DatabaseException {
        String sql = "UPDATE wood_type SET wood_type_meter_price = ? WHERE wood_type_id = ?;";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setFloat(1, newPrice);
                ps.setInt(2, woodTypeId);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected == 1) {
                    return true;
                }
                return false;
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Error getting wood type meter price from database");
        }
    }

    public static boolean updateDimensionMeterPrice(ConnectionPool connectionPool, float newPrice, int woodWidthInMm, int woodHeightInMm) throws DatabaseException {
        String sql = "UPDATE wood_dimensions SET wood_dimension_meter_price = ? WHERE wood_width = ? AND wood_height = ?;";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setFloat(1, newPrice);
                ps.setInt(2, woodWidthInMm);
                ps.setInt(3, woodHeightInMm);

                int rowsAffected = ps.executeUpdate();
                if (rowsAffected >= 1) {
                    return true;
                }
                return false;
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Error getting wood dimension meter price from database");
        }
    }

    public static boolean updateTreatmentMeterPrice(ConnectionPool connectionPool, float newPrice, int treatmentId) throws DatabaseException {
        String sql = "UPDATE wood_treatment SET wood_treatment_meter_price = ? WHERE wood_treatment_id = ?;";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setFloat(1, newPrice);
                ps.setInt(2, treatmentId);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected == 1) {
                    return true;
                }
                return false;
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Error getting wood treatment meter price from database");
        }
    }

    public static float getRoofPrice(ConnectionPool connectionPool, int roofId) throws DatabaseException {
        String sql = "SELECT roof_price FROM roofs WHERE roof_id = ?;";
        try (Connection connection = connectionPool.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, roofId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getFloat("roof_price");
                }
                throw new DatabaseException(null, "Roof not found for id: " + roofId);
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Error getting roof price from database");
        }
    }

    public static float getScrewsPrice(ConnectionPool connectionPool, int screwsId) throws DatabaseException {
        String sql = "SELECT screw_price FROM screws WHERE screw_id = ?;";
        try (Connection connection = connectionPool.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, screwsId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getFloat("screw_price");
                }
                throw new DatabaseException(null, "Screws not found for id: " + screwsId);
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Error getting screw price from database");
        }
    }

    public static float getMountPrice(ConnectionPool connectionPool, int mountId) throws DatabaseException {
        String sql = "SELECT mount_price FROM mounts WHERE mount_id = ?;";
        try (Connection connection = connectionPool.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, mountId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getFloat("mount_price");
                }
                throw new DatabaseException(null, "Mount not found for id: " + mountId);
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Error getting mount price from database");
        }
    }

    public static float getWoodTypeMeterPrice(ConnectionPool connectionPool, int woodTypeId) throws DatabaseException {
        String sql = "SELECT wood_type_meter_price FROM wood_type WHERE wood_type_id = ?;";
        try (Connection connection = connectionPool.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, woodTypeId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getFloat("wood_type_meter_price");
                }
                throw new DatabaseException(null, "WoodType not found for id: " + woodTypeId);
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Error getting wood type meter price from database");
        }
    }

    public static float getDimensionMeterPrice(ConnectionPool connectionPool, int dimensionId) throws DatabaseException {
        String sql = "SELECT wood_dimension_meter_price FROM wood_dimensions WHERE wood_dimension_id = ?;";
        try (Connection connection = connectionPool.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, dimensionId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getFloat("wood_dimension_meter_price");
                }
                throw new DatabaseException(null, "Dimension not found for id: " + dimensionId);
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Error getting wood dimension meter price from database");
        }
    }

    public static float getTreatmentMeterPrice(ConnectionPool connectionPool, int treatmentId) throws DatabaseException {
        String sql = "SELECT wood_treatment_meter_price FROM wood_treatment WHERE wood_treatment_id = ?;";
        try (Connection connection = connectionPool.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, treatmentId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getFloat("wood_treatment_meter_price");
                }
                throw new DatabaseException(null, "Treatment not found for id: " + treatmentId);
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Error getting wood treatment meter price from database");
        }
    }

    public static float getTotalWoodPrice(ConnectionPool connectionPool, int woodTypeId, int dimensionId, int treatmentId) throws DatabaseException {
        float woodTypePrice = getWoodTypeMeterPrice(connectionPool, woodTypeId);
        float dimensionPrice = getDimensionMeterPrice(connectionPool, dimensionId);
        float treatmentPrice = getTreatmentMeterPrice(connectionPool, treatmentId);

        return woodTypePrice + dimensionPrice + treatmentPrice;
    }

    public static int getMarkupPercentageFromExpensePrice(ConnectionPool connectionPool, float expensePrice) throws DatabaseException {
        String sql = "SELECT expenses_price, percentage FROM markup ORDER BY expenses_price DESC;";
        try (Connection connection = connectionPool.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {

                    if (rs.getInt("expenses_price") < expensePrice) {
                        return rs.getInt("percentage");
                    }
                }
                throw new DatabaseException(null, "Markup id not found for price: " + expensePrice);
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Error getting wood treatment meter price from database");
        }

    }



}



