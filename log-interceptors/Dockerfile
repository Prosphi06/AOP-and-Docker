#FROM openjdk:11
#COPY ${JAR_FILE} log-interceptors-0.0.1-SNAPSHOT.jar
#COPY ${JAR_FILE} app.jar
#FROM adoptopenjdk/openjdk11:alpine-jre

#FROM openjdk:8-jdk-alpine
#EXPOSE 8787
#ARG JAR_FILE=target/log-interceptors-0.0.1-SNAPSHOT.jar
#WORKDIR /opt/app
#ENTRYPOINT ["java", "-jar", "app.jar"]

FROM openjdk:8
#VOLUME /tmp
#ADD target/log-interceptors-0.0.1-SNAPSHOT.jar app.jar
#ENTRYPOINT ["java", "-jar", "/app.jar"]
copy ./target/log-interceptors-0.0.1-SNAPSHOT.jar log-interceptors-0.0.1-SNAPSHOT.jar
CMD ["java","-jar","log-interceptors-0.0.1-SNAPSHOT.jar"]


