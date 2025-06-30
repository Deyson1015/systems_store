/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

CREATE DATABASE IF NOT EXISTS `system_store` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `system_store`;

CREATE TABLE IF NOT EXISTS `marcas` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `marcas` (`id`, `nombre`) VALUES
	(1, 'SAMSUNG'),
	(2, 'APPLE'),
	(3, 'XIAOMI'),
	(4, 'OPPO'),
	(5, 'VIVO'),
	(6, 'HONOR'),
	(7, 'HUAWEI');

CREATE TABLE IF NOT EXISTS `productos` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `id_marca` int NOT NULL,
  `descripcion` text,
  `cantidad` int NOT NULL,
  `precio` double NOT NULL,
  `fecha_ingreso` date NOT NULL,
  `foto` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_productos_marcas` (`id_marca`),
  CONSTRAINT `FK_productos_marcas` FOREIGN KEY (`id_marca`) REFERENCES `marcas` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `productos` (`id`, `nombre`, `id_marca`, `descripcion`, `cantidad`, `precio`, `fecha_ingreso`, `foto`) VALUES
	(2, 'IPHONE 16', 2, 'PA RICOS', 6, 10000, '2024-06-13', 'honor x6.jpg'),
	(3, 'XIOMI 12', 3, 'PA POBRES', 1, 80000, '2025-06-01', 'xiaomi 12.jpg'),
	(4, 'HONR FZ', 6, 'PA MEROS POBRES', 7, 1000, '2025-06-02', 'honor x6.jpg'),
	(5, 'HUAWEI', 7, 'VAMOS SUBIENDO EL LVL', 7, 30000, '2025-05-05', 'huawei nova.jpg'),
	(6, 'SAMSUNG 1', 1, NULL, 1, 27677, '2025-09-03', 'samsung s24 ultra.jpg'),
	(7, 'IPHONE 15', 2, 'MELO', 6, 150000, '2024-06-13', 'iphone 15.jpg'),
	(8, 'HONOR PA POBRES', 6, 'POBRE', 3, 122222, '2026-06-09', 'honor x6.jpg'),
	(9, 'VIVO ABURRIDO', 5, NULL, 9, 1000044, '2025-08-12', 'huawei nova.jpg'),
	(10, 'SAMSUNG 18', 1, NULL, 123, 900000, '2025-12-01', 'samsung a35.jpg'),
	(11, 'OPPO 64 PRO MAX TEMU', 4, NULL, 3, 650000, '2034-06-09', 'oppo reno.jpg'),
	(12, 'IPHONE 64', 2, '@', 9, 1e25, '2031-06-13', 'huawei nova.jpg');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
