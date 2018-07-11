FROM openjdk:8-jdk-alpine
LABEL maintainer="@Anh3h"
WORKDIR /tmp
COPY build/libs/library-0.0.1-SNAPSHOT.jar .
CMD ["java", "-jar", "library-0.0.1-SNAPSHOT.jar"]
