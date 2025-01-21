FROM maven:3.8.3-openjdk-17

  # Set the working directory inside the container
WORKDIR /shakti-service

COPY api-gateway/target/api-gateway-service.jar api-gateway-service.jar

EXPOSE 9099
  
  # Specify the command to run your application
CMD ["java", "-jar", "api-gateway-service.jar"]
