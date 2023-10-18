FROM openjdk:11

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} serviceregistry.jar

ENTRYPOINT ["jave", "jar", "/serviceregistry.jar"]

EXPOSE 8050