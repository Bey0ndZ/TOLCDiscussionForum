create database if not exists `tolcdiscussionforum`;

USE `tolcdiscussionforum`;

DROP TABLE IF EXISTS `users`;
DROP TABLE IF EXISTS `user_roles`;
DROP TABLE IF EXISTS `courses`;

CREATE  TABLE users (
  username VARCHAR(45) NOT NULL,
  password VARCHAR(60) NOT NULL,
  firstname VARCHAR(45) NOT NULL,
  lastname VARCHAR(45) NOT NULL,
  phonenumber VARCHAR(45) NOT NULL,
  email VARCHAR(45) NOT NULL,
  enabled TINYINT NOT NULL,
  PRIMARY KEY (username));
  
  CREATE TABLE user_roles (
  user_role_id int(11) NOT NULL AUTO_INCREMENT,
  username VARCHAR(45) NOT NULL,
  role VARCHAR(45) NOT NULL,
  PRIMARY KEY (user_role_id),
  UNIQUE KEY uni_username_role (role,username),
  KEY fk_username_idx (username),
  CONSTRAINT fk_username FOREIGN KEY (username) REFERENCES users (username));
  
  CREATE TABLE courses (
  courseid int(11) NOT NULL AUTO_INCREMENT,
  coursename VARCHAR(255) NOT NULL,
  instructor VARCHAR(45) NOT NULL,
  coursedescription VARCHAR(255) NOT NULL,
  numberofstudentsenrolled int(11) NOT NULL,
  PRIMARY KEY (courseid),
  FOREIGN KEY (instructor) REFERENCES users (username));
  
  CREATE TABLE enrollment (
  courseid int(11) NOT NULL,
  studentregistered VARCHAR(45) NOT NULL,
  FOREIGN KEY (courseid) REFERENCES courses(courseid));
  
  CREATE TABLE discussionboard (
  courseid int(11) NOT NULL,
  threadid int(11) NOT NULL AUTO_INCREMENT,
  threadname VARCHAR(255) NOT NULL,
  threadsubject VARCHAR(255) NOT NULL,
  threadcontent VARCHAR(1000) NOT NULL,
  createdby VARCHAR(45) NOT NULL,
  postanonymously TINYINT NOT NULL,
  PRIMARY KEY (threadid),
  FOREIGN KEY (courseid) REFERENCES courses(courseid));
  
  CREATE TABLE discussionposts (
  threadid int(11) NOT NULL,
  postid int(11) NOT NULL AUTO_INCREMENT,
  postedby VARCHAR(45) NOT NULL,
  PRIMARY KEY (postid),
  FOREIGN KEY (threadid) REFERENCES discussionboard(threadid));

  