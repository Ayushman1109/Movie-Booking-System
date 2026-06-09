-- Insert dummy theatres
INSERT INTO theatre (version, name, address) VALUES (0, 'PVR Cinemas', 'Downtown Mall, City Center');
INSERT INTO theatre (version, name, address) VALUES (0, 'AMC Theatres', 'West End Avenue');

-- Insert dummy halls for the theatres
INSERT INTO hall (version, total_seats, theatre_id) VALUES (0, 150, 1);
INSERT INTO hall (version, total_seats, theatre_id) VALUES (0, 200, 1);
INSERT INTO hall (version, total_seats, theatre_id) VALUES (0, 120, 2);

-- Insert dummy movies
INSERT INTO movie (version, name, language, duration_in_minutes, rating, poster_url) 
VALUES (0, 'Inception', 'English', 148, 8.8, 'https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcQovCe0H45fWwAtV31ajOdXRPTxSsMQgPIQ3lcZX_mAW0jXV3kH');

INSERT INTO movie (version, name, language, duration_in_minutes, rating, poster_url) 
VALUES (0, 'Interstellar', 'English', 169, 8.6, 'https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcSkerNoNm6EKOjUmGU_mAofzymI6LXM9iMgIUfTRS1iWZcSdk5l');

INSERT INTO movie (version, name, language, duration_in_minutes, rating, poster_url) 
VALUES (0, 'The Dark Knight', 'English', 152, 9.0, 'https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcQkUywIUXDjHSQJIaNHYVs08osgBpF5Ot-xmB_omyEZeeRP9Xug');

INSERT INTO movie (version, name, language, duration_in_minutes, rating, poster_url) 
VALUES (0, 'Spider-Man: Across the Spider-Verse', 'English', 140, 8.7, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT0MiAFRF0oxZO8nwPESVBei050PmIs6_46y9pPRkfWS59pFJpi');

INSERT INTO movie (version, name, language, duration_in_minutes, rating, poster_url) 
VALUES (0, 'Avatar: The Way of Water', 'English', 192, 7.6, 'https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcSHAILTOQDx1YgNRjFS2cOQ079UnNqeZra5KCbnSV8N-aWWt34l');

INSERT INTO movie (version, name, language, duration_in_minutes, rating, poster_url) 
VALUES (0, 'Dune: Part Two', 'English', 166, 8.8, 'https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcRBu8Gzdygf5OOqBJUIJ3-ZxiPbLh62OhvLmtOvuR7x2gF3DucU');

-- Insert dummy shows (8 shows)
INSERT INTO shows (show_id, version, movie_id, hall_id, price, start_time, end_time) 
VALUES (1, 0, 1, 1, 250, '2026-06-10 10:00:00', '2026-06-10 13:00:00');
INSERT INTO shows (show_id, version, movie_id, hall_id, price, start_time, end_time) 
VALUES (2, 0, 2, 2, 300, '2026-06-10 10:00:00', '2026-06-10 13:00:00');
INSERT INTO shows (show_id, version, movie_id, hall_id, price, start_time, end_time) 
VALUES (3, 0, 3, 3, 200, '2026-06-10 10:00:00', '2026-06-10 13:00:00');

INSERT INTO shows (show_id, version, movie_id, hall_id, price, start_time, end_time) 
VALUES (4, 0, 4, 1, 250, '2026-06-10 14:00:00', '2026-06-10 17:00:00');
INSERT INTO shows (show_id, version, movie_id, hall_id, price, start_time, end_time) 
VALUES (5, 0, 5, 2, 350, '2026-06-10 14:00:00', '2026-06-10 17:30:00');
INSERT INTO shows (show_id, version, movie_id, hall_id, price, start_time, end_time) 
VALUES (6, 0, 6, 3, 400, '2026-06-10 14:00:00', '2026-06-10 17:00:00');

INSERT INTO shows (show_id, version, movie_id, hall_id, price, start_time, end_time) 
VALUES (7, 0, 1, 2, 250, '2026-06-10 18:00:00', '2026-06-10 21:00:00');
INSERT INTO shows (show_id, version, movie_id, hall_id, price, start_time, end_time) 
VALUES (8, 0, 2, 3, 300, '2026-06-10 18:00:00', '2026-06-10 21:00:00');

-- Populate all available seats for the shows (0 means available)
INSERT INTO show_avail_seats (show_show_id, avail_seats) SELECT 1, 0 FROM generate_series(1, 150);
INSERT INTO show_avail_seats (show_show_id, avail_seats) SELECT 2, 0 FROM generate_series(1, 200);
INSERT INTO show_avail_seats (show_show_id, avail_seats) SELECT 3, 0 FROM generate_series(1, 120);
INSERT INTO show_avail_seats (show_show_id, avail_seats) SELECT 4, 0 FROM generate_series(1, 150);
INSERT INTO show_avail_seats (show_show_id, avail_seats) SELECT 5, 0 FROM generate_series(1, 200);
INSERT INTO show_avail_seats (show_show_id, avail_seats) SELECT 6, 0 FROM generate_series(1, 120);
INSERT INTO show_avail_seats (show_show_id, avail_seats) SELECT 7, 0 FROM generate_series(1, 200);
INSERT INTO show_avail_seats (show_show_id, avail_seats) SELECT 8, 0 FROM generate_series(1, 120);
