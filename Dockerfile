# Use a imagem base do OpenJDK
FROM openjdk:17-jdk-slim

# Defina o diretório de trabalho
WORKDIR /app

# Copie o arquivo pom.xml e o diretório src
COPY pom.xml .
COPY src ./src

# Execute o Maven para construir o projeto
RUN apt-get update && apt-get install -y maven && \
    mvn clean package

# Copie o arquivo JAR gerado para o diretório de trabalho
COPY target/*.jar app.jar

# Comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
