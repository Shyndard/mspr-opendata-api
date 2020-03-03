FROM maven:3.6.3-jdk-8 AS build

WORKDIR /compile/
COPY . .
RUN mvn package -DskipTests=true

FROM openjdk:8-jdk-alpine

WORKDIR /app/
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
COPY --from=build /compile/target/opendata-api-1.0.0-SNAPSHOT.jar .

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/opendata-api-1.0.0-SNAPSHOT.jar"]