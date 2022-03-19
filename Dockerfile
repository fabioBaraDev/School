FROM maven:3.6.1-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package --no-transfer-progress

#
# Package stage
#
FROM adoptopenjdk/openjdk11:alpine-jre
COPY --from=build /home/app/target/School-0.0.1-SNAPSHOT.jar /usr/local/lib/app.jar
ENTRYPOINT [ "sh", "-c", "java -jar /usr/local/lib/app.jar" ]