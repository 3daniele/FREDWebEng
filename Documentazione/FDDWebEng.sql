-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:8889
-- Creato il: Ago 01, 2022 alle 10:41
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

--
-- Struttura della tabella `Artista`
--

CREATE TABLE IF NOT EXISTS `Artista` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `cognome` varchar(100) NOT NULL,
  `nomeDArte` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Struttura della tabella `Canzone`
--

CREATE TABLE IF NOT EXISTS `Canzone` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `durata` time NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Struttura della tabella `Collezione`
--

CREATE TABLE IF NOT EXISTS `Collezione` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `condivisione` enum('pubblica','privata','condivisa') NOT NULL DEFAULT 'privata',
  `dataDiCreazione` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `utente` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `utente` (`utente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Struttura della tabella `Disco`
--

CREATE TABLE IF NOT EXISTS `Disco` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `etichetta` varchar(50) NOT NULL,
  `anno` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Struttura della tabella `Genere`
--

CREATE TABLE IF NOT EXISTS `Genere` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(150) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=127 DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `Genere`
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
-- Struttura della tabella `ListaArtisti`
--

CREATE TABLE IF NOT EXISTS `ListaArtisti` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `artista` int(11) NOT NULL,
  `canzone` int(11) NOT NULL,
  `ruolo` enum('Compositore','Musicista','Entrambi') NOT NULL,
  PRIMARY KEY (`id`),
  KEY `artista` (`artista`),
  KEY `canzone` (`canzone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Struttura della tabella `ListaBrani`
--

CREATE TABLE IF NOT EXISTS `ListaBrani` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `disco` int(11) NOT NULL,
  `canzone` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `canzone` (`canzone`),
  KEY `disco` (`disco`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Struttura della tabella `ListaDischi`
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Struttura della tabella `ListaGeneri`
--

CREATE TABLE IF NOT EXISTS `ListaGeneri` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `canzone` int(11) NOT NULL,
  `genere` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `canzone` (`canzone`),
  KEY `genere` (`genere`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Struttura della tabella `Utente`
--

CREATE TABLE IF NOT EXISTS `Utente` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nickname` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(50) NOT NULL,
  `nome` varchar(50) DEFAULT NULL,
  `cognome` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nickname` (`nickname`,`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Struttura della tabella `UtentiAutorizzati`
--

CREATE TABLE IF NOT EXISTS `UtentiAutorizzati` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `collezione` int(11) NOT NULL,
  `utente` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `collezione` (`collezione`),
  KEY `utente` (`utente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `Collezione`
--
ALTER TABLE `Collezione`
  ADD CONSTRAINT `collezione_ibfk_1` FOREIGN KEY (`utente`) REFERENCES `Utente` (`id`);

--
-- Limiti per la tabella `ListaArtisti`
--
ALTER TABLE `ListaArtisti`
  ADD CONSTRAINT `listaartisti_ibfk_1` FOREIGN KEY (`artista`) REFERENCES `Artista` (`id`),
  ADD CONSTRAINT `listaartisti_ibfk_2` FOREIGN KEY (`canzone`) REFERENCES `Canzone` (`id`);

--
-- Limiti per la tabella `ListaBrani`
--
ALTER TABLE `ListaBrani`
  ADD CONSTRAINT `listabrani_ibfk_1` FOREIGN KEY (`canzone`) REFERENCES `Canzone` (`id`),
  ADD CONSTRAINT `listabrani_ibfk_2` FOREIGN KEY (`disco`) REFERENCES `Disco` (`id`);

--
-- Limiti per la tabella `ListaDischi`
--
ALTER TABLE `ListaDischi`
  ADD CONSTRAINT `listadischi_ibfk_1` FOREIGN KEY (`collezione`) REFERENCES `Collezione` (`id`),
  ADD CONSTRAINT `listadischi_ibfk_2` FOREIGN KEY (`disco`) REFERENCES `Disco` (`id`);

--
-- Limiti per la tabella `ListaGeneri`
--
ALTER TABLE `ListaGeneri`
  ADD CONSTRAINT `listageneri_ibfk_1` FOREIGN KEY (`canzone`) REFERENCES `Canzone` (`id`),
  ADD CONSTRAINT `listageneri_ibfk_2` FOREIGN KEY (`genere`) REFERENCES `Genere` (`id`);

--
-- Limiti per la tabella `UtentiAutorizzati`
--
ALTER TABLE `UtentiAutorizzati`
  ADD CONSTRAINT `utentiautorizzati_ibfk_1` FOREIGN KEY (`collezione`) REFERENCES `Collezione` (`id`),
  ADD CONSTRAINT `utentiautorizzati_ibfk_2` FOREIGN KEY (`utente`) REFERENCES `Utente` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
