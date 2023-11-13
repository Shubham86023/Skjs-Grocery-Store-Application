FROM openjdk:18-jdk-alpine
WORKDIR /app
ENV PORT 8761
EXPOSE 8761
COPY discovery-server/target/*.jar /app/discovery-server.jar
ENTRYPOINT exec java $JAVA_OPTS -jar discovery-server.jar
