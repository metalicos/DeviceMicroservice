FROM openjdk:17-oracle
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} cd-device-microservice.jar
EXPOSE 5555
ENTRYPOINT ["java","-jar","/cd-device-microservice.jar"]