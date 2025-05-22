package app.persistence.mappers.testSetupForMappers;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTestSchemaDatabase {

    public static void createTestSchemaWithData(Connection conn) throws SQLException {
        String ddlWithData = """
                -- Drop schema if it exists and create a new one
        DROP SCHEMA IF EXISTS test CASCADE;
        CREATE SCHEMA test;

        CREATE TABLE test.cities (
            zip_code INT NOT NULL PRIMARY KEY,
            city_name VARCHAR(255) NOT NULL
        );

        CREATE TABLE test.customer (
            customer_id BIGSERIAL PRIMARY KEY,
            customer_mail VARCHAR(255) NOT NULL UNIQUE,
            customer_firstname VARCHAR(255) NOT NULL,
            customer_lastname VARCHAR(255) NOT NULL,
            street_name VARCHAR(255) NOT NULL,
            house_number INT NOT NULL,
            zip_code INT NOT NULL, 
            phone_number INT NOT NULL
        );

        CREATE TABLE test.sellers (
            seller_id BIGSERIAL PRIMARY KEY,
            seller_mail VARCHAR(255) NOT NULL,
            seller_firstname VARCHAR(255) NOT NULL,
            seller_lastname VARCHAR(255) NOT NULL
        );

        CREATE TABLE test.status (
            status_id BIGINT PRIMARY KEY,
            status_description VARCHAR(255) NOT NULL,
            message_for_mail VARCHAR(255) NOT NULL
        );

        CREATE TABLE test.markup (
            expenses_price BIGINT PRIMARY KEY,
            percentage BIGINT NOT NULL
        );

        CREATE TABLE test.mounts (
            mount_id BIGSERIAL PRIMARY KEY,
            mount_price DECIMAL(10, 2) NOT NULL,
            mount_type_name VARCHAR(255) NOT NULL
        );

        CREATE TABLE test.roofs (
            roof_id BIGSERIAL PRIMARY KEY,
            roof_length_cm INT NOT NULL,
            roof_width_cm INT NOT NULL,
            roof_price DECIMAL(10, 2) NOT NULL,
            roof_type_name VARCHAR(255) NOT NULL
        );

        CREATE TABLE test.screws (
            screw_id BIGSERIAL PRIMARY KEY,
            amount_pr_container INT NOT NULL,
            screw_price DECIMAL(10, 2) NOT NULL,
            screw_type_name VARCHAR(255) NOT NULL
        );

        CREATE TABLE test.wood_type (
            wood_type_id BIGSERIAL PRIMARY KEY,
            wood_type_name VARCHAR(255) NOT NULL,
            wood_type_meter_price DECIMAL(10, 2) NOT NULL
        );

        CREATE TABLE test.wood_treatment (
            wood_treatment_id BIGSERIAL PRIMARY KEY,
            wood_treatment_type_name VARCHAR(255) NOT NULL,
            wood_treatment_meter_price DECIMAL(10, 2) NOT NULL
        );

        CREATE TABLE test.wood_dimensions (
            wood_dimension_id BIGSERIAL PRIMARY KEY,
            wood_length INT NOT NULL,
            wood_height INT NOT NULL,
            wood_width INT NOT NULL,
            wood_dimension_meter_price DECIMAL(10, 2) NOT NULL
        );

        CREATE TABLE test.offers (
            offer_id BIGSERIAL PRIMARY KEY,
            total_expense_price DECIMAL(10, 2) NOT NULL,
            total_sales_price DECIMAL(10, 2) NOT NULL,
            seller_id INT REFERENCES test.sellers(seller_id),
            customer_id INT REFERENCES test.customer(customer_id),
            expiration_date DATE NOT NULL,
            carport_length INT NOT NULL,
            carport_width INT NOT NULL,
            shed_length INT NOT NULL,
            shed_width INT NOT NULL
        );

        CREATE TABLE test.orders (
            order_id BIGSERIAL PRIMARY KEY,
            offer_id INT REFERENCES test.offers(offer_id) ON DELETE CASCADE,
            status_id INT REFERENCES test.status(status_id),
            purchase_date DATE NOT NULL,
            tracking_number UUID DEFAULT gen_random_uuid()
        );

        CREATE TABLE test.mounts_list (
            offer_id INT REFERENCES test.offers(offer_id) ON DELETE CASCADE,
            mount_id INT REFERENCES test.mounts(mount_id),
            mount_amount INT NOT NULL, 
            mount_description VARCHAR(255)
        );

        CREATE TABLE test.roof_list (
            offer_id INT REFERENCES test.offers(offer_id) ON DELETE CASCADE,
            roof_id INT REFERENCES test.roofs(roof_id),
            roof_amount INT NOT NULL, 
            roof_description VARCHAR(255)
        );

        CREATE TABLE test.screws_list (
            offer_id INT REFERENCES test.offers(offer_id) ON DELETE CASCADE,
            screw_id INT REFERENCES test.screws(screw_id),
            screws_amount INT NOT NULL, 
            screw_description VARCHAR(255)
        );

        CREATE TABLE test.wood_list (
            offer_id INT REFERENCES test.offers(offer_id) ON DELETE CASCADE,
            wood_type_id INT REFERENCES test.wood_type(wood_type_id),
            wood_treatment_id INT REFERENCES test.wood_treatment(wood_treatment_id),
            wood_dimension_id INT REFERENCES test.wood_dimensions(wood_dimension_id),
            wood_amount INT NOT NULL, 
            wood_description VARCHAR(255)
        );

        -- Insert test data
        INSERT INTO test.cities (zip_code, city_name)
        VALUES (1000, 'Copenhagen'), (2000, 'Aarhus'), (3000, 'Odense');

        INSERT INTO test.customer (customer_mail, customer_firstname, customer_lastname, street_name, house_number, zip_code, phone_number)
        VALUES
            ('john.doe@example.com', 'John', 'Doe', 'Main Street', 123, 1000, 60606060),
            ('jane.smith@example.com', 'Jane', 'Smith', 'Oak Avenue', 456, 2000, 70707070),
            ('alice.johnson@example.com', 'Alice', 'Johnson', 'Pine Road', 789, 3000, 80808080);

        INSERT INTO test.sellers (seller_mail, seller_firstname, seller_lastname)
        VALUES
            ('bob.martin@example.com', 'Bob', 'Martin'),
            ('mary.jane@example.com', 'Mary', 'Jane'),
            ('sam.doe@example.com', 'Sam', 'Doe');

        INSERT INTO test.status (status_id, status_description, message_for_mail)
        VALUES
            (1, 'Pending', 'Your order is pending.'),
            (2, 'Shipped', 'Your order has been shipped.'),
            (3, 'Delivered', 'Your order has been delivered.');

        INSERT INTO test.markup (expenses_price, percentage)
        VALUES (0, 150), (10000, 140), (25000, 135), (50000, 125);

        INSERT INTO test.mounts (mount_price, mount_type_name)
        VALUES 
            (15.00, 'Universalbeslag 190 mm højre'),
            (15.00, 'Universalbeslag 190 mm venstre'),
            (12.00, 'Firkantskiver 40 x 40 x 11 mm'),
            (49.00, 'Stalddørsgreb 50 x 75 mm'),
            (29.00, 'T-Hængsel 390 mm'),
            (8.00,  'Vinkelbeslag 50 x 50 x 35 mm');
               
        INSERT INTO test.roofs (roof_length_cm, roof_width_cm, roof_price, roof_type_name)
        VALUES
            (600, 120, 349.95, 'Plastmo Ecolite blåtonet'),
            (450, 120, 299.95, 'Plastmo Ecolite blåtonet'),
            (300, 120, 249.95, 'Plastmo Ecolite blåtonet'),
            (600, 120, 349.95, 'Plastmo Ecolite klarttonet'),
            (450, 120, 299.95, 'Plastmo Ecolite klarttonet'),
            (300, 120, 249.95, 'Plastmo Ecolite klarttonet'),
            (600, 120, 349.95, 'Plastmo Ecolite sorttonet'),
            (450, 120, 299.95, 'Plastmo Ecolite sorttonet'),
            (300, 120, 249.95, 'Plastmo Ecolite sorttonet');

        INSERT INTO test.screws (amount_pr_container, screw_price, screw_type_name)
        VALUES 
                (200, 69.95, 'Plastmo bundskruer 200 stk.'), 
                (200, 49.95, '4,5 x 60 mm. skruer 200 stk.'), 
                (250, 59.95, '4,0 x 50 mm. beslagskruer 250 stk.'), 
                (300, 79.95, '4,5 x 50 mm. Skruer 300 stk.'), 
                (1, 24.95, 'Bræddebolt 10 x 120 mm.'), 
                (1000, 99.95, 'Hulbånd 1x20 mm. 10 mtr.');
        
        INSERT INTO test.wood_type (wood_type_name, wood_type_meter_price)
        VALUES ('Spær', 10.25), ('Lægte', 5.45), ('Reglar', 7.25), ('Stolpe', 8.75), ('Bræt', 9.25);
        
        INSERT INTO test.wood_treatment (wood_treatment_type_name, wood_treatment_meter_price)
        VALUES ('Trykimprægneret', 10.00), ('Ubehandlet', 0.00), ('Poleret', 5.00), ('Malet', 10.00);
        
        INSERT INTO test.wood_dimensions (wood_length, wood_width, wood_height, wood_dimension_meter_price)
        SELECT l, w, h, price
        FROM (VALUES
            (150),(180),(210),(240),(270),(300),(330),(360),(390),(420),(450),(480),(510),(540),(570),(600),(630),(660),(690),(720),(750),(780)
        ) AS lengths(l),
        (VALUES
            (20, 100, 12.95), (20, 125, 14.50), (20, 150, 15.90), (20, 175, 17.25), (20, 200, 18.60),
            (25, 100, 13.95), (25, 125, 15.50), (25, 150, 16.90), (25, 175, 18.25), (25, 200, 19.60),
            (30, 100, 14.95), (30, 125, 16.50), (30, 150, 17.90), (30, 175, 19.25), (30, 200, 20.60),
            (40, 75, 10.50),
            (45, 95, 16.25), (45, 120, 17.75), (45, 145, 19.00), (45, 170, 20.25), (45, 195, 21.50), (45, 220, 22.75), (45, 245, 24.00), (45, 270, 25.25), (45, 295, 26.50), (45, 320, 27.75),
            (60, 95, 18.25), (60, 120, 19.75), (60, 145, 21.00), (60, 170, 22.25), (60, 195, 23.50), (60, 220, 24.75), (60, 245, 26.00), (60, 270, 27.25), (60, 295, 28.50), (60, 320, 29.75),
            (75, 95, 20.25), (75, 120, 21.75), (75, 145, 23.00), (75, 170, 24.25), (75, 195, 25.50), (75, 220, 26.75), (75, 245, 28.00), (75, 270, 29.25), (75, 295, 30.50), (75, 320, 31.75),
            (100, 100, 35.00)
        )AS dims(w, h, price);
        
        INSERT INTO test.offers (total_expense_price, total_sales_price, seller_id, customer_id, expiration_date, carport_length, carport_width, shed_length, shed_width)
        VALUES
            (5000.00, 6000.00, 1, 1, '2025-12-31', 500, 300, 200, 150),
            (7000.00, 8000.00, 2, 2, '2025-12-31', 600, 400, 250, 200),
            (9000.00, 10000.00, 3, 3, '2025-12-31', 700, 500, 300, 250);
        
        INSERT INTO test.orders (offer_id, status_id, purchase_date)
        VALUES
            (1, 1, '2025-05-01'),
            (2, 2, '2025-05-02');
        
        INSERT INTO test.orders (offer_id, status_id, purchase_date, tracking_number)
        VALUES
            (3, 3, '2025-05-03', 'f47ac10b-58cc-4372-a567-0e02b2c3d479');
        
        INSERT INTO test.mounts_list (offer_id, mount_id, mount_amount, mount_description)
        VALUES
            (1, 1, 10, 'Spær monterings beslag'), (1, 3, 6, 'Firkantskive til montering af rem'), (3, 4, 8, 'staldørsgreb til skur');

        INSERT INTO test.roof_list (offer_id, roof_id, roof_amount, roof_description)
        VALUES
            (1, 1, 10, 'Tagplader monteres på spær'), (1, 2, 10, 'Tagplader monteres på spær'), (3, 3, 8, 'Tagplader monteres på spær');

        INSERT INTO test.screws_list (offer_id, screw_id, screws_amount, screw_description)
        VALUES
            (1, 1, 50, 'Til spær beslag'), (1, 2, 30, 'Til rem montering på stolpe'), (3, 3, 20, 'Til beklædningsbræders montering');

        INSERT INTO test.wood_list (offer_id, wood_type_id, wood_treatment_id, wood_dimension_id, wood_amount, wood_description)
        VALUES
            (1, 1, 1, 1, 100, 'Rem til montering på stolper'), (1, 1, 2, 2, 200, 'Spær til montering på rem'), (3, 3, 3, 3, 150, 'Lægte til Z bag på skurets dør');
        """;

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(ddlWithData);
        }
    }
}