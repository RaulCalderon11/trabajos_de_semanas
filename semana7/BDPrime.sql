-- =============================================
-- SISTEMA DE GESTIÓN ACADÉMICA - BASE DE DATOS
-- =============================================

-- Crear base de datos y seleccionarla
DROP DATABASE IF EXISTS sistema_gestion_academica;
CREATE DATABASE sistema_gestion_academica;
USE sistema_gestion_academica;

-- =============================================
-- TABLA: Usuarios
-- =============================================
CREATE TABLE Usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    ingresoUsuario VARCHAR(50) UNIQUE NOT NULL,
    ingresoContrasenia VARCHAR(100) NOT NULL,
    rol ENUM('admin', 'profesor', 'estudiante') NOT NULL DEFAULT 'estudiante',
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    telefono VARCHAR(15),
    fecha_nacimiento DATE,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE
);

-- =============================================
-- TABLA: Cursos
-- =============================================
CREATE TABLE Cursos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre_curso VARCHAR(100) NOT NULL,
    nivel ENUM('A1', 'A2', 'B1', 'B2', 'C1', 'C2') NOT NULL,
    descripcion TEXT,
    profesor_id INT NOT NULL,
    max_estudiantes INT DEFAULT 20,
    fecha_inicio DATE,
    fecha_fin DATE,
    activo BOOLEAN DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (profesor_id) REFERENCES Usuarios(id)
);

-- =============================================
-- TABLA: Calificaciones
-- =============================================
CREATE TABLE Calificaciones (
    id INT AUTO_INCREMENT PRIMARY KEY,
    estudiante_id INT NOT NULL,
    curso_id INT NOT NULL,
    habilidad ENUM('speaking', 'writing', 'reading', 'listening', 'grammar', 'vocabulary') NOT NULL,
    puntaje DECIMAL(4,2) NOT NULL,
    fecha_evaluacion DATE NOT NULL,
    comentario TEXT,
    tipo_evaluacion ENUM('examen', 'tarea', 'proyecto', 'participacion') DEFAULT 'examen',
    periodo ENUM('Q1', 'Q2', 'Q3', 'Q4') NOT NULL,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (estudiante_id) REFERENCES Usuarios(id),
    FOREIGN KEY (curso_id) REFERENCES Cursos(id),
    CHECK (puntaje >= 0 AND puntaje <= 20),
    UNIQUE KEY unique_evaluacion (estudiante_id, curso_id, habilidad, fecha_evaluacion)
);

-- =============================================
-- TABLA: Inscripciones (Relación muchos a muchos)
-- =============================================
CREATE TABLE Inscripciones (
    id INT AUTO_INCREMENT PRIMARY KEY,
    estudiante_id INT NOT NULL,
    curso_id INT NOT NULL,
    fecha_inscripcion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado ENUM('activo', 'completado', 'cancelado') DEFAULT 'activo',
    FOREIGN KEY (estudiante_id) REFERENCES Usuarios(id),
    FOREIGN KEY (curso_id) REFERENCES Cursos(id),
    UNIQUE KEY unique_inscripcion (estudiante_id, curso_id)
);

-- =============================================
-- INSERCIÓN DE DATOS DE EJEMPLO
-- =============================================

-- =============================================
-- 1. INSERTAR USUARIOS
-- =============================================

-- Administradores
INSERT INTO Usuarios (ingresoUsuario, ingresoContrasenia, rol, nombre, email, telefono, fecha_nacimiento) VALUES
('admin', 'admin123', 'admin', 'María González', 'maria.gonzalez@instituto.edu', '+1234567890', '1980-05-15'),
('sistema', 'sistema456', 'admin', 'Carlos Rodríguez', 'carlos.rodriguez@instituto.edu', '+1234567891', '1975-08-22');

-- Profesores
INSERT INTO Usuarios (ingresoUsuario, ingresoContrasenia, rol, nombre, email, telefono, fecha_nacimiento) VALUES
('profe_ana', 'profe123', 'profesor', 'Ana Martínez', 'ana.martinez@instituto.edu', '+1234567892', '1985-03-10'),
('profe_luis', 'profe456', 'profesor', 'Luis Fernández', 'luis.fernandez@instituto.edu', '+1234567893', '1978-11-30'),
('profe_sofia', 'profe789', 'profesor', 'Sofia Ramírez', 'sofia.ramirez@instituto.edu', '+1234567894', '1990-07-25');

-- Estudiantes
INSERT INTO Usuarios (ingresoUsuario, ingresoContrasenia, rol, nombre, email, telefono, fecha_nacimiento) VALUES
('est_juan', 'est123', 'estudiante', 'Juan Pérez', 'juan.perez@estudiante.edu', '+1234567895', '2000-01-20'),
('est_maria', 'est456', 'estudiante', 'María López', 'maria.lopez@estudiante.edu', '+1234567896', '2001-03-15'),
('est_carlos', 'est789', 'estudiante', 'Carlos Sánchez', 'carlos.sanchez@estudiante.edu', '+1234567897', '1999-08-10'),
('est_laura', 'est012', 'estudiante', 'Laura García', 'laura.garcia@estudiante.edu', '+1234567898', '2000-12-05'),
('est_pedro', 'est345', 'estudiante', 'Pedro Martínez', 'pedro.martinez@estudiante.edu', '+1234567899', '2001-06-18'),
('est_ana', 'est678', 'estudiante', 'Ana Rodríguez', 'ana.rodriguez@estudiante.edu', '+1234567800', '2000-09-22');

-- =============================================
-- 2. INSERTAR CURSOS
-- =============================================

INSERT INTO Cursos (nombre_curso, nivel, descripcion, profesor_id, max_estudiantes, fecha_inicio, fecha_fin) VALUES
('Inglés Básico I', 'A1', 'Curso introductorio de inglés para principiantes. Enfoque en vocabulario esencial y conversación básica.', 3, 15, '2024-01-15', '2024-06-15'),
('Inglés Intermedio', 'B1', 'Desarrollo de habilidades comunicativas intermedias. Gramática y conversación fluida.', 3, 12, '2024-01-15', '2024-06-15'),
('Conversación Avanzada', 'C1', 'Perfeccionamiento del speaking y listening. Enfoque en fluidez y pronunciación.', 4, 10, '2024-02-01', '2024-07-01'),
('Writing Académico', 'B2', 'Desarrollo de habilidades de escritura formal y académica en inglés.', 4, 8, '2024-02-01', '2024-07-01'),
('Preparación TOEFL', 'C1', 'Curso intensivo de preparación para el examen TOEFL.', 5, 6, '2024-03-01', '2024-08-01');

-- =============================================
-- 3. INSERTAR INSCRIPCIONES
-- =============================================

-- Inscripciones para Inglés Básico I
INSERT INTO Inscripciones (estudiante_id, curso_id) VALUES
(6, 1), (7, 1), (8, 1), (9, 1);

-- Inscripciones para Inglés Intermedio
INSERT INTO Inscripciones (estudiante_id, curso_id) VALUES
(7, 2), (8, 2), (10, 2);

-- Inscripciones para Conversación Avanzada
INSERT INTO Inscripciones (estudiante_id, curso_id) VALUES
(9, 3), (10, 3), (11, 3);

-- =============================================
-- 4. INSERTAR CALIFICACIONES COMPLETAS
-- =============================================

-- Calificaciones para Juan Pérez (Inglés Básico I - Q1)
INSERT INTO Calificaciones (estudiante_id, curso_id, habilidad, puntaje, fecha_evaluacion, comentario, tipo_evaluacion, periodo) VALUES
(6, 1, 'speaking', 14.5, '2024-01-20', 'Buen progreso en pronunciación', 'examen', 'Q1'),
(6, 1, 'writing', 13.0, '2024-01-25', 'Necesita mejorar gramática', 'tarea', 'Q1'),
(6, 1, 'reading', 16.0, '2024-01-28', 'Excelente comprensión', 'examen', 'Q1'),
(6, 1, 'listening', 15.0, '2024-02-01', 'Buen oído para el idioma', 'examen', 'Q1'),
(6, 1, 'grammar', 12.5, '2024-02-05', 'Debe practicar verbos', 'tarea', 'Q1');

-- Calificaciones para María López (Inglés Básico I - Q1)
INSERT INTO Calificaciones (estudiante_id, curso_id, habilidad, puntaje, fecha_evaluacion, comentario, tipo_evaluacion, periodo) VALUES
(7, 1, 'speaking', 16.5, '2024-01-20', 'Muy fluida en conversación', 'examen', 'Q1'),
(7, 1, 'writing', 15.0, '2024-01-25', 'Buena estructura de párrafos', 'tarea', 'Q1'),
(7, 1, 'reading', 17.0, '2024-01-28', 'Comprensión excelente', 'examen', 'Q1'),
(7, 1, 'listening', 16.0, '2024-02-01', 'Distingue bien los sonidos', 'examen', 'Q1'),
(7, 1, 'grammar', 14.5, '2024-02-05', 'Sólidos conocimientos gramaticales', 'tarea', 'Q1');

-- Calificaciones para Carlos Sánchez (Inglés Intermedio - Q1)
INSERT INTO Calificaciones (estudiante_id, curso_id, habilidad, puntaje, fecha_evaluacion, comentario, tipo_evaluacion, periodo) VALUES
(8, 2, 'speaking', 17.0, '2024-01-22', 'Fluidez notable', 'examen', 'Q1'),
(8, 2, 'writing', 16.5, '2024-01-27', 'Excelente vocabulario', 'proyecto', 'Q1'),
(8, 2, 'reading', 18.0, '2024-01-30', 'Comprensión avanzada', 'examen', 'Q1'),
(8, 2, 'listening', 17.5, '2024-02-03', 'Excelente comprensión auditiva', 'examen', 'Q1'),
(8, 2, 'vocabulary', 16.0, '2024-02-07', 'Amplio vocabulario', 'tarea', 'Q1');

-- Calificaciones Q2 para mostrar progreso
INSERT INTO Calificaciones (estudiante_id, curso_id, habilidad, puntaje, fecha_evaluacion, comentario, tipo_evaluacion, periodo) VALUES
(6, 1, 'speaking', 16.0, '2024-04-15', 'Mejoró significativamente', 'examen', 'Q2'),
(6, 1, 'grammar', 14.0, '2024-04-20', 'Progreso en uso de verbos', 'tarea', 'Q2'),
(7, 1, 'speaking', 17.5, '2024-04-15', 'Excelente evolución', 'examen', 'Q2'),
(8, 2, 'speaking', 18.0, '2024-04-18', 'Nivel casi nativo', 'examen', 'Q2');

-- =============================================
-- CONSULTAS DE VERIFICACIÓN
-- =============================================

-- Verificar usuarios por rol
SELECT rol, COUNT(*) as total FROM Usuarios GROUP BY rol;

-- Verificar cursos y profesores
SELECT c.nombre_curso, c.nivel, u.nombre as profesor 
FROM Cursos c 
JOIN Usuarios u ON c.profesor_id = u.id;

-- Verificar inscripciones por curso
SELECT c.nombre_curso, COUNT(i.estudiante_id) as total_estudiantes
FROM Cursos c 
LEFT JOIN Inscripciones i ON c.id = i.curso_id 
GROUP BY c.nombre_curso;

-- Verificar calificaciones promedio por estudiante
SELECT u.nombre as estudiante, c.nombre_curso, AVG(cal.puntaje) as promedio
FROM Calificaciones cal
JOIN Usuarios u ON cal.estudiante_id = u.id
JOIN Cursos c ON cal.curso_id = c.id
GROUP BY u.nombre, c.nombre_curso
ORDER BY promedio DESC;

-- Verificar progreso por habilidad
SELECT u.nombre, cal.habilidad, AVG(cal.puntaje) as promedio, COUNT(*) as evaluaciones
FROM Calificaciones cal
JOIN Usuarios u ON cal.estudiante_id = u.id
WHERE u.rol = 'estudiante'
GROUP BY u.nombre, cal.habilidad
ORDER BY u.nombre, promedio DESC;







-- Verificar que el usuario 'admin' existe y tiene permisos
SELECT user, host FROM mysql.user WHERE user = 'admin';

-- Si no existe, créalo:
CREATE USER IF NOT EXISTS 'admin'@'localhost' IDENTIFIED BY 'admin';

-- Otorgar todos los privilegios sobre la nueva base de datos
GRANT ALL PRIVILEGES ON sistema_gestion_academica.* TO 'admin'@'localhost';

-- Aplicar los cambios
FLUSH PRIVILEGES;

-- Verificar permisos
SHOW GRANTS FOR 'admin'@'localhost';

select*from Usuarios;