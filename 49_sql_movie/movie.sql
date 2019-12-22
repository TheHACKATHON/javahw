-- db
CREATE DATABASE IF NOT EXISTS movie_site DEFAULT CHARSET 'utf8';
USE movie_site;

-- tables

CREATE TABLE IF NOT EXISTS `Nationality`
(
    PRIMARY KEY (`id`),
    `id`   INT  NOT NULL AUTO_INCREMENT,
    `name` TEXT NOT NULL UNIQUE CHECK ( length(`name`) > 0 )
);

CREATE TABLE IF NOT EXISTS `Genre`
(
    PRIMARY KEY (`id`),
    `id`   INT  NOT NULL AUTO_INCREMENT,
    `name` TEXT NOT NULL UNIQUE CHECK ( length(`name`) > 0 )
);

CREATE TABLE IF NOT EXISTS `Person`
(
    PRIMARY KEY (`id`),
    `id`             INT  NOT NULL AUTO_INCREMENT,
    `first_name`     TEXT NOT NULL CHECK ( length(`first_name`) > 0 ),
    `last_name`      TEXT NOT NULL CHECK ( length(`last_name`) > 0 ),
    `nationality_id` INT  NOT NULL,
    `birthday`       DATE NOT NULL CHECK ( `birthday` > '1800.01.01' ),
    CONSTRAINT fk_person_nationality FOREIGN KEY (`nationality_id`) REFERENCES `Nationality` (`id`) ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS `Movie`
(
    PRIMARY KEY (`id`),
    `id`                INT   NOT NULL AUTO_INCREMENT,
    `director_id`       INT   NOT NULL,
    `title`             TEXT  NOT NULL CHECK ( LENGTH(`title`) > 0 ),
    `release_date`      DATE  NOT NULL CHECK ( `release_date` > '1900.01.01' ),
    `rating`            FLOAT NOT NULL CHECK ( `rating` > 0 && `rating` <= 10 ),
    `plot`              TEXT  NOT NULL CHECK ( LENGTH(`plot`) > 0 ),
    `movie_len_minutes` INT   NOT NULL CHECK ( `movie_len_minutes` > 0 && `movie_len_minutes` <= 600 ),
    CONSTRAINT fk_movie_director FOREIGN KEY (`director_id`) REFERENCES `Person` (`id`) ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS `MovieActor`
(
    PRIMARY KEY (`movie_id`, `actor_id`),
    `movie_id` INT NOT NULL,
    `actor_id` INT NOT NULL,
    CONSTRAINT fk_movie_actor_movie FOREIGN KEY (`movie_id`) REFERENCES `Movie` (`id`) ON DELETE RESTRICT,
    CONSTRAINT fk_movie_actor_actor FOREIGN KEY (`actor_id`) REFERENCES `Person` (`id`) ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS `MovieGenre`
(
    PRIMARY KEY (`movie_id`, `genre_id`),
    `movie_id` INT NOT NULL,
    `genre_id` INT NOT NULL,
    CONSTRAINT fk_movie_genre_movie FOREIGN KEY (`movie_id`) REFERENCES `Movie` (`id`) ON DELETE RESTRICT,
    CONSTRAINT fk_movie_genre_genre FOREIGN KEY (`genre_id`) REFERENCES `Genre` (`id`) ON DELETE RESTRICT
);

-- data

INSERT INTO `Genre` (`id`, `name`)
VALUES (1, 'Action'),
       (2, 'Animation'),
       (3, 'Comedy'),
       (4, 'Crime'),
       (5, 'Drama'),
       (6, 'Thriller'),
       (7, 'Biography'),
       (8, 'History'),
       (9, 'War'),
       (10, 'Fantasy'),
       (11, 'Western');

INSERT INTO `Nationality` (`id`, `name`)
VALUES (1, 'American'),
       (2, 'French'),
       (3, 'British'),
       (4, 'Australian'),
       (5, 'Canadian'),
       (6, 'Austrian');

INSERT INTO `Person` (`id`, `first_name`, `last_name`, `nationality_id`, `birthday`)
VALUES (1, 'Frank', 'Darabont', 2, '1959-01-28'),
       (2, 'Tim', 'Robbins', 1, '1958-11-16'),
       (3, 'Bob', 'Gunton', 1, '1945-11-15'),
       (4, 'William', 'Sadler', 1, '1950-04-13'),
       (5, 'Morgan', 'Freeman', 1, '1937-07-01'),
       (6, 'Francis', 'Coppola', 1, '1939-04-07'),
       (7, 'Marlon', 'Brando', 1, '1924-04-03'),
       (8, 'Al', 'Pacino', 1, '1940-04-25'),
       (9, 'James', 'Caan', 1, '1940-03-26'),
       (10, 'Richard', 'Castellano', 1, '1933-09-04'),
       (11, 'Christopher', 'Nolan', 3, '1970-07-30'),
       (12, 'Christian', 'Bale', 3, '1974-01-30'),
       (13, 'Heath', 'Ledger', 4, '1979-04-04'),
       (14, 'Aaron', 'Eckhart', 1, '1968-03-12'),
       (15, 'Michael', 'Caine', 3, '1933-03-14'),
       (16, 'Sidney', 'Lumet', 1, '1924-06-25'),
       (17, 'Martin', 'Balsam', 1, '1919-11-04'),
       (18, 'John', 'Fiedler', 1, '1925-02-03'),
       (19, 'Lee', 'Cobb', 1, '1911-12-08'),
       (20, 'E.G.', 'Marshall', 1, '1914-06-18'),
       (21, 'Steven', 'Spielberg', 1, '1946-12-18'),
       (22, 'Liam', 'Neeson', 3, '1952-07-07'),
       (23, 'Ben', 'Kingsley', 3, '1943-12-31'),
       (24, 'Ralph', 'Fiennes', 3, '1962-12-22'),
       (25, 'Caroline', 'Goodall', 3, '1959-11-13'),
       (26, 'Tom', 'Hanks', 1, '1956-07-08'),
       (27, 'Tom', 'Sizemore ', 1, '1961-11-29'),
       (28, 'Edward', 'Burns', 1, '1968-01-29'),
       (29, 'Barry', 'Pepper', 5, '1970-04-04'),
       (30, 'David', 'Morse', 1, '1954-10-11'),
       (31, 'Michael', 'Duncan', 1, '1957-12-10'),
       (32, 'Leonardo', 'DiCaprio', 1, '1974-11-11'),
       (33, 'Christopher', 'Walken', 1, '1943-03-31'),
       (34, 'Martin', 'Sheen', 1, '1940-08-03'),
       (35, 'Quentin', 'Tarantino', 1, '1963-03-27'),
       (36, 'Samuel', 'Jackson', 1, '1948-12-21'),
       (37, 'Jamie', 'Foxx', 1, '1967-12-13'),
       (38, 'Christoph', 'Waltz', 6, '1956-10-04'),
       (39, 'Uma', 'Thurman', 1, '1970-04-29'),
       (40, 'Lucy', 'Liu', 1, '1968-12-02'),
       (41, 'Vivica', 'Fox', 1, '1964-07-30'),
       (42, 'Daryl', 'Hannah', 1, '1960-12-03'),
       (43, 'David', 'Carradine ', 1, '1936-12-08');

INSERT INTO `Movie` (`id`, `director_id`, `title`, `release_date`, `rating`, `movie_len_minutes`, `plot`)
VALUES (1, 1, 'The Shawshank Redemption', '1994-10-14', 9.3, 142,
        'Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.'),
       (2, 6, 'The Godfather', '1972-03-24', 9.2, 175,
        'The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.'),
       (3, 11, 'The Dark Knight', '2008-08-14', 9.0, 152,
        'When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.'),
       (4, 16,'12 Angry Men','1957-04-10', 8.9, 96,
        'A jury holdout attempts to prevent a miscarriage of justice by forcing his colleagues to reconsider the evidence.'),
       (5, 21, 'Schindler\'s List', '1994-02-04', 8.9, 195,
        'In German-occupied Poland during World War II, industrialist Oskar Schindler gradually becomes concerned for his Jewish workforce after witnessing their persecution by the Nazis.'),
       (6, 21, 'Saving Private Ryan', '1999-06-24', 8.6, 169,
        'Following the Normandy Landings, a group of U.S. soldiers go behind enemy lines to retrieve a paratrooper whose brothers have been killed in action.'),
       (7, 1, 'The Green Mile', '1999-12-10', 8.6, 186,
        'The lives of guards on Death Row are affected by one of their charges: a black man accused of child murder and rape, yet who has a mysterious gift.'),
       (8, 21,'Catch Me If You Can', '2002-12-25', 8.1, 141,
        'A seasoned FBI agent pursues Frank Abagnale Jr. who, before his 19th birthday, successfully forged millions of dollars'' worth of checks while posing as a Pan Am pilot, a doctor, and a legal prosecutor.'),
       (9, 35, 'Django Unchained', '2012-12-25', 8.4, 165,
        'With the help of a German bounty hunter, a freed slave sets out to rescue his wife from a brutal Mississippi plantation owner.'),
       (10, 35, 'Kill Bill: Vol. 1', '2003-10-10', 8.1, 111,
        'After awakening from a four-year coma, a former assassin wreaks vengeance on the team of assassins who betrayed her.');

INSERT INTO `MovieActor` (`movie_id`, `actor_id`)
VALUES (1, 2), (1, 3), (1, 4), (1, 5),
       (2, 7), (2, 8), (2, 9), (2, 10),
       (3, 12), (3, 13), (3, 14), (3, 15),
       (4, 17), (4, 18), (4, 19), (4, 20),
       (5, 22), (5, 23), (5, 24), (5, 25),
       (6, 26), (6, 27), (6, 28), (6, 29),
       (7, 26), (7, 29), (7, 30), (7, 31),
       (8, 32), (8, 26), (8, 33), (8, 34),
       (9, 32), (9, 36), (9, 37), (9, 38),
       (10, 39), (10, 40), (10, 41), (10, 42), (10, 43);

INSERT INTO `MovieGenre` (`movie_id`, `genre_id`)
VALUES (1, 5),
       (2, 4), (2, 5),
       (3, 1), (3, 4), (3, 5),
       (4, 5),
       (5, 5), (5, 7), (5, 8),
       (6, 5), (6, 9),
       (7, 4), (7, 5), (7, 10),
       (8, 7), (8, 4), (8, 5),
       (9, 5), (9, 11),
       (10, 1), (10, 4), (10, 6);

-- views

-- 1. Выбрать все фильмы и отсортировать по названию
SELECT * FROM `Movie` ORDER BY `title`;

-- 2. Выбрать все фильмы и отсортировать по году выпуска
SELECT * FROM `Movie` ORDER BY `release_date`;

-- 3. Выбрать все фильмы и отсортировать по рейтингу
SELECT * FROM `Movie` ORDER BY `rating`;

-- 4. Выбрать все фильмы и выпущенные в прошлом году
SELECT * FROM `Movie` WHERE YEAR(release_date) = (YEAR(NOW()) - 1);

-- 5. Выбрать все фильмы по заданному жанру (комедия, боевик, ...)
CREATE PROCEDURE `get_movies_by_genre_name`(IN $genre_name TEXT)
BEGIN
    SELECT m.* FROM `Movie` AS m
    JOIN `MovieGenre` AS `mg` ON `m`.`id` = `mg`.`movie_id`
    JOIN `Genre` AS `g` ON `mg`.`genre_id` = `g`.`id`
    WHERE `g`.`name` = $genre_name;
END;

CALL `get_movies_by_genre_name`('Crime');

-- 6. Выбрать все фильмы по заданному актеру
CREATE PROCEDURE `get_movies_by_actor_id`(IN $actor_id INT)
BEGIN
    SELECT m.* FROM `Movie` AS m
    JOIN MovieActor AS ma ON m.id = ma.movie_id
    WHERE ma.actor_id = $actor_id;
END;

CALL `get_movies_by_actor_id`(26);

-- 7. Выбрать все фильмы по заданному продюсеру
CREATE PROCEDURE `get_movies_by_director_id`(IN $director_id INT)
BEGIN
    SELECT m.* FROM `Movie` AS m
    WHERE m.director_id = $director_id;
END;

CALL `get_movies_by_director_id`(1);