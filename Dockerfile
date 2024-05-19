FROM openjdk:20-jdk
LABEL authors="mrthinkj"
WORKDIR /app
ADD target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]