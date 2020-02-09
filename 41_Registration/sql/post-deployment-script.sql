use `ShopDB`;

-- region tmp tables

create temporary table `tmp_role`(
  `name` nvarchar(50)
);

insert into `tmp_role`
values
  ('superdev'),
  ('manager'),
  ('vendor'),
  ('user');

-- endregion tmp tables
-- region fill constants

insert into `role` (`name`)
select `name` from `tmp_role`
where not exists (select `id` from `role`);

-- endregion fill constants
-- region delete tmp tables

drop temporary table `tmp_role`;

-- endregion delete tmp tables