FROM maven:3.8.3-openjdk-17

  # Set the working directory inside the container
WORKDIR /shakti-service

COPY app-service/target/app-service.jar app-service.jar

  # Specify the command to run your application
CMD ["java", "-jar", "app-service.jar"]
