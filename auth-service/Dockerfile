FROM maven:3.8.3-openjdk-17

  # Set the working directory inside the container
WORKDIR /shakti-service

COPY auth-service/target/auth-service.jar auth-service.jar

  # Specify the command to run your application
CMD ["java", "-jar", "auth-service.jar"]
