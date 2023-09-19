# Kafka Client Exercises

## Setup a basic Kafka Producer
1. Create a new maven Project
2. Add the necessary dependencies, also a logging library
3. Create the necessary Properties Object. bootstrap.url, key.serializer and value.deserializer are sufficient at the moment
4. Create a producer that uses Integer as Key and String as value
5. Produce 5 messages to the topic my-first-topic

## Kafka Consumer
1. Read the javadoc of [KafkaConsumer](http://kafka.apache.org/20/javadoc/org/apache/kafka/clients/consumer/KafkaConsumer.html) to know how to use the Consumer API (to consume messages from Kafka)
2. Create a new Project
3. Define dependency for Kafka Clients API library
    1. Use [mvnrepository](https://mvnrepository.com/artifact/org.apache.kafka/kafka-clients/2.0.0) to know the proper entry for `kafka-clients` dependency
4. Write a Kafka consumer
    1. Name of the object: **KafkaConsumerApp**
    2. Start with an empty `Properties` object and fill out the missing properties per exceptions at runtime
    3. Use [ConsumerConfig](https://kafka.apache.org/20/javadoc/org/apache/kafka/clients/consumer/ConsumerConfig.html) constants (not string values for properties)
    4. Don't forget to `close` the consumer (so the messages are actually acknowledged to the broker)
5. Send message with your producer application to a topic of your choice and consume it with your consumer application

## Kafka Consumer Group
Objective:
Create two Java applications (consumers) that read from the same Kafka topic. These consumers should be part of the same consumer group, meaning that they will share the load of consuming messages from the topic.
1. Create Consumer Application 1:
    1. Set up a Kafka consumer using the Java Kafka Client API.
    2. The consumer should be part of a consumer group called "group1."
    3. Subscribe to the Kafka topic, e.g., "test-topic."
    4. Print the messages consumed to the console.
2. Create Consumer Application 2:
    1. Similar to application 1, set up another Kafka consumer.
    2. This consumer should also be part of the consumer group "group1."
    3. Subscribe to the same Kafka topic, "test-topic."
    4. Print the messages consumed to the console.
3. Verify the Behavior:
    1. Produce some messages to the "test-topic."
    2. Observe how the two consumers within the same group share the load of consuming messages.

Notes:
The Kafka consumers must be properly configured to work in a group.


## Produce with callback
1. Review the available `send` methods in [KafkaProducer](https://kafka.apache.org/20/javadoc/org/apache/kafka/clients/producer/KafkaProducer.html)
2. Read the javadoc of [KafkaProducer.send​(ProducerRecord<K,V> record, Callback callback)](https://kafka.apache.org/20/javadoc/org/apache/kafka/clients/producer/KafkaProducer.html#send-org.apache.kafka.clients.producer.ProducerRecord-org.apache.kafka.clients.producer.Callback-)
    1. Explore [Callback](https://kafka.apache.org/20/javadoc/org/apache/kafka/clients/producer/Callback.html) interface
3. Check which parameters are available in the callback.

## Custom Partitioner
1. Read about the [Partitioner](https://kafka.apache.org/20/javadoc/org/apache/kafka/clients/producer/Partitioner.html) interface
2. Write a custom partitioner that sends an event to partition if the key starts with "zero", to partition one if the key starts with "one" and to partition 2 for any other key
3. Write a KafkaProducer
    1. Register the custom `Partitioner` using [ProducerConfig.PARTITIONER_CLASS_CONFIG](https://kafka.apache.org/20/javadoc/org/apache/kafka/clients/producer/ProducerConfig.html#PARTITIONER_CLASS_CONFIG) property
3. Create a topic with 3 partitions
4. Test your implementation
