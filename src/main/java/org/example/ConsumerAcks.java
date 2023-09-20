package org.example;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class ConsumerAcks {
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "my-first-consumer-group");
        properties.put(ConsumerConfig.GROUP_INSTANCE_ID_CONFIG, "consumer1");
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");

        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(properties);
        kafkaConsumer.subscribe(List.of("my-first-topic"));

        while (true) {
            ConsumerRecords<String, String> results = kafkaConsumer.poll(Duration.of(100, ChronoUnit.MILLIS));
            for (ConsumerRecord<String, String> result : results) {
                System.out.printf("Message received in topic %s and partition %s, key was %s, value was %s %n", result.topic(), result.partition(), result.key(), result.value());
                kafkaConsumer.commitSync(Map.of(new TopicPartition(result.topic(), result.partition()),new OffsetAndMetadata(result.offset()+1)));

            }
            kafkaConsumer.commitSync();
        }



    }
}
