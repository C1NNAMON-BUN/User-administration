    DROP TABLE IF EXISTS USERDAO;
    DROP TABLE IF EXISTS ROLEDAO;

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
        PRIMARY KEY (id), FOREIGN KEY (idRole) references ROLEDAO(idRole)
        );


    INSERT INTO ROLEDAO (name)
    VALUES ('Admin');
    INSERT INTO ROLEDAO (name)
    VALUES ('User');

    INSERT INTO USERDAO (login, password, email, firstName, lastName, birthday, idRole)
    VALUES ('admin', '$2a$12$Edt1FUr9kBNqjgpt78/l8../l46imb04nyTzX/W7UskNYeuiPzIp6', 'emailAdmin@gmail.com', 'Svitlana',
            'Zhylina', '2014-01-01', '1');
    INSERT INTO USERDAO (login, password, email, firstname, lastname, birthday, idRole)
    VALUES ('user', '$2a$10$/2FtLJ5JNljInCK9Gxoo4.wJATwiju0Kc/MWLwr8lrAIFQBShHM4e', 'emailUser@gmail.com', 'Svetlana',
            'Zhilina', '2016-02-02', '2');
    INSERT INTO USERDAO (login, password, email, firstname, lastname, birthday, idRole)
    VALUES ('users', '$2a$10$ZrH4sqMlaACDNpuTLdZiSeZRsva1yh8U/OP3V07RZkW9Z69nZ7XYq', 'emsasailUser@gmail.com', 'Sveta',
            'Zhilina', '2016-02-02', '2');
    INSERT INTO USERDAO (login, password, email, firstname, lastname, birthday, idRole)
    VALUES ('ussers', '$2a$10$/nqKxyo7fQ5Mzym2vQDCJuDvcjwkHhC8HiqJzvIyDlI8feu6Q8uMa', 'emsaailUser@gmail.com', 'Sveta',
            'Zhylina', '2016-02-02', '2');