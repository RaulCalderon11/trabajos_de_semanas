-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 10-10-2025 a las 20:00:58
-- Versión del servidor: 10.4.28-MariaDB
-- Versión de PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `producto`
--

INSERT INTO `producto` (`Id_producto`, `Nom_pro`, `Mar_pro`, `Tip_pro`, `Can_pro`, `Cad_pro`, `Pre_pro`) VALUES
('LBR01', 'Cien años de soledad', 'Gabriel Márques', 'Ficción', 20, 1967, 9),
('LBR02', 'El Principito', 'Antoine Saint', 'Ciencia Ficción', 10, 1943, 15),
('LBR03', 'De animales a dioses', 'Yuval Noah', 'Historia', 20, 2011, 30),
('LBR04', '1984', 'George Orwell', 'Biografía', 10, 1949, 12);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
