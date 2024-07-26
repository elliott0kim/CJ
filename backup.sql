-- MySQL dump 10.13  Distrib 8.3.0, for macos13.6 (x86_64)
--
-- Host: localhost    Database: CJ
-- ------------------------------------------------------
-- Server version	8.3.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `HeartRateHistory`
--

DROP TABLE IF EXISTS `HeartRateHistory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `HeartRateHistory` (
  `workDate` date NOT NULL,
  `userId` varchar(48) NOT NULL,
  `heartRateByteStream` blob NOT NULL,
  KEY `userId` (`userId`),
  CONSTRAINT `heartratehistory_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `UserInfo` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `HeartRateHistory`
--

LOCK TABLES `HeartRateHistory` WRITE;
/*!40000 ALTER TABLE `HeartRateHistory` DISABLE KEYS */;
INSERT INTO `HeartRateHistory` VALUES ('2024-07-01','jeremy',_binary 'HeartRateStream01'),('2024-07-01','elliott',_binary 'HeartRateStream02'),('2024-07-02','jeremy',_binary 'HeartRateStream03'),('2024-07-02','elliott',_binary 'HeartRateStream04'),('2024-07-03','jeremy',_binary 'HeartRateStream05'),('2024-07-03','elliott',_binary 'HeartRateStream06'),('2024-07-04','jeremy',_binary 'HeartRateStream07'),('2024-07-04','elliott',_binary 'HeartRateStream08'),('2024-07-05','jeremy',_binary 'HeartRateStream09'),('2024-07-05','elliott',_binary 'HeartRateStream10'),('2024-07-06','jeremy',_binary 'HeartRateStream11'),('2024-07-06','elliott',_binary 'HeartRateStream12'),('2024-07-07','jeremy',_binary 'HeartRateStream13'),('2024-07-07','elliott',_binary 'HeartRateStream14'),('2024-07-08','jeremy',_binary 'HeartRateStream15'),('2024-07-08','elliott',_binary 'HeartRateStream16'),('2024-07-09','jeremy',_binary 'HeartRateStream17'),('2024-07-09','elliott',_binary 'HeartRateStream18'),('2024-07-10','jeremy',_binary 'HeartRateStream19'),('2024-07-10','elliott',_binary 'HeartRateStream20'),('2024-07-11','jeremy',_binary 'HeartRateStream21'),('2024-07-11','elliott',_binary 'HeartRateStream22'),('2024-07-12','jeremy',_binary 'HeartRateStream23'),('2024-07-12','elliott',_binary 'HeartRateStream24'),('2024-07-13','jeremy',_binary 'HeartRateStream25'),('2024-07-13','elliott',_binary 'HeartRateStream26'),('2024-07-14','jeremy',_binary 'HeartRateStream27'),('2024-07-14','elliott',_binary 'HeartRateStream28'),('2024-07-15','jeremy',_binary 'HeartRateStream29'),('2024-07-15','elliott',_binary 'HeartRateStream30');
/*!40000 ALTER TABLE `HeartRateHistory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ReportHistory`
--

DROP TABLE IF EXISTS `ReportHistory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ReportHistory` (
  `userId` varchar(48) NOT NULL,
  `threshold` tinyint unsigned DEFAULT NULL,
  `reportHeartRate` tinyint unsigned NOT NULL,
  `reportDateTime` datetime NOT NULL,
  `action` tinyint unsigned DEFAULT NULL,
  `locationXPos` double NOT NULL,
  `locationYPos` double NOT NULL,
  KEY `userId` (`userId`),
  KEY `threshold` (`threshold`),
  CONSTRAINT `reporthistory_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `UserInfo` (`userId`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ReportHistory`
--

LOCK TABLES `ReportHistory` WRITE;
/*!40000 ALTER TABLE `ReportHistory` DISABLE KEYS */;
INSERT INTO `ReportHistory` VALUES ('jeremy',162,185,'2024-07-17 16:00:00',3,51.5074,-0.1278),('jeremy',162,175,'2024-07-20 14:30:00',1,37.7749,-122.4194),('elliott',152,180,'2024-07-19 10:15:00',2,34.0522,-118.2437),('elliott',152,190,'2024-07-18 08:45:00',0,40.7128,-74.006),('jeremy',162,185,'2024-07-17 16:00:00',3,51.5074,-0.1278),('elliott',152,195,'2024-07-16 12:30:00',NULL,48.8566,2.3522);
/*!40000 ALTER TABLE `ReportHistory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UserInfo`
--

DROP TABLE IF EXISTS `UserInfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `UserInfo` (
  `userUUID` varchar(48) NOT NULL DEFAULT (uuid()),
  `userId` varchar(48) NOT NULL,
  `password` varchar(48) NOT NULL,
  `name` varchar(32) NOT NULL,
  `phoneNum` varchar(16) NOT NULL,
  `gender` tinyint NOT NULL,
  `birth` date NOT NULL,
  `height` tinyint unsigned NOT NULL,
  `weight` tinyint unsigned NOT NULL,
  `threshold` tinyint unsigned DEFAULT '160',
  `level` tinyint NOT NULL DEFAULT '1',
  `reportCountTotal` smallint unsigned DEFAULT '0',
  `reportCountToday` smallint unsigned DEFAULT '0',
  PRIMARY KEY (`userUUID`),
  KEY `userId` (`userId`),
  KEY `threshold` (`threshold`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UserInfo`
--

LOCK TABLES `UserInfo` WRITE;
/*!40000 ALTER TABLE `UserInfo` DISABLE KEYS */;
INSERT INTO `UserInfo` VALUES ('01836b16-46ae-11ef-9088-f43119e84089','jeremy','1324','김동현','01036277363',0,'1996-08-08',174,73,160,1,0,0),('88db3e76-46b4-11ef-9088-f43119e84089','elliott','1324','김동현','01036277363',0,'1996-08-08',174,73,152,1,0,0);
/*!40000 ALTER TABLE `UserInfo` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `update_threshold` AFTER UPDATE ON `userinfo` FOR EACH ROW BEGIN
    IF NEW.threshold != OLD.threshold THEN
        UPDATE ReportHistory
        SET threshold = NEW.threshold
        WHERE userId = NEW.userId;
    END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `WorkStatus`
--

DROP TABLE IF EXISTS `WorkStatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `WorkStatus` (
  `userId` varchar(48) NOT NULL,
  `workStatus` tinyint unsigned NOT NULL,
  KEY `userId` (`userId`),
  CONSTRAINT `workstatus_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `UserInfo` (`userId`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `WorkStatus`
--

LOCK TABLES `WorkStatus` WRITE;
/*!40000 ALTER TABLE `WorkStatus` DISABLE KEYS */;
INSERT INTO `WorkStatus` VALUES ('elliott',1),('jeremy',0);
/*!40000 ALTER TABLE `WorkStatus` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-07-21 13:01:42
