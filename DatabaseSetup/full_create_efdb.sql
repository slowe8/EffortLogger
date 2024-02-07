DROP DATABASE IF EXISTS efdb;
CREATE DATABASE `efdb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE efdb;

CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `IV` varbinary(16) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_id_UNIQUE` (`user_id`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `password_UNIQUE` (`password`),
  UNIQUE KEY `IV_UNIQUE` (`IV`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='This table is where user_id, username, and encrypted password, and the encryption IV is stored';

CREATE TABLE `user_data` (
  `data_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `first_name` varchar(45) NOT NULL,
  `middle_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `address_line` varchar(45) NOT NULL,
  `city` varchar(45) NOT NULL,
  `state` varchar(45) NOT NULL,
  `zip` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `phone` varchar(45) NOT NULL,
  `IV` varbinary(16) NOT NULL,
  PRIMARY KEY (`data_id`),
  UNIQUE KEY `data_id_UNIQUE` (`data_id`),
  UNIQUE KEY `user_id_UNIQUE` (`user_id`),
  UNIQUE KEY `IV_UNIQUE` (`IV`),
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `projects` (
  `project_id` int NOT NULL AUTO_INCREMENT,
  `project_name` varchar(45) NOT NULL,
  PRIMARY KEY (`project_id`),
  UNIQUE KEY `project_id_UNIQUE` (`project_id`),
  UNIQUE KEY `project_namw_UNIQUE` (`project_name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
INSERT INTO `efdb`.`projects`(project_name) VALUES ("Project1");

CREATE TABLE `effortlogs` (
  `log_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `projectName` varchar(45) NOT NULL,
  `lifeCycle` varchar(45) NOT NULL,
  `effortCategory` varchar(45) NOT NULL,
  `deliverable` varchar(45) NOT NULL,
  `date` date NOT NULL,
  `startTime` time NOT NULL,
  `stopTime` time NOT NULL,
  PRIMARY KEY (`log_id`),
  KEY `log_user_id_idx` (`user_id`),
  KEY `log_project_name_idx` (`projectName`),
  CONSTRAINT `log_project_name` FOREIGN KEY (`projectName`) REFERENCES `projects` (`project_name`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `log_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `defectlogs` (
  `defect_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `projectName` varchar(45) NOT NULL,
  `defect` varchar(45) NOT NULL,
  `details` varchar(300) DEFAULT NULL,
  `injected` varchar(45) DEFAULT NULL,
  `removed` varchar(45) DEFAULT NULL,
  `category` varchar(45) DEFAULT NULL,
  `status` varchar(45) NOT NULL,
  `fix` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`defect_id`),
  KEY `defect_user_id_idx` (`user_id`),
  KEY `defect_project_name_idx` (`projectName`),
  CONSTRAINT `defect_project_name` FOREIGN KEY (`projectName`) REFERENCES `projects` (`project_name`),
  CONSTRAINT `defect_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


