FROM maven:3.9.12-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY pom.xml .
COPY /src ./src
RUN --mount=type=cache,target=/root/.m2 \
    mvn clean package -Dmaven.test.skip=true

FROM eclipse-temurin:21.0.9_10-jre-ubi10-minimal
WORKDIR /app
COPY --from=build /app/target/bookManagement*.jar /app/target/libraryService.jar

EXPOSE 8080
ENTRYPOINT ["java", "-Dspring.profiles.active=docker","-jar", "/app/target/libraryService.jar"]