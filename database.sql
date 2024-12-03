-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: qltv
-- ------------------------------------------------------
-- Server version	5.5.5-10.4.32-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `admins`
--

DROP TABLE IF EXISTS `admins`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admins` (
  `adminid` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `full_name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `reportTo` int(11) DEFAULT NULL,
  PRIMARY KEY (`adminid`),
  KEY `fk_reportTo` (`reportTo`),
  CONSTRAINT `fk_reportTo` FOREIGN KEY (`reportTo`) REFERENCES `admins` (`adminid`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admins`
--

LOCK TABLES `admins` WRITE;
/*!40000 ALTER TABLE `admins` DISABLE KEYS */;
INSERT INTO `admins` VALUES (1,'admin@gmail.com','admin','123',NULL);
/*!40000 ALTER TABLE `admins` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `books`
--

DROP TABLE IF EXISTS `books`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `books` (
  `bookid` int(11) NOT NULL AUTO_INCREMENT,
  `author` varchar(255) NOT NULL,
  `publish_year` int(11) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  `cate_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`bookid`),
  KEY `FKr1a6kmm0gdk6q7251iejy3m9h` (`cate_id`),
  CONSTRAINT `FKr1a6kmm0gdk6q7251iejy3m9h` FOREIGN KEY (`cate_id`) REFERENCES `categories` (`categoryid`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `books`
--

LOCK TABLES `books` WRITE;
/*!40000 ALTER TABLE `books` DISABLE KEYS */;
INSERT INTO `books` VALUES (1,'Nguyen Van Thinh',2024,19,'C++ For Beginer',1),(2,'Lê Hồng Hải',2024,20,'Database',1),(3,'Thomas H. Cormen',2000,10,'Introduction to Algorithms',1),(4,'Robert C. Martin',2000,10,'Clean Code',1),(5,'Jane Austen',2015,20,'Pride and Prejudice',2),(6,'Harper Lee',2012,20,'To Kill a Mockingbird',2),(7,'Andrew Hunt, David Thomas',1980,20,'The Pragmatic Programmer',1),(8,'Abraham Silberschatz',2002,24,'Database System Concepts',1),(9,'Erich Gamma, Richard Helm, Ralph Johnson, John Vlissides',2001,21,'Design Patterns',1),(10,'Thomas Piketty',2003,23,'Capital in the Twenty-First Century',3),(11,'Dale Carnegie',2009,14,'How to Win Friends and Influence People',4),(12,'J.K. Rowling',1999,12,'Harry Potter and the Philosopher\'s Stone',5),(13,'Ben Forta',1999,32,'SQL in 10 Minutes, Sams Teach Yourself',1),(14,'Raghu Ramakrishnan, Johannes Gehrke',1995,23,'Database Management Systems',1),(15,'Baron Schwartz, Peter Zaitsev, Vadim Tkachenko',2000,25,'High-Performance MySQL',1),(19,'Lê Hồng Hải',2024,100,'DATABASE THẦY Lê Hồng Hải',1);
/*!40000 ALTER TABLE `books` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `borrowrequests`
--

DROP TABLE IF EXISTS `borrowrequests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `borrowrequests` (
  `requestid` int(11) NOT NULL AUTO_INCREMENT,
  `borrow_date` date NOT NULL,
  `return_date` date DEFAULT NULL,
  `status` tinytext DEFAULT NULL,
  `bookid` int(11) DEFAULT NULL,
  `memberid` int(11) DEFAULT NULL,
  PRIMARY KEY (`requestid`),
  KEY `FKb46h3rrm6fobdv5q18wugo3e7` (`bookid`),
  KEY `FK2v86yo3igxjymktslmcpgchuy` (`memberid`),
  CONSTRAINT `FK2v86yo3igxjymktslmcpgchuy` FOREIGN KEY (`memberid`) REFERENCES `members` (`memberid`) ON DELETE CASCADE,
  CONSTRAINT `FKb46h3rrm6fobdv5q18wugo3e7` FOREIGN KEY (`bookid`) REFERENCES `books` (`bookid`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `borrowrequests`
--

LOCK TABLES `borrowrequests` WRITE;
/*!40000 ALTER TABLE `borrowrequests` DISABLE KEYS */;
INSERT INTO `borrowrequests` VALUES (1,'2024-12-03','2024-12-31','returned',1,1),(2,'2024-11-30','2024-12-31','PENDING',8,1),(3,'2024-10-30','2024-12-25','borrowed',9,2),(6,'2024-12-01','2025-01-11','borrowed',3,1),(7,'2024-11-02','2024-12-18','PENDING',9,1),(8,'2024-12-01','2024-12-25','PENDING',4,2),(10,'2024-11-05','2024-12-26','PENDING',7,1),(11,'2024-11-14','2025-01-02','PENDING',3,2),(12,'2024-12-01','2024-12-27','PENDING',3,3),(13,'2024-12-03','2024-12-31','returned',2,3),(14,'2024-12-03','2025-02-22','PENDING',19,1);
/*!40000 ALTER TABLE `borrowrequests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `categoryid` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`categoryid`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'CNTT'),(2,'Tiểu Thuyết'),(3,'Kinh tế'),(4,'Self-help'),(5,'Thiếu nhi'),(6,'Văn học'),(7,'Công nghệ');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fines`
--

DROP TABLE IF EXISTS `fines`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fines` (
  `fineID` int(11) NOT NULL AUTO_INCREMENT,
  `fineReason` varchar(255) NOT NULL,
  `fineAmount` decimal(10,2) NOT NULL,
  PRIMARY KEY (`fineID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fines`
--

LOCK TABLES `fines` WRITE;
/*!40000 ALTER TABLE `fines` DISABLE KEYS */;
INSERT INTO `fines` VALUES (1,'Late book return',10.00),(2,'Damaged book',20.00),(3,'Lost book',30.00);
/*!40000 ALTER TABLE `fines` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `memberpenalties`
--

DROP TABLE IF EXISTS `memberpenalties`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `memberpenalties` (
  `penaltyRecordID` int(11) NOT NULL AUTO_INCREMENT,
  `memberID` int(11) DEFAULT NULL,
  `fineID` int(11) DEFAULT NULL,
  `penaltyDate` date NOT NULL,
  `paidStatus` enum('paid','unpaid') DEFAULT 'unpaid',
  PRIMARY KEY (`penaltyRecordID`),
  KEY `memberID` (`memberID`),
  KEY `fineID` (`fineID`),
  CONSTRAINT `memberpenalties_ibfk_1` FOREIGN KEY (`memberID`) REFERENCES `members` (`memberid`) ON DELETE CASCADE,
  CONSTRAINT `memberpenalties_ibfk_2` FOREIGN KEY (`fineID`) REFERENCES `fines` (`fineID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `memberpenalties`
--

LOCK TABLES `memberpenalties` WRITE;
/*!40000 ALTER TABLE `memberpenalties` DISABLE KEYS */;
/*!40000 ALTER TABLE `memberpenalties` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `members`
--

DROP TABLE IF EXISTS `members`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `members` (
  `memberid` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `full_name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`memberid`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `members`
--

LOCK TABLES `members` WRITE;
/*!40000 ALTER TABLE `members` DISABLE KEYS */;
INSERT INTO `members` VALUES (1,'thinh@gmail.com','Nguyễn Văn Thịnh','123'),(2,'tan@gmail.com','Đào Ngọc Tân','123'),(3,'son@gmail.com','Nguyễn Trường Sơn','123'),(4,'user@gmail.com','Nguyễn Thị Mai','123'),(5,'hello@gmail.com','Mai Đức Văn','vanne'),(6,'thuyet@gmail.com','Ngọ Viết Thuyết','thuyet123'),(7,'haill@vnu.edu.vn','Lê Hồng Hải','lehonghai'),(8,'csdl@gmail.com','Chí Phèo','chipheo'),(9,'db@gmail.com','Thị Nở','thino'),(10,'dung@gmail.com','Nguyễn Hoàng Dũng','hd1213'),(11,'thinh@g','Nguyen Van Thinh 1','');
/*!40000 ALTER TABLE `members` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reviews`
--

DROP TABLE IF EXISTS `reviews`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reviews` (
  `reviewid` int(11) NOT NULL AUTO_INCREMENT,
  `comment` tinytext DEFAULT NULL,
  `rating` int(11) DEFAULT NULL,
  `review_date` datetime(6) DEFAULT NULL,
  `borrow_request_id` int(11) DEFAULT NULL,
  `member_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`reviewid`),
  KEY `FK8hdt1dv0iunluf9fn1nnvxle5` (`borrow_request_id`),
  KEY `FK6wsc8rr8tb1fc782foh3mjc8q` (`member_id`),
  CONSTRAINT `FK6wsc8rr8tb1fc782foh3mjc8q` FOREIGN KEY (`member_id`) REFERENCES `members` (`memberid`) ON DELETE CASCADE,
  CONSTRAINT `FK8hdt1dv0iunluf9fn1nnvxle5` FOREIGN KEY (`borrow_request_id`) REFERENCES `borrowrequests` (`requestid`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reviews`
--

LOCK TABLES `reviews` WRITE;
/*!40000 ALTER TABLE `reviews` DISABLE KEYS */;
INSERT INTO `reviews` VALUES (1,'sách hay',5,'2024-12-03 00:00:00.000000',1,1),(2,'hayyy ',5,'2024-12-02 00:00:00.000000',2,8),(3,'10 điểm k có nhưng ',4,'2024-12-03 20:36:02.000000',1,1),(4,'9,5 ',3,'2024-12-03 18:00:50.000000',1,1),(7,'sach nay oke ne',5,'2024-12-03 14:32:56.000000',10,1),(8,'sach nay kho qua',2,'2024-12-03 14:33:08.000000',6,1),(9,'hayyy qua de',5,'2024-12-03 14:34:45.000000',12,3),(10,'hay',5,'2024-12-03 14:35:07.000000',13,3),(11,'Sách này nay nè thầy ơi',5,'2024-12-03 14:53:03.000000',7,1),(12,'Sách thầy hải autooo hayyy',5,'2024-12-03 14:53:17.000000',14,1);
/*!40000 ALTER TABLE `reviews` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-04  0:03:50
