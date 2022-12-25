-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: express_system
-- ------------------------------------------------------
-- Server version	8.0.30

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
-- Table structure for table `article`
--

DROP TABLE IF EXISTS `article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `article` (
  `article_id` int NOT NULL AUTO_INCREMENT,
  `article` varchar(20) DEFAULT NULL,
  `cost` double DEFAULT NULL,
  `article_inf` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`article_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article`
--

LOCK TABLES `article` WRITE;
/*!40000 ALTER TABLE `article` DISABLE KEYS */;
INSERT INTO `article` VALUES (1,'服装',100,'穿的东西'),(2,'水产',100,'水里的吃的'),(3,'五金',20,'建筑水电常用'),(4,'化工',50,'与人类的生活息息相关'),(5,'食品',20,'平常吃的'),(6,'电脑',5000,'生产和娱乐工具'),(7,'手机',2000,'通讯设备'),(8,'药品',200,'治疗疾病'),(9,'家具',500,'家里的用品'),(10,'书',50,'某种梯子'),(11,'车',150000,'人类的代步工具'),(12,'房',500000,'人住的地方，现带有浓重的商品属性'),(13,'饰品',100,'人类为了美丽所佩戴的物品'),(14,'手表',120000,'本为了看时间，现在是一种饰品'),(15,'眼镜',500,'矫正视力的工具'),(16,'化妆品',200,'人们为了美丽的付出'),(17,'帽子',100,'也可以算某种饰品');
/*!40000 ALTER TABLE `article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `company`
--

DROP TABLE IF EXISTS `company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `company` (
  `company_id` int NOT NULL AUTO_INCREMENT,
  `company` varchar(20) DEFAULT NULL,
  `address` varchar(50) DEFAULT NULL,
  `company_inf` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`company_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company`
--

LOCK TABLES `company` WRITE;
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` VALUES (1,'德邦快递','北京','德邦快递，大件专送'),(2,'京东快递','上海','高服务，快速度'),(3,'圆通快递','广州','三通一达之一'),(4,'邮政包裹','北京','快递老大哥'),(5,'极兔快递','深圳','新起之秀'),(6,'申通快递','广州','三通一达之一'),(7,'韵达快递','广州','三通一达之一'),(8,'顺丰快递','深圳','快如闪电'),(9,'中通快递','广州','三通一达之一'),(10,'百世快递','深圳','默默无闻'),(11,'优速快递','深圳','闻所未闻');
/*!40000 ALTER TABLE `company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `express`
--

DROP TABLE IF EXISTS `express`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `express` (
  `express_id` int NOT NULL AUTO_INCREMENT,
  `article_id` int DEFAULT NULL,
  `cost` double DEFAULT NULL,
  `sent_id` int DEFAULT NULL,
  `receive_id` int DEFAULT NULL,
  `company_id` int DEFAULT NULL,
  `local` varchar(50) DEFAULT NULL,
  `origin` varchar(50) DEFAULT NULL,
  `dest` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`express_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `express`
--

LOCK TABLES `express` WRITE;
/*!40000 ALTER TABLE `express` DISABLE KEYS */;
INSERT INTO `express` VALUES (5,2,4,1,4,1,'重庆','重庆','北京'),(6,6,120,1,4,1,'北京','重庆1','深圳');
/*!40000 ALTER TABLE `express` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `address` varchar(50) DEFAULT NULL,
  `phone` bigint DEFAULT NULL,
  `sex` varchar(5) DEFAULT NULL,
  `is_admin` tinyint(1) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'test2','44',12345678911,'',1),(3,'test1','ad',12144242422,'男',0),(4,'admin',NULL,11111111111,NULL,1),(5,'12345',NULL,11111111111,NULL,0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_secret`
--

DROP TABLE IF EXISTS `user_secret`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_secret` (
  `username` varchar(20) NOT NULL,
  `password` char(64) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_secret`
--

LOCK TABLES `user_secret` WRITE;
/*!40000 ALTER TABLE `user_secret` DISABLE KEYS */;
INSERT INTO `user_secret` VALUES ('12345','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92'),('admin','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92'),('test1','a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3'),('test2','a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3');
/*!40000 ALTER TABLE `user_secret` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_token`
--

DROP TABLE IF EXISTS `user_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_token` (
  `username` varchar(20) NOT NULL,
  `refresh_token` varchar(300) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_token`
--

LOCK TABLES `user_token` WRITE;
/*!40000 ALTER TABLE `user_token` DISABLE KEYS */;
INSERT INTO `user_token` VALUES ('12345','eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMjM0NSIsImlhdCI6MTY3MTExODIxMiwiZXhwIjoxNjcxOTgyMjEyfQ.oEjd5QJgu0qEyJBFNhLyTLKj0TvBxfoB1qKue8mVyZO318gF511SQxS2ZxAdLorWgqWApaBWo-PG3eGHVcZPxQ'),('admin','eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY3MTg5MjY4OSwiZXhwIjoxNjcyNzU2Njg5fQ.vn4LZ87fnsD0xO9AUeTwXcdDnuRazOFNgZ1eOx-O0UI0NdDDueXh5FpZXTFpFx4fEEgR_Wgjj8vYfYXPDbV_aw'),('test2','eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0MiIsImlhdCI6MTY3MTEyMDIxMywiZXhwIjoxNjcxOTg0MjEzfQ.XSJfb488TGA3vh6MYxAUF8NXxBDgJdLZfJJdqyF1ojuvTtpTdnPPg8w0AydNLy3yLedTyDa0KOStohbcpvcNnw');
/*!40000 ALTER TABLE `user_token` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-12-24 22:54:48
