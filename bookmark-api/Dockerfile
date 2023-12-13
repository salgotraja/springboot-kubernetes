FROM openjdk:21-jdk
ARG JAR_FILE=target/*.jar

ADD ${JAR_FILE} app.jar
RUN sh -c 'touch /app.jar'
EXPOSE 8080
ENTRYPOINT ["sh","-c", "java","-jar","/app.jar"]
