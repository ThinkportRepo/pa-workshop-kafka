package org.example;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class ProducerTransactional {
    public static void main(String[] args) throws InterruptedException {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "producer1");

        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(properties);

        kafkaProducer.initTransactions();
        kafkaProducer.beginTransaction();

        kafkaProducer.send(new ProducerRecord<>("my-first-topic","Hello World roll me back"));
        kafkaProducer.send(new ProducerRecord<>("my-second-topic","Hello World roll me back"));
        kafkaProducer.send(new ProducerRecord<>("my-third-topic","Hello World roll me back"));

        Thread.sleep(1000);

        kafkaProducer.abortTransaction();
        kafkaProducer.flush();
    }
}