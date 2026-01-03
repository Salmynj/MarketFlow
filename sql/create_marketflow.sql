-- Script para crear la base de datos y tablas usadas por MarketFlow
-- Ajusta usuario/contraseña si es necesario.

-- 1) Crear base de datos
CREATE DATABASE IF NOT EXISTS `marketflow_db`
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

-- 2) Crear usuario y dar permisos (crea para localhost y 127.0.0.1)
CREATE USER IF NOT EXISTS 'UserTest'@'127.0.0.1' IDENTIFIED BY 'marketflow';
CREATE USER IF NOT EXISTS 'UserTest'@'localhost' IDENTIFIED BY 'marketflow';
GRANT ALL PRIVILEGES ON `marketflow_db`.* TO 'UserTest'@'127.0.0.1';
GRANT ALL PRIVILEGES ON `marketflow_db`.* TO 'UserTest'@'localhost';
FLUSH PRIVILEGES;

-- 3) Usar la base creada
USE `marketflow_db`;

-- 4) Crear tabla `publicacion` (corresponde a modelo.Publicacion)
CREATE TABLE IF NOT EXISTS `publicacion` (
  `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `titulo` VARCHAR(255),
  `categoria` VARCHAR(50),
  `nombreDelProducto` VARCHAR(255),
  `precio` DOUBLE,
  `estado` VARCHAR(50),
  `descripcion` TEXT,
  `imagen` VARCHAR(512),
  `usuario` VARCHAR(100)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Índices sugeridos
CREATE INDEX IF NOT EXISTS `idx_publicacion_usuario` ON `publicacion` (`usuario`);
CREATE INDEX IF NOT EXISTS `idx_publicacion_categoria` ON `publicacion` (`categoria`);

-- 5) Crear tabla `mensaje` (corresponde a modelo.Mensaje)
CREATE TABLE IF NOT EXISTS `mensaje` (
  `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `remitente` VARCHAR(100),
  `destinatario` VARCHAR(100),
  `contenido` TEXT,
  `fechaDeEnvio` VARCHAR(50)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 6) Comprobación: insertar filas de ejemplo
INSERT INTO `publicacion` (`titulo`,`categoria`,`nombreDelProducto`,`precio`,`estado`,`descripcion`,`imagen`,`usuario`)
VALUES ('Ejemplo teléfono','ELECTRONICA','Smartphone Demo',199.99,'NUEVO','Descripción de ejemplo','/images/demo.jpg','usuarioDemo');

INSERT INTO `mensaje` (`remitente`,`destinatario`,`contenido`,`fechaDeEnvio`)
VALUES ('usuarioDemo','vendedorDemo','Hola, estoy interesado','2026-01-02T00:00:00');

-- 7) Consultas de verificación
-- SHOW TABLES;
-- DESCRIBE publicacion;
-- SELECT COUNT(*) FROM publicacion;

-- Fin del script
