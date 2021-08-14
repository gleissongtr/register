FROM openjdk:11-jdk
MAINTAINER Gleicilenio
WORKDIR /app
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]