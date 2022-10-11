INSERT INTO ROLEDAO (name) VALUES('Junior Developer');
INSERT INTO ROLEDAO (name) VALUES('Middle Developer');
INSERT INTO ROLEDAO (name) VALUES('Senior Developer');
INSERT INTO ROLEDAO (name) VALUES('Project Manager');
INSERT INTO ROLEDAO (name) VALUES('Software Architect');

INSERT INTO USERDAO (login, password, email, firstName, lastName, birthday, id_Role) VALUES('login1', 'pass1', 'email1@gmail.com', 'Alushta', 'Andrii', CAST('1988-10-01' as datetime), 1);
INSERT INTO USERDAO (login, password, email, firstName, lastName, birthday, id_Role) VALUES('login2', 'pass2', 'email2@gmail.com', 'Borshchahivkа', 'Borysenko', CAST('2001-11-29' as datetime), 3);
INSERT INTO USERDAO (login, password, email, firstName, lastName, birthday, id_Role) VALUES('login3', 'pass3', 'email3@gmail.com', 'Vinnytsia', 'Volodymyr', CAST('1967-08-01' as datetime), 1);
INSERT INTO USERDAO (login, password, email, firstName, lastName, birthday, id_Role) VALUES('login4', 'pass4', 'email4@gmail.com', 'Galagan', 'Gorgany', CAST('1999-11-29' as datetime), 3);
INSERT INTO USERDAO (login, password, email, firstName, lastName, birthday, id_Role) VALUES('login5', 'pass5', 'email5@gmail.com', 'Donetsk', 'Dmytro', CAST('1980-02-21' as datetime), 2);