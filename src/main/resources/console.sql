-- MySQL dump 10.13  Distrib 8.0.31, for Linux (x86_64)
--
-- Host: localhost    Database: supermarket
-- ------------------------------------------------------
-- Server version	8.0.31

-- 自己添加
DROP DATABASE IF EXISTS supermarket;
CREATE DATABASE supermarket;
USE supermarket;
--

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
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `name` varchar(20) DEFAULT NULL,
                            `recommend` varchar(20) NOT NULL COMMENT '推荐指数',
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'食品','97'),(2,'日用品','90'),(3,'饮品','100'),(5,'衣物','100'),(6,'厨房用具','80'),(7,'速食品','88'),(8,'电子设备','77'),(9,'test','33');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
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
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `update_after_category` AFTER UPDATE ON `category` FOR EACH ROW BEGIN
    IF old.name != new.name THEN
        UPDATE product set category_name = new.name WHERE cid = new.id;
    END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
                           `id` bigint NOT NULL AUTO_INCREMENT,
                           `cid` bigint DEFAULT NULL,
                           `name` varchar(20) NOT NULL,
                           `yield_date` date NOT NULL COMMENT '生产日期',
                           `manufacturers` varchar(20) NOT NULL COMMENT '产家',
                           `price` decimal(6,2) NOT NULL,
                           `create_date` date NOT NULL COMMENT '进货日期',
                           `stock` int NOT NULL,
                           `now_price` decimal(6,2) NOT NULL COMMENT '售价',
                           `sale_count` int NOT NULL,
                           `category_name` varchar(20) NOT NULL,
                           PRIMARY KEY (`id`),
                           KEY `cid` (`cid`),
                           CONSTRAINT `product_ibfk_1` FOREIGN KEY (`cid`) REFERENCES `category` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
                           CONSTRAINT `product_chk_1` CHECK ((`stock` > 0))
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,1,'饼干','2020-07-02','奥利奥',5.00,'2020-07-12',978,9.00,11,'食品'),(2,2,'牙刷','2020-07-02','舒克',3.00,'2020-07-13',220,6.00,40,'日用品'),(3,2,'牙膏','2020-06-28','舒克',8.00,'2020-07-09',120,16.00,410,'日用品'),(4,3,'绿茶','2020-07-02','统一',1.50,'2020-07-13',3590,3.00,207,'饮品'),(6,3,'可口可乐','2020-02-06','可口可乐',1.20,'2020-07-12',2995,3.00,5,'饮品'),(7,6,'水果刀','2023-05-09','张小泉',88.00,'2023-05-11',499,99.00,1,'厨房用具'),(8,2,'剪刀','2023-05-09','张小泉',37.00,'2023-05-11',50,43.00,0,'日用品'),(9,1,'麦丽素','2023-05-07','金丝猴',3.30,'2023-05-07',1738,4.00,5,'食品'),(10,1,'果冻','2023-05-07','喜之郎',12.00,'2023-05-07',3000,15.00,0,'食品'),(11,2,'马克杯','2023-05-09','不知道',7.00,'2023-05-09',49,10.00,1,'日用品'),(12,7,'方便面','2023-05-11','康师傅',4.30,'2023-05-11',4998,4.50,2,'速食品'),(14,3,'test','2022-02-21','Coke',2.50,'2022-03-13',200,3.00,0,'饮品');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
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
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `insert_before_product` BEFORE INSERT ON `product` FOR EACH ROW BEGIN
    DECLARE cId BIGINT;
    set cID = (SELECT id FROM category WHERE name = new.category_name);
    set new.cid = cID;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `sale`
--

DROP TABLE IF EXISTS `sale`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sale` (
                        `id` bigint NOT NULL AUTO_INCREMENT,
                        `pid` bigint DEFAULT NULL,
                        `create_date` date NOT NULL,
                        `sale_count` int NOT NULL,
                        PRIMARY KEY (`id`),
                        KEY `pid` (`pid`),
                        CONSTRAINT `sale_ibfk_1` FOREIGN KEY (`pid`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sale`
--

LOCK TABLES `sale` WRITE;
/*!40000 ALTER TABLE `sale` DISABLE KEYS */;
INSERT INTO `sale` VALUES (1,1,'2020-07-14',11),(2,2,'2020-07-14',40),(3,3,'2020-07-14',410),(4,4,'2020-07-14',203),(5,6,'2023-05-11',3),(6,6,'2023-05-11',1),(7,6,'2023-05-11',1),(8,4,'2023-05-11',4),(9,12,'2023-05-12',2),(10,11,'2023-05-12',1),(11,7,'2023-05-12',1),(12,9,'2023-05-12',5);
/*!40000 ALTER TABLE `sale` ENABLE KEYS */;
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
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `insert_after_sale` AFTER INSERT ON `sale` FOR EACH ROW BEGIN
    UPDATE product set stock = stock - new.sale_count WHERE id = new.pid;
    UPDATE product set sale_count = sale_count + new.sale_count WHERE id = new.pid;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `sale_product`
--

DROP TABLE IF EXISTS `sale_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sale_product` (
                                `id` bigint NOT NULL AUTO_INCREMENT,
                                `count` int DEFAULT NULL,
                                `product_id` bigint DEFAULT NULL,
                                `sale_id` bigint DEFAULT NULL,
                                PRIMARY KEY (`id`),
                                KEY `FKrtwiisrmdqeslt86pacdwwn1o` (`product_id`),
                                KEY `FK4dtibi1vwxkx8gjs59nhp0cnq` (`sale_id`),
                                CONSTRAINT `FK4dtibi1vwxkx8gjs59nhp0cnq` FOREIGN KEY (`sale_id`) REFERENCES `sale` (`id`),
                                CONSTRAINT `FKrtwiisrmdqeslt86pacdwwn1o` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sale_product`
--

LOCK TABLES `sale_product` WRITE;
/*!40000 ALTER TABLE `sale_product` DISABLE KEYS */;
/*!40000 ALTER TABLE `sale_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
                        `id` bigint NOT NULL AUTO_INCREMENT,
                        `username` varchar(20) NOT NULL,
                        `password` varchar(50) NOT NULL,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin','123456'),(2,'perzch','perzch'),(3,'test','123456');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-09-14  2:00:17
