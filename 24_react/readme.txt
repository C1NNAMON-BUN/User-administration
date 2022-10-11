Для локального запуска необходимо исправить следующие строки в application.properties:
spring.hibernate.url = jdbc:h2:tcp://h2-database/test
spring.hibernate.username = sa

Для запуска через docker:
spring.hibernate.url = jdbc:h2:tcp://h2-database/test
spring.hibernate.username =

В базе данных хранится четыре пользователя:
1. Admin (login: admin, password: admin)
2. User (login: user, password: user)
3. User (login: users, password: users)
4. User (login: ussers, password: ussers)