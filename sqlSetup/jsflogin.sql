-- phpMyAdmin SQL Dump
-- version 4.5.2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jan 09, 2017 at 04:55 PM
-- Server version: 10.1.19-MariaDB
-- PHP Version: 5.5.38

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `jsflogin`
--

-- --------------------------------------------------------

--
-- Table structure for table `account`
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
-- Dumping data for table `account`
--

INSERT INTO `account` (`id`, `user`, `title`, `website`, `username`, `password`, `iv`, `salt`) VALUES
(7, 'user1', 'Youtube', 'www.youtube.com', 'meinBenutzer', '3vbu7UrNiI4IfEbnHQJCSkHfsCSbMnuK00g7sFAfHPk=', '32acfe61d1730f7f12784c8c8713dbf12aa039fd9b9fb1d8742f0c0de5659679732107334d2aed3a60bc524375719ddaf2d89dfd8a9b5bb897435592215757bc', 'a367d6c6029e193700467e4266cbd75787908fe083867d020bebeda207ea4bb94b10a8645fd1d578629a9a81bea8008c99625d63228896aa1ada0a0547a4bbb4'),
(8, 'user1', 'Google', 'www.google.com', 'meinBenutzer', 'c6RDD8+mnFNA5SPxGmLyLJLqV8nG1CV5N3LZ6N9CgYYrn5oCRtmJHbl7z+iRcwEN', '7939f080579e50451d9e2d00e2fbcb084400e7cfe39f4775f667e29e5417806ccd8a1ff0a1012357da2f530c5e6cb13dfe832499ff9b198eb63361207e9c1e41', 'd047a8404d74d9c3dc5eba766f12a48d4ef1d42ca7b0190f314dc841f1e8bbf6a0d16c4ab2399f172d96cb661842351e56cc8630cf853d6e605d581cdd40368d'),
(9, 'user1', 'FreudenSeite', 'www.vielfreude.ch', 'Freuden Mensch 12', 'irtAsvPGfSDkTOiY/u+maYGxEOmPbjdIo2n1x3v18co=', '022ef244f67f3ac80fa821c7b449777944473366cb7e40e7a91cf107411c0d451bd473897861f6fd1568c77d1d3e28d4c7f4e58b0c315554fa1837aa3c9712df', 'a1d7234c266cb96ccbc9ad8c6caa07a9fadc195f5ef8c011b10a6e86600ea5dee8391f19976e93c2ff2d728d8458c0ec4bcf78922cc5daf216c7bc403accf5a2'),
(10, 'user1', 'Secret', 'www.secret.ch', 'Secret', 'Bcb6fxmwa9DxjfB8CWI5VQ==', '6e8d7a6aeee4982b4db3b8d2b0fca8b1dbd5f3829480717cf62a1ce69558bb1009c9d5a40f1daa22d1fa69034fdf1467fe0061937cade104b9b98872b8052d0a', '82c143825ad7535d0c42387d63165d5814b7e28ee0687a3b4421b9afc48780b838f7e692c60e4816d7f411d018f45e586694ce83a3e71e3bfb7de054520a2819'),
(11, 'user2', 'Bunte Farben', 'www.buntefraben.ch', 'Ich bin bunt', 'C8HdYrJNDE94uITEP1KWYz0XmZdX5Ddcbl+7/jkn30w=', '9b5d00981bf71e9d4c485d957c0c0d4477ebd90c5ca2a12e65c24518188f616b5675a602760e69bc04d6fdec6f404ed5523b875a4d91e3fe2efc17e3e44f8778', 'c7edb0cd68b632cd897984b7c7a1aea66323fc5e805a5898eae097de27b6bec82e43f1b468ae4085606b0be335638531c460d50a76d9bfe93d5163edd13c9ba3'),
(12, 'user2', 'UBS Ebanking', 'www.ubs.com', 'https://ebanking-ch1.ubs.com/', 'gvVx/PCWcNPCIC+adLQyPA==', '4d47487da245601992ef7a291d5c2110ad5daf17077f7021de377d0806b042a9913fa9782c39195cdcfcee1a7d82ae468e09d364ecfe225d0efb63ee0ec12bd8', '82d722d8e88a2e7612273485d577b833061952be1c8d715cb70412e2d0a9f493cb3343084143460cf4b2e7a6abac7a084913f471a2ccd67e5f2ff39e7bc1f84a'),
(13, 'user2', 'yuhuu', 'www.juhuu.freude', 'yey', '6JWOp3qfZZ05ihHe+BUjhuNtfRzSFUJKQpZxrnntKPOIyDw5nMXa4b1tabsX45Qc', '614f652db5d94537cf6c0fd59dd38e7ba94aa2424b19073e84dbf71999dc74f6903557a756fdc4def232570b2c602c83b144a573c064f37dd4861d32d58c2826', 'fb10aa070282049203a6fd5aae19473c9d5028db12404d49dfb3981f668e229198714c08d789327f2bc2958f93e4a4ad1d7f06c0b3324a05b53815dc033f34cf');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `username` varchar(255) COLLATE utf8_bin NOT NULL,
  `password` varchar(4098) COLLATE utf8_bin NOT NULL,
  `salt` varchar(4096) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`username`, `password`, `salt`) VALUES
('user1', '144ddcf00edd6bfce6e0ce17825cfc4aa427ea96fc7f32acc69cae424ff9a4e5', 'sf9hi3e4fq7mh95li8pc5onppe'),
('user2', 'cf511ff11986a32e86a9989b6930e99e04749a9d028609cc02fad2dba04271ea', 'prf00epbm8n2ikq5e4u179h5be');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `account`
--
ALTER TABLE `account`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user` (`user`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `account`
--
ALTER TABLE `account`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `account`
--
ALTER TABLE `account`
  ADD CONSTRAINT `account_ibfk_1` FOREIGN KEY (`user`) REFERENCES `user` (`username`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
