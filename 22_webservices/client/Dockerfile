FROM maven:3.8.4-jdk-11 as builder
WORKDIR /app

COPY /client/ .

CMD ["mvn","clean","test"]