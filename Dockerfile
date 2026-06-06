# ====== STAGE 1: BUILD ======
FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# ====== STAGE 2: RUN ======
FROM eclipse-temurin:21-jdk

# 🔥 DEPENDENCIAS CRÍTICAS PARA JASPER + FONTS + JAVA AWT
RUN apt-get update && apt-get install -y \
    libfreetype6 \
    libfreetype6-dev \
    fontconfig \
    fonts-dejavu \
    libx11-6 \
    libxext6 \
    libxrender1 \
    libxtst6 \
    libxi6 \
    libxrandr2 \
    libgtk-3-0 \
    libglib2.0-0 \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

# 🔥 IMPORTANTE PARA JASPER EN SERVIDOR
ENV JAVA_TOOL_OPTIONS="-Djava.awt.headless=true"

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]