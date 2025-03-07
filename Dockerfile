FROM maven:3.8.5-openjdk-17-slim AS builder
WORKDIR /app
COPY pom.xml ./
RUN mvn dependency:go-offline
COPY src ./src

RUN mvn clean package -DskipTests
FROM openjdk:17-jdk-slim
ENV external.apitoken="191ef10a7a674820af6cdaf1a5dd9ef8"
ENV external.url="https://api.twelvedata.com"
ARG Dlogging.config=logback-spring.xml
COPY --from=builder /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]