FROM openjdk:17-jdk-slim

RUN apt-get update
RUN apt install maven -y

WORKDIR /app
COPY . /app/

RUN mvn clean install -DskipTests
RUN mvn clean compile assembly:single

ENTRYPOINT ["java", "-jar", "target/yp-kafka-sprint-1-project-1.0-SNAPSHOT-jar-with-dependencies.jar"]