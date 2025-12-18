-- ============================================
-- DATOS DE EJEMPLO
-- ============================================
-- Puedes ejecutar estos inserts después de que Spring Boot cree las tablas

/*

-- Insertar álbum de ejemplo
INSERT INTO albumes (nombre, imagen, fecha_lanzamiento, tipo_laminas, total_laminas, descripcion) 
VALUES 
('Mundial Qatar 2022', 'https://example.com/mundial2022.jpg', '2022-10-01', 'deportes', 640, 'Álbum oficial de la Copa Mundial de Fútbol Qatar 2022');

-- Obtener el ID del álbum creado
-- En PostgreSQL: SELECT currval('albumes_id_seq');

-- Insertar algunas láminas de ejemplo (reemplazar album_id con el ID real)
INSERT INTO laminas (numero, nombre, imagen, adquirida, cantidad_repetidas, album_id) 
VALUES 
(1, 'Emblema de la Copa Mundial', NULL, true, 0, 1),
(2, 'Mascota oficial', NULL, true, 2, 1),
(10, 'Lionel Messi - Argentina', 'https://example.com/messi.jpg', true, 3, 1),
(11, 'Cristiano Ronaldo - Portugal', 'https://example.com/ronaldo.jpg', false, 0, 1),
(25, 'Neymar Jr - Brasil', 'https://example.com/neymar.jpg', true, 1, 1);

*/
