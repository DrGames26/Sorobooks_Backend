FROM openjdk:17-jdk-slim

WORKDIR /app

# Copia apenas o pom.xml e atualiza o cache e instala o Maven em uma única instrução
COPY pom.xml ./

RUN apt-get update && \
    apt-get install -y maven && \
    mvn dependency:go-offline -B && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Copia o restante do código para o contêiner
COPY src ./src

# Compila o projeto com Maven, pulando os testes
RUN mvn clean package -DskipTests

# Copia o JAR gerado para o contêiner
COPY target/*.jar app.jar

# Define o comando de inicialização
ENTRYPOINT ["java", "-jar", "app.jar"]
