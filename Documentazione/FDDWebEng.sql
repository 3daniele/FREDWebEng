-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:8889
-- Creato il: Ago 25, 2022 alle 14:34
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `Artista`
--

INSERT INTO `Artista` (`id`, `nome`, `cognome`, `nomeDArte`) VALUES
(1, 'Nelson', 'Venceslai', 'Rovere'),
(2, 'Luciano', 'Ligabue', 'Ligabue'),
(3, 'Riccardo', 'Zanotti', 'Pinguini tattici nucleari');

-- --------------------------------------------------------

--
-- Struttura della tabella `Canzone`
--

CREATE TABLE IF NOT EXISTS `Canzone` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `durata` time NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `Canzone`
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
(26, 'Dentista Croazia', '00:04:23');

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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `Collezione`
--

INSERT INTO `Collezione` (`id`, `nome`, `condivisione`, `dataDiCreazione`, `utente`) VALUES
(1, 'Il meglio di Rovere', 'pubblica', '2022-08-12 09:04:05', 1),
(3, 'Nuovi pinguini', 'privata', '2022-08-25 14:28:08', 2);

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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `Disco`
--

INSERT INTO `Disco` (`id`, `nome`, `etichetta`, `anno`) VALUES
(1, 'Disponibile anche in mogano', 'Sony Music', '2019-03-29'),
(2, 'Dalla terra a Marte', 'Sony Music', '2022-02-18'),
(3, 'Dentista Croazia', 'Sony Music', '2022-08-24'),
(4, 'Giovani Wannabe', 'Sony Music', '2022-06-15');

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
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `ListaArtisti`
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
(26, 3, 26, 'Entrambi');

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
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `ListaBrani`
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
(26, 4, 25);

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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `ListaDischi`
--

INSERT INTO `ListaDischi` (`id`, `collezione`, `disco`, `numeroCopie`, `Stato`, `formato`, `barcode`, `imgCopertina`, `imgFronte`, `imgRetro`, `imgLibretto`) VALUES
(1, 1, 2, 5, 'Ottimo', 'Vinile', '0213456789', 'https://m.media-amazon.com/images/I/91iHBQuDJ-L._AC_SY450_.jpg', '', '', ''),
(2, 1, 1, 8, 'Ottimo', 'CD', '0123456789', 'https://is3-ssl.mzstatic.com/image/thumb/Music113/v4/f2/68/aa/f268aa06-1da7-84e9-f7eb-384b9fbc7f27/886447616475.jpg/600x600bf-60.jpg', '', '', ''),
(3, 3, 4, 2, 'Buono', 'Vinile', '0321456789', 'https://www.bergamonews.it/photogallery_new/images/2022/05/giovani-wannabe-il-nuovo-singolo-dei-pinguini-tattici-720072.large.jpg', NULL, NULL, NULL),
(4, 3, 3, 10, 'Ottimo', 'CD', '0912345678', 'https://www.spettacoloitaliano.it/wp-content/uploads/2022/08/dentista-croazia-testo.jpg', NULL, NULL, NULL);

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
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `ListaGeneri`
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
(26, 26, 60);

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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `Utente`
--

INSERT INTO `Utente` (`id`, `nickname`, `email`, `password`, `nome`, `cognome`) VALUES
(1, 'user', 'utente@utente.it', 'utenteutente', 'Utente', 'Utente'),
(2, 'gestore', 'gestore@gestore.it', 'gestoregestore', 'Gestore', 'Gestore');

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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `UtentiAutorizzati`
--

INSERT INTO `UtentiAutorizzati` (`id`, `collezione`, `utente`) VALUES
(3, 3, 1);

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
