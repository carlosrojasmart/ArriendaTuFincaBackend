# Usar la imagen base de OpenJDK 17
FROM openjdk:17

# Establecer el directorio de trabajo en el contenedor
WORKDIR /app

# Copiar el código fuente de la aplicación
COPY . .

# Otorgar permisos de ejecución al wrapper de Maven
RUN chmod +x mvnw

# Construir el JAR sin ejecutar pruebas
RUN ./mvnw clean package -DskipTests

# Exponer el puerto en el que corre la aplicación (ajústalo si usas otro)
EXPOSE 8080

# Ejecutar la aplicación cuando se inicie el contenedor
CMD ["java", "-jar", "target/arriendatufinca-0.0.1-SNAPSHOT.jar"]