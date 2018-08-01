FROM openjdk:8-jdk-alpine
LABEL maintainer="@Anh3h"
WORKDIR /tmp
COPY E-Library-874064026c9c.json .
ENV GOOGLE_APPLICATION_CREDENTIALS E-Library-874064026c9c.json \
    spring.datasource.url=jdbc:mysql://172.16.238.4:3306/library?autoReconnect=true&useSSL=false \
    spring.datasource.username=root \
    spring.datasource.password=mysql
COPY build/libs/library-0.0.1-SNAPSHOT.jar .
CMD ["java", "-jar", "library-0.0.1-SNAPSHOT.jar"]
