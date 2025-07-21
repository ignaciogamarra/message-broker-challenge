# Message Broker Code Challenge

This project is a CLI tool for sending Protobuf messages to a Kafka topic.

## Prerequisites

- Java 21
- Docker and Docker Compose
- Kafka

## How to Build the Project

1. Clone the repository.
2. Run the following commands to build the project:
   ```bash
   ./gradlew build
   ./gradlew jar

The compiled JAR file will be located in the build/libs directory.

## Run Kafka

3. Run Kafka with Docker:

    ```bash
    docker-compose up -d
    ```
    
    This will start Kafka and Zookeeper in the background.

4. To create the Kafka topic, run the following command:

   ```bash
   docker exec -it <kafka-container-id> kafka-topics --create --topic person-topic --bootstrap-server localhost:9092
   ```

   Replace `<kafka_container_name>` with the name of your Kafka container.

## How to Run the Project

5. To run the project, execute:
   ```bash
   java -jar message-broker-challenge-0.0.1-SNAPSHOT.jar message <JSON formatted file>
   ```
    Replace `<JSON formatted file>` with the path to your JSON file containing the message data. Example:

    ```json
   {
      "name": "Luke Skywalker",
      "id": 1,
      "email": "luke@rebels.com"
   }  
   ```
