# ====== STAGE 1: BUILD ======
FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# ====== STAGE 2: RUN ======
FROM eclipse-temurin:21-jdk

RUN apt-get update && apt-get install -y \
    libfreetype6 \
    fontconfig \
    fonts-dejavu \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

ENV JAVA_TOOL_OPTIONS="-Djava.awt.headless=true"

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]