FROM openjdk:11
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} ef-service.jar

ENTRYPOINT ["java","-jar","/ef-service.jar"]