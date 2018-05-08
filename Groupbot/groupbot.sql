/*
SQLyog Community v12.12 (64 bit)
MySQL - 10.1.19-MariaDB : Database - groupbot
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`groupbot` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `groupbot`;

/*Table structure for table `data` */

DROP TABLE IF EXISTS `data`;

CREATE TABLE `data` (
  `id_data` int(11) NOT NULL AUTO_INCREMENT,
  `id_user` varchar(255) DEFAULT NULL,
  `option` varchar(255) DEFAULT NULL,
  `id_meeting` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_data`),
  KEY `FKlpx0oeu8rpnmjnrkui0k1e3ub` (`id_meeting`),
  CONSTRAINT `FKlpx0oeu8rpnmjnrkui0k1e3ub` FOREIGN KEY (`id_meeting`) REFERENCES `meeting` (`id_meeting`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

/*Data for the table `data` */

insert  into `data`(`id_data`,`id_user`,`option`,`id_meeting`) values (1,'amos19','Ya',1),(2,'hotelrestaurant','Ya',1),(3,'budianto61','Ya',1),(4,'amos19','Ya',2),(5,'hotelrestaurant','Ya',2),(6,'amos19','Ya',3);

/*Table structure for table `meeting` */

DROP TABLE IF EXISTS `meeting`;

CREATE TABLE `meeting` (
  `id_meeting` int(11) NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL,
  `id_group` varchar(255) DEFAULT NULL,
  `id_user` varchar(255) DEFAULT NULL,
  `place` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `time` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_meeting`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `meeting` */

insert  into `meeting`(`id_meeting`,`date`,`id_group`,`id_user`,`place`,`status`,`time`,`title`) values (1,'2017-08-25','Groupbot@kaskus-s.obrol.id/com.kaskus.chat.official.37611641','amos19','Menara Citicon 17th Ruang Aquarium','Not Yet','10am - 11am','Spring'),(2,'2017-08-25','Groupbot@kaskus-s.obrol.id/com.kaskus.chat.official.37611641','amos19','Menara Citicon 17th Ruang Aquarium','Not Yet','10am - 11am','Testing'),(3,'2017-08-25','Groupbot@kaskus-s.obrol.id/com.kaskus.chat.official.37611641','amos19','Menara Citicon 17th Ruang Aquarium','Not Yet','10am - 11am','TEst');

/*Table structure for table `state` */

DROP TABLE IF EXISTS `state`;

CREATE TABLE `state` (
  `id_state` int(11) NOT NULL AUTO_INCREMENT,
  `event` varchar(255) DEFAULT NULL,
  `id_user` varchar(255) DEFAULT NULL,
  `id_meeting` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_state`),
  KEY `FK6266fjt58occi1yqpur6bgn8` (`id_meeting`),
  CONSTRAINT `FK6266fjt58occi1yqpur6bgn8` FOREIGN KEY (`id_meeting`) REFERENCES `meeting` (`id_meeting`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `state` */

insert  into `state`(`id_state`,`event`,`id_user`,`id_meeting`) values (4,'NULL','amos19',NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
