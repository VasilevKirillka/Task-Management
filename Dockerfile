FROM khipu/openjdk17-alpine
WORKDIR /app
COPY target/Task-Management-0.0.1-SNAPSHOT.jar /app
EXPOSE 8080
ENTRYPOINT ["java","-jar","Task-Management-0.0.1-SNAPSHOT.jar"]