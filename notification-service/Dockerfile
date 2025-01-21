FROM maven:3.8.3-openjdk-17

  # Set the working directory inside the container
WORKDIR /shakti-service

COPY notification-service/target/notification-service.jar notification-service.jar

# Specify the command to run your application
CMD ["java", "-jar", "notification-service.jar"]

