-- db
CREATE DATABASE IF NOT EXISTS MovieSite DEFAULT CHARSET 'utf8';
USE MovieSite;

-- tables

CREATE TABLE IF NOT EXISTS `Nationality` (
    PRIMARY KEY (`id`),
    `id`        INT     NOT NULL AUTO_INCREMENT,
    `name`      TEXT    NOT NULL UNIQUE CHECK ( length(`name`) > 0 )
);

CREATE TABLE IF NOT EXISTS `Genre` (
    PRIMARY KEY (`id`),
    `id`    INT     NOT NULL AUTO_INCREMENT,
    `name`  TEXT    NOT NULL UNIQUE CHECK ( length(`name`) > 0 )
);

CREATE TABLE IF NOT EXISTS `Person` (
    PRIMARY KEY (`id`),
    `id`                INT     NOT NULL AUTO_INCREMENT,
    `first_name`        TEXT    NOT NULL CHECK ( length(`first_name`) > 0 ),
    `last_name`         TEXT    NOT NULL CHECK ( length(`last_name`) > 0 ),
    `nationality_id`    INT     NOT NULL,
    `birthday`          DATE    NOT NULL CHECK ( `birthday` > '1800.01.01' ),
    `is_actor`          BIT     NOT NULL,
    `is_director`       BIT     NOT NULL,
    CONSTRAINT fk_person_nationality FOREIGN KEY (`nationality_id`) REFERENCES `Nationality` (`id`) ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS `Movie` (
    PRIMARY KEY (`id`),
    `id`                INT     NOT NULL AUTO_INCREMENT,
    `director_id`       INT     NOT NULL,
    `title`             TEXT    NOT NULL CHECK ( LENGTH(`title`) > 0 ),
    `release_date`      DATE    NOT NULL CHECK ( `release_date` > '1900.01.01' ),
    `rating`            FLOAT   NOT NULL CHECK ( `rating` > 0 && `rating` <= 10 ),
    `plot`              TEXT    NOT NULL CHECK ( LENGTH(`plot`) > 0 ),
    `movie_len_minutes` INT     NOT NULL CHECK ( `movie_len_minutes` > 0 && `movie_len_minutes` <= 600 ),
    CONSTRAINT fk_movie_director FOREIGN KEY (`director_id`) REFERENCES `Person` (`id`) ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS `MovieActor` (
    PRIMARY KEY (`movie_id`, `actor_id`),
    `movie_id`    INT   NOT NULL,
    `actor_id`    INT   NOT NULL,
    CONSTRAINT fk_movie_actor_movie FOREIGN KEY (`movie_id`) REFERENCES `Movie` (`id`) ON DELETE RESTRICT,
    CONSTRAINT fk_movie_actor_actor FOREIGN KEY (`actor_id`) REFERENCES `Person` (`id`) ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS `MovieGenre` (
    PRIMARY KEY (`movie_id`, `genre_id`),
    `movie_id`  INT     NOT NULL,
    `genre_id`  INT     NOT NULL,
    CONSTRAINT fk_movie_genre_movie FOREIGN KEY (`movie_id`) REFERENCES `Movie` (`id`) ON DELETE RESTRICT,
    CONSTRAINT fk_movie_genre_genre FOREIGN KEY (`genre_id`) REFERENCES `Genre` (`id`) ON DELETE RESTRICT
);

-- data

INSERT INTO `Genre` (`id`, `name`)
VALUES
(1, 'Action'), (2, 'Animation'), (3, 'Comedy'), (4, 'Crime'), (5, 'Drama');

INSERT INTO `Nationality` (`id`, `name`)
VALUES
(1, 'American'), (2, 'French'), (3, 'British'), (4, 'Australian');

INSERT INTO `Person` (`id`, `first_name`, `last_name`, `nationality_id`, `birthday`, `is_actor`, `is_director`)
VALUES
(1,     'Frank',        'Darabont',     2, '1959-01-28', 1, 1),
(2,     'Tim',          'Robbins',      1, '1958-11-16', 1, 1),
(3,     'Bob',          'Gunton',       1, '1945-11-15', 1, 0),
(4,     'William',      'Sadler',       1, '1950-04-13', 1, 0),
(5,     'Morgan',       'Freeman',      1, '1937-07-01', 1, 1),
(6,     'Francis',      'Coppola',      1, '1939-04-07', 1, 1),
(7,     'Marlon',       'Brando',       1, '1924-04-03', 1, 0),
(8,     'Al',           'Pacino',       1, '1940-04-25', 1, 1),
(9,     'James',        'Caan',         1, '1940-03-26', 1, 0),
(10,    'Richard',      'Castellano',   1, '1933-09-04', 1, 0),
(11,    'Christopher',  'Nolan',        3, '1970-07-30', 0, 1),
(12,    'Christian',    'Bale',         3, '1974-01-30', 1, 0),
(13,    'Heath',        'Ledger',       4, '1979-04-04', 1, 1),
(14,    'Aaron',        'Eckhart',      1, '1968-03-12', 1, 0),
(15,    'Michael',      'Caine',        3, '1933-03-14', 1, 0);

INSERT INTO `Movie` (`id`, `director_id`, `title`, `release_date`, `rating`, `movie_len_minutes`, `plot`)
VALUES
(1, 1,  'The Shawshank Redemption', '1994-10-14', 9.3, 142, 'Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.'),
(2, 6,  'The Godfather',            '1972-03-24', 9.2, 175, 'The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.'),
(3, 11, 'The Dark Knight',          '2008-08-14', 9.0, 152, 'When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.');

INSERT INTO `MovieActor` (`movie_id`, `actor_id`)
VALUES
(1, 2), (1, 3), (1, 4), (1, 5),
(2, 7), (2, 8), (2, 9), (2, 10),
(3, 12), (3, 13), (3, 14), (3, 15);

INSERT INTO `MovieGenre` (`movie_id`, `genre_id`)
VALUES
(1, 5),
(2, 4), (2, 5),
(3, 1), (3, 4), (3, 5);