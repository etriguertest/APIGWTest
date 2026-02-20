#==========================================
# Etapa 1: Construcción (Build)
# ==========================================
# Usamos una imagen oficial de Maven con Amazon Corretto (Java 17)
FROM maven:3.9.6-amazoncorretto-17 AS builder
WORKDIR /app

# Copiamos primero el pom.xml para aprovechar la caché de Docker
COPY pom.xml .
# Descargamos las dependencias (esto acelera futuras construcciones)
RUN mvn dependency:go-offline

# Copiamos el código fuente de la aplicación
COPY src ./src
# Compilamos y empaquetamos la aplicación (omitiendo tests para mayor rapidez)
RUN mvn clean package -DskipTests

# ==========================================
# Etapa 2: Ejecución (Runtime)
# ==========================================
# Usamos una imagen mucho más ligera (Alpine) solo con el entorno de ejecución de Java
FROM amazoncorretto:17-alpine
WORKDIR /app

# Copiamos el archivo .jar generado en la Etapa 1
COPY --from=builder /app/target/*.jar app.jar

# Exponemos el puerto 8080 (el que usa Spring Boot por defecto)
EXPOSE 8080

# Comando que se ejecutará al arrancar el contenedor
ENTRYPOINT ["java", "-jar", "app.jar"]