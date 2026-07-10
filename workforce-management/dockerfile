FROM openjdk:22-jdk
WORKDIR /app
ADD target/workforce.jar app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]