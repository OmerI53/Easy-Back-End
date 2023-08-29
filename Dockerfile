FROM openjdk:17-alpine
VOLUME /tnp
WORKDIR /app
COPY target/Easy-0.0.1-SNAPSHOT.jar /app/Easy-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "Easy-0.0.1-SNAPSHOT.jar"]
