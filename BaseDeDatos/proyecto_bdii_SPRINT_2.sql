-- phpMyAdmin SQL Dump
-- version 5.1.2
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost:3306
-- Tiempo de generación: 26-11-2025 a las 03:43:29
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
  `IDCLIENTE` varchar(10) NOT NULL,
  `NOMBRECLIENTE` varchar(100) DEFAULT NULL,
  `APELLIDOCLIENTE` varchar(100) DEFAULT NULL,
  `TELEFONO` varchar(10) DEFAULT NULL,
  `DIRECCION` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `cliente`
--

INSERT INTO `cliente` (`IDCLIENTE`, `NOMBRECLIENTE`, `APELLIDOCLIENTE`, `TELEFONO`, `DIRECCION`) VALUES
('C1', 'ANA MARÍA', 'SANDOVAL ESQUIVEL', '9712478658', 'AV. JOAQUIN AMARO #56 COLONIA SAN PEDRO, CD. IXTEPEC OAXACA'),
('C2', 'LUIS AMARO', 'FELIX CASTELLANO', '9711224659', 'CALLE VICENTE GUERRERO #40 COLONIA ESTACIÓN, CD. IXTEPEC OAXACA'),
('C3', 'GUILLERMO', 'HERRERA LLANEZ', '9711456790', 'UNIDAD MILITAR º15, LOTE 82 Nº 25, CD. JUAREZ'),
('C4', 'FELIPE', 'ENRIQUEZ GUESS', '9711265987', 'AV. MORELOS #47 COLONIA MODERNA, JUCHITAN DE ZARAGOZA OAXACA '),
('C5', 'MIRELLA', 'GOMEZ LOPEZ', '9711436848', 'AV. 16 SEPTIEMBRE #46 COLONIA NOGALES CD. OBREGON'),
('C6', 'JOSÉ ANTONIO', 'CARRASCO HERNANDEZ', '9711564763', 'AV. FRANCISCO I. MADERO #47'),
('C7', 'CARLOS', 'BENITEZ GONZALES', '9711267890', 'CALLE NICOLAS BRAVO #57, COLONIA LAURELES, CD. JUAREZ');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `compra`
--

CREATE TABLE `compra` (
  `FK_IDVENTA` varchar(2) NOT NULL,
  `FK_IDCLIENTE` varchar(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalleventa`
--

CREATE TABLE `detalleventa` (
  `IDDETALLE` varchar(25) NOT NULL,
  `IDVENTA` varchar(15) DEFAULT NULL,
  `IDPRODUCTO` varchar(10) DEFAULT NULL,
  `CANTIDAD` int(11) NOT NULL,
  `PRECIO_UNITARIO` decimal(10,2) DEFAULT NULL,
  `SUBTOTAL` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `detalleventa`
--

INSERT INTO `detalleventa` (`IDDETALLE`, `IDVENTA`, `IDPRODUCTO`, `CANTIDAD`, `PRECIO_UNITARIO`, `SUBTOTAL`) VALUES
('D1-V1', 'V1', 'P6', 5, '997.00', 4985),
('D1-V2', 'V2', 'P4', 5, '32000.00', 160000),
('D1-V3', 'V3', 'P6', 3, '997.00', 2991),
('D1-V4', 'V4', 'P3', 6, '17599.00', 105594),
('D1-V5', 'V5', 'P5', 6, '2500.00', 15000),
('D2-V2', 'V2', 'P3', 5, '17599.00', 87995),
('D2-V3', 'V3', 'P6', 1, '997.00', 997),
('D2-V4', 'V4', 'P3', 3, '17599.00', 52797);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `incluye`
--

CREATE TABLE `incluye` (
  `FK_IDPRODUCTO` varchar(2) NOT NULL,
  `FK_IDVENTA` varchar(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--

CREATE TABLE `producto` (
  `IDPRODUCTO` varchar(10) NOT NULL,
  `NOMBREPRODUCTO` varchar(100) DEFAULT NULL,
  `DESCRIPCION` varchar(100) DEFAULT NULL,
  `PRECIO` decimal(10,2) DEFAULT NULL,
  `STOCK` int(11) DEFAULT NULL,
  `CODIGOBARRAS` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `producto`
--

INSERT INTO `producto` (`IDPRODUCTO`, `NOMBREPRODUCTO`, `DESCRIPCION`, `PRECIO`, `STOCK`, `CODIGOBARRAS`) VALUES
('P1', 'MONITOR LG', '32\'\' PANEL IPS 4K', '7000.00', 15, NULL),
('P2', 'MOUSE AJAZZ', 'INALAMBRICO 2.4Gz', '500.00', 199, NULL),
('P3', 'LAPTOP LENOVO', '16GB RAM, 512GB SSD, 15\'\' COLOR NEGRO', '17599.00', 1, NULL),
('P4', 'GOOGLE PIXEL 10PRO XL', 'COLOR BLANCO, 16GB RAM, 1TB MEMORIA INTERNA', '32000.00', 20, NULL),
('P5', 'TECLADO RED DRAGON', 'MECANICO, GAMER, COLOR NEGRO, RGB, ALAMBRICO', '2500.00', 4, NULL),
('P6', 'AUDIFONOS JBL', 'INALAMBRICOS, NEGROS, CANCELACION RUIDO', '997.00', 1, NULL),
('P7', 'IMPRESORA HP', 'FUNCIONES: IMPRESION, ESCANEO Y COPIADO. COLOR NEGRO', '17679.00', 34, NULL),
('P8', 'MINI PC ACEMAGIC', 'RYZEN 9 9240, 32GB RAM, 1TR SSD', '19599.00', 20, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `venta`
--

CREATE TABLE `venta` (
  `IDVENTA` varchar(15) NOT NULL,
  `FECHAVENTA` date DEFAULT NULL,
  `TOTAL` decimal(10,2) DEFAULT NULL,
  `IDCLIENTE` varchar(15) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `venta`
--

INSERT INTO `venta` (`IDVENTA`, `FECHAVENTA`, `TOTAL`, `IDCLIENTE`) VALUES
('V1', '2025-11-25', '4985.00', NULL),
('V2', '2025-11-25', '247995.00', NULL),
('V3', '2025-11-25', '3988.00', NULL),
('V4', '2025-11-25', '158391.00', NULL),
('V5', '2025-11-25', '15000.00', NULL);

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
-- Indices de la tabla `detalleventa`
--
ALTER TABLE `detalleventa`
  ADD PRIMARY KEY (`IDDETALLE`),
  ADD KEY `IDVENTA` (`IDVENTA`),
  ADD KEY `IDPRODUCTO` (`IDPRODUCTO`);

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
  ADD PRIMARY KEY (`IDPRODUCTO`),
  ADD UNIQUE KEY `CODIGOBARRAS` (`CODIGOBARRAS`);

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
-- Filtros para la tabla `detalleventa`
--
ALTER TABLE `detalleventa`
  ADD CONSTRAINT `detalleventa_ibfk_1` FOREIGN KEY (`IDVENTA`) REFERENCES `venta` (`IDVENTA`),
  ADD CONSTRAINT `detalleventa_ibfk_2` FOREIGN KEY (`IDPRODUCTO`) REFERENCES `producto` (`IDPRODUCTO`);

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
