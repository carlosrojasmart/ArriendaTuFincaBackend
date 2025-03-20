# Usar la imagen base de OpenJDK 17
FROM openjdk:17

# Establecer el directorio de trabajo en el contenedor
WORKDIR /app

# Copiar el código fuente de la aplicación
COPY . .

# Otorgar permisos de ejecución al wrapper de Maven
RUN arriendatufinca/mvnw clean install -DskipTests

# Construir el JAR sin ejecutar pruebas
WORKDIR /app/arriendatufinca
RUN ./mvnw clean install -DskipTests


# Ejecutar la aplicación cuando se inicie el contenedor
CMD ["java", "-jar", "target/arriendatufinca-0.0.1-SNAPSHOT.jar"]