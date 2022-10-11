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
                                       id_Role LONG NOT NULL,
                                       FOREIGN KEY (id_Role) REFERENCES ROLEDAO (ID)
                                           ON DELETE CASCADE);

INSERT INTO ROLEDAO VALUES ('1', 'ADMIN');
INSERT INTO ROLEDAO VALUES ('2', 'USER');
INSERT INTO USERDAO VALUES ('1', 'user', 'user', 'user@gmail.com', 'Kate', 'Aleksandrova', '2001-07-09','2');
INSERT INTO USERDAO VALUES ('2', 'admin', 'admin', 'admin@gmail.com', 'Svetlana', 'Zhilina', '2001-05-26','1');