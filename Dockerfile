FROM openjdk:11-jdk
WORKDIR /working_dir
ARG VERSION=0.0.1-SNAPSHOT
ARG SERVICE_NAME=UltiOauth
COPY build/libs/${SERVICE_NAME}-${VERSION}.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
