FROM openjdk:8-jdk-alpine
LABEL maintainer="@Anh3h"
ARG database_ip
WORKDIR /tmp
COPY E-Library-874064026c9c.json .
ENV GOOGLE_APPLICATION_CREDENTIALS="E-Library-874064026c9c.json"
ENV SPRING_DATASOURCE_URL=jdbc:mysql://${database_ip}:3306/library?autoReconnect=true&useSSL=false
COPY build/libs/library-0.0.1-SNAPSHOT.jar .
CMD ["java", "-jar", "library-0.0.1-SNAPSHOT.jar"]
