FROM eclipse-temurin:21-jdk

# Instalar librerías necesarias para JasperReports
RUN apt-get update && apt-get install -y \
    libfreetype6 \
    fontconfig \
    fonts-dejavu \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY target/*.jar app.jar

ENV JAVA_TOOL_OPTIONS="-Djava.awt.headless=true"

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]