FROM openjdk:19
ARG JAR_FILE=target/*.jar
COPY $JAR_FILE testing-webapp-0.0.1-SNAPSHOT.jar
EXPOSE 8090
ENTRYPOINT ["java", "-jar", "/testing-webapp-0.0.1-SNAPSHOT.jar"]