create database if not exists `tolcdiscussionforum`;

USE `tolcdiscussionforum`;

DROP TABLE IF EXISTS `users`;
DROP TABLE IF EXISTS `user_roles`;

CREATE  TABLE users (
  username VARCHAR(45) NOT NULL,
  password VARCHAR(60) NOT NULL,
  firstname VARCHAR(45) NOT NULL,
  lastname VARCHAR(45) NOT NULL,
  isstudent VARCHAR(45) NOT NULL,
  phonenumber VARCHAR(45) NOT NULL,
  email VARCHAR(45) NOT NULL,
  PRIMARY KEY (username));
  
  CREATE TABLE user_roles (
  user_role_id int(11) NOT NULL AUTO_INCREMENT,
  username VARCHAR(45) NOT NULL,
  ROLE VARCHAR(45) NOT NULL,
  PRIMARY KEY (user_role_id),
  UNIQUE KEY uni_username_role (ROLE,username),
  KEY fk_username_idx (username),
  CONSTRAINT fk_username FOREIGN KEY (username) REFERENCES users (username));
  
 INSERT INTO user_roles (username, ROLE)
 VALUES ('Bey0ndZ', 'ROLE_USER');