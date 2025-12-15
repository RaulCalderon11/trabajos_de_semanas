CREATE DATABASE  IF NOT EXISTS `bdgestiontesis` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `bdgestiontesis`;
-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: localhost    Database: bdgestiontesis
-- ------------------------------------------------------
-- Server version	8.0.44

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
-- Table structure for table `documentos_requisito`
--

DROP TABLE IF EXISTS `documentos_requisito`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `documentos_requisito` (
  `id_documento` int NOT NULL AUTO_INCREMENT,
  `id_tramite` int NOT NULL,
  `tipo_documento` enum('Solicitud','DNI','Bachiller','Certificado_Idiomas','Constancia_Practicas','Foto') NOT NULL,
  `ruta_archivo` varchar(255) NOT NULL,
  `estado_validacion` enum('Pendiente','Validado','Rechazado') DEFAULT 'Pendiente',
  `observacion_rechazo` text,
  `fecha_subida` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_documento`),
  KEY `fk_documento_tramite` (`id_tramite`),
  CONSTRAINT `fk_documento_tramite` FOREIGN KEY (`id_tramite`) REFERENCES `tramites` (`id_tramite`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `documentos_requisito`
--

LOCK TABLES `documentos_requisito` WRITE;
/*!40000 ALTER TABLE `documentos_requisito` DISABLE KEYS */;
INSERT INTO `documentos_requisito` VALUES (4,1,'Bachiller','req_1_Bachiller_1763700421061.pdf','Validado',NULL,'2025-11-21 04:47:01'),(5,1,'DNI','req_1_DNI_1763700654902.pdf','Validado',NULL,'2025-11-21 04:50:54'),(6,1,'Certificado_Idiomas','req_1_Certificado_Idiomas_1763732753434.pdf','Validado',NULL,'2025-11-21 13:45:53'),(7,1,'Foto','req_1_Foto_1763703596190.pdf','Validado',NULL,'2025-11-21 05:39:56'),(8,6,'DNI','req_6_DNI_1763740911198.pdf','Validado',NULL,'2025-11-21 16:01:51'),(9,7,'DNI','req_7_DNI_1763745631648.pdf','Validado',NULL,'2025-11-21 17:20:31'),(10,7,'Bachiller','req_7_Bachiller_1763745639279.pdf','Validado',NULL,'2025-11-21 17:20:39'),(11,7,'Certificado_Idiomas','req_7_Certificado_Idiomas_1763745648551.pdf','Validado',NULL,'2025-11-21 17:20:48'),(12,7,'Foto','req_7_Foto_1763745658521.pdf','Validado',NULL,'2025-11-21 17:20:58'),(13,1,'Constancia_Practicas','req_1_Constancia_Practicas_1764466628288.pdf','Pendiente',NULL,'2025-11-30 01:37:08'),(14,3,'DNI','req_3_DNI_1764467465247.pdf','Validado',NULL,'2025-11-30 01:51:05'),(15,3,'Bachiller','req_3_Bachiller_1764467853685.pdf','Validado',NULL,'2025-11-30 01:57:33'),(16,10,'DNI','req_10_DNI_1764535204598.pdf','Validado',NULL,'2025-11-30 20:40:04'),(17,10,'Bachiller','req_10_Bachiller_1764535213852.pdf','Validado',NULL,'2025-11-30 20:40:13'),(18,10,'Certificado_Idiomas','req_10_Certificado_Idiomas_1764535222622.pdf','Validado',NULL,'2025-11-30 20:40:22'),(19,10,'Constancia_Practicas','req_10_Constancia_Practicas_1764535230715.pdf','Validado',NULL,'2025-11-30 20:40:30'),(20,10,'Foto','req_10_Foto_1764535241102.pdf','Validado',NULL,'2025-11-30 20:40:41');
/*!40000 ALTER TABLE `documentos_requisito` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `evaluaciones`
--

DROP TABLE IF EXISTS `evaluaciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `evaluaciones` (
  `id_evaluacion` int NOT NULL AUTO_INCREMENT,
  `id_tesis` int NOT NULL,
  `codigo_docente` varchar(20) NOT NULL,
  `comentarios` text,
  `fecha_evaluacion` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `item_1` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_2` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_3` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_4` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_5` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_6` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_7` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_8` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_9` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_10` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_11` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_12` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_13` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_14` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_15` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_16` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_17` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_18` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_19` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_20` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_21` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_22` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_23` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_24` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_25` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_26` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_27` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_28` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_29` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_30` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_31` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_32` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_33` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_34` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_35` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_36` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_37` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_38` decimal(2,1) NOT NULL DEFAULT '0.0',
  `puntaje_total` decimal(4,1) NOT NULL DEFAULT '0.0',
  `condicion` enum('Aprobado','Aprobado con observaciones menores','Desaprobado con observaciones mayores') NOT NULL,
  PRIMARY KEY (`id_evaluacion`),
  KEY `codigo_docente` (`codigo_docente`),
  KEY `evaluaciones_ibfk_1` (`id_tesis`),
  CONSTRAINT `evaluaciones_ibfk_1` FOREIGN KEY (`id_tesis`) REFERENCES `tesis` (`id_tesis`) ON DELETE CASCADE,
  CONSTRAINT `evaluaciones_ibfk_2` FOREIGN KEY (`codigo_docente`) REFERENCES `usuarios` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evaluaciones`
--

LOCK TABLES `evaluaciones` WRITE;
/*!40000 ALTER TABLE `evaluaciones` DISABLE KEYS */;
INSERT INTO `evaluaciones` VALUES (9,20,'DOC001','Corregir\r\n','2025-11-30 20:19:57',1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,0.5,0.0,1.0,0.5,1.0,0.0,0.5,1.0,0.5,1.0,0.0,1.0,1.0,1.0,0.0,0.5,1.0,0.5,0.0,0.0,1.0,0.5,1.0,0.0,0.5,0.0,1.0,1.0,1.0,26.0,'Aprobado'),(11,20,'DOC001','CORREGIR URGENTE','2025-11-21 06:19:19',0.0,0.0,0.0,0.0,0.0,1.0,0.0,0.0,0.0,0.0,0.0,0.0,1.0,1.0,1.0,1.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,5.0,'Desaprobado con observaciones mayores'),(14,21,'DOC007','corrige por favor','2025-11-21 16:04:04',1.0,0.5,1.0,0.5,0.0,0.0,0.0,1.0,0.5,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,0.0,1.0,1.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,17.5,'Aprobado con observaciones menores'),(15,22,'DOC005','CORREGIDO','2025-11-21 17:19:58',1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,26.0,'Aprobado'),(18,20,'DOC001','Aprobado, listo para sustentar','2025-11-28 01:13:37',1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,38.0,'Aprobado'),(19,21,'DOC001','muy bien','2025-11-30 00:45:49',1.0,1.0,1.0,1.0,1.0,1.0,0.0,0.0,0.0,0.0,1.0,1.0,0.0,0.5,1.0,1.0,0.0,1.0,1.0,1.0,1.0,1.0,1.0,0.0,0.0,1.0,1.0,0.0,0.0,0.5,0.0,1.0,1.0,0.5,0.5,1.0,1.0,1.0,25.0,'Aprobado'),(22,26,'DOC002','okay','2025-11-30 17:26:10',1.0,1.0,1.0,1.0,1.0,0.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,0.0,1.0,1.0,1.0,1.0,1.0,1.0,0.0,1.0,0.0,0.0,1.0,0.0,1.0,1.0,0.0,1.0,1.0,1.0,1.0,1.0,0.0,1.0,1.0,0.0,29.0,'Aprobado'),(23,27,'DOC008','APROBADO PARA LOS JURADOS','2025-11-30 20:39:31',1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,26.5,'Aprobado');
/*!40000 ALTER TABLE `evaluaciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `evaluaciones_jurado`
--

DROP TABLE IF EXISTS `evaluaciones_jurado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `evaluaciones_jurado` (
  `id_evaluacion_jurado` int NOT NULL AUTO_INCREMENT,
  `id_sustentacion` int NOT NULL,
  `codigo_jurado` varchar(20) NOT NULL,
  `item_1` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_2` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_3` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_4` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_5` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_6` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_7` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_8` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_9` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_10` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_11` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_12` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_13` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_14` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_15` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_16` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_17` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_18` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_19` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_20` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_21` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_22` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_23` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_24` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_25` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_26` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_27` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_28` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_29` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_30` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_31` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_32` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_33` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_34` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_35` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_36` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_37` decimal(2,1) NOT NULL DEFAULT '0.0',
  `item_38` decimal(2,1) NOT NULL DEFAULT '0.0',
  `puntaje_total` decimal(4,1) NOT NULL DEFAULT '0.0',
  `condicion` enum('Aprobado','Aprobado con observaciones menores','Desaprobado con observaciones mayores') NOT NULL,
  `observaciones` text,
  `fecha_evaluacion` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_evaluacion_jurado`),
  KEY `fk_evaljurado_sust` (`id_sustentacion`),
  KEY `fk_evaljurado_user` (`codigo_jurado`),
  CONSTRAINT `fk_evaljurado_sust` FOREIGN KEY (`id_sustentacion`) REFERENCES `sustentaciones` (`id_sustentacion`) ON DELETE CASCADE,
  CONSTRAINT `fk_evaljurado_user` FOREIGN KEY (`codigo_jurado`) REFERENCES `usuarios` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evaluaciones_jurado`
--

LOCK TABLES `evaluaciones_jurado` WRITE;
/*!40000 ALTER TABLE `evaluaciones_jurado` DISABLE KEYS */;
INSERT INTO `evaluaciones_jurado` VALUES (1,4,'DOC001',0.0,1.0,1.0,1.0,1.0,1.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,5.0,'Desaprobado con observaciones mayores','f','2025-11-28 00:33:29'),(2,4,'DOC002',1.0,1.0,1.0,1.0,1.0,1.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,6.0,'Desaprobado con observaciones mayores','ff','2025-11-28 00:34:11'),(3,4,'DOC005',1.0,1.0,1.0,1.0,1.0,1.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,6.0,'Desaprobado con observaciones mayores','fff','2025-11-28 00:34:29'),(4,2,'DOC002',1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,38.0,'Aprobado','Aprobado','2025-11-28 01:16:02'),(5,2,'DOC005',1.0,1.0,1.0,1.0,1.0,0.5,0.5,0.5,0.5,1.0,1.0,1.0,1.0,0.5,0.5,0.5,1.0,0.5,0.5,0.5,0.5,1.0,1.0,0.5,0.5,0.5,1.0,1.0,0.5,0.5,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,30.0,'Aprobado','aPROBADO','2025-11-28 01:16:43'),(6,2,'DOC006',1.0,1.0,1.0,1.0,1.0,1.0,0.5,0.5,1.0,0.5,1.0,1.0,1.0,1.0,1.0,1.0,0.5,0.0,1.0,1.0,1.0,0.5,0.5,0.0,0.5,0.5,1.0,1.0,0.5,0.5,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,31.0,'Aprobado','APROBADO','2025-11-28 01:17:26'),(7,3,'DOC001',1.0,1.0,1.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,3.0,'Desaprobado con observaciones mayores','efe','2025-11-30 01:52:15'),(9,9,'DOC002',1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,38.0,'Aprobado','ok','2025-11-30 18:44:32'),(10,9,'DOC005',1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,0.0,1.0,1.0,0.5,0.5,1.0,1.0,0.0,0.0,0.0,1.0,0.5,0.5,0.0,0.0,0.0,1.0,1.0,0.5,0.0,1.0,1.0,27.5,'Aprobado','ok','2025-11-30 18:45:35'),(11,9,'DOC007',1.0,1.0,1.0,1.0,0.0,1.0,1.0,1.0,1.0,0.0,1.0,1.0,0.0,1.0,1.0,0.0,0.0,0.0,0.0,0.5,0.0,1.0,1.0,0.0,0.0,0.5,0.5,0.5,1.0,0.0,0.0,1.0,1.0,0.5,1.0,0.0,1.0,1.0,22.5,'Aprobado con observaciones menores','ok\r\n','2025-11-30 18:46:47'),(12,10,'DOC001',1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,0.5,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,25.5,'Aprobado','aprobado\r\n','2025-11-30 21:13:00'),(13,10,'DOC002',1.0,1.0,1.0,1.0,1.0,0.5,1.0,0.5,0.5,0.0,0.0,1.0,1.0,0.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,0.0,0.0,0.0,0.0,29.5,'Aprobado','programa sustentacion','2025-11-30 21:13:39'),(14,10,'DOC005',1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,0.0,0.0,0.0,35.0,'Aprobado','aprobado','2025-11-30 21:14:25');
/*!40000 ALTER TABLE `evaluaciones_jurado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notificaciones`
--

DROP TABLE IF EXISTS `notificaciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notificaciones` (
  `id_notificacion` int NOT NULL AUTO_INCREMENT,
  `codigo_usuario_destino` varchar(20) NOT NULL,
  `mensaje` text NOT NULL,
  `tipo` enum('Sistema','Email_Simulado','SMS_Simulado') NOT NULL,
  `leido` tinyint(1) DEFAULT '0',
  `fecha_envio` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_notificacion`),
  KEY `fk_notificacion_usuario` (`codigo_usuario_destino`),
  CONSTRAINT `fk_notificacion_usuario` FOREIGN KEY (`codigo_usuario_destino`) REFERENCES `usuarios` (`codigo`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=292 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notificaciones`
--

LOCK TABLES `notificaciones` WRITE;
/*!40000 ALTER TABLE `notificaciones` DISABLE KEYS */;
INSERT INTO `notificaciones` VALUES (15,'DOC001','Su trámite de titulación ha cambiado al estado: Ha sido designado Presidente de Jurado.','Sistema',0,'2025-11-21 03:48:29'),(16,'DOC001','Copia de correo enviado: Su trámite de titulación ha cambiado al estado: Ha sido designado Presidente de Jurado.','Email_Simulado',0,'2025-11-21 03:48:29'),(17,'DOC002','Su trámite de titulación ha cambiado al estado: Ha sido designado Secretario de Jurado.','Sistema',0,'2025-11-21 03:48:29'),(18,'DOC002','Copia de correo enviado: Su trámite de titulación ha cambiado al estado: Ha sido designado Secretario de Jurado.','Email_Simulado',0,'2025-11-21 03:48:29'),(19,'DOC005','Su trámite de titulación ha cambiado al estado: Ha sido designado Vocal de Jurado.','Sistema',0,'2025-11-21 03:48:29'),(20,'DOC005','Copia de correo enviado: Su trámite de titulación ha cambiado al estado: Ha sido designado Vocal de Jurado.','Email_Simulado',0,'2025-11-21 03:48:29'),(25,'ALU001','Hemos recibido su documento: Bachiller. Está pendiente de validación.','Sistema',0,'2025-11-21 04:47:01'),(26,'ALU001','Copia de correo enviado: Hemos recibido su documento: Bachiller. Está pendiente de validación.','Email_Simulado',0,'2025-11-21 04:47:01'),(27,'ALU001','Su trámite de titulación ha cambiado al estado: Documento Aprobado','Sistema',0,'2025-11-21 04:47:27'),(28,'ALU001','Copia de correo enviado: Su trámite de titulación ha cambiado al estado: Documento Aprobado','Email_Simulado',0,'2025-11-21 04:47:27'),(29,'ALU001','Hemos recibido su documento: DNI. Está pendiente de validación.','Sistema',0,'2025-11-21 04:50:19'),(30,'ALU001','Copia de correo enviado: Hemos recibido su documento: DNI. Está pendiente de validación.','Email_Simulado',0,'2025-11-21 04:50:19'),(31,'ALU001','Su trámite de titulación ha cambiado al estado: Documento Rechazado: ta feo','Sistema',0,'2025-11-21 04:50:41'),(32,'ALU001','Copia de correo enviado: Su trámite de titulación ha cambiado al estado: Documento Rechazado: ta feo','Email_Simulado',0,'2025-11-21 04:50:41'),(33,'ALU001','Hemos recibido su documento: DNI. Está pendiente de validación.','Sistema',0,'2025-11-21 04:50:54'),(34,'ALU001','Copia de correo enviado: Hemos recibido su documento: DNI. Está pendiente de validación.','Email_Simulado',0,'2025-11-21 04:50:54'),(35,'ALU001','Su trámite de titulación ha cambiado al estado: Documento Aprobado','Sistema',0,'2025-11-21 04:51:04'),(36,'ALU001','Copia de correo enviado: Su trámite de titulación ha cambiado al estado: Documento Aprobado','Email_Simulado',0,'2025-11-21 04:51:04'),(37,'ALU001','Hemos recibido su documento: Certificado_Idiomas. Está pendiente de validación.','Sistema',0,'2025-11-21 05:16:44'),(38,'ALU001','Copia de correo enviado: Hemos recibido su documento: Certificado_Idiomas. Está pendiente de validación.','Email_Simulado',0,'2025-11-21 05:16:44'),(39,'ALU001','Su trámite de titulación ha cambiado al estado: Documento Rechazado: error de archivo','Sistema',0,'2025-11-21 05:17:02'),(40,'ALU001','Copia de correo enviado: Su trámite de titulación ha cambiado al estado: Documento Rechazado: error de archivo','Email_Simulado',0,'2025-11-21 05:17:02'),(41,'ALU001','Hemos recibido su documento: Foto. Está pendiente de validación.','Sistema',0,'2025-11-21 05:32:24'),(42,'ALU001','Copia de correo enviado: Hemos recibido su documento: Foto. Está pendiente de validación.','Email_Simulado',0,'2025-11-21 05:32:24'),(43,'ALU001','Su trámite de titulación ha cambiado al estado: Documento Rechazado: ver archivo correcto','Sistema',0,'2025-11-21 05:32:42'),(44,'ALU001','Copia de correo enviado: Su trámite de titulación ha cambiado al estado: Documento Rechazado: ver archivo correcto','Email_Simulado',0,'2025-11-21 05:32:42'),(45,'ALU001','Hemos recibido su documento: Foto. Está pendiente de validación.','Sistema',0,'2025-11-21 05:39:56'),(46,'ALU001','Copia de correo enviado: Hemos recibido su documento: Foto. Está pendiente de validación.','Email_Simulado',0,'2025-11-21 05:39:56'),(47,'ALU001','Su trámite de titulación ha cambiado al estado: Documento Aprobado','Sistema',0,'2025-11-21 05:40:03'),(48,'ALU001','Copia de correo enviado: Su trámite de titulación ha cambiado al estado: Documento Aprobado','Email_Simulado',0,'2025-11-21 05:40:03'),(49,'ALU001','Hemos recibido su documento: Certificado_Idiomas. Está pendiente de validación.','Sistema',0,'2025-11-21 13:45:53'),(50,'ALU001','Copia de correo enviado: Hemos recibido su documento: Certificado_Idiomas. Está pendiente de validación.','Email_Simulado',0,'2025-11-21 13:45:53'),(51,'ALU001','Su trámite de titulación ha cambiado al estado: Documento Aprobado','Sistema',0,'2025-11-21 13:46:24'),(52,'ALU001','Copia de correo enviado: Su trámite de titulación ha cambiado al estado: Documento Aprobado','Email_Simulado',0,'2025-11-21 13:46:24'),(53,'ALU001','Su trámite de titulación ha cambiado al estado: Revisión de Carpeta','Sistema',0,'2025-11-21 13:48:11'),(54,'ALU001','Copia de correo enviado: Su trámite de titulación ha cambiado al estado: Revisión de Carpeta','Email_Simulado',0,'2025-11-21 13:48:11'),(57,'DOC001','Su trámite de titulación ha cambiado al estado: Designado Presidente de Jurado','Sistema',0,'2025-11-21 13:49:20'),(58,'DOC001','Copia de correo enviado: Su trámite de titulación ha cambiado al estado: Designado Presidente de Jurado','Email_Simulado',0,'2025-11-21 13:49:20'),(59,'DOC002','Su trámite de titulación ha cambiado al estado: Designado Secretario de Jurado','Sistema',0,'2025-11-21 13:49:20'),(60,'DOC002','Copia de correo enviado: Su trámite de titulación ha cambiado al estado: Designado Secretario de Jurado','Email_Simulado',0,'2025-11-21 13:49:20'),(61,'DOC005','Su trámite de titulación ha cambiado al estado: Designado Vocal de Jurado','Sistema',0,'2025-11-21 13:49:20'),(62,'DOC005','Copia de correo enviado: Su trámite de titulación ha cambiado al estado: Designado Vocal de Jurado','Email_Simulado',0,'2025-11-21 13:49:20'),(63,'ALU001','Su trámite de titulación ha cambiado al estado: Designación de Jurado','Sistema',0,'2025-11-21 13:49:38'),(64,'ALU001','Copia de correo enviado: Su trámite de titulación ha cambiado al estado: Designación de Jurado','Email_Simulado',0,'2025-11-21 13:49:38'),(65,'ALU001','Su trámite de titulación ha cambiado al estado: Sustentación Programada','Sistema',0,'2025-11-21 13:50:39'),(66,'ALU001','Copia de correo enviado: Su trámite de titulación ha cambiado al estado: Sustentación Programada','Email_Simulado',0,'2025-11-21 13:50:39'),(67,'DOC005','Su trámite de titulación ha cambiado al estado: Designado Presidente de Jurado','Sistema',0,'2025-11-21 13:50:39'),(68,'DOC005','Copia de correo enviado: Su trámite de titulación ha cambiado al estado: Designado Presidente de Jurado','Email_Simulado',0,'2025-11-21 13:50:39'),(69,'DOC001','Su trámite de titulación ha cambiado al estado: Designado Secretario de Jurado','Sistema',0,'2025-11-21 13:50:39'),(70,'DOC001','Copia de correo enviado: Su trámite de titulación ha cambiado al estado: Designado Secretario de Jurado','Email_Simulado',0,'2025-11-21 13:50:39'),(71,'DOC006','Su trámite de titulación ha cambiado al estado: Designado Vocal de Jurado','Sistema',0,'2025-11-21 13:50:39'),(72,'DOC006','Copia de correo enviado: Su trámite de titulación ha cambiado al estado: Designado Vocal de Jurado','Email_Simulado',0,'2025-11-21 13:50:39'),(73,'T01236B','Hemos recibido su documento: DNI. Está pendiente de validación.','Sistema',0,'2025-11-21 16:01:51'),(74,'T01236B','Copia de correo enviado: Hemos recibido su documento: DNI. Está pendiente de validación.','Email_Simulado',0,'2025-11-21 16:01:51'),(75,'T01236B','Su trámite de titulación ha cambiado al estado: Sustentación Programada','Sistema',0,'2025-11-21 16:02:42'),(76,'T01236B','Copia de correo enviado: Su trámite de titulación ha cambiado al estado: Sustentación Programada','Email_Simulado',0,'2025-11-21 16:02:42'),(77,'DOC005','Su trámite de titulación ha cambiado al estado: Designado Presidente de Jurado','Sistema',0,'2025-11-21 16:02:42'),(78,'DOC005','Copia de correo enviado: Su trámite de titulación ha cambiado al estado: Designado Presidente de Jurado','Email_Simulado',0,'2025-11-21 16:02:42'),(79,'DOC001','Su trámite de titulación ha cambiado al estado: Designado Secretario de Jurado','Sistema',0,'2025-11-21 16:02:42'),(80,'DOC001','Copia de correo enviado: Su trámite de titulación ha cambiado al estado: Designado Secretario de Jurado','Email_Simulado',0,'2025-11-21 16:02:42'),(81,'DOC002','Su trámite de titulación ha cambiado al estado: Designado Vocal de Jurado','Sistema',0,'2025-11-21 16:02:42'),(82,'DOC002','Copia de correo enviado: Su trámite de titulación ha cambiado al estado: Designado Vocal de Jurado','Email_Simulado',0,'2025-11-21 16:02:42'),(83,'T01303C','Hemos recibido su documento: DNI. Está pendiente de validación.','Sistema',0,'2025-11-21 17:20:31'),(84,'T01303C','Copia de correo enviado: Hemos recibido su documento: DNI. Está pendiente de validación.','Email_Simulado',0,'2025-11-21 17:20:31'),(85,'T01303C','Hemos recibido su documento: Bachiller. Está pendiente de validación.','Sistema',0,'2025-11-21 17:20:39'),(86,'T01303C','Copia de correo enviado: Hemos recibido su documento: Bachiller. Está pendiente de validación.','Email_Simulado',0,'2025-11-21 17:20:39'),(87,'T01303C','Hemos recibido su documento: Certificado_Idiomas. Está pendiente de validación.','Sistema',0,'2025-11-21 17:20:48'),(88,'T01303C','Copia de correo enviado: Hemos recibido su documento: Certificado_Idiomas. Está pendiente de validación.','Email_Simulado',0,'2025-11-21 17:20:48'),(89,'T01303C','Hemos recibido su documento: Foto. Está pendiente de validación.','Sistema',0,'2025-11-21 17:20:58'),(90,'T01303C','Copia de correo enviado: Hemos recibido su documento: Foto. Está pendiente de validación.','Email_Simulado',0,'2025-11-21 17:20:58'),(91,'T01303C','Su trámite de titulación ha cambiado al estado: Documento Aprobado','Sistema',0,'2025-11-21 17:21:17'),(92,'T01303C','Copia de correo enviado: Su trámite de titulación ha cambiado al estado: Documento Aprobado','Email_Simulado',0,'2025-11-21 17:21:17'),(93,'T01303C','Su trámite de titulación ha cambiado al estado: Documento Aprobado','Sistema',0,'2025-11-21 17:21:20'),(94,'T01303C','Copia de correo enviado: Su trámite de titulación ha cambiado al estado: Documento Aprobado','Email_Simulado',0,'2025-11-21 17:21:20'),(95,'T01303C','Su trámite de titulación ha cambiado al estado: Documento Aprobado','Sistema',0,'2025-11-21 17:21:23'),(96,'T01303C','Copia de correo enviado: Su trámite de titulación ha cambiado al estado: Documento Aprobado','Email_Simulado',0,'2025-11-21 17:21:23'),(97,'T01303C','Su trámite de titulación ha cambiado al estado: Documento Aprobado','Sistema',0,'2025-11-21 17:21:25'),(98,'T01303C','Copia de correo enviado: Su trámite de titulación ha cambiado al estado: Documento Aprobado','Email_Simulado',0,'2025-11-21 17:21:25'),(99,'T01303C','Su trámite de titulación ha cambiado al estado: Sustentación Programada','Sistema',0,'2025-11-21 17:21:54'),(100,'T01303C','Copia de correo enviado: Su trámite de titulación ha cambiado al estado: Sustentación Programada','Email_Simulado',0,'2025-11-21 17:21:54'),(101,'DOC006','Su trámite de titulación ha cambiado al estado: Designado Presidente de Jurado','Sistema',0,'2025-11-21 17:21:54'),(102,'DOC006','Copia de correo enviado: Su trámite de titulación ha cambiado al estado: Designado Presidente de Jurado','Email_Simulado',0,'2025-11-21 17:21:54'),(103,'DOC001','Su trámite de titulación ha cambiado al estado: Designado Secretario de Jurado','Sistema',0,'2025-11-21 17:21:54'),(104,'DOC001','Copia de correo enviado: Su trámite de titulación ha cambiado al estado: Designado Secretario de Jurado','Email_Simulado',0,'2025-11-21 17:21:54'),(105,'DOC002','Su trámite de titulación ha cambiado al estado: Designado Vocal de Jurado','Sistema',0,'2025-11-21 17:21:54'),(106,'DOC002','Copia de correo enviado: Su trámite de titulación ha cambiado al estado: Designado Vocal de Jurado','Email_Simulado',0,'2025-11-21 17:21:54'),(107,'T01303C','Su trámite de titulación ha cambiado al estado: Sustentación Programada','Sistema',0,'2025-11-21 17:59:02'),(108,'T01303C','Copia de correo enviado: Su trámite de titulación ha cambiado al estado: Sustentación Programada','Email_Simulado',0,'2025-11-21 17:59:02'),(109,'DOC002','Su trámite de titulación ha cambiado al estado: Designado Presidente de Jurado','Sistema',0,'2025-11-21 17:59:02'),(110,'DOC002','Copia de correo enviado: Su trámite de titulación ha cambiado al estado: Designado Presidente de Jurado','Email_Simulado',0,'2025-11-21 17:59:02'),(111,'DOC002','Su trámite de titulación ha cambiado al estado: Designado Secretario de Jurado','Sistema',0,'2025-11-21 17:59:02'),(112,'DOC002','Copia de correo enviado: Su trámite de titulación ha cambiado al estado: Designado Secretario de Jurado','Email_Simulado',0,'2025-11-21 17:59:02'),(113,'DOC001','Su trámite de titulación ha cambiado al estado: Designado Vocal de Jurado','Sistema',0,'2025-11-21 17:59:02'),(114,'DOC001','Copia de correo enviado: Su trámite de titulación ha cambiado al estado: Designado Vocal de Jurado','Email_Simulado',0,'2025-11-21 17:59:02'),(115,'T01303C','Su trámite de titulación ha cambiado al estado: Resolución de Designación de Jurado emitida. Verifique su panel.','Sistema',0,'2025-11-27 23:21:43'),(116,'T01303C','Copia de correo enviado: Su trámite de titulación ha cambiado al estado: Resolución de Designación de Jurado emitida. Verifique su panel.','Email_Simulado',0,'2025-11-27 23:21:43'),(117,'DOC002','Su trámite de titulación ha cambiado al estado: Ha sido designado mediante Resolución como Miembro de Jurado para el estudiante T01303C (Miembro 1)','Sistema',0,'2025-11-27 23:21:43'),(118,'DOC002','Copia de correo enviado: Su trámite de titulación ha cambiado al estado: Ha sido designado mediante Resolución como Miembro de Jurado para el estudiante T01303C (Miembro 1)','Email_Simulado',0,'2025-11-27 23:21:43'),(119,'DOC002','Su trámite de titulación ha cambiado al estado: Ha sido designado mediante Resolución como Miembro de Jurado para el estudiante T01303C (Miembro 2)','Sistema',0,'2025-11-27 23:21:43'),(120,'DOC002','Copia de correo enviado: Su trámite de titulación ha cambiado al estado: Ha sido designado mediante Resolución como Miembro de Jurado para el estudiante T01303C (Miembro 2)','Email_Simulado',0,'2025-11-27 23:21:43'),(121,'DOC001','Su trámite de titulación ha cambiado al estado: Ha sido designado mediante Resolución como Miembro de Jurado para el estudiante T01303C (Miembro 3)','Sistema',0,'2025-11-27 23:21:43'),(122,'DOC001','Copia de correo enviado: Su trámite de titulación ha cambiado al estado: Ha sido designado mediante Resolución como Miembro de Jurado para el estudiante T01303C (Miembro 3)','Email_Simulado',0,'2025-11-27 23:21:43'),(123,'DOC005','Su trámite de titulación ha cambiado al estado: Ha sido designado mediante Resolución como Miembro de Jurado para el estudiante T01303C (Suplente)','Sistema',0,'2025-11-27 23:21:43'),(124,'DOC005','Copia de correo enviado: Su trámite de titulación ha cambiado al estado: Ha sido designado mediante Resolución como Miembro de Jurado para el estudiante T01303C (Suplente)','Email_Simulado',0,'2025-11-27 23:21:43'),(125,'T01303C','Su trámite de titulación ha cambiado al estado: Resolución de Designación de Jurado emitida. Verifique su panel.','Sistema',0,'2025-11-27 23:43:58'),(126,'T01303C','Copia de correo enviado: Su trámite de titulación ha cambiado al estado: Resolución de Designación de Jurado emitida. Verifique su panel.','Email_Simulado',0,'2025-11-27 23:43:58'),(127,'DOC002','Su trámite de titulación ha cambiado al estado: Ha sido designado mediante Resolución como Miembro de Jurado para el estudiante T01303C (Miembro 1)','Sistema',0,'2025-11-27 23:43:58'),(128,'DOC002','Copia de correo enviado: Su trámite de titulación ha cambiado al estado: Ha sido designado mediante Resolución como Miembro de Jurado para el estudiante T01303C (Miembro 1)','Email_Simulado',0,'2025-11-27 23:43:58'),(129,'DOC002','Su trámite de titulación ha cambiado al estado: Ha sido designado mediante Resolución como Miembro de Jurado para el estudiante T01303C (Miembro 2)','Sistema',0,'2025-11-27 23:43:58'),(130,'DOC002','Copia de correo enviado: Su trámite de titulación ha cambiado al estado: Ha sido designado mediante Resolución como Miembro de Jurado para el estudiante T01303C (Miembro 2)','Email_Simulado',0,'2025-11-27 23:43:58'),(131,'DOC001','Su trámite de titulación ha cambiado al estado: Ha sido designado mediante Resolución como Miembro de Jurado para el estudiante T01303C (Miembro 3)','Sistema',0,'2025-11-27 23:43:58'),(132,'DOC001','Copia de correo enviado: Su trámite de titulación ha cambiado al estado: Ha sido designado mediante Resolución como Miembro de Jurado para el estudiante T01303C (Miembro 3)','Email_Simulado',0,'2025-11-27 23:43:58'),(133,'DOC005','Su trámite de titulación ha cambiado al estado: Ha sido designado mediante Resolución como Miembro de Jurado para el estudiante T01303C (Suplente)','Sistema',0,'2025-11-27 23:43:58'),(134,'DOC005','Copia de correo enviado: Su trámite de titulación ha cambiado al estado: Ha sido designado mediante Resolución como Miembro de Jurado para el estudiante T01303C (Suplente)','Email_Simulado',0,'2025-11-27 23:43:58'),(135,'T01303C','Su trámite de titulación ha cambiado al estado: Resolución de Designación de Jurado emitida. Verifique su panel.','Sistema',0,'2025-11-28 00:00:11'),(136,'T01303C','Copia de correo enviado: Su trámite de titulación ha cambiado al estado: Resolución de Designación de Jurado emitida. Verifique su panel.','Email_Simulado',0,'2025-11-28 00:00:11'),(137,'DOC002','Su trámite de titulación ha cambiado al estado: Ha sido designado mediante Resolución como Miembro de Jurado para el estudiante T01303C (Miembro 1)','Sistema',0,'2025-11-28 00:00:11'),(138,'DOC002','Copia de correo enviado: Su trámite de titulación ha cambiado al estado: Ha sido designado mediante Resolución como Miembro de Jurado para el estudiante T01303C (Miembro 1)','Email_Simulado',0,'2025-11-28 00:00:11'),(139,'DOC002','Su trámite de titulación ha cambiado al estado: Ha sido designado mediante Resolución como Miembro de Jurado para el estudiante T01303C (Miembro 2)','Sistema',0,'2025-11-28 00:00:11'),(140,'DOC002','Copia de correo enviado: Su trámite de titulación ha cambiado al estado: Ha sido designado mediante Resolución como Miembro de Jurado para el estudiante T01303C (Miembro 2)','Email_Simulado',0,'2025-11-28 00:00:11'),(141,'DOC001','Su trámite de titulación ha cambiado al estado: Ha sido designado mediante Resolución como Miembro de Jurado para el estudiante T01303C (Miembro 3)','Sistema',0,'2025-11-28 00:00:11'),(142,'DOC001','Copia de correo enviado: Su trámite de titulación ha cambiado al estado: Ha sido designado mediante Resolución como Miembro de Jurado para el estudiante T01303C (Miembro 3)','Email_Simulado',0,'2025-11-28 00:00:11'),(143,'DOC005','Su trámite de titulación ha cambiado al estado: Ha sido designado mediante Resolución como Miembro de Jurado para el estudiante T01303C (Suplente)','Sistema',0,'2025-11-28 00:00:11'),(144,'DOC005','Copia de correo enviado: Su trámite de titulación ha cambiado al estado: Ha sido designado mediante Resolución como Miembro de Jurado para el estudiante T01303C (Suplente)','Email_Simulado',0,'2025-11-28 00:00:11'),(145,'T01303C','Su trámite de titulación ha cambiado al estado: Resolución de Designación de Jurado emitida. Verifique su panel.','Sistema',0,'2025-11-28 00:02:52'),(146,'T01303C','Copia de correo enviado: Su trámite de titulación ha cambiado al estado: Resolución de Designación de Jurado emitida. Verifique su panel.','Email_Simulado',0,'2025-11-28 00:02:52'),(147,'DOC001','Su trámite de titulación ha cambiado al estado: Ha sido designado mediante Resolución como Miembro de Jurado para el estudiante T01303C (Miembro 1)','Sistema',0,'2025-11-28 00:02:52'),(148,'DOC001','Copia de correo enviado: Su trámite de titulación ha cambiado al estado: Ha sido designado mediante Resolución como Miembro de Jurado para el estudiante T01303C (Miembro 1)','Email_Simulado',0,'2025-11-28 00:02:52'),(149,'DOC002','Su trámite de titulación ha cambiado al estado: Ha sido designado mediante Resolución como Miembro de Jurado para el estudiante T01303C (Miembro 2)','Sistema',0,'2025-11-28 00:02:52'),(150,'DOC002','Copia de correo enviado: Su trámite de titulación ha cambiado al estado: Ha sido designado mediante Resolución como Miembro de Jurado para el estudiante T01303C (Miembro 2)','Email_Simulado',0,'2025-11-28 00:02:52'),(151,'DOC005','Su trámite de titulación ha cambiado al estado: Ha sido designado mediante Resolución como Miembro de Jurado para el estudiante T01303C (Miembro 3)','Sistema',0,'2025-11-28 00:02:52'),(152,'DOC005','Copia de correo enviado: Su trámite de titulación ha cambiado al estado: Ha sido designado mediante Resolución como Miembro de Jurado para el estudiante T01303C (Miembro 3)','Email_Simulado',0,'2025-11-28 00:02:52'),(153,'DOC006','Su trámite de titulación ha cambiado al estado: Ha sido designado mediante Resolución como Miembro de Jurado para el estudiante T01303C (Suplente)','Sistema',0,'2025-11-28 00:02:52'),(154,'DOC006','Copia de correo enviado: Su trámite de titulación ha cambiado al estado: Ha sido designado mediante Resolución como Miembro de Jurado para el estudiante T01303C (Suplente)','Email_Simulado',0,'2025-11-28 00:02:52'),(155,'T01303C','Su trámite de titulación ha cambiado al estado: Iniciado','Sistema',0,'2025-11-28 00:27:27'),(156,'T01303C','Copia de correo enviado: Su trámite de titulación ha cambiado al estado: Iniciado','Email_Simulado',0,'2025-11-28 00:27:27'),(157,'T01303C','Su trámite de titulación ha cambiado al estado: Resolución de Designación de Jurado emitida. Verifique su panel.','Sistema',0,'2025-11-28 00:27:39'),(158,'T01303C','Copia de correo enviado: Su trámite de titulación ha cambiado al estado: Resolución de Designación de Jurado emitida. Verifique su panel.','Email_Simulado',0,'2025-11-28 00:27:39'),(159,'DOC001','Su trámite de titulación ha cambiado al estado: Ha sido designado mediante Resolución como Miembro de Jurado para el estudiante T01303C (Miembro 1)','Sistema',0,'2025-11-28 00:27:39'),(160,'DOC001','Copia de correo enviado: Su trámite de titulación ha cambiado al estado: Ha sido designado mediante Resolución como Miembro de Jurado para el estudiante T01303C (Miembro 1)','Email_Simulado',0,'2025-11-28 00:27:39'),(161,'DOC002','Su trámite de titulación ha cambiado al estado: Ha sido designado mediante Resolución como Miembro de Jurado para el estudiante T01303C (Miembro 2)','Sistema',0,'2025-11-28 00:27:39'),(162,'DOC002','Copia de correo enviado: Su trámite de titulación ha cambiado al estado: Ha sido designado mediante Resolución como Miembro de Jurado para el estudiante T01303C (Miembro 2)','Email_Simulado',0,'2025-11-28 00:27:39'),(163,'DOC005','Su trámite de titulación ha cambiado al estado: Ha sido designado mediante Resolución como Miembro de Jurado para el estudiante T01303C (Miembro 3)','Sistema',0,'2025-11-28 00:27:39'),(164,'DOC005','Copia de correo enviado: Su trámite de titulación ha cambiado al estado: Ha sido designado mediante Resolución como Miembro de Jurado para el estudiante T01303C (Miembro 3)','Email_Simulado',0,'2025-11-28 00:27:39'),(165,'DOC006','Su trámite de titulación ha cambiado al estado: Ha sido designado mediante Resolución como Miembro de Jurado para el estudiante T01303C (Suplente)','Sistema',0,'2025-11-28 00:27:39'),(166,'DOC006','Copia de correo enviado: Su trámite de titulación ha cambiado al estado: Ha sido designado mediante Resolución como Miembro de Jurado para el estudiante T01303C (Suplente)','Email_Simulado',0,'2025-11-28 00:27:39'),(167,'T01303C','Su trámite de titulación ha cambiado al estado: Resolución de Designación de Jurado emitida. Verifique su panel.','Sistema',0,'2025-11-28 00:33:12'),(168,'DOC001','Su trámite de titulación ha cambiado al estado: Ha sido designado mediante Resolución como Miembro de Jurado para el estudiante T01303C (Miembro 1)','Sistema',0,'2025-11-28 00:33:12'),(169,'DOC002','Su trámite de titulación ha cambiado al estado: Ha sido designado mediante Resolución como Miembro de Jurado para el estudiante T01303C (Miembro 2)','Sistema',0,'2025-11-28 00:33:12'),(170,'DOC005','Su trámite de titulación ha cambiado al estado: Ha sido designado mediante Resolución como Miembro de Jurado para el estudiante T01303C (Miembro 3)','Sistema',0,'2025-11-28 00:33:12'),(171,'DOC006','Su trámite de titulación ha cambiado al estado: Ha sido designado mediante Resolución como Miembro de Jurado para el estudiante T01303C (Suplente)','Sistema',0,'2025-11-28 00:33:12'),(172,'ALU001','Su trámite de titulación ha cambiado al estado: Resolución de Designación de Jurado emitida. Verifique su panel.','Sistema',0,'2025-11-28 01:15:05'),(173,'DOC002','Su trámite de titulación ha cambiado al estado: Ha sido designado mediante Resolución como Miembro de Jurado para el estudiante ALU001 (Miembro 1)','Sistema',0,'2025-11-28 01:15:05'),(174,'DOC005','Su trámite de titulación ha cambiado al estado: Ha sido designado mediante Resolución como Miembro de Jurado para el estudiante ALU001 (Miembro 2)','Sistema',0,'2025-11-28 01:15:05'),(175,'DOC006','Su trámite de titulación ha cambiado al estado: Ha sido designado mediante Resolución como Miembro de Jurado para el estudiante ALU001 (Miembro 3)','Sistema',0,'2025-11-28 01:15:05'),(176,'DOC007','Su trámite de titulación ha cambiado al estado: Ha sido designado mediante Resolución como Miembro de Jurado para el estudiante ALU001 (Suplente)','Sistema',0,'2025-11-28 01:15:05'),(177,'T01303C','Su trámite de titulación ha cambiado al estado: Iniciado','Sistema',0,'2025-11-29 22:51:01'),(178,'T01303C','Su trámite de titulación ha cambiado al estado: Titulado','Sistema',0,'2025-11-29 22:51:08'),(179,'DOC001','Su trámite de titulación ha cambiado al estado: Se le ha reasignado un nuevo proyecto de tesis (ID: 21).','Sistema',0,'2025-11-30 00:17:17'),(180,'ALU001','Su trámite de titulación ha cambiado al estado: ¡FELICITACIONES! Has aprobado la sustentación y obtenido el título.','Sistema',0,'2025-11-30 01:30:22'),(181,'ALU001','Hemos recibido su documento: Constancia_Practicas. Está pendiente de validación.','Sistema',0,'2025-11-30 01:37:08'),(182,'ALU002','Hemos recibido su documento: DNI. Está pendiente de validación.','Sistema',0,'2025-11-30 01:51:05'),(183,'ADM001','El Jurado Raúl Enrique Fernández Bejarano ha emitido su voto para la Sustentación #3. Nota: 3.0','Sistema',0,'2025-11-30 01:52:15'),(184,'ALU002','Hemos recibido su documento: Bachiller. Está pendiente de validación.','Sistema',0,'2025-11-30 01:57:33'),(185,'ADM001','El estudiante Jefferson Luis Paredes Yauri (ALU002) ha subido el documento: Bachiller','Sistema',0,'2025-11-30 01:57:33'),(187,'DOC007','Su trámite de titulación ha cambiado al estado: Resolución: Ha sido designado como ASESOR del proyecto \'Implementación de un dashboard de gestión de créditos como herramienta para la toma de decisiones en la C.M.A.C.\' del estudiante T00049K. El borrador está disponible en su bandeja de \'Tesis Asignadas\'.','Sistema',0,'2025-11-30 02:45:41'),(188,'DOC007','Su trámite de titulación ha cambiado al estado: Se le ha reasignado un nuevo proyecto de tesis (ID: 24).','Sistema',0,'2025-11-30 04:20:15'),(190,'DOC006','Su trámite de titulación ha cambiado al estado: Resolución: Ha sido designado como ASESOR del proyecto \'aaa\' del estudiante aa. El borrador está disponible en su bandeja de \'Tesis Asignadas\'.','Sistema',0,'2025-11-30 04:31:37'),(191,'ADM001','El Asesor Juan Carlos Cotrina Aliaga ha evaluado la Tesis #25. Resultado: Desaprobado con observaciones mayores','Sistema',0,'2025-11-30 04:32:49'),(193,'DOC001','Su trámite de titulación ha cambiado al estado: Designado Presidente de Jurado','Sistema',0,'2025-11-30 04:35:05'),(194,'DOC002','Su trámite de titulación ha cambiado al estado: Designado Secretario de Jurado','Sistema',0,'2025-11-30 04:35:05'),(195,'DOC005','Su trámite de titulación ha cambiado al estado: Designado Vocal de Jurado','Sistema',0,'2025-11-30 04:35:05'),(196,'ADM001','El Jurado Raúl Enrique Fernández Bejarano ha emitido su voto para la Sustentación #7. Nota: 33.0','Sistema',0,'2025-11-30 04:36:10'),(197,'DOC001','Su trámite de titulación ha cambiado al estado: Se le ha reasignado un nuevo proyecto de tesis (ID: 24).','Sistema',0,'2025-11-30 04:53:52'),(198,'ADM001','El Asesor Raúl Enrique Fernández Bejarano ha evaluado la Tesis #24. Resultado: Desaprobado con observaciones mayores','Sistema',0,'2025-11-30 04:55:37'),(199,'T01236B','Su trámite de titulación ha cambiado al estado: Documento Validado','Sistema',0,'2025-11-30 05:38:54'),(200,'ALU002','Su trámite de titulación ha cambiado al estado: Plan de Tesis registrado: Sistema Web para Educación con Inteligencia Artificial','Sistema',0,'2025-11-30 17:07:43'),(201,'DOC002','Su trámite de titulación ha cambiado al estado: Nueva tesis asignada: Sistema Web para Educación con Inteligencia Artificial','Sistema',0,'2025-11-30 17:07:43'),(202,'ADM001','El Asesor Karina Luz Cruz Oscanoa ha evaluado la Tesis #26. Resultado: Aprobado','Sistema',0,'2025-11-30 17:26:10'),(203,'ALU002','Su trámite de titulación ha cambiado al estado: Documento Validado','Sistema',0,'2025-11-30 17:27:56'),(204,'ALU002','Su trámite de titulación ha cambiado al estado: Documento Validado','Sistema',0,'2025-11-30 17:27:57'),(205,'DOC001','Su trámite de titulación ha cambiado al estado: Designado Presidente de Jurado','Sistema',0,'2025-11-30 17:55:29'),(206,'DOC002','Su trámite de titulación ha cambiado al estado: Designado Secretario de Jurado','Sistema',0,'2025-11-30 17:55:29'),(207,'DOC006','Su trámite de titulación ha cambiado al estado: Designado Vocal de Jurado','Sistema',0,'2025-11-30 17:55:29'),(208,'ALU002','Su trámite de titulación ha cambiado al estado: Designación de Jurado','Sistema',0,'2025-11-30 17:55:29'),(209,'DOC001','Su trámite de titulación ha cambiado al estado: Designado Presidente de Jurado','Sistema',0,'2025-11-30 18:09:48'),(210,'DOC002','Su trámite de titulación ha cambiado al estado: Designado Secretario de Jurado','Sistema',0,'2025-11-30 18:09:48'),(211,'DOC006','Su trámite de titulación ha cambiado al estado: Designado Vocal de Jurado','Sistema',0,'2025-11-30 18:09:48'),(212,'ALU002','Su trámite de titulación ha cambiado al estado: Designación de Jurado','Sistema',0,'2025-11-30 18:09:48'),(213,'DOC001','Su trámite de titulación ha cambiado al estado: Designado Presidente de Jurado','Sistema',0,'2025-11-30 18:13:09'),(214,'DOC002','Su trámite de titulación ha cambiado al estado: Designado Secretario de Jurado','Sistema',0,'2025-11-30 18:13:09'),(215,'DOC006','Su trámite de titulación ha cambiado al estado: Designado Vocal de Jurado','Sistema',0,'2025-11-30 18:13:09'),(216,'ALU002','Su trámite de titulación ha cambiado al estado: Designación de Jurado','Sistema',0,'2025-11-30 18:13:09'),(217,'DOC005','Su trámite de titulación ha cambiado al estado: Designado Presidente de Jurado','Sistema',0,'2025-11-30 18:13:24'),(218,'DOC002','Su trámite de titulación ha cambiado al estado: Designado Secretario de Jurado','Sistema',0,'2025-11-30 18:13:24'),(219,'DOC007','Su trámite de titulación ha cambiado al estado: Designado Vocal de Jurado','Sistema',0,'2025-11-30 18:13:24'),(220,'ALU002','Su trámite de titulación ha cambiado al estado: Designación de Jurado','Sistema',0,'2025-11-30 18:13:24'),(221,'DOC005','Su trámite de titulación ha cambiado al estado: Designado Presidente de Jurado','Sistema',0,'2025-11-30 18:43:40'),(222,'DOC002','Su trámite de titulación ha cambiado al estado: Designado Secretario de Jurado','Sistema',0,'2025-11-30 18:43:40'),(223,'DOC007','Su trámite de titulación ha cambiado al estado: Designado Vocal de Jurado','Sistema',0,'2025-11-30 18:43:40'),(224,'ALU002','Su trámite de titulación ha cambiado al estado: Designación de Jurado','Sistema',0,'2025-11-30 18:43:40'),(225,'ADM001','El Jurado Karina Luz Cruz Oscanoa ha emitido su voto para la Sustentación #9. Nota: 38.0','Sistema',0,'2025-11-30 18:44:32'),(226,'ADM001','El Jurado Walter Estares Ventocilla ha emitido su voto para la Sustentación #9. Nota: 27.5','Sistema',0,'2025-11-30 18:45:35'),(227,'ADM001','El Jurado Herberth Aranda Rojas ha emitido su voto para la Sustentación #9. Nota: 22.5','Sistema',0,'2025-11-30 18:46:47'),(228,'ALU002','Su trámite de titulación ha cambiado al estado: Sustentación Programada','Sistema',0,'2025-11-30 18:47:16'),(229,'DOC005','Su trámite de titulación ha cambiado al estado: Designado Presidente de Jurado','Sistema',0,'2025-11-30 18:53:23'),(230,'DOC002','Su trámite de titulación ha cambiado al estado: Designado Secretario de Jurado','Sistema',0,'2025-11-30 18:53:23'),(231,'DOC007','Su trámite de titulación ha cambiado al estado: Designado Vocal de Jurado','Sistema',0,'2025-11-30 18:53:23'),(232,'ALU002','Su trámite de titulación ha cambiado al estado: Designación de Jurado','Sistema',0,'2025-11-30 18:53:23'),(233,'DOC005','Su trámite de titulación ha cambiado al estado: Designado Presidente de Jurado','Sistema',0,'2025-11-30 19:01:15'),(234,'DOC002','Su trámite de titulación ha cambiado al estado: Designado Secretario de Jurado','Sistema',0,'2025-11-30 19:01:15'),(235,'DOC007','Su trámite de titulación ha cambiado al estado: Designado Vocal de Jurado','Sistema',0,'2025-11-30 19:01:15'),(236,'ALU002','Su trámite de titulación ha cambiado al estado: Designación de Jurado','Sistema',0,'2025-11-30 19:01:15'),(237,'DOC005','Su trámite de titulación ha cambiado al estado: Designado Presidente de Jurado','Sistema',0,'2025-11-30 19:08:35'),(238,'DOC002','Su trámite de titulación ha cambiado al estado: Designado Secretario de Jurado','Sistema',0,'2025-11-30 19:08:35'),(239,'DOC007','Su trámite de titulación ha cambiado al estado: Designado Vocal de Jurado','Sistema',0,'2025-11-30 19:08:35'),(240,'ALU002','Su trámite de titulación ha cambiado al estado: Designación de Jurado','Sistema',0,'2025-11-30 19:08:35'),(241,'DOC001','Su trámite de titulación ha cambiado al estado: Designado Presidente de Jurado','Sistema',0,'2025-11-30 19:16:32'),(242,'DOC002','Su trámite de titulación ha cambiado al estado: Designado Secretario de Jurado','Sistema',0,'2025-11-30 19:16:32'),(243,'DOC007','Su trámite de titulación ha cambiado al estado: Designado Vocal de Jurado','Sistema',0,'2025-11-30 19:16:32'),(244,'ALU002','Su trámite de titulación ha cambiado al estado: Designación de Jurado','Sistema',0,'2025-11-30 19:16:32'),(245,'DOC005','Su trámite de titulación ha cambiado al estado: Designado Presidente de Jurado','Sistema',0,'2025-11-30 19:16:55'),(246,'DOC002','Su trámite de titulación ha cambiado al estado: Designado Secretario de Jurado','Sistema',0,'2025-11-30 19:16:55'),(247,'DOC007','Su trámite de titulación ha cambiado al estado: Designado Vocal de Jurado','Sistema',0,'2025-11-30 19:16:55'),(248,'ALU002','Su trámite de titulación ha cambiado al estado: Designación de Jurado','Sistema',0,'2025-11-30 19:16:55'),(249,'DOC001','Su trámite de titulación ha cambiado al estado: Designado Presidente de Jurado','Sistema',0,'2025-11-30 19:17:00'),(250,'DOC002','Su trámite de titulación ha cambiado al estado: Designado Secretario de Jurado','Sistema',0,'2025-11-30 19:17:00'),(251,'DOC007','Su trámite de titulación ha cambiado al estado: Designado Vocal de Jurado','Sistema',0,'2025-11-30 19:17:00'),(252,'ALU002','Su trámite de titulación ha cambiado al estado: Designación de Jurado','Sistema',0,'2025-11-30 19:17:00'),(253,'DOC005','Su trámite de titulación ha cambiado al estado: Designado Presidente de Jurado','Sistema',0,'2025-11-30 19:17:41'),(254,'DOC002','Su trámite de titulación ha cambiado al estado: Designado Secretario de Jurado','Sistema',0,'2025-11-30 19:17:41'),(255,'DOC007','Su trámite de titulación ha cambiado al estado: Designado Vocal de Jurado','Sistema',0,'2025-11-30 19:17:41'),(256,'ALU002','Su trámite de titulación ha cambiado al estado: Designación de Jurado','Sistema',0,'2025-11-30 19:17:41'),(257,'ADM001','El Asesor Raúl Enrique Fernández Bejarano ha evaluado la Tesis #20. Resultado: Aprobado','Sistema',0,'2025-11-30 19:32:45'),(258,'ADM001','El Asesor Raúl Enrique Fernández Bejarano ha evaluado la Tesis #20. Resultado: Aprobado con observaciones menores','Sistema',0,'2025-11-30 19:44:13'),(259,'DOC001','Su trámite de titulación ha cambiado al estado: El estudiante Diego Flores Yupanqui ha subido una nueva versión de su tesis. Nota: corregido ingeniero\r\n','Sistema',0,'2025-11-30 19:53:48'),(260,'DOC001','Su trámite de titulación ha cambiado al estado: El estudiante Diego Flores Yupanqui ha subido una nueva versión de su tesis. Nota: CORREGI EL ITEM 23','Sistema',0,'2025-11-30 20:06:25'),(261,'ADM001','El Asesor Raúl Enrique Fernández Bejarano ha evaluado la Tesis #20. Resultado: Aprobado con observaciones menores','Sistema',0,'2025-11-30 20:14:54'),(262,'ADM001','El Asesor Raúl Enrique Fernández Bejarano ha evaluado la Tesis #20. Resultado: Aprobado','Sistema',0,'2025-11-30 20:19:57'),(263,'T01286E','Su trámite de titulación ha cambiado al estado: Plan de Tesis registrado: IMPLEMENTACIÓN DE SISTEMA WEB PARA EL CONTROL DE CITAS MÉDICAS EN LA CLÍNICA DENTAL IMPLACIX S.A.C. CHICLAYO 2024','Sistema',0,'2025-11-30 20:35:29'),(264,'DOC008','Su trámite de titulación ha cambiado al estado: Nueva tesis asignada: IMPLEMENTACIÓN DE SISTEMA WEB PARA EL CONTROL DE CITAS MÉDICAS EN LA CLÍNICA DENTAL IMPLACIX S.A.C. CHICLAYO 2024','Sistema',0,'2025-11-30 20:35:29'),(265,'ADM001','El Asesor Juan  Perez Soto ha evaluado la Tesis #27. Resultado: Aprobado con observaciones menores','Sistema',0,'2025-11-30 20:38:02'),(266,'DOC008','Su trámite de titulación ha cambiado al estado: El estudiante Arturo Flores Yupanqui ha subido una nueva versión de su tesis. Nota: HE CORREGIDO TODOS LOS ITEMS','Sistema',0,'2025-11-30 20:38:43'),(267,'ADM001','El Asesor Juan  Perez Soto ha evaluado la Tesis #27. Resultado: Aprobado','Sistema',0,'2025-11-30 20:39:31'),(268,'T01286E','Hemos recibido su documento: DNI. Está pendiente de validación.','Sistema',0,'2025-11-30 20:40:04'),(269,'ADM001','El estudiante Arturo Flores Yupanqui (T01286E) ha subido el documento: DNI','Sistema',0,'2025-11-30 20:40:04'),(270,'T01286E','Hemos recibido su documento: Bachiller. Está pendiente de validación.','Sistema',0,'2025-11-30 20:40:13'),(271,'ADM001','El estudiante Arturo Flores Yupanqui (T01286E) ha subido el documento: Bachiller','Sistema',0,'2025-11-30 20:40:13'),(272,'T01286E','Hemos recibido su documento: Certificado_Idiomas. Está pendiente de validación.','Sistema',0,'2025-11-30 20:40:22'),(273,'ADM001','El estudiante Arturo Flores Yupanqui (T01286E) ha subido el documento: Certificado_Idiomas','Sistema',0,'2025-11-30 20:40:22'),(274,'T01286E','Hemos recibido su documento: Constancia_Practicas. Está pendiente de validación.','Sistema',0,'2025-11-30 20:40:30'),(275,'ADM001','El estudiante Arturo Flores Yupanqui (T01286E) ha subido el documento: Constancia_Practicas','Sistema',0,'2025-11-30 20:40:30'),(276,'T01286E','Hemos recibido su documento: Foto. Está pendiente de validación.','Sistema',0,'2025-11-30 20:40:41'),(277,'ADM001','El estudiante Arturo Flores Yupanqui (T01286E) ha subido el documento: Foto','Sistema',0,'2025-11-30 20:40:41'),(278,'T01286E','Su trámite de titulación ha cambiado al estado: Documento Validado','Sistema',0,'2025-11-30 20:41:10'),(279,'T01286E','Su trámite de titulación ha cambiado al estado: Documento Validado','Sistema',0,'2025-11-30 20:41:12'),(280,'T01286E','Su trámite de titulación ha cambiado al estado: Documento Validado','Sistema',0,'2025-11-30 20:41:13'),(281,'T01286E','Su trámite de titulación ha cambiado al estado: Documento Validado','Sistema',0,'2025-11-30 20:41:13'),(282,'T01286E','Su trámite de titulación ha cambiado al estado: Documento Validado','Sistema',0,'2025-11-30 20:41:14'),(283,'DOC001','Su trámite de titulación ha cambiado al estado: Designado Presidente de Jurado','Sistema',0,'2025-11-30 20:41:32'),(284,'DOC002','Su trámite de titulación ha cambiado al estado: Designado Secretario de Jurado','Sistema',0,'2025-11-30 20:41:32'),(285,'DOC005','Su trámite de titulación ha cambiado al estado: Designado Vocal de Jurado','Sistema',0,'2025-11-30 20:41:32'),(286,'T01286E','Su trámite de titulación ha cambiado al estado: Designación de Jurado','Sistema',0,'2025-11-30 20:41:32'),(287,'ADM001','El Jurado Raúl Enrique Fernández Bejarano ha emitido su voto para la Sustentación #10. Nota: 25.5','Sistema',0,'2025-11-30 21:13:00'),(288,'ADM001','El Jurado Karina Luz Cruz Oscanoa ha emitido su voto para la Sustentación #10. Nota: 29.5','Sistema',0,'2025-11-30 21:13:39'),(289,'ADM001','El Jurado Walter Estares Ventocilla ha emitido su voto para la Sustentación #10. Nota: 35.0','Sistema',0,'2025-11-30 21:14:25'),(290,'T01286E','Su trámite de titulación ha cambiado al estado: Sustentación Programada','Sistema',0,'2025-11-30 21:15:09'),(291,'T01286E','Su trámite de titulación ha cambiado al estado: ¡FELICITACIONES! Has aprobado la sustentación y obtenido el título.','Sistema',0,'2025-11-30 21:23:52');
/*!40000 ALTER TABLE `notificaciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sustentaciones`
--

DROP TABLE IF EXISTS `sustentaciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sustentaciones` (
  `id_sustentacion` int NOT NULL AUTO_INCREMENT,
  `id_tramite` int NOT NULL,
  `codigo_miembro1` varchar(20) NOT NULL,
  `codigo_miembro2` varchar(20) NOT NULL,
  `codigo_miembro3` varchar(20) NOT NULL,
  `codigo_suplente` varchar(20) DEFAULT NULL,
  `fecha_hora` timestamp NOT NULL,
  `lugar_enlace` varchar(255) DEFAULT NULL,
  `nota_final` decimal(4,2) DEFAULT NULL,
  `resultado_defensa` enum('Pendiente','Aprobado','Desaprobado') DEFAULT 'Pendiente',
  `observaciones` text,
  PRIMARY KEY (`id_sustentacion`),
  KEY `id_tramite` (`id_tramite`),
  KEY `codigo_presidente` (`codigo_miembro1`),
  KEY `codigo_secretario` (`codigo_miembro2`),
  KEY `codigo_vocal` (`codigo_miembro3`),
  KEY `fk_sustentacion_suplente` (`codigo_suplente`),
  CONSTRAINT `fk_sustentacion_suplente` FOREIGN KEY (`codigo_suplente`) REFERENCES `usuarios` (`codigo`),
  CONSTRAINT `sustentaciones_ibfk_1` FOREIGN KEY (`id_tramite`) REFERENCES `tramites` (`id_tramite`) ON DELETE CASCADE,
  CONSTRAINT `sustentaciones_ibfk_2` FOREIGN KEY (`codigo_miembro1`) REFERENCES `usuarios` (`codigo`),
  CONSTRAINT `sustentaciones_ibfk_3` FOREIGN KEY (`codigo_miembro2`) REFERENCES `usuarios` (`codigo`),
  CONSTRAINT `sustentaciones_ibfk_4` FOREIGN KEY (`codigo_miembro3`) REFERENCES `usuarios` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sustentaciones`
--

LOCK TABLES `sustentaciones` WRITE;
/*!40000 ALTER TABLE `sustentaciones` DISABLE KEYS */;
INSERT INTO `sustentaciones` VALUES (2,1,'DOC002','DOC005','DOC006','DOC007','2025-11-30 01:00:00','Auditorio 4',33.00,'Aprobado',NULL),(3,6,'DOC005','DOC001','DOC002',NULL,'2025-11-29 21:02:00','Auditorio 1',NULL,'Pendiente',NULL),(4,7,'DOC001','DOC002','DOC005','DOC006','2025-12-18 22:21:00','Auditorio 1',NULL,'Pendiente',NULL),(9,3,'DOC005','DOC002','DOC007','DOC006','2025-01-11 05:00:00','Auditorio 1',NULL,'Pendiente',NULL),(10,10,'DOC001','DOC002','DOC005','DOC006','2025-01-10 05:00:00','Auditorio 4',30.00,'Aprobado',NULL);
/*!40000 ALTER TABLE `sustentaciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tesis`
--

DROP TABLE IF EXISTS `tesis`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tesis` (
  `id_tesis` int NOT NULL AUTO_INCREMENT,
  `titulo` varchar(500) NOT NULL,
  `codigo_estudiante` varchar(20) NOT NULL,
  `codigo_docente_revisor` varchar(20) DEFAULT NULL,
  `estado` enum('Iniciado','En Proceso','Necesita Correcciones','Aprobado') DEFAULT 'Iniciado',
  `archivo_path` varchar(500) DEFAULT NULL,
  `fecha_subida` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_tesis`),
  KEY `codigo_estudiante` (`codigo_estudiante`),
  KEY `codigo_docente_revisor` (`codigo_docente_revisor`),
  CONSTRAINT `tesis_ibfk_1` FOREIGN KEY (`codigo_estudiante`) REFERENCES `usuarios` (`codigo`),
  CONSTRAINT `tesis_ibfk_2` FOREIGN KEY (`codigo_docente_revisor`) REFERENCES `usuarios` (`codigo`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tesis`
--

LOCK TABLES `tesis` WRITE;
/*!40000 ALTER TABLE `tesis` DISABLE KEYS */;
INSERT INTO `tesis` VALUES (20,'MODELO DE GESTIÓN DE SEGURIDAD DE LA INFORMACIÓN BASADO EN LA NORMA TÉCNICA PERUANA 27001:2022 PARA UNA INSTITUCIÓN EDUCATIVA, 2024','ALU001','DOC001','Aprobado','T037_70896951_T.pdf','2025-11-30 20:06:25'),(21,'Implementación de un sistema web con código QR para optimizar la atención al cliente del restaurante Delif, Huancayo 2025','T01236B','DOC001','Aprobado','T037_73391151_T.pdf','2025-11-21 16:01:03'),(22,'Rediseño de la Red de Datos con la Metodología Top Down para mejorar la calidad de los servicios de comunicación en la Universidad Privada de Huancayo Franklin Roosevelt-2022','T01303C','DOC005','Aprobado','T037_46765498_T.pdf','2025-11-21 17:18:48'),(26,'Sistema Web para Educación con Inteligencia Artificial','ALU002','DOC002','Aprobado','T037_41686442_T.pdf','2025-11-30 17:07:43'),(27,'IMPLEMENTACIÓN DE SISTEMA WEB PARA EL CONTROL DE CITAS MÉDICAS EN LA CLÍNICA DENTAL IMPLACIX S.A.C. CHICLAYO 2024','T01286E','DOC008','Aprobado','T037_40158987_T.pdf','2025-11-30 20:38:43');
/*!40000 ALTER TABLE `tesis` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tramites`
--

DROP TABLE IF EXISTS `tramites`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tramites` (
  `id_tramite` int NOT NULL AUTO_INCREMENT,
  `codigo_estudiante` varchar(20) NOT NULL,
  `estado_actual` enum('Iniciado','Revisión de Carpeta','Designación de Jurado','Apto para Sustentar','Sustentación Programada','Titulado') DEFAULT 'Iniciado',
  `fecha_inicio` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `fecha_actualizacion` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_tramite`),
  KEY `fk_tramite_usuario` (`codigo_estudiante`),
  CONSTRAINT `fk_tramite_usuario` FOREIGN KEY (`codigo_estudiante`) REFERENCES `usuarios` (`codigo`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tramites`
--

LOCK TABLES `tramites` WRITE;
/*!40000 ALTER TABLE `tramites` DISABLE KEYS */;
INSERT INTO `tramites` VALUES (1,'ALU001','Titulado','2025-11-21 02:40:11','2025-11-30 01:30:22'),(3,'ALU002','Designación de Jurado','2025-11-21 04:14:04','2025-11-30 18:53:23'),(6,'T01236B','Revisión de Carpeta','2025-11-21 15:59:03','2025-11-30 17:25:06'),(7,'T01303C','Titulado','2025-11-21 17:16:52','2025-11-29 22:51:08'),(10,'T01286E','Titulado','2025-11-30 20:34:11','2025-11-30 21:23:52');
/*!40000 ALTER TABLE `tramites` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `codigo` varchar(20) NOT NULL,
  `nombres` varchar(150) NOT NULL,
  `apellidos` varchar(150) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `rol` enum('admin','docente','alumno') NOT NULL,
  PRIMARY KEY (`codigo`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES ('ADM001','Pedro','Perez Lopez','admin@ms.upla.edu.pe','admin123','admin'),('ALU001','Diego','Flores Yupanqui','a.dflores@ms.upla.edu.pe','diego123','alumno'),('ALU002','Jefferson Luis','Paredes Yauri','t01304d@ms.upla.edu.pe','ALU002','alumno'),('DOC001','Raúl Enrique','Fernández Bejarano','d.rfernandez@ms.upla.edu.pe','raul123','docente'),('DOC002','Karina Luz','Cruz Oscanoa','d.kcruz@ms.upla.edu.pe','karina123','docente'),('DOC005','Walter','Estares Ventocilla','d.westaresv@ms.upla.edu.pe','DOC005','docente'),('DOC006','Juan Carlos','Cotrina Aliaga','d.jcotrinaa@ms.upla.edu.pe','DOC006','docente'),('DOC007','Herberth','Aranda Rojas','d.harandar@ms.upla.edu.pe','DOC007','docente'),('DOC008','Juan ','Perez Soto','d.jperezs@ms.upla.edu.pe','DOC008','docente'),('T01236B','Raul','Calderon Palacios','T01236B@ms.upla.edu.pe','T01236B','alumno'),('T01286E','Arturo','Flores Yupanqui','T01286E@ms.upla.edu.pe','T01286E','alumno'),('T01303C','Genis','Paquiyauri Vivanco','T01303C@hotmail.com','T01303C','alumno');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'bdgestiontesis'
--

--
-- Dumping routines for database 'bdgestiontesis'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-12-12 13:12:53
