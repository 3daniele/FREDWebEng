-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:8889
-- Generation Time: Sep 28, 2022 at 01:27 PM
-- Server version: 5.7.34
-- PHP Version: 7.4.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `FDDWebEng`
--
CREATE DATABASE IF NOT EXISTS `FDDWebEng` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `FDDWebEng`;

-- --------------------------------------------------------

--
-- Table structure for table `Artista`
--

CREATE TABLE IF NOT EXISTS `Artista` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `cognome` varchar(100) NOT NULL,
  `nomeDArte` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Artista`
--

INSERT INTO `Artista` (`id`, `nome`, `cognome`, `nomeDArte`) VALUES
(1, 'Nelson', 'Venceslai', 'Rovere'),
(2, 'Luciano', 'Ligabue', 'Ligabue'),
(3, 'Riccardo', 'Zanotti', 'Pinguini tattici nucleari'),
(4, 'Elisa', 'Toffoli', 'Elisa');

-- --------------------------------------------------------

--
-- Table structure for table `Canzone`
--

CREATE TABLE IF NOT EXISTS `Canzone` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `durata` time NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Canzone`
--

INSERT INTO `Canzone` (`id`, `nome`, `durata`) VALUES
(1, 'caccia militare', '00:03:11'),
(2, 'tadb', '00:03:09'),
(3, 'soli come a bologna', '00:03:22'),
(4, 'peter pan', '00:03:00'),
(5, 'sport', '00:03:46'),
(6, 'conforto', '00:02:09'),
(7, 'silezio', '00:03:28'),
(8, 'everest', '00:03:05'),
(9, 'primavera 80', '00:03:00'),
(10, 'la pioggia che non sapevo', '00:02:51'),
(11, '321... major tom', '00:00:59'),
(12, 'astronauta', '00:04:04'),
(13, 'la libertà', '00:03:31'),
(14, 'freddo cane', '00:03:08'),
(15, 'looney', '00:03:25'),
(16, 'lupo', '00:03:00'),
(17, 'dalla terra a marte', '00:03:40'),
(18, 'alveare', '00:03:20'),
(19, 'martedì', '00:03:42'),
(20, 'sentiero rapido', '00:04:14'),
(21, 'bim bum bam', '00:03:18'),
(22, 'mappamondo', '00:03:37'),
(23, 'crescere', '00:03:46'),
(24, 'precipitare', '00:05:13'),
(25, 'Giovani Wannabe', '00:03:33'),
(26, 'Dentista Croazia', '00:04:23'),
(27, 'Quando canterai la tua canzone', '00:03:33'),
(28, 'La linea sottile', '00:04:03'),
(29, 'Nel tempo', '00:03:47'),
(30, 'Ci sei sempre stata', '00:04:58'),
(31, 'La verità è una scelta', '00:04:17'),
(32, 'Caro il mio francesco', '00:05:58'),
(33, 'Atto di fede', '00:04:09'),
(34, 'Un colpo all\'anima', '00:03:22'),
(35, 'Il peso della valigia', '00:04:38'),
(36, 'Taca banda', '00:02:30'),
(37, 'Quando mi vieni a prendere', '00:07:05'),
(38, 'Il meglio deve ancora venire', '00:04:20');

-- --------------------------------------------------------

--
-- Table structure for table `Collezione`
--

CREATE TABLE IF NOT EXISTS `Collezione` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `condivisione` enum('pubblica','privata','condivisa') NOT NULL DEFAULT 'privata',
  `dataDiCreazione` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `utente` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `utente` (`utente`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Collezione`
--

INSERT INTO `Collezione` (`id`, `nome`, `condivisione`, `dataDiCreazione`, `utente`) VALUES
(1, 'Il meglio di Rovere', 'pubblica', '2022-08-12 09:04:05', 1),
(3, 'Nuovi pinguini', 'pubblica', '2022-08-25 14:28:08', 2),
(8, 'Antonio\'s collection', 'pubblica', '2022-09-07 10:55:43', 3),
(11, 'My collection', 'privata', '2022-09-17 12:12:13', 3),
(12, 'Prova', 'privata', '2022-09-27 13:55:52', 1);

-- --------------------------------------------------------

--
-- Table structure for table `Disco`
--

CREATE TABLE IF NOT EXISTS `Disco` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `etichetta` varchar(50) NOT NULL,
  `anno` date NOT NULL,
  `artista` int(11) NOT NULL,
  `creatore` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `artista` (`artista`),
  KEY `creatore` (`creatore`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Disco`
--

INSERT INTO `Disco` (`id`, `nome`, `etichetta`, `anno`, `artista`, `creatore`) VALUES
(1, 'Disponibile in mogano', 'Sony Music', '2019-03-29', 1, 2),
(2, 'Dalla terra a Marte', 'Sony Music', '2022-02-18', 1, 2),
(3, 'Dentista Croazia', 'Sony Music', '2022-08-24', 3, 2),
(4, 'Giovani Wannabe', 'Sony Music', '2022-06-15', 3, 2),
(5, 'Arrivederci mostro', 'Warner music', '2011-05-11', 2, 2);

-- --------------------------------------------------------

--
-- Table structure for table `Genere`
--

CREATE TABLE IF NOT EXISTS `Genere` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(150) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=127 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Genere`
--

INSERT INTO `Genere` (`id`, `nome`) VALUES
(1, 'acoustic'),
(2, 'afrobeat'),
(3, 'alt-rock'),
(4, 'alternative'),
(5, 'ambient'),
(6, 'anime'),
(7, 'black-metal'),
(8, 'bluegrass'),
(9, 'blues'),
(10, 'bossanova'),
(11, 'brazil'),
(12, 'breakbeat'),
(13, 'british'),
(14, 'cantopop'),
(15, 'chicago-house'),
(16, 'children'),
(17, 'chill'),
(18, 'classical'),
(19, 'club'),
(20, 'comedy'),
(21, 'country'),
(22, 'dance'),
(23, 'dancehall'),
(24, 'death-metal'),
(25, 'deep-house'),
(26, 'detroit-techno'),
(27, 'disco'),
(28, 'disney'),
(29, 'drum-and-bass'),
(30, 'dub'),
(31, 'dubstep'),
(32, 'edm'),
(33, 'electro'),
(34, 'electronic'),
(35, 'emo'),
(36, 'folk'),
(37, 'forro'),
(38, 'french'),
(39, 'funk'),
(40, 'garage'),
(41, 'german'),
(42, 'gospel'),
(43, 'goth'),
(44, 'grindcore'),
(45, 'groove'),
(46, 'grunge'),
(47, 'guitar'),
(48, 'happy'),
(49, 'hard-rock'),
(50, 'hardcore'),
(51, 'hardstyle'),
(52, 'heavy-metal'),
(53, 'hip-hop'),
(54, 'holidays'),
(55, 'honky-tonk'),
(56, 'house'),
(57, 'idm'),
(58, 'indian'),
(59, 'indie'),
(60, 'indie-pop'),
(61, 'industrial'),
(62, 'iranian'),
(63, 'j-dance'),
(64, 'j-idol'),
(65, 'j-pop'),
(66, 'j-rock'),
(67, 'jazz'),
(68, 'k-pop'),
(69, 'kids'),
(70, 'latin'),
(71, 'latino'),
(72, 'malay'),
(73, 'mandopop'),
(74, 'metal'),
(75, 'metal-misc'),
(76, 'metalcore'),
(77, 'minimal-techno'),
(78, 'movies'),
(79, 'mpb'),
(80, 'new-age'),
(81, 'new-release'),
(82, 'opera'),
(83, 'pagode'),
(84, 'party'),
(85, 'philippines-opm'),
(86, 'piano'),
(87, 'pop'),
(88, 'pop-film'),
(89, 'post-dubstep'),
(90, 'power-pop'),
(91, 'progressive-house'),
(92, 'psych-rock'),
(93, 'punk'),
(94, 'punk-rock'),
(95, 'r-n-b'),
(96, 'rainy-day'),
(97, 'reggae'),
(98, 'reggaeton'),
(99, 'road-trip'),
(100, 'rock'),
(101, 'rock-n-roll'),
(102, 'rockabilly'),
(103, 'romance'),
(104, 'sad'),
(105, 'salsa'),
(106, 'samba'),
(107, 'sertanejo'),
(108, 'show-tunes'),
(109, 'singer-songwriter'),
(110, 'ska'),
(111, 'sleep'),
(112, 'songwriter'),
(113, 'soul'),
(114, 'soundtracks'),
(115, 'spanish'),
(116, 'study'),
(117, 'summer'),
(118, 'swedish'),
(119, 'synth-pop'),
(120, 'tango'),
(121, 'techno'),
(122, 'trance'),
(123, 'trip-hop'),
(124, 'turkish'),
(125, 'work-out'),
(126, 'world-music');

-- --------------------------------------------------------

--
-- Table structure for table `ListaArtisti`
--

CREATE TABLE IF NOT EXISTS `ListaArtisti` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `artista` int(11) NOT NULL,
  `canzone` int(11) NOT NULL,
  `ruolo` enum('Compositore','Musicista','Entrambi') NOT NULL,
  PRIMARY KEY (`id`),
  KEY `artista` (`artista`),
  KEY `canzone` (`canzone`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `ListaArtisti`
--

INSERT INTO `ListaArtisti` (`id`, `artista`, `canzone`, `ruolo`) VALUES
(1, 1, 1, 'Entrambi'),
(2, 1, 2, 'Entrambi'),
(3, 1, 3, 'Entrambi'),
(4, 1, 4, 'Entrambi'),
(5, 1, 5, 'Entrambi'),
(6, 1, 6, 'Entrambi'),
(7, 1, 7, 'Entrambi'),
(8, 1, 8, 'Entrambi'),
(9, 1, 9, 'Entrambi'),
(10, 1, 10, 'Entrambi'),
(11, 1, 11, 'Entrambi'),
(12, 1, 12, 'Entrambi'),
(13, 1, 13, 'Entrambi'),
(14, 1, 14, 'Entrambi'),
(15, 1, 15, 'Entrambi'),
(16, 1, 16, 'Entrambi'),
(17, 1, 17, 'Entrambi'),
(18, 1, 18, 'Entrambi'),
(19, 1, 19, 'Entrambi'),
(20, 1, 20, 'Entrambi'),
(21, 1, 21, 'Entrambi'),
(22, 1, 22, 'Entrambi'),
(23, 1, 23, 'Entrambi'),
(24, 1, 24, 'Entrambi'),
(25, 3, 25, 'Entrambi'),
(26, 3, 26, 'Entrambi'),
(27, 2, 27, 'Entrambi'),
(28, 2, 28, 'Entrambi'),
(29, 2, 29, 'Entrambi'),
(30, 2, 30, 'Entrambi'),
(31, 2, 31, 'Entrambi'),
(32, 2, 32, 'Entrambi'),
(33, 2, 33, 'Entrambi'),
(34, 2, 34, 'Entrambi'),
(35, 2, 35, 'Entrambi'),
(36, 2, 36, 'Entrambi'),
(37, 2, 37, 'Entrambi'),
(38, 2, 38, 'Entrambi'),
(39, 4, 27, 'Musicista'),
(40, 4, 37, 'Musicista'),
(41, 4, 32, 'Musicista'),
(42, 3, 32, 'Musicista');

-- --------------------------------------------------------

--
-- Table structure for table `ListaBrani`
--

CREATE TABLE IF NOT EXISTS `ListaBrani` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `disco` int(11) NOT NULL,
  `canzone` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `canzone` (`canzone`),
  KEY `disco` (`disco`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `ListaBrani`
--

INSERT INTO `ListaBrani` (`id`, `disco`, `canzone`) VALUES
(1, 1, 1),
(2, 1, 2),
(3, 1, 3),
(4, 1, 4),
(5, 1, 5),
(6, 1, 6),
(7, 1, 7),
(8, 1, 8),
(9, 1, 9),
(10, 1, 10),
(11, 2, 11),
(12, 2, 12),
(13, 2, 13),
(14, 2, 14),
(15, 2, 15),
(16, 2, 16),
(17, 2, 17),
(18, 2, 18),
(19, 2, 19),
(20, 2, 20),
(21, 2, 21),
(22, 2, 22),
(23, 2, 23),
(24, 2, 24),
(25, 3, 26),
(26, 4, 25),
(27, 5, 27),
(28, 5, 28),
(29, 5, 29),
(30, 5, 30),
(31, 5, 31),
(32, 5, 32),
(33, 5, 33),
(34, 5, 34),
(35, 5, 35),
(36, 5, 36),
(37, 5, 37),
(38, 5, 38);

-- --------------------------------------------------------

--
-- Table structure for table `ListaDischi`
--

CREATE TABLE IF NOT EXISTS `ListaDischi` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `collezione` int(11) NOT NULL,
  `disco` int(11) NOT NULL,
  `numeroCopie` int(11) NOT NULL,
  `Stato` enum('Ottimo','Buono','Discreto','Sufficiente','Pessimo') NOT NULL,
  `formato` enum('Vinile','CD','Digitale','Cassetta','Altro') NOT NULL,
  `barcode` varchar(11) DEFAULT NULL,
  `imgCopertina` text,
  `imgFronte` text,
  `imgRetro` text,
  `imgLibretto` text,
  PRIMARY KEY (`id`),
  UNIQUE KEY `barcode` (`barcode`),
  KEY `collezione` (`collezione`),
  KEY `disco` (`disco`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `ListaDischi`
--

INSERT INTO `ListaDischi` (`id`, `collezione`, `disco`, `numeroCopie`, `Stato`, `formato`, `barcode`, `imgCopertina`, `imgFronte`, `imgRetro`, `imgLibretto`) VALUES
(1, 1, 2, 5, 'Ottimo', 'Vinile', '0213456789', 'https://m.media-amazon.com/images/I/91iHBQuDJ-L._AC_SY450_.jpg', '', '', ''),
(2, 1, 1, 8, 'Ottimo', 'CD', '0123456789', 'https://is3-ssl.mzstatic.com/image/thumb/Music113/v4/f2/68/aa/f268aa06-1da7-84e9-f7eb-384b9fbc7f27/886447616475.jpg/600x600bf-60.jpg', '', '', ''),
(5, 8, 5, 25, 'Discreto', 'CD', NULL, 'https://m.media-amazon.com/images/I/51f2020w9rL._AC_SX466_.jpg', NULL, 'https://m.media-amazon.com/images/I/6169scxiiHL._AC_SX466_.jpg', 'https://www.picclickimg.com/d/l400/pict/172933603868_/Luciano-Ligabue-%E2%80%8E–-Arrivederci-Mostro-vinile-blu-sigillato.jpg'),
(6, 8, 1, 12, 'Ottimo', 'Digitale', NULL, 'https://www.diregiovani.it/wp-content/uploads/2019/04/Rovere-2-no-scritta.jpeg', NULL, NULL, NULL),
(7, 8, 2, 2, 'Discreto', 'CD', NULL, 'https://www.angolotesti.it/cover/297286.jpg', NULL, 'https://images.genius.com/5daa166c35d4c8d7d1978bc230e49cb2.1000x1000x1.jpg', NULL),
(8, 3, 3, 2, 'Ottimo', 'Digitale', NULL, 'https://i.ytimg.com/vi/KfaEg4bfx_A/hqdefault.jpg', NULL, NULL, NULL),
(9, 3, 4, 1, 'Sufficiente', 'Vinile', NULL, 'https://i.ytimg.com/vi/_8FRkoqKVhk/maxresdefault.jpg', NULL, NULL, NULL),
(10, 11, 1, 1, 'Ottimo', 'Vinile', NULL, 'https://i.scdn.co/image/ab67616d0000b2736d8c3e309ec1c8c9f9e827cd', NULL, 'https://images.genius.com/6f459c8504e7794da54a2cc4b4ec49d8.520x520x1.jpg', NULL),
(11, 11, 2, 1, 'Ottimo', 'Vinile', NULL, 'https://i.discogs.com/3i683fZphgglkY6uQDDhg85fYYxjdo-C0Hc1Z2AdPJg/rs:fit/g:sm/q:90/h:600/w:577/czM6Ly9kaXNjb2dz/LWRhdGFiYXNlLWlt/YWdlcy9SLTIyMzU5/MjcxLTE2NDYyMzUw/MjgtMzMyMC5qcGVn.jpeg', NULL, 'https://images.genius.com/5daa166c35d4c8d7d1978bc230e49cb2.1000x1000x1.jpg', NULL),
(12, 11, 3, 1, 'Ottimo', 'Digitale', NULL, 'https://i.ytimg.com/vi/KfaEg4bfx_A/hqdefault.jpg', NULL, NULL, NULL),
(13, 11, 4, 1, 'Ottimo', 'Digitale', NULL, 'https://binaries.radioitalia.it/2022/06/06/102108393-6e304f53-db11-4bb7-b10b-31874facef80.jpg', NULL, NULL, NULL),
(14, 11, 5, 1, 'Ottimo', 'Vinile', NULL, 'https://m.media-amazon.com/images/I/51f2020w9rL._AC_SX466_.jpg', NULL, 'https://m.media-amazon.com/images/I/6169scxiiHL._AC_SX466_.jpg', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `ListaGeneri`
--

CREATE TABLE IF NOT EXISTS `ListaGeneri` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `canzone` int(11) NOT NULL,
  `genere` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `canzone` (`canzone`),
  KEY `genere` (`genere`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `ListaGeneri`
--

INSERT INTO `ListaGeneri` (`id`, `canzone`, `genere`) VALUES
(1, 1, 60),
(2, 2, 60),
(3, 3, 60),
(4, 4, 60),
(5, 5, 60),
(6, 6, 60),
(7, 7, 60),
(8, 8, 60),
(9, 9, 60),
(10, 10, 60),
(11, 11, 60),
(12, 12, 60),
(13, 13, 60),
(14, 14, 60),
(15, 15, 60),
(16, 16, 60),
(17, 17, 60),
(18, 18, 60),
(19, 19, 60),
(20, 20, 60),
(21, 21, 60),
(22, 22, 60),
(23, 23, 60),
(24, 24, 60),
(25, 25, 60),
(26, 26, 60),
(27, 27, 100),
(28, 28, 100),
(29, 29, 100),
(30, 30, 100),
(31, 31, 100),
(32, 32, 100),
(33, 33, 100),
(34, 34, 100),
(35, 35, 100),
(36, 36, 100),
(37, 37, 100),
(38, 38, 100),
(39, 28, 15),
(40, 33, 15),
(41, 30, 15);

-- --------------------------------------------------------

--
-- Table structure for table `Utente`
--

CREATE TABLE IF NOT EXISTS `Utente` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nickname` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(500) NOT NULL,
  `nome` varchar(50) DEFAULT NULL,
  `cognome` varchar(50) DEFAULT NULL,
  `token` tinyint(1) NOT NULL DEFAULT '0',
  `link` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nickname` (`nickname`,`email`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Utente`
--

INSERT INTO `Utente` (`id`, `nickname`, `email`, `password`, `nome`, `cognome`, `token`, `link`) VALUES
(1, 'user', 'utente@utente.it', '$2a$12$bvRMGZxAa0r4mW/8yCH1HOq9kfuNYWLjlBwc5SR/beauhTV4P5zz2', 'Utente', 'Utente', 1, NULL),
(2, 'gestore', 'gestore@gestore.it', '$2a$12$WarrbFzzniyiEv26H2gZVe.RTFStpAHdGuyBA.EiYj2ANS.vDxsoi', 'Gestore', 'Gestore', 1, NULL),
(3, 'antonio', 'antonio@site.it', '$2a$10$pTeOgmfUIdNwML4PvKHfxOxaetREbug8rQxANjn9P87aWhOpRjbI6', 'Antonio', 'De Amicis', 1, NULL),
(4, 'danieledd', 'daniele@danieledd.it', '$2a$10$E1vFy59Mx9bzbnNOYpW2mu69faBol7IwgP1jJaOEiIRopbV0sqLty', 'Daniele', 'Di Desidero', 1, NULL),
(5, 'test', 'test@test.it', '$2a$10$vdhVv7S/KxmawTmFbKbXUO4h5tXOV0YUrfggw.TZN8FSQz/hS2BWq', 'test', 'test', 1, '$2a$10$NgxIzMlykJH61CxMLmR0pOEhRBbuqAYCxcpWsjr8eO/jmMlg3eDDe');

-- --------------------------------------------------------

--
-- Table structure for table `UtentiAutorizzati`
--

CREATE TABLE IF NOT EXISTS `UtentiAutorizzati` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `collezione` int(11) NOT NULL,
  `utente` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `collezione` (`collezione`),
  KEY `utente` (`utente`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `UtentiAutorizzati`
--

INSERT INTO `UtentiAutorizzati` (`id`, `collezione`, `utente`) VALUES
(3, 3, 1),
(6, 8, 2),
(7, 12, 5);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `Collezione`
--
ALTER TABLE `Collezione`
  ADD CONSTRAINT `collezione_ibfk_1` FOREIGN KEY (`utente`) REFERENCES `Utente` (`id`);

--
-- Constraints for table `Disco`
--
ALTER TABLE `Disco`
  ADD CONSTRAINT `disco_ibfk_1` FOREIGN KEY (`artista`) REFERENCES `Artista` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `disco_ibfk_2` FOREIGN KEY (`creatore`) REFERENCES `Utente` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `ListaArtisti`
--
ALTER TABLE `ListaArtisti`
  ADD CONSTRAINT `listaartisti_ibfk_1` FOREIGN KEY (`artista`) REFERENCES `Artista` (`id`),
  ADD CONSTRAINT `listaartisti_ibfk_2` FOREIGN KEY (`canzone`) REFERENCES `Canzone` (`id`);

--
-- Constraints for table `ListaBrani`
--
ALTER TABLE `ListaBrani`
  ADD CONSTRAINT `listabrani_ibfk_1` FOREIGN KEY (`canzone`) REFERENCES `Canzone` (`id`),
  ADD CONSTRAINT `listabrani_ibfk_2` FOREIGN KEY (`disco`) REFERENCES `Disco` (`id`);

--
-- Constraints for table `ListaDischi`
--
ALTER TABLE `ListaDischi`
  ADD CONSTRAINT `listadischi_ibfk_1` FOREIGN KEY (`collezione`) REFERENCES `Collezione` (`id`),
  ADD CONSTRAINT `listadischi_ibfk_2` FOREIGN KEY (`disco`) REFERENCES `Disco` (`id`);

--
-- Constraints for table `ListaGeneri`
--
ALTER TABLE `ListaGeneri`
  ADD CONSTRAINT `listageneri_ibfk_1` FOREIGN KEY (`canzone`) REFERENCES `Canzone` (`id`),
  ADD CONSTRAINT `listageneri_ibfk_2` FOREIGN KEY (`genere`) REFERENCES `Genere` (`id`);

--
-- Constraints for table `UtentiAutorizzati`
--
ALTER TABLE `UtentiAutorizzati`
  ADD CONSTRAINT `utentiautorizzati_ibfk_1` FOREIGN KEY (`collezione`) REFERENCES `Collezione` (`id`),
  ADD CONSTRAINT `utentiautorizzati_ibfk_2` FOREIGN KEY (`utente`) REFERENCES `Utente` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
