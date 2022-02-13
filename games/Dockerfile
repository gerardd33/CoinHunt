FROM gradle:7.4.0-jdk11-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle clean build --no-daemon

FROM openjdk:11-jre-slim
EXPOSE 9111
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/games-app.jar /app/games-app.jar

ENTRYPOINT ["java", "-jar", "/app/games-app.jar"]
