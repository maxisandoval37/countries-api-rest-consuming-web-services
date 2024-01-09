FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-17-jdk -y
COPY . .

RUN chmod +x mvnw
RUN chmod +x ./mvnw
RUN ./mvnw dependency:go-offline -B
RUN ./mvnw clean package -DskipTests

FROM openjdk:17-jdk-slim

EXPOSE 8080

COPY --from=build target/*.jar demo.jar

ENTRYPOINT ["java", "-jar", "demo.jar"]
