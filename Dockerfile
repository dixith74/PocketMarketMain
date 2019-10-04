# Start with a base image containing Java runtime
FROM openjdk:8-jdk-alpine

RUN apk --no-cache add curl

# Add Maintainer Info
LABEL maintainer="tatarao.oleti@gmail.com"

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8080 available to the world outside this container
#EXPOSE 8080
#ARG JAR_FILE= build/libs/pocket-market-main-0.0.1-SNAPSHOT.jar

# Add the application's jar to the container
COPY build/libs/pocket-market-main-0.0.1-SNAPSHOT.jar pm-main.jar

# Run the jar file 
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=prod","-jar","/pm-main.jar"]