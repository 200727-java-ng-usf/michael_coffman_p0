CREATE USER project0_user
WITH password 'project0password';

grant all privileges
on database postgres
to project0_user;

CREATE TABLE user_roles(
	id serial,
	name varchar(25) NOT NULL,
	
	CONSTRAINT user_roles_pk
	PRIMARY KEY (id)
);


create table app_users(
	id serial,
	first_name varchar(25) NOT NULL,
	last_name varchar(25) NOT NULL,
	username varchar(25) NOT NULL,
	PASSWORD varchar(25) NOT NULL,
	email varchar(256) NOT NULL,
	
	
	constraint user_roles_pk 
	primary key (id)
);