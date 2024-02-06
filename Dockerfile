# Set Maven environment
FROM maven:3.8.2-openjdk-11 as build
WORKDIR /app
COPY . /app
RUN mvn clean package -Dmaven.test.skip

# Build application
FROM adoptopenjdk/openjdk11:alpine-jre
WORKDIR /app
COPY --from=build /app/target/*.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]