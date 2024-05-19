FROM openjdk:20-jdk

LABEL authors="mrthinkj"

WORKDIR .

COPY target/Kindergarten.jar Kindergarten.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/Kindergarten.jar"]