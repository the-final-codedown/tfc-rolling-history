FROM openjdk:8-jre-alpine

EXPOSE 8084

RUN mkdir /app
WORKDIR /app
ADD target/tfc-rolling-history-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT java -jar app.jar