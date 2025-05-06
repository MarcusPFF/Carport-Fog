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
            conn.createStatement().execute("BEGIN;");
            createTestSchema(conn);
        }
    }

    @AfterAll
    static void afterAll() throws SQLException {
        try (Connection conn = connectionPool.getConnection()) {
            conn.createStatement().execute("ROLLBACK;");
        }
    }

    @BeforeEach
    void setUp() {
        orderMapper = new OrderMapper();
    }

    private static void createTestSchema(Connection conn) throws SQLException {
        String ddl = """
                DROP SCHEMA IF EXISTS test CASCADE;
                CREATE SCHEMA test;
                
                CREATE TABLE test.addresses (
                    address_id   BIGINT      NOT NULL,
                    street_name  VARCHAR     NOT NULL,
                    house_number BIGINT      NOT NULL,
                    zip_code     BIGINT      NOT NULL,
                    CONSTRAINT pk_addresses       PRIMARY KEY (address_id)
                );
                
                CREATE TABLE test.cities (
                    zip_code   BIGINT  NOT NULL,
                    city_name  VARCHAR NOT NULL,
                    CONSTRAINT pk_cities  PRIMARY KEY (zip_code)
                );
                
                CREATE TABLE test.customer (
                    customer_id        BIGINT  NOT NULL,
                    customer_mail      VARCHAR NOT NULL,
                    customer_firstname VARCHAR NOT NULL,
                    customer_lastname  VARCHAR NOT NULL,
                    address_id         BIGINT  NOT NULL,
                    CONSTRAINT pk_customer  PRIMARY KEY (customer_id)
                );
                
                CREATE TABLE test.markup (
                    expenses_price BIGINT NOT NULL,
                    percentage     BIGINT NOT NULL,
                    CONSTRAINT pk_markup PRIMARY KEY (expenses_price)
                );
                
                CREATE TABLE test.mounts (
                    mount_id        BIGINT  NOT NULL,
                    mount_price     NUMERIC NOT NULL,
                    mount_type_name VARCHAR NOT NULL,
                    CONSTRAINT pk_mounts PRIMARY KEY (mount_id)
                );
                
                CREATE TABLE test.mounts_list (
                    offer_id     BIGINT NOT NULL,
                    mount_id     BIGINT NOT NULL,
                    mount_amount BIGINT NOT NULL
                );
                
                CREATE TABLE test.offers (
                    offer_id            BIGINT      NOT NULL,
                    total_expense_price DOUBLE PRECISION NOT NULL,
                    total_offer_price   DOUBLE PRECISION NOT NULL,
                    seller_id           BIGINT      NOT NULL,
                    customer_id         BIGINT      NOT NULL,
                    expiration_date     DATE        NOT NULL,
                    carport_length      BIGINT      NOT NULL,
                    carport_width       BIGINT      NOT NULL,
                    shed_length         BIGINT,
                    shed_width          BIGINT,
                    CONSTRAINT pk_offers PRIMARY KEY (offer_id)
                );
                
                CREATE TABLE test.orders (
                    order_id       BIGINT NOT NULL,
                    offer_id       BIGINT NOT NULL,
                    tracking_number UUID  NOT NULL,
                    status_id      BIGINT NOT NULL,
                    purchase_date  DATE   NOT NULL,
                    CONSTRAINT pk_orders PRIMARY KEY (order_id)
                );
                
                CREATE TABLE test.roof_list (
                    offer_id    BIGINT NOT NULL,
                    roof_id     BIGINT NOT NULL,
                    roof_amount BIGINT NOT NULL
                );
                
                CREATE TABLE test.roofs (
                    roof_id        BIGINT NOT NULL,
                    roof_length_cm BIGINT NOT NULL,
                    roof_width_cm  BIGINT NOT NULL,
                    roof_type_name VARCHAR NOT NULL,
                    roof_price     NUMERIC NOT NULL,
                    CONSTRAINT pk_roofs PRIMARY KEY (roof_id)
                );
                
                CREATE TABLE test.screws (
                    screw_id            BIGINT  NOT NULL,
                    amount_pr_container BIGINT  NOT NULL,
                    screw_price         NUMERIC NOT NULL,
                    screw_type_name     VARCHAR NOT NULL,
                    CONSTRAINT pk_screws PRIMARY KEY (screw_id)
                );
                
                CREATE TABLE test.screws_list (
                    offer_id      BIGINT NOT NULL,
                    screw_id      BIGINT NOT NULL,
                    screws_amount BIGINT NOT NULL
                );
                
                CREATE TABLE test.sellers (
                    seller_id        BIGINT  NOT NULL,
                    seller_mail      VARCHAR NOT NULL,
                    seller_firstname VARCHAR NOT NULL,
                    seller_lastname  VARCHAR NOT NULL,
                    CONSTRAINT pk_sellers PRIMARY KEY (seller_id)
                );
                
                CREATE TABLE test.status (
                    status_id          BIGINT  NOT NULL,
                    status_description VARCHAR,
                    message_for_mail   VARCHAR,
                    CONSTRAINT pk_status PRIMARY KEY (status_id)
                );
                
                CREATE TABLE test.wood_dimensions (
                    wood_dimension_id         BIGINT  NOT NULL,
                    wood_length               BIGINT  NOT NULL,
                    wood_height               BIGINT  NOT NULL,
                    wood_width                BIGINT  NOT NULL,
                    wood_dimension_meter_price NUMERIC NOT NULL,
                    CONSTRAINT pk_wood_dimensions PRIMARY KEY (wood_dimension_id)
                );
                
                CREATE TABLE test.wood_list (
                    wood_list_id      BIGINT NOT NULL,
                    offer_id          BIGINT NOT NULL,
                    wood_type_id      BIGINT NOT NULL,
                    wood_treatment_id BIGINT NOT NULL,
                    wood_dimension_id BIGINT NOT NULL,
                    wood_amount       BIGINT NOT NULL,
                    CONSTRAINT pk_wood_list PRIMARY KEY (wood_list_id)
                );
                
                CREATE TABLE test.wood_treatment (
                    wood_treatment_id         BIGINT  NOT NULL,
                    wood_treatment_type_name  VARCHAR NOT NULL,
                    wood_treatment_meter_price NUMERIC NOT NULL,
                    CONSTRAINT pk_wood_treatment PRIMARY KEY (wood_treatment_id)
                );
                
                CREATE TABLE test.wood_type (
                    wood_type_id          BIGINT  NOT NULL,
                    wood_type_name        VARCHAR NOT NULL,
                    wood_type_meter_price NUMERIC NOT NULL,
                    CONSTRAINT pk_wood_type PRIMARY KEY (wood_type_id)
                );
                
                -- Foreign keys
                ALTER TABLE test.addresses
                    ADD CONSTRAINT fk_addresses_zip_code FOREIGN KEY (zip_code)
                        REFERENCES test.cities (zip_code);
                
                ALTER TABLE test.customer
                    ADD CONSTRAINT fk_customer_address FOREIGN KEY (address_id)
                        REFERENCES test.addresses (address_id);
                
                ALTER TABLE test.mounts_list
                    ADD CONSTRAINT fk_mounts_list_mount FOREIGN KEY (mount_id)
                        REFERENCES test.mounts (mount_id),
                    ADD CONSTRAINT fk_mounts_list_offer FOREIGN KEY (offer_id)
                        REFERENCES test.offers (offer_id);
                
                ALTER TABLE test.offers
                    ADD CONSTRAINT fk_offers_customer FOREIGN KEY (customer_id)
                        REFERENCES test.customer (customer_id),
                    ADD CONSTRAINT fk_offers_seller FOREIGN KEY (seller_id)
                        REFERENCES test.sellers (seller_id);
                
                ALTER TABLE test.orders
                    ADD CONSTRAINT fk_orders_offer FOREIGN KEY (offer_id)
                        REFERENCES test.offers (offer_id),
                    ADD CONSTRAINT fk_orders_status FOREIGN KEY (status_id)
                        REFERENCES test.status (status_id);
                
                ALTER TABLE test.roof_list
                    ADD CONSTRAINT fk_roof_list_offer FOREIGN KEY (offer_id)
                        REFERENCES test.offers (offer_id),
                    ADD CONSTRAINT fk_roof_list_roof FOREIGN KEY (roof_id)
                        REFERENCES test.roofs (roof_id);
                
                ALTER TABLE test.screws_list
                    ADD CONSTRAINT fk_screws_list_offer FOREIGN KEY (offer_id)
                        REFERENCES test.offers (offer_id),
                    ADD CONSTRAINT fk_screws_list_screw FOREIGN KEY (screw_id)
                        REFERENCES test.screws (screw_id);
                
                ALTER TABLE test.wood_list
                    ADD CONSTRAINT fk_wood_list_offer FOREIGN KEY (offer_id)
                        REFERENCES test.offers (offer_id),
                    ADD CONSTRAINT fk_wood_list_wood_dimension FOREIGN KEY (wood_dimension_id)
                        REFERENCES test.wood_dimensions (wood_dimension_id),
                    ADD CONSTRAINT fk_wood_list_wood_treatment FOREIGN KEY (wood_treatment_id)
                        REFERENCES test.wood_treatment (wood_treatment_id),
                    ADD CONSTRAINT fk_wood_list_wood_type FOREIGN KEY (wood_type_id)
                        REFERENCES test.wood_type (wood_type_id);

                """;
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(ddl);
        }
        try (Connection connection = connectionPool.getConnection()) {
            connection.prepareStatement("INSERT INTO test.cities (zip_code, city_name) VALUES (1000, 'TestCity')").execute();
            connection.prepareStatement("INSERT INTO test.addresses (address_id, street_name, house_number, zip_code) VALUES (1, 'Main Street', 123, 1000)").execute();
            connection.prepareStatement("INSERT INTO test.customer (customer_id, customer_mail, customer_firstname, customer_lastname, address_id) VALUES (1, 'cust@example.com', 'Test', 'Customer', 1)").execute();
            connection.prepareStatement("INSERT INTO test.sellers (seller_id, seller_mail, seller_firstname, seller_lastname) VALUES (1, 'seller@example.com', 'Test', 'Seller')").execute();
            connection.prepareStatement("""
        INSERT INTO test.offers (
            offer_id, total_expense_price, total_offer_price,
            seller_id, customer_id, expiration_date,
            carport_length, carport_width, shed_length, shed_width
        ) VALUES (
            10, 5000.0, 7500.0,
            1, 1, CURRENT_DATE,
            600, 300, 200, 200
        )
    """).execute();
            connection.prepareStatement("INSERT INTO test.status (status_id, status_description) VALUES (1, 'Bekræftet')").execute();
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
        int orderId = 1;
        int offerId = 1;
        int statusId = 1;
        String statusDescription = "Bekræftet";
        UUID trackingNumber = UUID.randomUUID();
        Date purchaseDate = Date.valueOf("2024-05-01");

        try (Connection conn = connectionPool.getConnection()) {
            // Insert status
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO test.status (status_id, status_description) VALUES (?, ?)")) {
                ps.setInt(1, statusId);
                ps.setString(2, statusDescription);
                ps.executeUpdate();
            }

            // Insert order
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO test.orders (order_id, offer_id, tracking_number, status_id, purchase_date) " +
                            "VALUES (?, ?, ?, ?, ?)")) {
                ps.setInt(1, orderId);
                ps.setInt(2, offerId);
                ps.setObject(3, trackingNumber);
                ps.setInt(4, statusId);
                ps.setDate(5, purchaseDate);
                ps.executeUpdate();
            }
        }

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