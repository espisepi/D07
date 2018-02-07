﻿start transaction;
create database `Acme-Explorer`;

use `Acme-Explorer`;

create user 'acme-user'@'%' identified by password '*4F10007AADA9EE3DBB2CC36575DFC6F4FDE27577';
create user 'acme-manager'@'%' identified by password '*FDB8CD304EB2317D10C95D797A4BD7492560F55F';

grant select, insert, update, delete on `Acme-Explorer`.* to 'acme-user'@'%';
grant select, insert, update, delete, create, drop, references, index, alter, create temporary tables, lock tables, create view, create routine, alter routine, execute, trigger, show view on `Acme-Explorer`.* to 'acme-manager'@'%';




-- MySQL dump 10.13  Distrib 5.5.29, for Win64 (x86)
--
-- Host: localhost    Database: acme-explorer
-- ------------------------------------------------------
-- Server version	5.5.29

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `actor_messagefolder`
--

DROP TABLE IF EXISTS `actor_messagefolder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `actor_messagefolder` (
  `Actor_id` int(11) NOT NULL,
  `messagesFolders_id` int(11) NOT NULL,
  UNIQUE KEY `UK_9r38jrnmcit3asym390tk6fyb` (`messagesFolders_id`),
  CONSTRAINT `FK_9r38jrnmcit3asym390tk6fyb` FOREIGN KEY (`messagesFolders_id`) REFERENCES `messagefolder` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actor_messagefolder`
--

LOCK TABLES `actor_messagefolder` WRITE;
/*!40000 ALTER TABLE `actor_messagefolder` DISABLE KEYS */;
INSERT INTO `actor_messagefolder` VALUES (32,27),(32,28),(32,29),(32,30),(32,31);
/*!40000 ALTER TABLE `actor_messagefolder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `actor_socialidentity`
--

DROP TABLE IF EXISTS `actor_socialidentity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `actor_socialidentity` (
  `Actor_id` int(11) NOT NULL,
  `socialIdentities_id` int(11) NOT NULL,
  UNIQUE KEY `UK_fvjtbijvsijias3suehxiq0jm` (`socialIdentities_id`),
  CONSTRAINT `FK_fvjtbijvsijias3suehxiq0jm` FOREIGN KEY (`socialIdentities_id`) REFERENCES `socialidentity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actor_socialidentity`
--

LOCK TABLES `actor_socialidentity` WRITE;
/*!40000 ALTER TABLE `actor_socialidentity` DISABLE KEYS */;
/*!40000 ALTER TABLE `actor_socialidentity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `administrator`
--

DROP TABLE IF EXISTS `administrator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `administrator` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `suspicious` bit(1) NOT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_idt4b4u259p6vs4pyr9lax4eg` (`userAccount_id`),
  CONSTRAINT `FK_idt4b4u259p6vs4pyr9lax4eg` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administrator`
--

LOCK TABLES `administrator` WRITE;
/*!40000 ALTER TABLE `administrator` DISABLE KEYS */;
INSERT INTO `administrator` VALUES (32,0,'Administrator address','Administrator1@administrator.com','Admin','+34662657322','Administrator1 Surnames','\0',26);
/*!40000 ALTER TABLE `administrator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `applicationfor`
--

DROP TABLE IF EXISTS `applicationfor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `applicationfor` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `brandName` varchar(255) DEFAULT NULL,
  `cvv` int(11) NOT NULL,
  `expirationMonth` varchar(255) DEFAULT NULL,
  `expirationYear` varchar(255) DEFAULT NULL,
  `holderName` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `moment` datetime DEFAULT NULL,
  `reasonWhy` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `explorer_id` int(11) NOT NULL,
  `manager_id` int(11) DEFAULT NULL,
  `trip_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_qkn29o630r4wdvtaugkns82g3` (`explorer_id`),
  KEY `FK_ep3lhiylkqxx6w8v5n9jiacf6` (`manager_id`),
  KEY `FK_djyjfgplhq5lreh4yipstoch1` (`trip_id`),
  CONSTRAINT `FK_djyjfgplhq5lreh4yipstoch1` FOREIGN KEY (`trip_id`) REFERENCES `trip` (`id`),
  CONSTRAINT `FK_ep3lhiylkqxx6w8v5n9jiacf6` FOREIGN KEY (`manager_id`) REFERENCES `manager` (`id`),
  CONSTRAINT `FK_qkn29o630r4wdvtaugkns82g3` FOREIGN KEY (`explorer_id`) REFERENCES `explorer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `applicationfor`
--

LOCK TABLES `applicationfor` WRITE;
/*!40000 ALTER TABLE `applicationfor` DISABLE KEYS */;
/*!40000 ALTER TABLE `applicationfor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `applicationfor_comments`
--

DROP TABLE IF EXISTS `applicationfor_comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `applicationfor_comments` (
  `ApplicationFor_id` int(11) NOT NULL,
  `comments` varchar(255) DEFAULT NULL,
  KEY `FK_n30okwyuwhsnvclw83sjncnlt` (`ApplicationFor_id`),
  CONSTRAINT `FK_n30okwyuwhsnvclw83sjncnlt` FOREIGN KEY (`ApplicationFor_id`) REFERENCES `applicationfor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `applicationfor_comments`
--

LOCK TABLES `applicationfor_comments` WRITE;
/*!40000 ALTER TABLE `applicationfor_comments` DISABLE KEYS */;
/*!40000 ALTER TABLE `applicationfor_comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `fatherCategory_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_8dbpkngc2chtdg1xbd67fu6s0` (`fatherCategory_id`),
  CONSTRAINT `FK_8dbpkngc2chtdg1xbd67fu6s0` FOREIGN KEY (`fatherCategory_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (38,1,'Lake',44),(39,1,'Savana',45),(40,1,'River',46),(41,1,'River',44),(42,1,'Mountain',46),(43,1,'Mountain',45),(44,1,'Water',47),(45,1,'Ground',47),(46,1,'Climbing',48),(47,1,'Safari',48),(48,0,'CATEGORY',NULL);
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category_trip`
--

DROP TABLE IF EXISTS `category_trip`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category_trip` (
  `Category_id` int(11) NOT NULL,
  `trips_id` int(11) NOT NULL,
  KEY `FK_jpmhd61n1frm86vucckf760uh` (`trips_id`),
  KEY `FK_mvh6bvklcktdyn2rpho7d0cp0` (`Category_id`),
  CONSTRAINT `FK_mvh6bvklcktdyn2rpho7d0cp0` FOREIGN KEY (`Category_id`) REFERENCES `category` (`id`),
  CONSTRAINT `FK_jpmhd61n1frm86vucckf760uh` FOREIGN KEY (`trips_id`) REFERENCES `trip` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category_trip`
--

LOCK TABLES `category_trip` WRITE;
/*!40000 ALTER TABLE `category_trip` DISABLE KEYS */;
/*!40000 ALTER TABLE `category_trip` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `configurationsystem`
--

DROP TABLE IF EXISTS `configurationsystem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `configurationsystem` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `VAT` double NOT NULL,
  `banner` varchar(255) DEFAULT NULL,
  `cacheMaxTime` int(11) NOT NULL,
  `defaultPhone` varchar(255) DEFAULT NULL,
  `maxNumberFinder` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `configurationsystem`
--

LOCK TABLES `configurationsystem` WRITE;
/*!40000 ALTER TABLE `configurationsystem` DISABLE KEYS */;
INSERT INTO `configurationsystem` VALUES (25,0,0.21,'http://creek-tours.com/wp-content/uploads/Kenya-Tanzania-Family-Safari-banner.jpg',1,'+34',10);
/*!40000 ALTER TABLE `configurationsystem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `configurationsystem_category`
--

DROP TABLE IF EXISTS `configurationsystem_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `configurationsystem_category` (
  `ConfigurationSystem_id` int(11) NOT NULL,
  `defaultCategories_id` int(11) NOT NULL,
  UNIQUE KEY `UK_pmon6y1retrhnobrj15emstb` (`defaultCategories_id`),
  KEY `FK_q1lbwcfv2ac1wq7bqjp1w89av` (`ConfigurationSystem_id`),
  CONSTRAINT `FK_q1lbwcfv2ac1wq7bqjp1w89av` FOREIGN KEY (`ConfigurationSystem_id`) REFERENCES `configurationsystem` (`id`),
  CONSTRAINT `FK_pmon6y1retrhnobrj15emstb` FOREIGN KEY (`defaultCategories_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `configurationsystem_category`
--

LOCK TABLES `configurationsystem_category` WRITE;
/*!40000 ALTER TABLE `configurationsystem_category` DISABLE KEYS */;
INSERT INTO `configurationsystem_category` VALUES (25,38),(25,39),(25,40),(25,41),(25,42),(25,43),(25,44),(25,45),(25,46),(25,47),(25,48);
/*!40000 ALTER TABLE `configurationsystem_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `configurationsystem_spamwords`
--

DROP TABLE IF EXISTS `configurationsystem_spamwords`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `configurationsystem_spamwords` (
  `ConfigurationSystem_id` int(11) NOT NULL,
  `spamWords` varchar(255) DEFAULT NULL,
  KEY `FK_ndaj1y1wr90fuqw567bbgckpt` (`ConfigurationSystem_id`),
  CONSTRAINT `FK_ndaj1y1wr90fuqw567bbgckpt` FOREIGN KEY (`ConfigurationSystem_id`) REFERENCES `configurationsystem` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `configurationsystem_spamwords`
--

LOCK TABLES `configurationsystem_spamwords` WRITE;
/*!40000 ALTER TABLE `configurationsystem_spamwords` DISABLE KEYS */;
INSERT INTO `configurationsystem_spamwords` VALUES (25,'viagra'),(25,'cialis'),(25,'sex'),(25,'jes extender');
/*!40000 ALTER TABLE `configurationsystem_spamwords` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `configurationsystem_tag`
--

DROP TABLE IF EXISTS `configurationsystem_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `configurationsystem_tag` (
  `ConfigurationSystem_id` int(11) NOT NULL,
  `defaultTags_id` int(11) NOT NULL,
  UNIQUE KEY `UK_q5qv6nnhwa8iv9rd4ew7xdnji` (`defaultTags_id`),
  KEY `FK_t57c5hdi2dbn7txplhgodfmc8` (`ConfigurationSystem_id`),
  CONSTRAINT `FK_t57c5hdi2dbn7txplhgodfmc8` FOREIGN KEY (`ConfigurationSystem_id`) REFERENCES `configurationsystem` (`id`),
  CONSTRAINT `FK_q5qv6nnhwa8iv9rd4ew7xdnji` FOREIGN KEY (`defaultTags_id`) REFERENCES `tag` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `configurationsystem_tag`
--

LOCK TABLES `configurationsystem_tag` WRITE;
/*!40000 ALTER TABLE `configurationsystem_tag` DISABLE KEYS */;
INSERT INTO `configurationsystem_tag` VALUES (25,33),(25,34),(25,35);
/*!40000 ALTER TABLE `configurationsystem_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contactemergency`
--

DROP TABLE IF EXISTS `contactemergency`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contactemergency` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contactemergency`
--

LOCK TABLES `contactemergency` WRITE;
/*!40000 ALTER TABLE `contactemergency` DISABLE KEYS */;
/*!40000 ALTER TABLE `contactemergency` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `explorer`
--

DROP TABLE IF EXISTS `explorer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `explorer` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `suspicious` bit(1) NOT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_fjs8q8hlbsegiuxw4r2ko6uv6` (`userAccount_id`),
  CONSTRAINT `FK_fjs8q8hlbsegiuxw4r2ko6uv6` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `explorer`
--

LOCK TABLES `explorer` WRITE;
/*!40000 ALTER TABLE `explorer` DISABLE KEYS */;
/*!40000 ALTER TABLE `explorer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `explorer_contactemergency`
--

DROP TABLE IF EXISTS `explorer_contactemergency`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `explorer_contactemergency` (
  `Explorer_id` int(11) NOT NULL,
  `contactsEmergency_id` int(11) NOT NULL,
  KEY `FK_snjdqww8qtb98wuqwl09evr4e` (`contactsEmergency_id`),
  KEY `FK_5g88gq101v31gy45c492yy3gp` (`Explorer_id`),
  CONSTRAINT `FK_5g88gq101v31gy45c492yy3gp` FOREIGN KEY (`Explorer_id`) REFERENCES `explorer` (`id`),
  CONSTRAINT `FK_snjdqww8qtb98wuqwl09evr4e` FOREIGN KEY (`contactsEmergency_id`) REFERENCES `contactemergency` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `explorer_contactemergency`
--

LOCK TABLES `explorer_contactemergency` WRITE;
/*!40000 ALTER TABLE `explorer_contactemergency` DISABLE KEYS */;
/*!40000 ALTER TABLE `explorer_contactemergency` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequences`
--

DROP TABLE IF EXISTS `hibernate_sequences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequences` (
  `sequence_name` varchar(255) DEFAULT NULL,
  `sequence_next_hi_value` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequences`
--

LOCK TABLES `hibernate_sequences` WRITE;
/*!40000 ALTER TABLE `hibernate_sequences` DISABLE KEYS */;
INSERT INTO `hibernate_sequences` VALUES ('DomainEntity',1);
/*!40000 ALTER TABLE `hibernate_sequences` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `legaltext`
--

DROP TABLE IF EXISTS `legaltext`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `legaltext` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `body` varchar(255) DEFAULT NULL,
  `draftMode` bit(1) NOT NULL,
  `lawsNumber` int(11) NOT NULL,
  `moment` datetime DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `legaltext`
--

LOCK TABLES `legaltext` WRITE;
/*!40000 ALTER TABLE `legaltext` DISABLE KEYS */;
INSERT INTO `legaltext` VALUES (36,0,' LEGAL TEXT   2. Acceptance of these Conditions   The fact that you access this website implies that    you know, understand and accept the Terms of Use in the version existing    at the time of access.','\0',4,'2017-11-23 23:59:00','title english'),(37,0,'AVISO LEGAL   2. Aceptación de estas Condiciones   El hecho de que acceda a este sitio Web implica que conoce, entiende y acepta las Condiciones de    en la versión existente en el momento del acceso.','\0',4,'2017-11-23 23:59:00','title español');
/*!40000 ALTER TABLE `legaltext` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `legaltext_trip`
--

DROP TABLE IF EXISTS `legaltext_trip`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `legaltext_trip` (
  `LegalText_id` int(11) NOT NULL,
  `trips_id` int(11) NOT NULL,
  UNIQUE KEY `UK_nhe46krpx0p82b2bfg4rfivtl` (`trips_id`),
  KEY `FK_euperohyjlo853196aj98q7k0` (`LegalText_id`),
  CONSTRAINT `FK_euperohyjlo853196aj98q7k0` FOREIGN KEY (`LegalText_id`) REFERENCES `legaltext` (`id`),
  CONSTRAINT `FK_nhe46krpx0p82b2bfg4rfivtl` FOREIGN KEY (`trips_id`) REFERENCES `trip` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `legaltext_trip`
--

LOCK TABLES `legaltext_trip` WRITE;
/*!40000 ALTER TABLE `legaltext_trip` DISABLE KEYS */;
/*!40000 ALTER TABLE `legaltext_trip` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `manager`
--

DROP TABLE IF EXISTS `manager`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `manager` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `suspicious` bit(1) NOT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_84bmmxlq61tiaoc7dy7kdcghh` (`userAccount_id`),
  CONSTRAINT `FK_84bmmxlq61tiaoc7dy7kdcghh` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `manager`
--

LOCK TABLES `manager` WRITE;
/*!40000 ALTER TABLE `manager` DISABLE KEYS */;
/*!40000 ALTER TABLE `manager` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `body` varchar(255) DEFAULT NULL,
  `moment` datetime DEFAULT NULL,
  `priority` varchar(255) DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `messageFolder_id` int(11) NOT NULL,
  `recipient_id` int(11) NOT NULL,
  `sender_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_iq38mox9qghnx2rc8hpdtmros` (`messageFolder_id`),
  CONSTRAINT `FK_iq38mox9qghnx2rc8hpdtmros` FOREIGN KEY (`messageFolder_id`) REFERENCES `messagefolder` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `messagefolder`
--

DROP TABLE IF EXISTS `messagefolder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `messagefolder` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `modifiable` bit(1) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `messagefolder`
--

LOCK TABLES `messagefolder` WRITE;
/*!40000 ALTER TABLE `messagefolder` DISABLE KEYS */;
INSERT INTO `messagefolder` VALUES (27,0,'\0','In box'),(28,0,'\0','Out box'),(29,0,'\0','Notification box'),(30,0,'\0','Trash box'),(31,0,'\0','Spam box');
/*!40000 ALTER TABLE `messagefolder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notification`
--

DROP TABLE IF EXISTS `notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notification` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `gauge` int(11) NOT NULL,
  `moment` datetime DEFAULT NULL,
  `trip_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_3noubh3upwpivxfecdsu2370o` (`code`),
  KEY `FK_kjbr1rntlmr592ptxli1uui1a` (`trip_id`),
  CONSTRAINT `FK_kjbr1rntlmr592ptxli1uui1a` FOREIGN KEY (`trip_id`) REFERENCES `trip` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification`
--

LOCK TABLES `notification` WRITE;
/*!40000 ALTER TABLE `notification` DISABLE KEYS */;
/*!40000 ALTER TABLE `notification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ranger`
--

DROP TABLE IF EXISTS `ranger`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ranger` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `suspicious` bit(1) NOT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_axw07q0mobub2mvr0xeh7al5f` (`userAccount_id`),
  CONSTRAINT `FK_axw07q0mobub2mvr0xeh7al5f` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ranger`
--

LOCK TABLES `ranger` WRITE;
/*!40000 ALTER TABLE `ranger` DISABLE KEYS */;
/*!40000 ALTER TABLE `ranger` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `socialidentity`
--

DROP TABLE IF EXISTS `socialidentity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `socialidentity` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `link` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `nick` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `socialidentity`
--

LOCK TABLES `socialidentity` WRITE;
/*!40000 ALTER TABLE `socialidentity` DISABLE KEYS */;
/*!40000 ALTER TABLE `socialidentity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stage`
--

DROP TABLE IF EXISTS `stage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stage` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `number` int(11) NOT NULL,
  `price` double NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `totalPrice` double NOT NULL,
  `trip_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_g8uuphy1i55oxqtlti7cftxk5` (`trip_id`),
  CONSTRAINT `FK_g8uuphy1i55oxqtlti7cftxk5` FOREIGN KEY (`trip_id`) REFERENCES `trip` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stage`
--

LOCK TABLES `stage` WRITE;
/*!40000 ALTER TABLE `stage` DISABLE KEYS */;
/*!40000 ALTER TABLE `stage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tag`
--

DROP TABLE IF EXISTS `tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tag` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tag`
--

LOCK TABLES `tag` WRITE;
/*!40000 ALTER TABLE `tag` DISABLE KEYS */;
INSERT INTO `tag` VALUES (33,0,'country'),(34,0,'expertise'),(35,0,'dangerousness');
/*!40000 ALTER TABLE `tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trip`
--

DROP TABLE IF EXISTS `trip`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trip` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `cancelled` bit(1) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `finishDate` date DEFAULT NULL,
  `price` double NOT NULL,
  `publicationDate` datetime DEFAULT NULL,
  `reasonWhy` varchar(255) DEFAULT NULL,
  `requirementsExplorers` varchar(255) DEFAULT NULL,
  `startDate` date DEFAULT NULL,
  `ticker` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `manager_id` int(11) NOT NULL,
  `ranger_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ugq799eb7dgmgmqjoxkjpx5k` (`ticker`),
  KEY `FK_bt26lls0o7ykesy9ye75887rk` (`manager_id`),
  KEY `FK_65bd0l9xa0l1x5qnjg2su9tig` (`ranger_id`),
  CONSTRAINT `FK_65bd0l9xa0l1x5qnjg2su9tig` FOREIGN KEY (`ranger_id`) REFERENCES `ranger` (`id`),
  CONSTRAINT `FK_bt26lls0o7ykesy9ye75887rk` FOREIGN KEY (`manager_id`) REFERENCES `manager` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trip`
--

LOCK TABLES `trip` WRITE;
/*!40000 ALTER TABLE `trip` DISABLE KEYS */;
/*!40000 ALTER TABLE `trip` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trip_stage`
--

DROP TABLE IF EXISTS `trip_stage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trip_stage` (
  `Trip_id` int(11) NOT NULL,
  `stages_id` int(11) NOT NULL,
  UNIQUE KEY `UK_pypsdnr343h93d9c7hpugcyyv` (`stages_id`),
  KEY `FK_stea9evs7jmvh3yf9i17pw6kn` (`Trip_id`),
  CONSTRAINT `FK_stea9evs7jmvh3yf9i17pw6kn` FOREIGN KEY (`Trip_id`) REFERENCES `trip` (`id`),
  CONSTRAINT `FK_pypsdnr343h93d9c7hpugcyyv` FOREIGN KEY (`stages_id`) REFERENCES `stage` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trip_stage`
--

LOCK TABLES `trip_stage` WRITE;
/*!40000 ALTER TABLE `trip_stage` DISABLE KEYS */;
/*!40000 ALTER TABLE `trip_stage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `useraccount`
--

DROP TABLE IF EXISTS `useraccount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `useraccount` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `activated` bit(1) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_csivo9yqa08nrbkog71ycilh5` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `useraccount`
--

LOCK TABLES `useraccount` WRITE;
/*!40000 ALTER TABLE `useraccount` DISABLE KEYS */;
INSERT INTO `useraccount` VALUES (26,0,'','21232f297a57a5a743894a0e4a801fc3','admin');
/*!40000 ALTER TABLE `useraccount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `useraccount_authorities`
--

DROP TABLE IF EXISTS `useraccount_authorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `useraccount_authorities` (
  `UserAccount_id` int(11) NOT NULL,
  `authority` varchar(255) DEFAULT NULL,
  KEY `FK_b63ua47r0u1m7ccc9lte2ui4r` (`UserAccount_id`),
  CONSTRAINT `FK_b63ua47r0u1m7ccc9lte2ui4r` FOREIGN KEY (`UserAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `useraccount_authorities`
--

LOCK TABLES `useraccount_authorities` WRITE;
/*!40000 ALTER TABLE `useraccount_authorities` DISABLE KEYS */;
INSERT INTO `useraccount_authorities` VALUES (26,'ADMINISTRATOR');
/*!40000 ALTER TABLE `useraccount_authorities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `value`
--

DROP TABLE IF EXISTS `value`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `value` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `value` int(11) DEFAULT NULL,
  `tag_id` int(11) DEFAULT NULL,
  `trip_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_gshaqbur2cof541isdpqw7fhs` (`tag_id`),
  KEY `FK_i0yqkpsgylvwk2bp6nky4o4es` (`trip_id`),
  CONSTRAINT `FK_i0yqkpsgylvwk2bp6nky4o4es` FOREIGN KEY (`trip_id`) REFERENCES `trip` (`id`),
  CONSTRAINT `FK_gshaqbur2cof541isdpqw7fhs` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `value`
--

LOCK TABLES `value` WRITE;
/*!40000 ALTER TABLE `value` DISABLE KEYS */;
/*!40000 ALTER TABLE `value` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-02-07  0:51:33
