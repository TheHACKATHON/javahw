CREATE DATABASE IF NOT EXISTS `academy`;
USE `academy`;
CREATE TABLE IF NOT EXISTS `academy`.`teacher` (
	PRIMARY KEY (`id`),
	`id`				INT				NOT NULL AUTO_INCREMENT,
    `name`				TEXT			NOT NULL CHECK (length(`name`) > 0),
    `surname`			TEXT			NOT NULL CHECK (length(`surname`) > 0),
    `premium`			DECIMAL(5,2)	NOT NULL DEFAULT 0 CHECK (`premium` >= 0),
    `salary`			DECIMAL(5,2)	NOT NULL CHECK (`salary` > 0),
    `employment_date`	DATE			NOT NULL CHECK (`employment_date` > '1990.01.01')
);

CREATE TABLE IF NOT EXISTS `academy`.`group` (
	PRIMARY KEY (`id`),
	`id`		INT				NOT NULL AUTO_INCREMENT,
    `name`		VARCHAR(10)		NOT NULL UNIQUE CHECK (length(`name`) > 0),
    `rating`	INT				NOT NULL CHECK (`rating` >= 0 && `rating` <= 5),
    `year`		INT				NOT NULL CHECK (`year` >= 1 && `year` <= 5)
);

CREATE TABLE IF NOT EXISTS `academy`.`department` (
	PRIMARY KEY (`id`),
	`id`		INT				NOT NULL AUTO_INCREMENT,
    `financing`	DECIMAL(5,2)	NOT NULL DEFAULT 0 CHECK (`financing` >= 0),
    `name`		VARCHAR(100)	NOT NULL UNIQUE CHECK (length(`name`) > 0)
);

CREATE TABLE IF NOT EXISTS `academy`.`faculty` (
	PRIMARY KEY (`id`),
	`id`		INT				NOT NULL AUTO_INCREMENT,
    `name`		VARCHAR(100)	NOT NULL UNIQUE CHECK (length(`name`) > 0)
);
