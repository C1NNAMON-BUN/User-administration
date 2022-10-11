DROP TABLE IF EXISTS USERDAO;
DROP TABLE IF EXISTS ROLEDAO;

CREATE TABLE IF NOT EXISTS ROLEDAO (
id IDENTITY,
name VARCHAR(255) NOT NULL UNIQUE);

CREATE TABLE IF NOT EXISTS USERDAO (
id IDENTITY,
login VARCHAR(100) UNIQUE,
password VARCHAR(100),
email VARCHAR(100) UNIQUE,
firstname VARCHAR(100),
lastname VARCHAR(100),
birthday DATE,
id_Role LONG NOT NULL);

INSERT INTO ROLEDAO VALUES ('1', 'Admin');
INSERT INTO ROLEDAO VALUES ('2', 'User');
INSERT INTO USERDAO (login, password, email, firstName, lastName, birthday,
                     id_Role) VALUES ( 'user', 'user', 'kirill@domain.com', 'Kirill', 'Ostashkin',
                                      '2003-08-10','2');
INSERT INTO USERDAO (login, password, email, firstName, lastName, birthday,
                     id_Role) VALUES ( 'admin', 'admin', 'svetlana@domain.com', 'Svetlana', 'Zhilina',
                                      '2001-05-26','1');