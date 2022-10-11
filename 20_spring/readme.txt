Для запуска докера используйте команду: docker-compose up --build
После того, как контейнеры поднялись, нужно перейти в браузер, написать localhost:8080.

Для запуска не через докер заменить в файле src/main/resources/config.properties
db.driver=org.h2.Driver
db.url=jdbc:h2:tcp://localhost/~/test
db.user=sa
db.password=

В базе данных хранится два пользователя:
1. Admin (login: admin, password: admin)
2. User (login: user, password: user)