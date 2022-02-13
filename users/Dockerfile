FROM gradle:7.4.0-jdk11-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle clean build --no-daemon

FROM openjdk:11-jre-slim
EXPOSE 9122
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/users-app.jar /app/users-app.jar

ENTRYPOINT ["java", "-jar", "/app/users-app.jar"]
