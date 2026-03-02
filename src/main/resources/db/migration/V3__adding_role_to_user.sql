alter table users
    add role varchar(20) default 'USER' not null;

--ALTER TABLE `store_api`.`users`
--ADD COLUMN `role` VARCHAR(20) NOT NULL DEFAULT 'USER' AFTER `password`;