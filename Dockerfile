FROM openjdk:17-jre-slim
COPY target/your-app-name.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
