package com.example.UltiOauth.Config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.Map;

@Configuration
public class InitKafkaTopic {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    public static final String ADMIN_TOPIC = "admin";

    public static final String SYSTEM_STATISTICS = "system-statistics";
    public static final String EVENT_OBSERVER = "event-observer";

    @Bean
    public KafkaAdmin admin() {
        return new KafkaAdmin(Map.of(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers));
    }

    @Bean
    public NewTopic adminTopic() {
        return TopicBuilder.name(ADMIN_TOPIC)
                .partitions(1)
                .replicas(1)
                .build();
    }


    @Bean
    public NewTopic systemStatisticsTopic() {
        return TopicBuilder.name(SYSTEM_STATISTICS)
                .partitions(1)
                .replicas(  1)
                .build();
    }


    @Bean
    public NewTopic eventObserver() {
        return TopicBuilder.name(EVENT_OBSERVER)
                .partitions(1)
                .replicas(1)
                .build();
    }

}
