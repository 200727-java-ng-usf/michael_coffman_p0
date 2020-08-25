CREATE USER project0_user
WITH password 'project0password';

grant all privileges
on database postgres
to project0_user;

DROP TABLE user_roles;
DROP TABLE app_users;
DROP TABLE accounts;

-- Create Tables for user_roles, app_users, and accounts
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
	role_id int NOT NULL,
	
	constraint app_users_pk 
	primary key (id),
	
	CONSTRAINT user_roles_fk
	FOREIGN KEY (role_id) 
	REFERENCES user_roles(id)
);

CREATE TABLE accounts(
	id serial,
	name varchar(25) DEFAULT 'Checking' NOT NULL,
	amount numeric(10, 2) DEFAULT 0.00 NOT NULL,
	user_id serial,
	
	CONSTRAINT amount_nonnegative 
	CHECK (amount >= 0)
	
	CONSTRAINT accounts_pk
	PRIMARY KEY (id),
	
	CONSTRAINT app_user_fk
	FOREIGN KEY (user_id) 
	REFERENCES app_users(id) 
	ON DELETE CASCADE 
);


-- Insert constant roles into user_roles
INSERT INTO user_roles (name)
VALUES 
	('Basic User'), ('Admin'), ('LOCKED');

-- Create the function that will prevent overdrafting from accounts table

CREATE OR REPLACE FUNCTION no_overdraft()
RETURNS TRIGGER 
AS $$

	BEGIN 
		 
		IF (NEW.amount < 0) THEN 
			RETURN NULL; -- cancels the execution of the original statement
		END IF;
	
	
	RETURN NEW; -- return to the trigger without halting the original statement
	
	END

$$ LANGUAGE plpgsql;

CREATE TRIGGER no_overdraft
BEFORE INSERT OR UPDATE ON accounts
FOR EACH ROW 
EXECUTE FUNCTION no_overdraft();


COMMIT;

