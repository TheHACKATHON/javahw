drop database if exists `article_db`;
create database `article_db` default charset 'utf8';
use `article_db`;

drop user if exists 'article_user'@'%';
create user 'article_user'@'%' identified by '@superpassword';
revoke all on *.* from 'article_user'@'%';
grant execute on `article_db`.* TO 'article_user'@'%';

create table `user`
(
  `id`          bigint unsigned auto_increment not null primary key,
  `email`       nvarchar(100) unique           not null check ( length(`email`) > 0 ),
  `password`    varchar(32)                    not null check ( length(`password`) = 32 ),
  `first_name`  nvarchar(100)                  not null check ( length(`first_name`) > 0 ),
  `last_name`   nvarchar(100)                  not null check ( length(`last_name`) > 0 ),
  `is_disabled` bit                            not null default 0
);

create table `post`
(
  `id`        bigint unsigned auto_increment not null primary key,
  `title`     nvarchar(200)                  not null check ( length(`title`) > 0 ),
  `subtitle`  nvarchar(500)                  not null check ( length(`subtitle`) > 0 ),
  `image_url` nvarchar(100)                  not null check ( length(`image_url`) > 0 ),
  `html_body` nvarchar(10000)                not null check ( length(`html_body`) > 0 ),
  `author_id` bigint unsigned                not null,
  `created`   datetime                       not null default current_timestamp(),
  `modified`  datetime                       not null default current_timestamp(),
  `is_hidden` bit                            not null default 0,

  foreign key `fk_post_author` (`author_id`) references `user` (`id`) on delete restrict
);

create procedure `get_posts_sp`(
  in $limit int
)
begin
  select P.id,
         P.title,
         P.html_body,
         P.created,
         concat(U.last_name, ' ', U.first_name) as author,
         U.id as authorId,
         P.image_url,
         P.subtitle
  from post as P
         join user as U on P.author_id = U.id
  where is_hidden = 0
  order by P.created desc
  limit $limit;
end;

create procedure `get_post_sp`(
  in $post_id int
)
begin
  select P.id,
         P.title,
         P.html_body,
         P.created,
         concat(U.last_name, ' ', U.first_name) as author,
         U.id as authorId,
         P.image_url,
         P.subtitle
  from post as P
         join user as U on P.author_id = U.id
  where P.id = $post_id
    and is_hidden = 0
  order by P.created desc
  limit 1;
end;

create procedure `create_post_sp`(in $title nvarchar(200),
                                  in $subtitle nvarchar(500),
                                  in $image_url nvarchar(100),
                                  in $html_body nvarchar(10000),
                                  in $author_id bigint unsigned)
begin
  insert into `post` (title, subtitle, image_url, html_body, author_id)
  values ($title, $subtitle, $image_url, $html_body, $author_id);

  select last_insert_id();
end;

create procedure `update_post_sp`(in $id bigint unsigned,
                                  in $title nvarchar(200),
                                  in $subtitle nvarchar(500),
                                  in $image_url nvarchar(100),
                                  in $html_body nvarchar(10000))
begin
  update `post`
  set title     = $title,
      subtitle  = $subtitle,
      image_url = $image_url,
      html_body = $html_body,
      modified  = current_timestamp()
  where id = $id;
end;

create procedure `delete_post_sp`(in $post_id bigint unsigned)
begin
  update `post`
  set is_hidden = 1,
      modified  = current_timestamp()
  where id = $post_id;
end;

create procedure `get_user_by_email_sp`(
  in $user_email nvarchar(100)
)
begin
  select U.id,
         U.email,
         U.password,
         U.first_name,
         U.last_name
  from user as U
  where U.email = $user_email;
end;

