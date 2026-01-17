# Estágio de Build
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# 1. Copia o arquivo de configuração de dependências primeiro
COPY pom.xml /app/

# 2. Copia a pasta de código fonte
COPY src /app/src

# 3. Lista os arquivos para termos certeza (aparecerá no log do Docker se falhar)
RUN ls -la /app

# 4. Agora sim, executa o empacotamento
RUN mvn clean package -DskipTests

# Estágio de Execução
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]