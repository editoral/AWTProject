-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Erstellungszeit: 15. Nov 2016 um 11:11
-- Server-Version: 10.1.13-MariaDB
-- PHP-Version: 5.6.23

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `jsflogin`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `account`
--

CREATE TABLE `account` (
  `id` int(11) NOT NULL,
  `user` varchar(255) COLLATE utf8_bin NOT NULL,
  `title` varchar(1024) COLLATE utf8_bin NOT NULL,
  `website` varchar(1024) COLLATE utf8_bin NOT NULL,
  `username` varchar(1024) COLLATE utf8_bin NOT NULL,
  `password` varchar(4096) COLLATE utf8_bin NOT NULL,
  `iv` varchar(4096) COLLATE utf8_bin NOT NULL,
  `salt` varchar(4096) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Daten für Tabelle `account`
--

INSERT INTO `account` (`id`, `user`, `title`, `website`, `username`, `password`, `iv`, `salt`) VALUES
(3, 'test', 'Test', 'test', 'test', 'testwe', '', ''),
(4, 'test', 'Test', 'test', 'test', 'null', '', ''),
(5, 'test', 'g', 'er', 'reer', 'U2FsdGVkX19X7osDmMFUobaRZ5hIgZD8nCqJj2yCUco4i/ZenCqixjOKaFLj01Lw', '', ''),
(6, 'test', '', '', '', 'U2FsdGVkX185OPeA7nT4/z5iDmmFmH1xWA5BPVbnMI07ZxnOtYZh3VAlvK+cRGe8', '', '');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `user`
--

CREATE TABLE `user` (
  `username` varchar(255) COLLATE utf8_bin NOT NULL,
  `password` varchar(4098) COLLATE utf8_bin NOT NULL,
  `salt` varchar(4096) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Daten für Tabelle `user`
--

INSERT INTO `user` (`username`, `password`, `salt`) VALUES
('blah', 'blah', ''),
('test', 'test', '');

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `account`
--
ALTER TABLE `account`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user` (`user`);

--
-- Indizes für die Tabelle `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`username`);

--
-- AUTO_INCREMENT für exportierte Tabellen
--

--
-- AUTO_INCREMENT für Tabelle `account`
--
ALTER TABLE `account`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- Constraints der exportierten Tabellen
--

--
-- Constraints der Tabelle `account`
--
ALTER TABLE `account`
  ADD CONSTRAINT `account_ibfk_1` FOREIGN KEY (`user`) REFERENCES `user` (`username`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
