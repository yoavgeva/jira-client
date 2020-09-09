FROM openjdk:11-jdk-slim
# Copies the compiled jar file to the root of the container -- DOUBLE CHECK THIS.
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} jira-bts.jar
 # Sets up ports the container listens on: 8080 (spring boots default) and 8000 (debugger port)
EXPOSE 8080 8000
# Specifies the command that will start up the application.
ENTRYPOINT ["java","-jar","/jira-bts.jar"]