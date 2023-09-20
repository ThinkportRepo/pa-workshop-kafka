package org.example;

import digital.thinkport.Match;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;

import java.util.List;
import java.util.Map;
import java.util.Properties;

public class MatchExample {

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "my_streams_application");
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.Integer().getClass());
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        properties.put(StreamsConfig.PROCESSING_GUARANTEE_CONFIG, StreamsConfig.EXACTLY_ONCE_V2);
        StreamsBuilder builder = new StreamsBuilder();

        SpecificAvroSerde<Match> matchSerde = new SpecificAvroSerde<>();
        matchSerde.configure(Map.of("schema.registry.url", "http://localhost:8081"), false);


        KTable<String, Integer> table = builder.stream("match", Consumed.with(Serdes.String(), matchSerde))
                .flatMap(((key, value) -> mapToTableResults(value)))
                .groupByKey(Grouped.with(Serdes.String(), Serdes.Integer()))
                .aggregate(() -> 0, ((key, value, aggregate) -> aggregate + value), Materialized.with(Serdes.String(), Serdes.Integer()));


        builder.stream("match", Consumed.with(Serdes.String(), matchSerde))
                .filter((key, value) -> value.getFirstTeam().equalsIgnoreCase("Real Madrid")
                        || value.getSecondTeam().equalsIgnoreCase("Real Madrid"))
                // Real Madrid 2 - 0 Bayern München
                .map((k, v) -> KeyValue.pair("Real Madrid", String.format("%s %s - %s %s", v.getFirstTeam(), v.getScoreFirstTeam(), v.getScoreSecondTeam(), v.getSecondTeam())))
                // Real Madrid 2 - 0 Bayern München, Real Points: 15
                .join(table, ((msg, tablePlacement) -> String.format("%s, Real Points: %d", msg, tablePlacement)),Joined.with(Serdes.String(), Serdes.String(), Serdes.Integer()))
                .to("real_games", Produced.with(Serdes.String(), Serdes.String()));


        KafkaStreams kafkaStreams = new KafkaStreams(builder.build(), properties);
        kafkaStreams.start();


    }

    private static List<KeyValue<String, Integer>> mapToTableResults(Match m) {
        if (Integer.parseInt(m.getScoreFirstTeam()) > Integer.parseInt(m.getScoreSecondTeam())) {
            return List.of(KeyValue.pair(m.getFirstTeam(), 3));
        } else if (Integer.parseInt(m.getScoreFirstTeam()) < Integer.parseInt(m.getScoreSecondTeam())) {
            return List.of(KeyValue.pair(m.getSecondTeam(), 3));
        } else {
            return List.of(
                    KeyValue.pair(m.getFirstTeam(), 1),
                    KeyValue.pair(m.getSecondTeam(), 1)
            );
        }
    }
}
