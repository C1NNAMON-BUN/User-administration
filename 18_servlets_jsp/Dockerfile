FROM maven:3.8.4-jdk-11 as builder

EXPOSE 8080
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app

RUN mvn -f /usr/src/app/pom.xml clean package

FROM tomcat:9.0

COPY --from=builder /usr/src/app/target/ROOT.war $CATALINA_HOME/webapps/ROOT.war

CMD ["catalina.sh", "run"]