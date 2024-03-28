FROM openjdk:17-alpine
ADD build/libs/transaction-bff-0.0.1-SNAPSHOT.jar /tmp/app.jar
RUN ls /tmp
ENTRYPOINT ["java", "-jar", "/tmp/app.jar"]
EXPOSE 8080