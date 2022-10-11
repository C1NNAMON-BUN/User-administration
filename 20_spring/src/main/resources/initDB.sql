drop table USERDAO;
Drop table ROLEDAO;

CREATE TABLE IF NOT EXISTS ROLEDAO
(
    idRole   INT PRIMARY KEY auto_increment,
    name VARCHAR(255) not null
    );
CREATE TABLE IF NOT EXISTS USERDAO
(
    id        INT PRIMARY KEY auto_increment,
    login     VARCHAR(255),
    password  VARCHAR(255),
    email     VARCHAR(255),
    firstName VARCHAR(255),
    lastName  VARCHAR(255),
    birthday  DATE,
    idRole    INTEGER,
    PRIMARY KEY (id), FOREIGN KEY (idRole) references ROLEENTITY(idRole)
    );
INSERT INTO ROLEDAO (name)
VALUES ('Admin');
INSERT INTO ROLEDAO (name)
VALUES ('User');
INSERT INTO USERDAO (login, password, email, firstName, lastName, birthday, idRole)
VALUES ('admin', '$2a$12$Edt1FUr9kBNqjgpt78/l8../l46imb04nyTzX/W7UskNYeuiPzIp6', 'emailAdmin@gmail.com', 'Svetlana', 'Zhilina', '2014-01-01', '1');
INSERT INTO USERDAO (login, password, email, firstname, lastname, birthday, idRole)
VALUES ('user', '$2a$10$WMrnUMGUWiyOtiKJTmrlneIv5j0R3XzpgFtLsiNZU/7Z4aBSbwy1m', 'emailUser@gmail.com', 'Svitlana', 'Zhylina', '2016-02-02', '2');
