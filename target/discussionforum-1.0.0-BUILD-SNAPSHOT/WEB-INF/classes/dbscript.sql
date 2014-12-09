create database if not exists `tolcdiscussionforum`;

USE `tolcdiscussionforum`;

DROP TABLE IF EXISTS `users`;
DROP TABLE IF EXISTS `user_roles`;
DROP TABLE IF EXISTS `courses`;
DROP TABLE IF EXISTS `enrollment`;
DROP TABLE IF EXISTS `discussionboard`;
DROP TABLE IF EXISTS `discussionposts`;
DROP TABLE IF EXISTS `subscriptions`;

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
  firepadurl VARCHAR(255) NOT NULL,
  PRIMARY KEY (threadid),
  FOREIGN KEY (courseid) REFERENCES courses(courseid));
  
  CREATE TABLE discussionposts (
  threadid int(11) NOT NULL,
  postid int(11) NOT NULL AUTO_INCREMENT,
  postcontent TEXT NOT NULL,
  postedby VARCHAR(45) NOT NULL,
  postanonymously TINYINT NOT NULL,
  postedat timestamp NOT NULL,
  editedwiki TINYINT NOT NULL,
  PRIMARY KEY (postid),
  FOREIGN KEY (threadid) REFERENCES discussionboard(threadid));
  
  CREATE TABLE subscriptions (
  sno int(11) NOT NULL AUTO_INCREMENT,
  threadid int(11) NOT NULL,
  studentname VARCHAR(45) NOT NULL,
  subscription TINYINT NOT NULL,
  PRIMARY KEY (sno),
  FOREIGN KEY (threadid) REFERENCES discussionboard(threadid));
  
  CREATE TABLE calendarevents(
  eventnumber int(11) NOT NULL AUTO_INCREMENT,
  courseid int(11) NOT NULL,
  eventinformation VARCHAR(1000) NOT NULL,
  eventcreatedby VARCHAR(45) NOT NULL,
  personalevent TINYINT NOT NULL,
  eventtimestamp timestamp NOT NULL,
  PRIMARY KEY(eventnumber),
  FOREIGN KEY(courseid) REFERENCES courses(courseid));
  
  CREATE TABLE follow (
  courseid int(11) NOT NULL,
  username VARCHAR(45) NOT NULL,
  following VARCHAR(45) NOT NULL);
  