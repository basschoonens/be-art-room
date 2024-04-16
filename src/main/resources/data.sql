INSERT INTO artworks (
    id, title, artist, description, date_created, gallery_buying_price,
    edition, image_url, artwork_type
) VALUES (
             1001, 'Head of a woman', 'Leonardo da Vinci', 'A drawing of the head of a woman',
             15081658, 2000000, 1, 'www.leonardodavinci.com', 'drawing'
         );

INSERT INTO drawings (
    id, drawing_draw_type, drawing_surface, drawing_material, drawing_dimensions_width_in_cm, drawing_dimensions_height_in_cm
) VALUES (
             1001, 'pencil', 'paper', 'pencil', 20, 30
         );