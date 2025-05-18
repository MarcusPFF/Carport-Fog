package app.persistence.mappers;

import app.persistence.connection.ConnectionPool;
import app.exceptions.DatabaseException;

import java.sql.*;

public class OfferMapper {

    public int getTreatmentIdFromTreatmentName(ConnectionPool connectionPool, String treatmentName) throws DatabaseException {
        String sql = "SELECT wood_treatment_id FROM wood_treatment WHERE wood_treatment_type_name = ?;";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, treatmentName);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    int treatmentId = rs.getInt("wood_treatment_id");
                    return treatmentId;
                } else {
                    return 0;
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Database error while fetching treatment ID: ");
        }
    }


    public int getWoodTypeIdFromWoodTypeName(ConnectionPool connectionPool, String woodTypeName) throws DatabaseException {
        String sql = "SELECT wood_type_id FROM wood_type WHERE wood_type_name = ?;";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, woodTypeName);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    int woodTypeId = rs.getInt("wood_type_id");
                    return woodTypeId;
                } else {
                    return 0;
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Database error while fetching wood type ID: ");
        }
    }

    public int getWoodDimensionIdFromLengthWidthHeight(ConnectionPool connectionPool, int woodLenght, int woodWidth, int woodHeight) throws DatabaseException {
        String sql = "SELECT wood_dimension_id FROM wood_dimensions WHERE wood_length = ? AND wood_width = ? AND wood_height = ?;";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, woodLenght);
            statement.setInt(2, woodWidth);
            statement.setInt(3, woodHeight);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    int woodDimensionId = rs.getInt("wood_dimension_id");
                    return woodDimensionId;
                } else {
                    return 0;
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Database error while fetching dimension ID: ");
        }
    }
    public int getRoofIdFromRoofLength(ConnectionPool connection, int roofLengthInCm) {
        return 1;
    }
    //TEST KODE SOM MIDLERTIDIGT ER HER, Kan ikke fjernes
    public static float getSalesPriceFromOfferId(ConnectionPool connection, int offerId){
        return 999.99F;
    }

    public static String getCustomerMailFromOfferId(ConnectionPool connection, int offerId){
        return "test@kunde.dk";
    }
}
