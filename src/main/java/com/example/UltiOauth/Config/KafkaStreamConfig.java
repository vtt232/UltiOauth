package com.example.UltiOauth.Config;

import com.example.UltiOauth.DTO.SystemStatisticsDTO;
import com.example.UltiOauth.DTO.WebSocketAnnouncementDTO;
import com.example.UltiOauth.Serializer.SystemStatisticsSerializer;
import com.example.UltiOauth.Service.SystemStatisticsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerde;

import javax.persistence.Entity;
import java.util.Properties;

@Configuration
@Slf4j
public class KafkaStreamConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;


    private SystemStatisticsService systemStatisticsService;

    public static final String SYSTEM_STATISTICS = "system-statistics";
    public static final String EVENT_OBSERVER = "event-observer";

    public KafkaStreamConfig(SystemStatisticsService systemStatisticsService) {
        this.systemStatisticsService = systemStatisticsService;
    }

    @Bean
    public Properties kafkaStreamsProps() {
        Properties properties = new Properties();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "rest-backend");
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
        properties.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, "0");
        properties.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return properties;
    }


    @Bean
    public Topology buildTopology() {

        StreamsBuilder streamsBuilder = new StreamsBuilder();
        Serde<WebSocketAnnouncementDTO> webSocketAnnouncementSerde = new JsonSerde<>(WebSocketAnnouncementDTO.class);


        KStream<String, String> systemStatisticsStream = streamsBuilder.stream(EVENT_OBSERVER, Consumed.with(Serdes.String(), webSocketAnnouncementSerde)).map((key, value) -> {
            SystemStatisticsDTO systemStatisticsDTO = this.systemStatisticsService.updateAndGet();
            String message = "{\"userCount\":"+ systemStatisticsDTO.getUserCount() +",\"repoCount\":"+systemStatisticsDTO.getRepoCount()+",\"noteCount\":"+systemStatisticsDTO.getNoteCount()+",\"lastUpdate\":\""+systemStatisticsDTO.getLastUpdate().toString()+"\"}";
            return KeyValue.pair(key, message);
        });
        systemStatisticsStream.to(SYSTEM_STATISTICS, Produced.with(Serdes.String(), Serdes.String()));

        return streamsBuilder.build();

    }

    @Bean
    public KafkaStreams kafkaStreams(@Qualifier("kafkaStreamsProps") Properties properties) {
        Topology topology = buildTopology();
        KafkaStreams kafkaStreams = new KafkaStreams(topology, properties);

        kafkaStreams.start();

        Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));

        return kafkaStreams;

    }

    @Bean
    public JsonSerde<SystemStatisticsDTO> entityJsonSerde(
            ObjectMapper objectMapper,
            KafkaProperties kafkaProperties) {

        JsonSerde<SystemStatisticsDTO> serde = new JsonSerde<>(SystemStatisticsDTO.class, objectMapper);
        serde.deserializer().configure(kafkaProperties.buildConsumerProperties(), false);
        serde.serializer().configure(kafkaProperties.buildProducerProperties(), false);
        return serde;
    }
}
