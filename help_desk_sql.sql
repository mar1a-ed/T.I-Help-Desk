create database help_desk;

use help_desk;

select * from tb_users;

describe tb_users;

alter table tb_users modify column role enum('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_SUPPORT', 'ROLE_USER');

alter table tb_users add column updated_at datetime(6);