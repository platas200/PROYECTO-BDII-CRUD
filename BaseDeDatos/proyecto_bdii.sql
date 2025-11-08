-- phpMyAdmin SQL Dump
-- version 5.1.2
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost:3306
-- Tiempo de generación: 08-11-2025 a las 14:45:20
-- Versión del servidor: 5.7.24
-- Versión de PHP: 8.3.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `proyecto_bdii`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE `cliente` (
  `IDCLIENTE` varchar(2) NOT NULL,
  `NOMBRECLIENTE` varchar(100) DEFAULT NULL,
  `APELLIDOCLIENTE` varchar(100) DEFAULT NULL,
  `TELEFONO` varchar(10) DEFAULT NULL,
  `DIRECCION` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `cliente`
--

INSERT INTO `cliente` (`IDCLIENTE`, `NOMBRECLIENTE`, `APELLIDOCLIENTE`, `TELEFONO`, `DIRECCION`) VALUES
('C1', 'Ana María', 'González', '5551234567', 'Calle 10, Oaxaca'),
('C2', 'Luis Angel', 'Perez Gomez', '9711224659', 'Col. Laureles'),
('C3', 'José', 'Espinoza Gutierrez', '9711456790', 'Av. 16 Septiembre');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `compra`
--

CREATE TABLE `compra` (
  `FK_IDVENTA` varchar(2) NOT NULL,
  `FK_IDCLIENTE` varchar(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `compra`
--

INSERT INTO `compra` (`FK_IDVENTA`, `FK_IDCLIENTE`) VALUES
('V1', 'C1'),
('V2', 'C2');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `incluye`
--

CREATE TABLE `incluye` (
  `FK_IDPRODUCTO` varchar(2) NOT NULL,
  `FK_IDVENTA` varchar(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `incluye`
--

INSERT INTO `incluye` (`FK_IDPRODUCTO`, `FK_IDVENTA`) VALUES
('P1', 'V1');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--

CREATE TABLE `producto` (
  `IDPRODUCTO` varchar(2) NOT NULL,
  `NOMBREPRODUCTO` varchar(100) DEFAULT NULL,
  `DESCRIPCION` varchar(100) DEFAULT NULL,
  `PRECIO` decimal(10,2) DEFAULT NULL,
  `STOCK` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `producto`
--

INSERT INTO `producto` (`IDPRODUCTO`, `NOMBREPRODUCTO`, `DESCRIPCION`, `PRECIO`, `STOCK`) VALUES
('P1', 'Laptop HP', 'intel i5, 8GB RAM, SSD 256GB', '13500.00', 15);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `venta`
--

CREATE TABLE `venta` (
  `IDVENTA` varchar(2) NOT NULL,
  `FECHAVENTA` date DEFAULT NULL,
  `TOTAL` decimal(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `venta`
--

INSERT INTO `venta` (`IDVENTA`, `FECHAVENTA`, `TOTAL`) VALUES
('V1', '2025-10-13', '349.99'),
('V2', '2024-11-08', '799.99'),
('V3', '2025-08-15', '5000.00');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`IDCLIENTE`);

--
-- Indices de la tabla `compra`
--
ALTER TABLE `compra`
  ADD PRIMARY KEY (`FK_IDVENTA`,`FK_IDCLIENTE`),
  ADD KEY `COMPRA_FK_IDCLIENTE` (`FK_IDCLIENTE`);

--
-- Indices de la tabla `incluye`
--
ALTER TABLE `incluye`
  ADD PRIMARY KEY (`FK_IDPRODUCTO`,`FK_IDVENTA`),
  ADD KEY `INCLUYE_FK_IDVENTA` (`FK_IDVENTA`);

--
-- Indices de la tabla `producto`
--
ALTER TABLE `producto`
  ADD PRIMARY KEY (`IDPRODUCTO`);

--
-- Indices de la tabla `venta`
--
ALTER TABLE `venta`
  ADD PRIMARY KEY (`IDVENTA`);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `compra`
--
ALTER TABLE `compra`
  ADD CONSTRAINT `COMPRA_FK_IDCLIENTE` FOREIGN KEY (`FK_IDCLIENTE`) REFERENCES `cliente` (`IDCLIENTE`) ON DELETE CASCADE,
  ADD CONSTRAINT `COMPRA_FK_IDVENTA` FOREIGN KEY (`FK_IDVENTA`) REFERENCES `venta` (`IDVENTA`) ON DELETE CASCADE;

--
-- Filtros para la tabla `incluye`
--
ALTER TABLE `incluye`
  ADD CONSTRAINT `INCLUYE_FK_IDPRODUCTO` FOREIGN KEY (`FK_IDPRODUCTO`) REFERENCES `producto` (`IDPRODUCTO`) ON DELETE CASCADE,
  ADD CONSTRAINT `INCLUYE_FK_IDVENTA` FOREIGN KEY (`FK_IDVENTA`) REFERENCES `venta` (`IDVENTA`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
