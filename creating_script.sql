CREATE DATABASE `usersdb`;
USE `usersdb`;
CREATE TABLE `usersdb`.`role` (
	`id` INT AUTO_INCREMENT,
	`roleName` ENUM ('user', 'moderator', 'admin') NOT NULL,
	PRIMARY KEY (`id`));
INSERT INTO `role` (`roleName`) VALUE ('user');
INSERT INTO `role` (`roleName`) VALUE ('moderator');
INSERT INTO `role` (`roleName`) VALUE ('admin');
CREATE TABLE `usersdb`.`user` (
	`id` INT AUTO_INCREMENT,
	`login` VARCHAR(30) NOT NULL,
	`password` VARCHAR(30) NOT NULL,
	`birthDate` DATE NOT NULL,
	`email` VARCHAR(30),
	`id_role` INT NOT NULL,
	PRIMARY KEY(`id`),
	FOREIGN KEY (`id_role`) REFERENCES `usersdb`.`role` (id)); 
CREATE TABLE `usersdb`.`adress` (
	`id` INT AUTO_INCREMENT,
	`country` VARCHAR(30) NOT NULL,
	`region` VARCHAR(30) NOT NULL,
	`city` VARCHAR(30),
	`street` VARCHAR(30),
	`building` VARCHAR(30),
	`app` VARCHAR(5),
	`id_user` INT NOT NULL,
	PRIMARY KEY (`id`),
	UNIQUE INDEX (`id_user`),
	FOREIGN KEY (`id_user`) REFERENCES `usersdb`.`user` (id));
CREATE TABLE `usersdb`.`musicType` (
	`id` INT AUTO_INCREMENT,
	`typeName` VARCHAR(30) NOT NULL,
	PRIMARY KEY (`id`) );
INSERT INTO `musicType` (`typeName`) value ('Rock');
INSERT INTO `musicType` (`typeName`) value ('Pop');
INSERT INTO `musicType` (`typeName`) value ('Jazz');
INSERT INTO `musicType` (`typeName`) value ('Classic');
INSERT INTO `musicType` (`typeName`) value ('Folk');
CREATE TABLE `usersdb`.`userMusicType` (
	`id` INT AUTO_INCREMENT,
	`id_user` INT NOT NULL,
	`id_musicType` INT NOT NULL,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`id_user`) REFERENCES `usersdb`.`user` (id),
	FOREIGN KEY (`id_musicType`) REFERENCES `usersdb`.`musicType` (id)); 
INSERT INTO `user` (`login`, `password`, `birthDate`, `email`, `id_role`) VALUES 
	('admin','admin','1900-01-01','qwerty@qwerty.com',(SELECT `id` FROM `role` WHERE `rolename`='admin'));