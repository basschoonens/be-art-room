INSERT INTO artworks (id, title, artist, description, date_created, gallery_buying_price, edition, image_url, artwork_type) VALUES (1001, 'Head of a woman', 'Leonardo da Vinci', 'A drawing of the head of a woman', 15081658, 2000000, 1, 'www.leonardodavinci.com', 'drawing');
INSERT INTO artworks (id, title, artist, description, date_created, gallery_buying_price, edition, image_url, artwork_type) VALUES (1002, 'The Scream', 'Edvard Munch', 'De Schreeuw (Noors: Skrik) is de titel van een viertal schilderijen en een lithografie van Edvard Munch uit 1893.', 1011893, 1500000, 1, 'www.edvardmunch.com', 'painting');

INSERT INTO drawings (id, drawing_draw_type, drawing_surface, drawing_material, drawing_dimensions_width_in_cm, drawing_dimensions_height_in_cm) VALUES (1001, 'pencil', 'paper', 'pencil', 20, 30);

INSERT INTO paintings (id, painting_paint_type, painting_surface, painting_material, painting_dimensions_width_in_cm, painting_dimensions_height_in_cm) VALUES (1002, 'oil', 'canvas', 'oil', 50, 70);

INSERT INTO ratings (id, rating, artwork_id, comment) VALUES (1001, 5, 1001, 'Wow, This looks great');
INSERT INTO ratings (id, rating, artwork_id, comment) VALUES (1002, 2, 1001, 'Very impresive art');

INSERT INTO ratings (id, rating, artwork_id, comment) VALUES (1003, 5, 1002, 'Wow, This looks great');
INSERT INTO ratings (id, rating, artwork_id, comment) VALUES (1004, 2, 1002, 'Very impresive art');