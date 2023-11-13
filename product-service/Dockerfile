FROM openjdk:18-jdk-alpine
WORKDIR /app
COPY target/product-service.jar /app/productservice.jar
ENTRYPOINT exec java $JAVA_OPTS -jar productservice.jar