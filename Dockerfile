# 1. Build-Phase mit Maven
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# 2. Runtime-Phase mit schlankem JDK
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Port, den Spring Boot nutzt
EXPOSE 8080

# Startbefehl
ENTRYPOINT ["java","-jar","app.jar"]