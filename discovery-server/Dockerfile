FROM maven:3.8.3-openjdk-17

  # Set the working directory inside the container
WORKDIR /shakti-service

COPY discovery-server/target/discovery-server.jar discovery-server.jar

EXPOSE 8761

# Specify the command to run your application
CMD ["java", "-jar", "discovery-server.jar"]

