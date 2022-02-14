FROM gradle:7.4.0-jdk11-alpine AS build
COPY --chown=gradle:gradle ./server /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle clean build --no-daemon

FROM node:14-alpine AS ui
WORKDIR /app

COPY ./ui/ ./
RUN yarn install

RUN npm install -g @angular/cli
RUN ng build

FROM openjdk:11-jre-slim
EXPOSE 9133
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/front-app.jar /app/front-app.jar

ENTRYPOINT ["java", "-jar", "/app/front-app.jar"]
