package com.ngm.consumerpull;

import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;

import com.ngm.configuration.KafkaConsumerConfigurationProvider;

/**
 * Класс для подготовки конфигурации консьюмера Kafka,
 * работающего по модели PULL
 * 
 * @author gmnaumov
 */
public class ConsumerPullConfiguration implements KafkaConsumerConfigurationProvider {
    private static final String KAFKA_SERVER_CONFIG = "localhost:9094";
    private static final String KAFKA_CONSUMER_CONFIG = "pull-group";
    private static final int FETCH_BYTES_MIN = 10 * 1024 * 1024;

    public Properties getConsumerConfiguration() {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_SERVER_CONFIG);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, KAFKA_CONSUMER_CONFIG);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class.getName());

        // Настройки консьюмера для ручного управления коммитами
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        properties.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, FETCH_BYTES_MIN);

        return properties;
    }
}
