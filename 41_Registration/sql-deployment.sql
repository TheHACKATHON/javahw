-- можно не разворачивать, в приложении кредлы на такую же внешнюю базу
-- dbs --

CREATE DATABASE IF NOT EXISTS `my_site_db`;
USE `my_site_db`;

-- tables --

CREATE TABLE IF NOT EXISTS `gender`
(
    `id`   INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` NVARCHAR(30) NOT NULL UNIQUE CHECK ( LENGTH(`name`) > 0 )
);

CREATE TABLE IF NOT EXISTS `user`
(
    `id`         INT           NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `username`   NVARCHAR(50)  NOT NULL UNIQUE CHECK ( LENGTH(`username`) > 0 ),
    `password`   NVARCHAR(100) NOT NULL CHECK ( LENGTH(`password`) > 0 ),
    `email`      NVARCHAR(100) NOT NULL UNIQUE CHECK ( LENGTH(`email`) > 0 ),
    `first_name` NVARCHAR(100) NOT NULL CHECK ( LENGTH(`first_name`) > 0 ),
    `last_name`  NVARCHAR(100) NOT NULL CHECK ( LENGTH(`last_name`) > 0 ),
    `gender_id`  INT           NOT NULL,
    `birthday`   DATE          NOT NULL CHECK ( `birthday` > '1900-01-01' ),
    `subscribe`  BIT           NOT NULL,
    `cookie`     NVARCHAR(200) NULL,
    CONSTRAINT fk_user_gender FOREIGN KEY (`gender_id`) REFERENCES `gender` (`id`) ON DELETE RESTRICT
);

-- fill enums --

INSERT INTO `gender`
    (`name`)
VALUES ('male'),
       ('female'),
       ('other');

-- procedures --

CREATE PROCEDURE `sp_create_user`(IN $username NVARCHAR(50),
                                  IN $password NVARCHAR(100),
                                  IN $email NVARCHAR(100),
                                  IN $first_name NVARCHAR(100),
                                  IN $last_name NVARCHAR(100),
                                  IN $gender_id INT,
                                  IN $birthday DATE,
                                  IN $sub BIT)
BEGIN
    INSERT INTO `user` (`username`, `password`, `email`, `first_name`, `last_name`, `gender_id`, `birthday`,
                        `subscribe`, `cookie`)
    VALUES ($username, $password, $email, $first_name, $last_name, $gender_id, $birthday, $sub, NULL);
END;

CREATE PROCEDURE `sp_get_genders`()
BEGIN
    SELECT * FROM `gender`;
END;

CREATE PROCEDURE `sp_get_users`()
BEGIN
    SELECT `u`.`id`,
           `u`.`username`,
           `u`.`password`,
           `u`.`email`,
           `u`.`first_name`,
           `u`.`last_name`,
           `g`.`name` as `gender`,
           `u`.`birthday`,
           `u`.`subscribe`,
           `u`.`cookie`
    FROM `user` AS `u`
             LEFT JOIN `gender` AS `g` ON `u`.`gender_id` = `g`.`id`;
END;

CREATE PROCEDURE `sp_get_user_by_cookie`(IN $cookie NVARCHAR(200))
BEGIN
    SELECT `u`.`id`,
           `u`.`username`,
           `u`.`password`,
           `u`.`email`,
           `u`.`first_name`,
           `u`.`last_name`,
           `g`.`name` as gender,
           `u`.`birthday`,
           `u`.`subscribe`,
           `u`.`cookie`
    FROM `user` AS `u`
             LEFT JOIN `gender` AS `g` ON `u`.`gender_id` = `g`.`id`
    WHERE `u`.`cookie` = $cookie;
END;

CREATE PROCEDURE `sp_get_user_by_username`(IN $username NVARCHAR(50))
BEGIN
    SELECT `u`.`id`,
           `u`.`username`,
           `u`.`password`,
           `u`.`email`,
           `u`.`first_name`,
           `u`.`last_name`,
           `g`.`name` as gender,
           `u`.`birthday`,
           `u`.`subscribe`,
           `u`.`cookie`
    FROM `user` AS `u`
             LEFT JOIN `gender` AS `g` ON `u`.`gender_id` = `g`.`id`
    WHERE `u`.`username` = $username;
END;

CREATE PROCEDURE `sp_set_cookie_to_user`(IN $username NVARCHAR(50),
                                         IN $cookie NVARCHAR(200))
BEGIN
    UPDATE `user`
    SET `cookie` = $cookie
    WHERE `username` = $username;
END;

CREATE PROCEDURE `sp_get_username_count`(IN $username NVARCHAR(50))
BEGIN
    SELECT COUNT(*) AS count
    FROM `user`
    WHERE `username` = $username;
END;

-- test data (superdev, superdev)
CALL sp_create_user(
        'superdev',
        'F7CB0D28B1BD03EE8B6D95E8EF804C39',
        'superdev@sitename',
        'Super',
        'Admin',
        1,
        '2000.01.01',
        1
    );

-- можно не разворачивать, в приложении кредлы на такую же внешнюю базу
