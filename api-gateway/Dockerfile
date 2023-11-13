FROM openjdk:18-jdk-alpine
WORKDIR /app
ENV PORT 9292
EXPOSE 9292
COPY target/api-gateway.jar /app/apigateway.jar
ENTRYPOINT exec java $JAVA_OPTS -jar apigateway.jar