package com.message.broker.messagebrokerchallenge.command;

import java.io.FileReader;
import java.util.Properties;

import com.google.protobuf.util.JsonFormat;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;



@ShellComponent
public class MessageBrokerCommands {

  @ShellMethod(key = "message")
  public String sendMessage(
      @ShellOption(defaultValue = "file") String arg
  ) {
    String kafkaTopic = "person-topic";

    PersonOuterClass.Person.Builder personBuilder = PersonOuterClass.Person.newBuilder();

    JSONParser parser = new JSONParser();

    try {
      Object obj = parser.parse(new FileReader(arg));

      JSONObject jsonObject =  (JSONObject) obj;
      JsonFormat.parser().merge(jsonObject.toString(), personBuilder);
    } catch (Exception e) {
      e.printStackTrace();
    }
    PersonOuterClass.Person person = personBuilder.build();

    Properties props = new Properties();
    props.put("bootstrap.servers", "localhost:9092");
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    props.put("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");

    try (KafkaProducer<String, byte[]> producer = new KafkaProducer<>(props)) {
      producer.send(new ProducerRecord<>(kafkaTopic, person.toByteArray()));
      System.out.println("Message sent to Kafka topic: " + kafkaTopic);
    }
    return person.toString();
  }
}