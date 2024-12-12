FROM maven:3.8.8-eclipse-temurin-21-alpine AS build
WORKDIR /backend
COPY backend/ .
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /backend/target/*.jar /app/application.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "application.jar"]
