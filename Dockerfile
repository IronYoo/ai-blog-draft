FROM bellsoft/liberica-openjdk-alpine:17
ARG JAR_FILE=build/libs/*.jar
ARG ACTIVE=local-container
COPY ${JAR_FILE} app.jar
EXPOSE 5000
ENTRYPOINT ["java", "-Dfile.encoding=UTF-8", "-Dspring.profiles.active=${ACTIVE}", "-jar","/app.jar"]
