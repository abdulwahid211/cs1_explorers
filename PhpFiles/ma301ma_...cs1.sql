-- phpMyAdmin SQL Dump
-- version 3.4.10.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Mar 10, 2015 at 04:13 PM
-- Server version: 5.1.71
-- PHP Version: 5.3.3

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `ma301ma_...cs1`
--

-- --------------------------------------------------------

--
-- Table structure for table `userGroups`
--

CREATE TABLE IF NOT EXISTS `userGroups` (
  `id` int(50) NOT NULL AUTO_INCREMENT,
  `admin_id` int(50) NOT NULL,
  `eventName` varchar(100) NOT NULL,
  `location` varchar(100) NOT NULL,
  `time` varchar(100) NOT NULL,
  `ageGroup` varchar(100) NOT NULL,
  `description` varchar(100) NOT NULL,
  `postCode` varchar(200) NOT NULL,
  `language` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=159 ;

--
-- Dumping data for table `userGroups`
--

INSERT INTO `userGroups` (`id`, `admin_id`, `eventName`, `location`, `time`, `ageGroup`, `description`, `postCode`, `language`) VALUES
(107, 2, 'Bowling ', 'Bethleham ', '6pm 24/03', '20+', 'Meet friends and enjoy bowling', 'E14 8AQ', 'French'),
(108, 2, 'Jazz Night', 'New Cross Road', '12am', '30+', 'Jazz nights with nice music ', 'E14 8BN', 'Arabic'),
(109, 2, 'Cafe nero', 'Chester Road', '9pm', '27+ ', 'Cafe event', 'E14 4AN', 'Spanishkmkn'),
(158, 0, 'yftzug', '', '', '', '', '', 'Bengali');

-- --------------------------------------------------------

--
-- Table structure for table `userInfo`
--

CREATE TABLE IF NOT EXISTS `userInfo` (
  `id` int(50) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `FirstName` varchar(50) NOT NULL,
  `LastName` varchar(11) NOT NULL,
  `Age` varchar(3) NOT NULL,
  `Nationality` varchar(100) NOT NULL,
  `Occupation` varchar(100) NOT NULL,
  `About` varchar(200) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=18 ;

--
-- Dumping data for table `userInfo`
--

INSERT INTO `userInfo` (`id`, `username`, `password`, `FirstName`, `LastName`, `Age`, `Nationality`, `Occupation`, `About`) VALUES
(1, 'wahid ', '1234', 'Abdul', 'Wahid', '22', 'British', 'Software Programmer', 'Hi love playing football and coding as well'),
(17, 'fred ', '1', 'Tom ', 'Jones ', '89', 'American ', 'solider ', 'I''m so and so bvsgjn\ngfdsyjj\nbvxshkkoiutsszxbnj\nhcdsstybxdf\nvxstgi');

-- --------------------------------------------------------

--
-- Table structure for table `userJoined`
--

CREATE TABLE IF NOT EXISTS `userJoined` (
  `ids` int(11) NOT NULL AUTO_INCREMENT,
  `group_id` varchar(20) NOT NULL,
  `user_id` varchar(20) NOT NULL,
  PRIMARY KEY (`ids`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=165 ;

--
-- Dumping data for table `userJoined`
--

INSERT INTO `userJoined` (`ids`, `group_id`, `user_id`) VALUES
(149, '108', '1'),
(150, '108', '1'),
(151, '109', '1'),
(152, '107', '1'),
(153, '107', '1'),
(154, '109', '1'),
(155, '108', '17'),
(156, '108', '17'),
(157, '108', '17'),
(158, '147', '1'),
(159, '147', '1'),
(160, '108', '1'),
(161, '109', '1'),
(162, '107', '1'),
(163, '108', '1'),
(164, '152', '1');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
