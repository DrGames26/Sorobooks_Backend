# Use a imagem base do OpenJDK
FROM openjdk:17-jdk-slim

# Defina o diretório de trabalho
WORKDIR /app

# Copie o arquivo JAR gerado para o diretório de trabalho
COPY target/*.jar app.jar

# Comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
