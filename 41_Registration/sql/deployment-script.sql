-- region create db

drop database if exists `ShopDB`;
create database `ShopDB` default charset 'utf8';

-- endregion create db
use `ShopDB`;
-- region users

drop user if exists 'shop_user'@'%';
create user 'shop_user'@'%' identified by '9@mns59ux%^z9x3$%xa!5q8*@*c3vjsy';
revoke all on *.* from 'shop_user'@'%';
grant execute on `ShopDB`.* to 'shop_user'@'%';

drop user if exists 'shop_dev'@'%';
create user 'shop_dev'@'%' identified by '9b*5mw7uskxb4dmy%n6y4$6x#4*c%mjj';
revoke all on *.* from 'shop_dev'@'%';
grant all on `ShopDB`.* to 'shop_dev'@'%';

-- endregion users
-- region tables

create table if not exists `user`
(
  `id`                 bigint unsigned auto_increment not null primary key,
  `email`              nvarchar(100) unique           not null check ( length(`email`) > 5 ),
  `username`           nvarchar(50) unique            not null check ( length(`username`) > 2 ),
  `password`           nchar(32)                      not null check ( length(`password`) = 32 ),
  `password_salt`      nchar(10)                      not null check ( length(`password_salt`) = 10 ),
  `salt_position`      tinyint unsigned               not null,
  `created`            timestamp                      not null default current_timestamp(),
  `is_blocked`         bit                            not null default 0,
  `last_login`         timestamp                      not null default current_timestamp(),
  `failed_login_count` bigint                         not null default 0,
  `is_removed`         bit                            not null default 0
);

create table if not exists `user_details`
(
  `user_id` bigint unsigned not null primary key,
  `mobile`  nvarchar(20)    null,

  foreign key `fk_user_details_user` (`user_id`) references `user` (`id`)
);

create table if not exists `role`
(
  `id`   bigint unsigned auto_increment not null primary key,
  `name` nvarchar(50) unique            not null check ( length(`name`) > 0 )
);

create table if not exists `user_role`
(
  `role_id` bigint unsigned not null,
  `user_id` bigint unsigned not null,

  unique key `uk_user_role` (`role_id` asc, `user_id` asc),
  foreign key `fk_user_role_role` (`role_id`) references `role` (`id`) on delete restrict,
  foreign key `fk_user_role_user` (`user_id`) references `user` (`id`) on delete restrict
);

create table if not exists `product`
(
  `id`          bigint unsigned auto_increment not null primary key,
  `name`        nvarchar(200)                  not null check ( length(`name`) > 0 ),
  `description` nvarchar(5000)                 not null check ( length(`description`) > 0 ),
  `price`       decimal(18, 2)                 not null check ( `price` > 0 ),
  `in_stock`    int unsigned                   not null check ( `in_stock` >= 0 ),
  `created`     timestamp                      not null default current_timestamp(),
  `vendor_id`   bigint unsigned                not null,
  `visit_count` bigint unsigned                not null default 0,

  foreign key `fk_product_user` (`vendor_id`) references `user` (`id`) on delete restrict
);

create table if not exists `product_modify`
(
  `product_id`     bigint unsigned not null primary key,
  `modified`       timestamp       not null default current_timestamp(),
  `modified_by_id` bigint unsigned not null,
  `changelog`      nvarchar(10000) not null check ( length(`changelog`) > 0 ),

  foreign key `fk_product_modify_product` (`product_id`) references `product` (`id`) on delete restrict,
  foreign key `fk_product_modify_user` (`modified_by_id`) references `user` (`id`) on delete restrict
);

create table if not exists `image`
(
  `id`        bigint unsigned auto_increment not null primary key,
  `original`  nvarchar(500)                  not null check ( length(`original`) > 0 ),
  `big`       nvarchar(500)                  not null check ( length(`big`) > 0 ),
  `medium`    nvarchar(500)                  not null check ( length(`medium`) > 0 ),
  `small`     nvarchar(500)                  not null check ( length(`small`) > 0 ),
  `thumbnail` nvarchar(500)                  not null check ( length(`thumbnail`) > 0 )
);

create table if not exists `product_image`
(
  `product_id` bigint unsigned not null,
  `image_id`   bigint unsigned not null,

  foreign key `fk_product_image_product` (`product_id`) references `product` (`id`) on delete restrict,
  foreign key `fk_product_image_image` (`image_id`) references `image` (`id`) on delete restrict
);

create table if not exists `feature_group`
(
  `id`   bigint unsigned auto_increment not null primary key,
  `name` nvarchar(100)                  not null check ( length(`name`) > 0 )
);

create table if not exists `feature`
(
  `id`          bigint unsigned auto_increment not null primary key,
  `key`         nvarchar(100)                  not null check ( length(`key`) > 0 ),
  `description` nvarchar(500)                  not null check ( length(`description`) > 0 ),
  `group_id`    bigint unsigned                not null,

  unique key `uk_key_group` (`key`, `group_id`),
  foreign key `fk_feature_feature_group` (`group_id`) references `feature_group` (`id`) on delete restrict
);

create table if not exists `product_feature`
(
  `product_id`  bigint unsigned not null,
  `feature_id`  bigint unsigned not null,
  `feature_val` nvarchar(100)   not null check ( length(`feature_val`) > 0 ),

  unique key `uk_product_feature` (`product_id`, `feature_id`),
  foreign key `fk_product_feature_product` (`product_id`) references `product` (`id`) on delete restrict,
  foreign key `fk_product_feature_feature` (`feature_id`) references `feature` (`id`) on delete restrict
);

create table if not exists `cart`
(
  `id`         bigint unsigned auto_increment not null primary key,
  `user_id`    bigint unsigned                not null,
  `created`    timestamp                      not null default current_timestamp(),
  `is_enabled` bit                            not null default 1,

  foreign key `fk_cart_user` (`user_id`) references `user` (`id`) on delete restrict
);

create table if not exists `cart_product`
(
  `cart_id`       bigint unsigned not null,
  `product_id`    bigint unsigned not null,
  `product_count` int unsigned    not null check ( `product_count` > 0 ),

  unique key `uk_cart_product` (`cart_id`, `product_id`),
  foreign key `fk_cart_product_cart` (`cart_id`) references `cart` (`id`),
  foreign key `fk_cart_product_product` (`product_id`) references `product` (`id`)
);

create table if not exists `order`
(
  `id`                 bigint unsigned auto_increment not null primary key,
  `cart_id`            bigint unsigned                not null,
  `f_name`             nvarchar(100)                  not null check ( length(`f_name`) > 0 ),
  `l_name`             nvarchar(100)                  not null check ( length(`l_name`) > 0 ),
  `city`               nvarchar(100)                  not null check ( length(`city`) > 0 ),
  `address`            nvarchar(500)                  not null check ( length(`address`) > 0 ),
  `created`            timestamp                      not null default current_timestamp(),
  `notification_email` nvarchar(100)                  not null,

  foreign key `fk_order_cart` (`cart_id`) references `cart` (`id`) on delete restrict
);

create table if not exists `exception_log`
(
  `id`                  bigint unsigned auto_increment not null primary key,
  `message`             nvarchar(500)                  not null,
  `stacktrace`          nvarchar(5000)                 not null,
  `created`             timestamp                      not null default current_timestamp(),
  `file`                nvarchar(100)                  not null,
  `parent_exception_id` bigint unsigned                null
);

-- endregion tables
-- region procedures

create procedure create_user(in `@username` nvarchar(50),
                             in `@email` nvarchar(100),
                             in `@password` nchar(32),
                             in `@password_salt` nchar(10),
                             in `@salt_pos` tinyint unsigned,
                             in `@role_id` bigint unsigned)
begin
  -- get user.*
end;

create procedure get_user_password_info(in `@username` bigint unsigned)
begin
  -- get salt + pos
end;

create procedure login_user(in `@username` nvarchar(50),
                            in `@password` nchar(32))
begin
  -- get 0/1
end;

create procedure change_password(in `@username` nvarchar(50),
                                 in `@old_password` nchar(32),
                                 in `@new_password` nchar(32),
                                 in `@new_password_salt` nchar(10),
                                 in `@new_salt_pos` tinyint unsigned)
begin
  -- get 0/1
end;

create procedure log_exception(
)
begin

end;

-- endregion procedures
