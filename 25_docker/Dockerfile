FROM maven:3.8.4-jdk-11-slim as builder

COPY src /usr/src/app/src
COPY pom.xml /usr/src/app

RUN mvn -f /usr/src/app/pom.xml clean package

FROM openjdk:11-jdk-slim

ARG JAR_FILE=/usr/src/app/target/25-docker.jar
ARG JAR_LIB_FILE=target/lib/

WORKDIR /usr/local/runme

COPY --from=builder ${JAR_FILE} 25-docker.jar

ADD ${JAR_LIB_FILE} lib/

ENTRYPOINT ["java","-jar","25-docker.jar"]