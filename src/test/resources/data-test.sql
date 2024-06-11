-- -- Dropping existing tables
-- DROP TABLE IF EXISTS ratings;
-- DROP TABLE IF EXISTS orders_artworks;
-- DROP TABLE IF EXISTS orders;
-- DROP TABLE IF EXISTS paintings;
-- DROP TABLE IF EXISTS drawings;
-- DROP TABLE IF EXISTS artwork;
-- DROP TABLE IF EXISTS authorities;
-- DROP TABLE IF EXISTS users;
--
-- -- Creating tables
-- -- Table 'artwork'
-- CREATE TABLE artwork (
--                          artwork_id INT PRIMARY KEY,
--                          title VARCHAR(255),
--                          artist VARCHAR(255),
--                          description TEXT,
--                          date_created DATE,
--                          gallery_buying_price DECIMAL(10, 2),
--                          edition VARCHAR(50),
--                          artwork_type VARCHAR(50),
--                          username VARCHAR(50),
--                          FOREIGN KEY (username) REFERENCES users(username)
-- );
--
-- -- Table 'drawings'
-- CREATE TABLE drawings (
--                           artwork_id INT PRIMARY KEY,
--                           drawing_surface VARCHAR(50),
--                           drawing_material VARCHAR(50),
--                           drawing_dimensions_width_in_cm INT,
--                           drawing_dimensions_height_in_cm INT,
--                           FOREIGN KEY (artwork_id) REFERENCES artwork(artwork_id)
-- );
--
-- -- Table 'paintings'
-- CREATE TABLE paintings (
--                            artwork_id INT PRIMARY KEY,
--                            painting_surface VARCHAR(50),
--                            painting_material VARCHAR(50),
--                            painting_dimensions_width_in_cm INT,
--                            painting_dimensions_height_in_cm INT,
--                            FOREIGN KEY (artwork_id) REFERENCES artwork(artwork_id)
-- );
--
-- -- Table 'orders'
-- CREATE TABLE orders (
--                         order_id INT PRIMARY KEY,
--                         order_number VARCHAR(50),
--                         order_date VARCHAR(50),
--                         order_status VARCHAR(50),
--                         payment_method VARCHAR(50),
--                         total_price DECIMAL(10, 2),
--                         name VARCHAR(255),
--                         address VARCHAR(255),
--                         postal_code VARCHAR(20),
--                         city VARCHAR(100)
-- );
--
-- -- Table 'orders_artworks'
-- CREATE TABLE orders_artworks (
--                                  order_id INT,
--                                  artwork_id INT,
--                                  PRIMARY KEY (order_id, artwork_id),
--                                  FOREIGN KEY (order_id) REFERENCES orders(order_id),
--                                  FOREIGN KEY (artwork_id) REFERENCES artwork(artwork_id)
-- );
--
-- -- Table 'ratings'
-- CREATE TABLE ratings (
--                          rating_id INT PRIMARY KEY,
--                          artwork_id INT,
--                          rating INT,
--                          comment TEXT,
--                          FOREIGN KEY (artwork_id) REFERENCES artwork(artwork_id)
-- );
--
-- -- Table 'users'
-- CREATE TABLE users (
--                        username VARCHAR(50) PRIMARY KEY,
--                        password VARCHAR(255),
--                        email VARCHAR(255)
-- );
--
-- -- Table 'authorities'
-- CREATE TABLE authorities (
--                              username VARCHAR(50),
--                              authority VARCHAR(50),
--                              PRIMARY KEY (username, authority),
--                              FOREIGN KEY (username) REFERENCES users(username)
-- );

-- Inserting into 'users' table
INSERT INTO users (username, password, email)
VALUES
    ('artist1', '$2a$12$IzA1Ja1LH4PSMoro9PeITO1etDlknPjSX1nLusgt1vi9c1uaEXdEK', 'user@test.nl'),
    ('artist2', '$2a$12$IzA1Ja1LH4PSMoro9PeITO1etDlknPjSX1nLusgt1vi9c1uaEXdEK', 'admin@test.nl');

-- Inserting into 'authorities' table
INSERT INTO authorities (username, authority)
VALUES
    ('artist1', 'ROLE_USER'),
    ('artist2', 'ROLE_ADMIN');

-- Inserting into 'artwork' table
INSERT INTO artworks (artwork_id, title, artist, description, date_created, gallery_buying_price, edition, artwork_type, username)
VALUES
    (1, 'Drawing Example', 'John Doe', 'A beautiful drawing', '2024-06-10', 100.0, 'Limited Edition', 'drawing', 'artist1'),
    (2, 'Painting Example', 'Jane Smith', 'A stunning painting', '2024-06-10', 200.0, 'Original', 'painting', 'artist2');

-- Inserting into 'drawings' table
INSERT INTO drawings (artwork_id, drawing_surface, drawing_material, drawing_dimensions_width_in_cm, drawing_dimensions_height_in_cm)
VALUES (1, 'Paper', 'Pencil', 20, 30);

-- Inserting into 'paintings' table
INSERT INTO paintings (artwork_id, painting_surface, painting_material, painting_dimensions_width_in_cm, painting_dimensions_height_in_cm)
VALUES (2, 'Canvas', 'Oil', 50, 60);

-- Inserting into 'orders' table
INSERT INTO orders (order_id, order_number, order_date, order_status, payment_method, total_price, name, address, postal_code, city)
VALUES
    (100, 'ORD123456','2024-06-10', 'Pending', 'Credit Card', 250.0, 'John Doe', '123 Main St', '1234AB', 'Amsterdam'),
    (200, 'ORD789012','2024-06-11', 'Processing', 'PayPal', 150.0, 'Jane Smith', '456 Elm St', '5678CD', 'Rotterdam');

-- Inserting into 'orders_artworks' table
INSERT INTO order_artwork (order_id, artwork_id)
VALUES
    (100, 1),
    (200, 2);

-- Inserting into 'ratings' table
INSERT INTO ratings (rating_id, artwork_id, rating, comment)
VALUES
    (1, 1, 4, 'Great artwork! I love it.'),
    (2, 1, 5, 'Amazing piece of art. Highly recommended.'),
    (3, 2, 3, 'Decent work, but could be improved.'),
    (4, 2, 2, 'Not satisfied with the quality.');

