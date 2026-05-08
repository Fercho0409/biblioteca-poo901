-- ============================================================
--  SISTEMA DE BIBLIOTECA - Colegio Amigos De Don Bosco
--  POO901 - Universidad Don Bosco
--  Fase I del Proyecto
-- ============================================================

CREATE DATABASE IF NOT EXISTS biblioteca_db
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_spanish_ci;

USE biblioteca_db;

-- ------------------------------------------------------------
-- TIPO_DOCUMENTO
-- Guarda los tipos: Libro, Revista, CD de audio, DVD
-- y el prefijo para generar el código interno automáticamente
-- ------------------------------------------------------------
CREATE TABLE tipo_documento (
  id_tipo     INT          NOT NULL AUTO_INCREMENT,
  nombre      VARCHAR(50)  NOT NULL,
  prefijo     VARCHAR(5)   NOT NULL,
  PRIMARY KEY (id_tipo)
);

INSERT INTO tipo_documento (nombre, prefijo) VALUES
  ('Libro',       'LIB'),
  ('Revista',     'REV'),
  ('CD de Audio', 'CDA'),
  ('DVD',         'DVD');

-- ------------------------------------------------------------
-- USUARIOS
-- tipo_usuario: ADMINISTRADOR, PROFESOR, ALUMNO
-- ------------------------------------------------------------
CREATE TABLE usuarios (
  id_usuario      INT           NOT NULL AUTO_INCREMENT,
  nombre          VARCHAR(80)   NOT NULL,
  apellido        VARCHAR(80)   NOT NULL,
  email           VARCHAR(120)  NOT NULL UNIQUE,
  password        VARCHAR(255)  NOT NULL,
  tipo_usuario    ENUM('ADMINISTRADOR','PROFESOR','ALUMNO') NOT NULL DEFAULT 'ALUMNO',
  activo          TINYINT(1)    NOT NULL DEFAULT 1,
  fecha_creacion  TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id_usuario)
);

-- Usuario administrador por defecto (password: admin123)
INSERT INTO usuarios (nombre, apellido, email, password, tipo_usuario) VALUES
  ('Admin', 'Sistema', 'admin@biblioteca.edu', 'admin123', 'ADMINISTRADOR');

-- ------------------------------------------------------------
-- DOCUMENTOS
-- Tabla padre con campos comunes a todos los tipos
-- ------------------------------------------------------------
CREATE TABLE documentos (
  id_documento        INT           NOT NULL AUTO_INCREMENT,
  id_tipo             INT           NOT NULL,
  codigo_interno      VARCHAR(12)   NOT NULL UNIQUE,
  titulo              VARCHAR(200)  NOT NULL,
  ubicacion_fisica    VARCHAR(100),
  unidades_totales    INT           NOT NULL DEFAULT 1,
  unidades_disponibles INT          NOT NULL DEFAULT 1,
  activo              TINYINT(1)    NOT NULL DEFAULT 1,
  fecha_ingreso       TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id_documento),
  CONSTRAINT fk_doc_tipo FOREIGN KEY (id_tipo)
    REFERENCES tipo_documento(id_tipo)
);

-- ------------------------------------------------------------
-- LIBROS (extiende DOCUMENTOS)
-- ------------------------------------------------------------
CREATE TABLE libros (
  id_libro          INT           NOT NULL AUTO_INCREMENT,
  id_documento      INT           NOT NULL,
  autor             VARCHAR(150)  NOT NULL,
  editorial         VARCHAR(100),
  isbn              VARCHAR(20),
  num_paginas       INT,
  anio_publicacion  INT,
  PRIMARY KEY (id_libro),
  CONSTRAINT fk_libro_doc FOREIGN KEY (id_documento)
    REFERENCES documentos(id_documento) ON DELETE CASCADE
);

-- ------------------------------------------------------------
-- REVISTAS (extiende DOCUMENTOS)
-- ------------------------------------------------------------
CREATE TABLE revistas (
  id_revista        INT           NOT NULL AUTO_INCREMENT,
  id_documento      INT           NOT NULL,
  editorial         VARCHAR(100),
  periodicidad      VARCHAR(50),
  fecha_publicacion DATE,
  PRIMARY KEY (id_revista),
  CONSTRAINT fk_revista_doc FOREIGN KEY (id_documento)
    REFERENCES documentos(id_documento) ON DELETE CASCADE
);

-- ------------------------------------------------------------
-- CDS_AUDIO (extiende DOCUMENTOS)
-- ------------------------------------------------------------
CREATE TABLE cds_audio (
  id_cd          INT           NOT NULL AUTO_INCREMENT,
  id_documento   INT           NOT NULL,
  artista        VARCHAR(150),
  genero         VARCHAR(80),
  duracion_min   INT,
  num_canciones  INT,
  PRIMARY KEY (id_cd),
  CONSTRAINT fk_cd_doc FOREIGN KEY (id_documento)
    REFERENCES documentos(id_documento) ON DELETE CASCADE
);

-- ------------------------------------------------------------
-- DVDS (extiende DOCUMENTOS)
-- ------------------------------------------------------------
CREATE TABLE dvds (
  id_dvd         INT           NOT NULL AUTO_INCREMENT,
  id_documento   INT           NOT NULL,
  director       VARCHAR(150),
  duracion_min   INT,
  genero         VARCHAR(80),
  PRIMARY KEY (id_dvd),
  CONSTRAINT fk_dvd_doc FOREIGN KEY (id_documento)
    REFERENCES documentos(id_documento) ON DELETE CASCADE
);

-- ------------------------------------------------------------
-- PRESTAMOS
-- estado: ACTIVO, DEVUELTO, CON_MORA
-- ------------------------------------------------------------
CREATE TABLE prestamos (
  id_prestamo       INT             NOT NULL AUTO_INCREMENT,
  id_usuario        INT             NOT NULL,
  id_documento      INT             NOT NULL,
  fecha_prestamo    DATE            NOT NULL,
  fecha_limite      DATE            NOT NULL,
  fecha_devolucion  DATE,
  mora_acumulada    DECIMAL(10,2)   NOT NULL DEFAULT 0.00,
  estado            ENUM('ACTIVO','DEVUELTO','CON_MORA') NOT NULL DEFAULT 'ACTIVO',
  PRIMARY KEY (id_prestamo),
  CONSTRAINT fk_prestamo_usuario FOREIGN KEY (id_usuario)
    REFERENCES usuarios(id_usuario),
  CONSTRAINT fk_prestamo_doc FOREIGN KEY (id_documento)
    REFERENCES documentos(id_documento)
);

-- ------------------------------------------------------------
-- CONFIGURACION
-- Parámetros configurables por el administrador
-- ------------------------------------------------------------
CREATE TABLE configuracion (
  id_config    INT           NOT NULL AUTO_INCREMENT,
  clave        VARCHAR(80)   NOT NULL UNIQUE,
  valor        VARCHAR(200)  NOT NULL,
  descripcion  VARCHAR(255),
  PRIMARY KEY (id_config)
);

INSERT INTO configuracion (clave, valor, descripcion) VALUES
  ('mora_diaria',              '0.25',  'Mora en dólares por día de retraso'),
  ('max_prestamos_alumno',     '3',     'Cantidad máxima de préstamos simultáneos para alumnos'),
  ('max_prestamos_profesor',   '6',     'Cantidad máxima de préstamos simultáneos para profesores'),
  ('dias_prestamo_alumno',     '7',     'Días de préstamo permitidos para alumnos'),
  ('dias_prestamo_profesor',   '15',    'Días de préstamo permitidos para profesores');

-- ------------------------------------------------------------
-- VISTA útil: documentos con disponibilidad y tipo
-- ------------------------------------------------------------
CREATE VIEW vista_documentos AS
SELECT
  d.id_documento,
  d.codigo_interno,
  t.nombre          AS tipo,
  d.titulo,
  d.ubicacion_fisica,
  d.unidades_totales,
  d.unidades_disponibles,
  d.activo
FROM documentos d
JOIN tipo_documento t ON d.id_tipo = t.id_tipo;

-- ------------------------------------------------------------
-- VISTA útil: préstamos activos con datos del usuario y doc
-- ------------------------------------------------------------
CREATE VIEW vista_prestamos_activos AS
SELECT
  p.id_prestamo,
  u.nombre          AS nombre_usuario,
  u.apellido        AS apellido_usuario,
  u.tipo_usuario,
  d.codigo_interno,
  d.titulo,
  p.fecha_prestamo,
  p.fecha_limite,
  p.mora_acumulada,
  p.estado
FROM prestamos p
JOIN usuarios   u ON p.id_usuario   = u.id_usuario
JOIN documentos d ON p.id_documento = d.id_documento
WHERE p.estado IN ('ACTIVO','CON_MORA');
