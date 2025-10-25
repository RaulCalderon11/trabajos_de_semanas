-- phpMyAdmin SQL Dump
-- version 4.9.1
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost
-- Tiempo de generación: 09-10-2025 a las 17:53:01
-- Versión del servidor: 8.0.17
-- Versión de PHP: 7.3.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `bdfarmacia`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE `cliente` (
  `Id_cliente` varchar(4) DEFAULT NULL,
  `Ape_cli` varchar(20) DEFAULT NULL,
  `Nom_cli` varchar(20) DEFAULT NULL,
  `Dir_cli` varchar(50) DEFAULT NULL,
  `Dis_cli` varchar(30) DEFAULT NULL,
  `Pro_cli` varchar(20) DEFAULT NULL,
  `Sex_cli` varchar(1) DEFAULT NULL,
  `Eda_cli` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `cliente`
--

INSERT INTO `cliente` (`Id_cliente`, `Ape_cli`, `Nom_cli`, `Dir_cli`, `Dis_cli`, `Pro_cli`, `Sex_cli`, `Eda_cli`) VALUES
('CL01', 'Guerra', 'Juan', 'Calle N°123', 'El Tambo', 'Huancayo', 'M', 19),
('CL02', 'Apumayta', 'Rosita', 'Calle Lima N°323', 'Chilca', 'Huancayo', 'F', 29);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--

CREATE TABLE `producto` (
  `Id_producto` varchar(4) DEFAULT NULL,
  `Nom_pro` varchar(20) DEFAULT NULL,
  `Mar_pro` varchar(20) DEFAULT NULL,
  `Tip_pro` varchar(20) DEFAULT NULL,
  `Can_pro` int(11) DEFAULT NULL,
  `Cad_pro` int(11) DEFAULT NULL,
  `Pre_pro` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `producto`
--

INSERT INTO `producto` (`Id_producto`, `Nom_pro`, `Mar_pro`, `Tip_pro`, `Can_pro`, `Cad_pro`, `Pre_pro`) VALUES
('PR01', 'Paracetamol 500Gr', 'Zunsac', 'Analgésico', 20, 2027, 0),
('PR02', 'Doloflan', 'Marsa', 'Para el Dolor', 10, 2030, 0);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
