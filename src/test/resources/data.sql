INSERT INTO artwork (artwork_id, title, artist, description, date_created, gallery_buying_price, edition, artwork_type,
                     drawing_surface, drawing_material, drawing_dimensions_width_in_cm, drawing_dimensions_height_in_cm,
                     username)
VALUES (1, 'Drawing Example', 'John Doe', 'A beautiful drawing', '2024-06-10', 100.0, 'Limited Edition', 'drawing',
        'Paper', 'Pencil', 20, 30, 'artist1');

INSERT INTO artwork (artwork_id, title, artist, description, date_created, gallery_buying_price, edition, artwork_type,
                     painting_surface, painting_material, painting_dimensions_width_in_cm,
                     painting_dimensions_height_in_cm, username)
VALUES (2, 'Painting Example', 'Jane Smith', 'A stunning painting', '2024-06-10', 200.0, 'Original', 'painting',
        'Canvas', 'Oil', 50, 60, 'artist2');

INSERT INTO orders (order_id, order_number, order_date, order_status, payment_method, total_price, name, address, postal_code, city)
VALUES (1, 'ORD123456', '2024-06-10', 'Pending', 'Credit Card', 250.0, 'John Doe', '123 Main St', '1234AB', 'Amsterdam');

INSERT INTO orders_artworks (order_id, artwork_id)
VALUES (1, 1);

INSERT INTO orders (order_id, order_number, order_date, order_status, payment_method, total_price, name, address, postal_code, city)
VALUES (2, 'ORD789012', '2024-06-11', 'Processing', 'PayPal', 150.0, 'Jane Smith', '456 Elm St', '5678CD', 'Rotterdam');

INSERT INTO orders_artworks (order_id, artwork_id)
VALUES (2, 2);

INSERT INTO ratings (rating_id, artwork_id, rating, comment)
VALUES (1, 1, 4, 'Great artwork! I love it.');

INSERT INTO ratings (rating_id, artwork_id, rating, comment)
VALUES (2, 1, 5, 'Amazing piece of art. Highly recommended.');

INSERT INTO ratings (rating_id, artwork_id, rating, comment)
VALUES (3, 2, 3, 'Decent work, but could be improved.');

INSERT INTO ratings (rating_id, artwork_id, rating, comment)
VALUES (4, 2, 2, 'Not satisfied with the quality.');

INSERT INTO users (username, password, email)
VALUES ('user', '$2a$12$IzA1Ja1LH4PSMoro9PeITO1etDlknPjSX1nLusgt1vi9c1uaEXdEK', 'user@test.nl');
INSERT INTO users (username, password, email)
VALUES ('admin', '$2a$12$IzA1Ja1LH4PSMoro9PeITO1etDlknPjSX1nLusgt1vi9c1uaEXdEK', 'user@test.nl');

INSERT INTO authorities (username, authority)
VALUES ('user', 'ROLE_USER');
INSERT INTO authorities (username, authority)
VALUES ('admin', 'ROLE_ADMIN');