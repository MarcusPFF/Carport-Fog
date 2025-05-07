package app.persistence.mappers;

import app.exceptions.DatabaseException;
import app.persistence.connection.ConnectionPool;
import app.entities.Order;
import org.junit.jupiter.api.*;


import java.sql.*;
import java.util.UUID;

class OrderMapperTest {
    private static ConnectionPool connectionPool;
    private static OrderMapper orderMapper;

    @BeforeAll
    static void beforeAll() throws SQLException {
        String USER = "postgres";
        String PASSWORD = "jo221mf411jk513!";
        //TODO FÅ Getenv til at virke
        //String PASSWORD = System.getenv("kudsk_db_password");
        String URL = "jdbc:postgresql://134.122.71.16/%s?currentSchema=test";
        String DB = "Fog_Carport";

        connectionPool = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);

        try (Connection conn = connectionPool.getConnection()) {
            createTestSchemaWithData(conn);
        }
    }

    @AfterAll
    static void afterAll() throws SQLException {
        try (Connection conn = connectionPool.getConnection()) {

        }
    }

    @BeforeEach
    void setUp() {
        orderMapper = new OrderMapper();
    }

    private static void createTestSchemaWithData(Connection conn) throws SQLException {
        String ddlWithData = """
        -- Drop schema if it exists and create a new one
        DROP SCHEMA IF EXISTS test CASCADE;
        CREATE SCHEMA test;

        -- Create tables
        CREATE TABLE test.cities (
            id SERIAL PRIMARY KEY,
            zip_code INT NOT NULL,
            city_name VARCHAR(255) NOT NULL
        );

        CREATE TABLE test.customer (
            id SERIAL PRIMARY KEY,
            customer_mail VARCHAR(255) NOT NULL,
            customer_firstname VARCHAR(255) NOT NULL,
            customer_lastname VARCHAR(255) NOT NULL,
            street_name VARCHAR(255) NOT NULL,
            house_number INT NOT NULL,
            zip_code INT NOT NULL
        );

        CREATE TABLE test.sellers (
            id SERIAL PRIMARY KEY,
            seller_mail VARCHAR(255) NOT NULL,
            seller_firstname VARCHAR(255) NOT NULL,
            seller_lastname VARCHAR(255) NOT NULL
        );

        CREATE TABLE test.status (
            id SERIAL PRIMARY KEY,
            status_description VARCHAR(255) NOT NULL,
            message_for_mail VARCHAR(255) NOT NULL
        );

        CREATE TABLE test.markup (
            id SERIAL PRIMARY KEY,
            expenses_price DECIMAL(10, 2) NOT NULL,
            percentage DECIMAL(5, 2) NOT NULL
        );

        CREATE TABLE test.mounts (
            id SERIAL PRIMARY KEY,
            mount_price DECIMAL(10, 2) NOT NULL,
            mount_type_name VARCHAR(255) NOT NULL
        );

        CREATE TABLE test.roofs (
            id SERIAL PRIMARY KEY,
            roof_length_cm INT NOT NULL,
            roof_width_cm INT NOT NULL,
            roof_price DECIMAL(10, 2) NOT NULL
        );

        CREATE TABLE test.screws (
            id SERIAL PRIMARY KEY,
            amount_pr_container INT NOT NULL,
            screw_price DECIMAL(10, 2) NOT NULL,
            screw_type_name VARCHAR(255) NOT NULL
        );

        CREATE TABLE test.wood_type (
            id SERIAL PRIMARY KEY,
            wood_type_name VARCHAR(255) NOT NULL,
            wood_type_meter_price DECIMAL(10, 2) NOT NULL
        );

        CREATE TABLE test.wood_treatment (
            id SERIAL PRIMARY KEY,
            wood_treatment_type_name VARCHAR(255) NOT NULL,
            wood_treatment_meter_price DECIMAL(10, 2) NOT NULL
        );

        CREATE TABLE test.wood_dimensions (
            id SERIAL PRIMARY KEY,
            wood_length INT NOT NULL,
            wood_height INT NOT NULL,
            wood_width INT NOT NULL,
            wood_dimension_meter_price DECIMAL(10, 2) NOT NULL
        );

        CREATE TABLE test.offers (
            id SERIAL PRIMARY KEY,
            total_expense_price DECIMAL(10, 2) NOT NULL,
            total_offer_price DECIMAL(10, 2) NOT NULL,
            seller_id INT REFERENCES test.sellers(id),
            customer_id INT REFERENCES test.customer(id),
            expiration_date DATE NOT NULL,
            carport_length INT NOT NULL,
            carport_width INT NOT NULL,
            shed_length INT NOT NULL,
            shed_width INT NOT NULL
        );

        CREATE TABLE test.orders (
            id SERIAL PRIMARY KEY,
            offer_id INT REFERENCES test.offers(id),
            status_id INT REFERENCES test.status(id),
            purchase_date DATE NOT NULL,
            tracking_number UUID DEFAULT gen_random_uuid()
        );

        CREATE TABLE test.mounts_list (
            offer_id INT REFERENCES test.offers(id),
            mount_id INT REFERENCES test.mounts(id),
            mount_amount INT NOT NULL
        );

        CREATE TABLE test.roof_list (
            offer_id INT REFERENCES test.offers(id),
            roof_id INT REFERENCES test.roofs(id),
            roof_amount INT NOT NULL
        );

        CREATE TABLE test.screws_list (
            offer_id INT REFERENCES test.offers(id),
            screw_id INT REFERENCES test.screws(id),
            screws_amount INT NOT NULL
        );

        CREATE TABLE test.wood_list (
            offer_id INT REFERENCES test.offers(id),
            wood_type_id INT REFERENCES test.wood_type(id),
            wood_treatment_id INT REFERENCES test.wood_treatment(id),
            wood_dimension_id INT REFERENCES test.wood_dimensions(id),
            wood_amount INT NOT NULL
        );

        -- Insert test data
        INSERT INTO test.cities (zip_code, city_name)
        VALUES (1000, 'Copenhagen'), (2000, 'Aarhus'), (3000, 'Odense');

        INSERT INTO test.customer (customer_mail, customer_firstname, customer_lastname, street_name, house_number, zip_code)
        VALUES
            ('john.doe@example.com', 'John', 'Doe', 'Main Street', 123, 1000),
            ('jane.smith@example.com', 'Jane', 'Smith', 'Oak Avenue', 456, 2000),
            ('alice.johnson@example.com', 'Alice', 'Johnson', 'Pine Road', 789, 3000);

        INSERT INTO test.sellers (seller_mail, seller_firstname, seller_lastname)
        VALUES
            ('bob.martin@example.com', 'Bob', 'Martin'),
            ('mary.jane@example.com', 'Mary', 'Jane'),
            ('sam.doe@example.com', 'Sam', 'Doe');

        INSERT INTO test.status (status_description, message_for_mail)
        VALUES
            ('Pending', 'Your order is pending.'),
            ('Shipped', 'Your order has been shipped.'),
            ('Delivered', 'Your order has been delivered.');

        INSERT INTO test.markup (expenses_price, percentage)
        VALUES (1000, 20), (2000, 15), (3000, 10);

        INSERT INTO test.mounts (mount_price, mount_type_name)
        VALUES (500.00, 'Steel'), (1000.00, 'Aluminum'), (1500.00, 'Wood');

        INSERT INTO test.roofs (roof_length_cm, roof_width_cm, roof_price)
        VALUES (1000, 500, 1000.00), (1200, 600, 1500.00), (1400, 700, 2000.00);

        INSERT INTO test.screws (amount_pr_container, screw_price, screw_type_name)
        VALUES (100, 10.00, 'Wood Screw'), (200, 15.00, 'Metal Screw'), (300, 20.00, 'Concrete Screw');

        INSERT INTO test.wood_type (wood_type_name, wood_type_meter_price)
        VALUES ('Oak', 50.00), ('Pine', 30.00), ('Maple', 40.00);

        INSERT INTO test.wood_treatment (wood_treatment_type_name, wood_treatment_meter_price)
        VALUES ('Polished', 5.00), ('Varnished', 10.00), ('Painted', 15.00);

        INSERT INTO test.wood_dimensions (wood_length, wood_height, wood_width, wood_dimension_meter_price)
        VALUES (200, 50, 30, 15.00), (250, 60, 35, 20.00), (300, 70, 40, 25.00);

        INSERT INTO test.offers (total_expense_price, total_offer_price, seller_id, customer_id, expiration_date, carport_length, carport_width, shed_length, shed_width)
        VALUES
            (5000.00, 6000.00, 1, 1, '2025-12-31', 500, 300, 200, 150),
            (7000.00, 8000.00, 2, 2, '2025-12-31', 600, 400, 250, 200),
            (9000.00, 10000.00, 3, 3, '2025-12-31', 700, 500, 300, 250);

        INSERT INTO test.orders (offer_id, status_id, purchase_date)
        VALUES
            (1, 1, '2025-05-01'),
            (2, 2, '2025-05-02'),
            (3, 3, '2025-05-03');

        INSERT INTO test.mounts_list (offer_id, mount_id, mount_amount)
        VALUES
            (1, 1, 10), (2, 2, 5), (3, 3, 8);

        INSERT INTO test.roof_list (offer_id, roof_id, roof_amount)
        VALUES
            (1, 1, 10), (2, 2, 5), (3, 3, 8);

        INSERT INTO test.screws_list (offer_id, screw_id, screws_amount)
        VALUES
            (1, 1, 50), (2, 2, 30), (3, 3, 20);

        INSERT INTO test.wood_list (offer_id, wood_type_id, wood_treatment_id, wood_dimension_id, wood_amount)
        VALUES
            (1, 1, 1, 1, 100), (2, 2, 2, 2, 200), (3, 3, 3, 3, 150);
        """;

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(ddlWithData);
        }
    }





    @Test
    void createNewOrder() throws SQLException, DatabaseException {
        // Arrange:
        int offerId = 10;

        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO test.orders (offer_id, status_id, purchase_date) VALUES (?,1,current_date);")) {
            ps.setInt(1, offerId);
            ps.executeUpdate();
        }

        // Act
        orderMapper.createNewOrder(connectionPool, offerId);

        // Assert
        try (Connection conn = connectionPool.getConnection(); PreparedStatement ps = conn.prepareStatement("SELECT order_id, offer_id, tracking_number, status_id, purchase_date FROM test.orders WHERE offer_id = ?")) {
            ps.setInt(1, offerId);
            try (ResultSet rs = ps.executeQuery()) {
                Assertions.assertTrue(rs.next(), "Der forventes en række med offerId = " + offerId);
                Assertions.assertEquals(offerId, rs.getInt("offer_id"));
            }
        }
    }

    @Test
    void getOrderDetailsFromOrderId() throws SQLException, DatabaseException {
        // Arrange
int orderId = 3;
        int offerId = 3;
        int statusId = 3;
        String statusDescription = "Delivered";
        UUID trackingNumber = UUID.fromString("aa0f3c1b-6348-4c41-bef9-79d91dce774d");
        Date purchaseDate = Date.valueOf("2024-05-03");


        // Act
        Order order = orderMapper.getOrderDetailsFromOrderId(connectionPool, orderId);

        // Assert
        Assertions.assertNotNull(order);
        Assertions.assertEquals(orderId, order.getOrderId());
        Assertions.assertEquals(offerId, order.getOfferId());
        Assertions.assertEquals(trackingNumber, order.getTrackingNumber());
        Assertions.assertEquals(purchaseDate, order.getPurchaseDate());
        Assertions.assertEquals(statusDescription, order.getStatus());
    }
}