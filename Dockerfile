# Estágio de build (compilação)
FROM maven:3.8.5-openjdk-17 AS build

WORKDIR /app

# Copia o POM e as dependências para o cache do Maven
COPY pom.xml ./
RUN mvn dependency:go-offline -B

# Copia o código-fonte e constrói o projeto
COPY src ./src
RUN mvn clean package -DskipTests

# Estágio final
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copia o JAR construído no estágio anterior
COPY --from=build /app/target/*.jar app.jar

# Define o comando de inicialização
ENTRYPOINT ["java", "-jar", "app.jar"]
