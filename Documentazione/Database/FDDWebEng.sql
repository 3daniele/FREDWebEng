-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:8889
-- Creato il: Ott 12, 2022 alle 11:00
-- Versione del server: 5.7.34
-- Versione PHP: 7.4.21

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

/* ----- CREATE TABLE ----- */

CREATE TABLE IF NOT EXISTS `Artista` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `cognome` varchar(100) NOT NULL,
  `nomeDArte` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nomeDArte` (`nomeDArte`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `Canzone` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `durata` time NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `Collezione` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `condivisione` enum('pubblica','privata') NOT NULL DEFAULT 'privata',
  `dataDiCreazione` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `utente` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `utente` (`utente`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `Genere` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(150) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=127 DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `ListaArtisti` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `artista` int(11) NOT NULL,
  `canzone` int(11) NOT NULL,
  `ruolo` enum('Compositore','Musicista','Entrambi') NOT NULL,
  PRIMARY KEY (`id`),
  KEY `artista` (`artista`),
  KEY `canzone` (`canzone`),
  UNIQUE KEY `artistaP` (`artista`,`canzone`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `ListaBrani` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `disco` int(11) NOT NULL,
  `canzone` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `canzone` (`canzone`),
  KEY `disco` (`disco`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;

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
  KEY `collezione` (`collezione`),
  KEY `disco` (`disco`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `ListaGeneri` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `canzone` int(11) NOT NULL,
  `genere` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `canzone` (`canzone`),
  KEY `genere` (`genere`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8;

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

CREATE TABLE IF NOT EXISTS `UtentiAutorizzati` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `collezione` int(11) NOT NULL,
  `utente` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `collezione` (`collezione`),
  KEY `utente` (`utente`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

/* ----- TRUNCATE TABLE ----- */

TRUNCATE TABLE `Artista`;

TRUNCATE TABLE `Canzone`;

TRUNCATE TABLE `Collezione`;

TRUNCATE TABLE `Disco`;

TRUNCATE TABLE `Genere`;

TRUNCATE TABLE `ListaArtisti`;

TRUNCATE TABLE `ListaBrani`;

TRUNCATE TABLE `ListaDischi`;

TRUNCATE TABLE `ListaGeneri`;

TRUNCATE TABLE `Utente`;

TRUNCATE TABLE `UtentiAutorizzati`;


/* ----- INSERT ----- */

INSERT INTO `Artista` (`id`, `nome`, `cognome`, `nomeDArte`) VALUES
(1, 'Nelson', 'Venceslai', 'Rovere'),
(2, 'Luciano', 'Ligabue', 'Ligabue'),
(3, 'Riccardo', 'Zanotti', 'Pinguini tattici nucleari'),
(4, 'Elisa', 'Toffoli', 'Elisa'),
(5, 'Achille', 'Lauro', 'Achille Lauro'),
(6, 'Alessandra ', 'Amoroso', 'Alessandra Amoroso'),
(7, 'Biagio', 'Antonacci', 'Biagio Antonacci'),
(8, 'Federico', 'Lucia', 'Fedez'),
(9, 'Gianna ', 'Nannini', 'Gianna Nannini'),
(10, 'Stefano', 'Belisari', 'Elio e le Storie Tese '),
(11, 'Francesca', 'Michielin', 'Francesca Michielin'),
(12, 'Alessandro', 'Aleotti', 'J-Ax'),
(13, 'Dargen', 'D\'Amico', 'Dargen D\'Amico'),
(14, 'Cosimo', 'Fini', 'Gue Pequeno');

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
(26, 'Dentista Croazia', '07:04:23'),
(27, 'Quando canterai la tua canzone', '07:03:33'),
(28, 'La linea sottile', '07:04:03'),
(29, 'Nel tempo', '00:03:47'),
(30, 'Ci sei sempre stata', '00:04:58'),
(31, 'La verità è una scelta', '00:04:17'),
(32, 'Caro il mio francesco', '07:05:58'),
(33, 'Atto di fede', '00:04:09'),
(34, 'Un colpo all\'anima', '07:03:22'),
(35, 'Il peso della valigia', '00:04:38'),
(36, 'Taca banda', '00:02:30'),
(37, 'Quando mi vieni a prendere', '00:07:05'),
(38, 'Il meglio deve ancora venire', '00:04:20'),
(39, 'Nuvole di fango', '07:03:06'),
(40, 'Cambia', '07:03:20'),
(41, 'Questa vita', '07:03:06'),
(42, 'Si scrive schiavitÃ¹ ma si legge libertÃ ', '07:03:10'),
(43, 'Polaroid', '07:03:17'),
(44, 'Alfonso signorini', '07:03:13'),
(45, 'Cigno nero', '07:03:21'),
(46, 'SignorsÃ¬', '07:03:20'),
(47, 'Non ci pensi mai', '07:03:44'),
(48, 'Sembra semplice', '07:03:54'),
(49, 'Pensavo fossa amore e invece...', '07:04:23'),
(50, 'Nel mio piccolo', '07:03:32'),
(51, 'Mentine', '07:03:15');

INSERT INTO `Collezione` (`id`, `nome`, `condivisione`, `dataDiCreazione`, `utente`) VALUES
(1, 'Il meglio di Rovere', 'pubblica', '2022-08-12 09:04:05', 1),
(2, 'Nuovi pinguini', 'pubblica', '2022-08-25 14:28:08', 2),
(3, 'Antonio\'s collection', 'pubblica', '2022-09-07 10:55:43', 3),
(4, 'My collection', 'pubblica', '2022-09-17 12:12:13', 3);

INSERT INTO `Disco` (`id`, `nome`, `etichetta`, `anno`, `artista`, `creatore`) VALUES
(1, 'Disponibile in mogano', 'Sony Music', '2019-03-29', 1, 2),
(2, 'Dalla terra a Marte', 'Sony Music', '2022-02-18', 1, 2),
(3, 'Dentista Croazia', 'Sony Music', '2022-08-23', 3, 2),
(4, 'Giovani Wannabe', 'Sony Music', '2022-06-15', 3, 2),
(5, 'Arrivederci mostro', 'Warner music', '2011-05-10', 2, 2),
(6, 'Sottaqua', 'Sony Music', '2022-10-03', 1, 1),
(7, 'Nuovo!', 'Sony music', '2022-10-12', 3, 1),
(8, 'Sig. Brainwash', 'Diamond Record', '2013-01-18', 8, 1);

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
(27, 2, 27, 'Entrambi'),
(28, 2, 28, 'Entrambi'),
(29, 2, 29, 'Entrambi'),
(30, 2, 30, 'Entrambi'),
(31, 2, 31, 'Entrambi'),
(33, 2, 33, 'Entrambi'),
(34, 2, 34, 'Entrambi'),
(35, 2, 35, 'Entrambi'),
(36, 2, 36, 'Entrambi'),
(37, 2, 37, 'Entrambi'),
(38, 2, 38, 'Entrambi'),
(39, 4, 27, 'Musicista'),
(40, 4, 37, 'Musicista'),
(41, 2, 32, 'Entrambi'),
(42, 3, 32, 'Entrambi'),
(43, 3, 26, 'Entrambi'),
(44, 8, 39, 'Entrambi'),
(45, 9, 39, 'Entrambi'),
(46, 8, 40, 'Entrambi'),
(47, 8, 41, 'Entrambi'),
(48, 8, 42, 'Entrambi'),
(49, 8, 43, 'Entrambi'),
(50, 8, 44, 'Entrambi'),
(51, 10, 44, 'Entrambi'),
(52, 8, 45, 'Entrambi'),
(53, 11, 45, 'Entrambi'),
(54, 8, 46, 'Entrambi'),
(55, 8, 47, 'Entrambi'),
(56, 8, 48, 'Entrambi'),
(57, 12, 48, 'Entrambi'),
(58, 8, 49, 'Entrambi'),
(59, 14, 49, 'Entrambi'),
(60, 8, 50, 'Entrambi'),
(61, 8, 51, 'Entrambi');

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
(38, 5, 38),
(39, 8, 39),
(40, 8, 40),
(41, 8, 41),
(42, 8, 42),
(43, 8, 43),
(44, 8, 44),
(45, 8, 45),
(46, 8, 46),
(47, 8, 47),
(48, 8, 48),
(49, 8, 49),
(50, 8, 50),
(51, 8, 51);

INSERT INTO `ListaDischi` (`id`, `collezione`, `disco`, `numeroCopie`, `Stato`, `formato`, `barcode`, `imgCopertina`, `imgFronte`, `imgRetro`, `imgLibretto`) VALUES
(1, 1, 2, 5, 'Ottimo', 'Vinile', '0213456789', 'images/upload-img/1/91iHBQuDJ-L._AC_SY450_.jpg', NULL, NULL, NULL),
(2, 1, 1, 8, 'Ottimo', 'CD', '0123456789', 'images/upload-img/1/600x600bf-60.jpg', NULL, NULL, NULL),
(3, 3, 5, 25, 'Discreto', 'CD', NULL, 'images/upload-img/3/51f2020w9rL._AC_SX466_-2.jpg', NULL, NULL, NULL),
(4, 3, 1, 12, 'Ottimo', 'Digitale', NULL, 'images/upload-img/3/Rovere-2-no-scritta.jpeg', NULL, NULL, NULL),
(5, 3, 2, 2, 'Discreto', 'CD', NULL, 'images/upload-img/3/297286.jpg', NULL, NULL, NULL),
(6, 2, 3, 3, 'Ottimo', 'Digitale', '', 'images/upload-img/2/hqdefault.jpg', NULL, NULL, NULL),
(7, 2, 4, 1, 'Sufficiente', 'Vinile', NULL, 'images/upload-img/2/maxresdefault.jpg', NULL, NULL, NULL),
(8, 4, 3, 1, 'Ottimo', 'Digitale', NULL, 'images/upload-img/4/hqdefault.jpg', NULL, NULL, NULL),
(9, 4, 4, 1, 'Ottimo', 'Digitale', NULL, 'images/upload-img/4/102108393-6e304f53-db11-4bb7-b10b-31874facef80.jpg', NULL, NULL, NULL),
(10, 4, 5, 1, 'Ottimo', 'Vinile', NULL, 'images/upload-img/4/51f2020w9rL._AC_SX466_-2.jpg', NULL, NULL, NULL),
(11, 1, 8, 4, 'Buono', 'CD', '', 'images/upload-img/1/71kvtKk7htL._AC_SL1500_.jpg', '', '', '');

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
(27, 27, 100),
(28, 28, 100),
(29, 29, 100),
(30, 30, 100),
(31, 31, 100),
(33, 33, 100),
(34, 34, 100),
(35, 35, 100),
(36, 36, 100),
(37, 37, 100),
(38, 38, 100),
(39, 28, 15),
(40, 33, 15),
(41, 30, 15),
(43, 32, 5),
(44, 32, 100),
(46, 26, 60),
(47, 39, 87),
(48, 40, 87),
(49, 41, 87),
(50, 42, 87),
(51, 43, 87),
(52, 44, 87),
(53, 45, 87),
(54, 46, 87),
(55, 47, 87),
(56, 48, 53),
(57, 48, 87),
(58, 49, 87),
(59, 50, 87),
(60, 51, 87);

INSERT INTO `Utente` (`id`, `nickname`, `email`, `password`, `nome`, `cognome`, `token`, `link`) VALUES
(1, 'user', 'utente@utente.it', '$2a$12$bvRMGZxAa0r4mW/8yCH1HOq9kfuNYWLjlBwc5SR/beauhTV4P5zz2', 'Utente', 'Utente', 1, NULL),
(2, 'gestore', 'admin@admin.it', '$2a$10$DPXMRdH4AbW8QToxaIK/j.EFj1yWGG4ro122brNDcnpTyR27gFBuG', 'Gestore', 'Gestore', 1, NULL),
(3, 'antonio', 'antonio@site.it', '$2a$10$pTeOgmfUIdNwML4PvKHfxOxaetREbug8rQxANjn9P87aWhOpRjbI6', 'Antonio', 'De Amicis', 1, NULL);

/* ----- ALTER TABLE ----- */

ALTER TABLE `Collezione`
  ADD CONSTRAINT `collezione_ibfk_1` FOREIGN KEY (`utente`) REFERENCES `Utente` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `Disco`
  ADD CONSTRAINT `disco_ibfk_1` FOREIGN KEY (`artista`) REFERENCES `Artista` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `disco_ibfk_2` FOREIGN KEY (`creatore`) REFERENCES `Utente` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `ListaArtisti`
  ADD CONSTRAINT `listaartisti_ibfk_1` FOREIGN KEY (`artista`) REFERENCES `Artista` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `listaartisti_ibfk_2` FOREIGN KEY (`canzone`) REFERENCES `Canzone` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `ListaBrani`
  ADD CONSTRAINT `listabrani_ibfk_1` FOREIGN KEY (`canzone`) REFERENCES `Canzone` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `listabrani_ibfk_2` FOREIGN KEY (`disco`) REFERENCES `Disco` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `ListaDischi`
  ADD CONSTRAINT `listadischi_ibfk_1` FOREIGN KEY (`collezione`) REFERENCES `Collezione` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `listadischi_ibfk_2` FOREIGN KEY (`disco`) REFERENCES `Disco` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `ListaGeneri`
  ADD CONSTRAINT `listageneri_ibfk_1` FOREIGN KEY (`canzone`) REFERENCES `Canzone` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `listageneri_ibfk_2` FOREIGN KEY (`genere`) REFERENCES `Genere` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `UtentiAutorizzati`
  ADD CONSTRAINT `utentiautorizzati_ibfk_1` FOREIGN KEY (`collezione`) REFERENCES `Collezione` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `utentiautorizzati_ibfk_2` FOREIGN KEY (`utente`) REFERENCES `Utente` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/* ----- View ----- */
CREATE VIEW ricercaTable AS
SELECT Collezione.id AS collezioneId, Collezione.nome AS nomeCollezione, Collezione.condivisione AS condivisione, Utente.id AS utenteId, Utente.nickname as username, Disco.nome as nomeDisco, Disco.anno AS annoDisco, Disco.id AS discoId, ListaDischi.numeroCopie AS numeroCopie, ListaDischi.Stato AS statoDisco, ListaDischi.formato AS formatoDisco, ListaDischi.barcode AS barcode, ListaDischi.imgCopertina as imgCopertina, Canzone.nome as nomeCanzone, Canzone.id AS canzoneId, Canzone.durata AS durata, Artista.id as artistaId, Artista.nomeDArte as nomeDArte, Genere.id as genereId, Genere.nome as nomeGenere
FROM Artista, ListaGeneri, Genere, Canzone, Utente, ListaArtisti, Collezione, Disco, ListaBrani, ListaDischi
WHERE ((Collezione.utente = Utente.id)AND(Collezione.id = ListaDischi.collezione)AND(ListaDischi.disco = Disco.id)AND(Disco.id = ListaBrani.disco)AND(ListaBrani.canzone = Canzone.id)AND(ListaGeneri.canzone=Canzone.id)AND(ListaGeneri.genere=Genere.id)AND(Canzone.id=ListaArtisti.canzone)AND(ListaArtisti.artista=Artista.id));

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
