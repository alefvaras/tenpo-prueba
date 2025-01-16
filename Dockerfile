# Etapa de construcción
FROM gradle:7.6 as builder

WORKDIR /app

# Copiar archivos de configuración de Gradle y código fuente
COPY settings.gradle .
COPY build.gradle .
COPY gradle gradle
COPY src src

# Compilar el proyecto (sin ejecutar pruebas)
RUN gradle clean build -x test

# Etapa de ejecución
FROM openjdk:21-jdk-slim

WORKDIR /app

# Copiar el archivo JAR generado desde la etapa de construcción
COPY --from=builder /app/build/libs/prueba-tenpo-0.0.1-SNAPSHOT.jar prueba-tenpo.jar

# Exponer el puerto 8080
EXPOSE 8080

# Ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "prueba-tenpo.jar"]
