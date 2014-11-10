create database if not exists `tolcdiscussionforum`;

USE `tolcdiscussionforum`;

DROP TABLE IF EXISTS `users`;

CREATE  TABLE users (
  username VARCHAR(45) NOT NULL,
  password VARCHAR(60) NOT NULL,
  firstname VARCHAR(45) NOT NULL,
  lastname VARCHAR(45) NOT NULL,
  isstudent VARCHAR(45) NOT NULL,
  phonenumber VARCHAR(45) NOT NULL,
  email VARCHAR(45) NOT NULL,
  PRIMARY KEY (username));