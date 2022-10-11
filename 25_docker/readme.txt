Для начала необходимо перейти в модуль с докером: cd 25_docker
Для запуска достаточно ввести: mvn package и docker-compose up --build

Вариант проверки 1:
Шаг 1. mvn package - сборка проекта и получение артефакта jar
Шаг 2. docker build -t zhylina-25_docker:1.0 .
Шаг 3. docker-compose run db
Шаг 4. docker run --rm --network host zhylina-25_docker:1.0

Вариант проверки 2:
Шаг 1. mvn package - сборка проекта и получение артефакта jar
Шаг 2. docker build -t zhylina-25_docker:1.0 .
Шаг 3. docker run -t zhylina-25_docker:1.0 .
Шаг 4. docker-compose up --build



