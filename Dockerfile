FROM maven:3-jdk-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src/ ./src/
RUN mvn clean package

FROM openjdk:20-jdk
LABEL authors="mrthinkj"
WORKDIR /app
COPY --from=build /app/target/Kindergarten.jar ./app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]