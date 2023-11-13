FROM openjdk:18-jdk-alpine
WORKDIR /app

COPY target/main-service.jar /app/mainservice.jar
ENTRYPOINT exec java $JAVA_OPTS -jar mainservice.jar
