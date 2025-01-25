package com.ngm.producer;

import java.util.Properties;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.ByteArraySerializer;

import com.ngm.configuration.KafkaProducerConfigurationProvider;

/**
 * Класс для конфигурирования настроек продьюсера
 * 
 * @author gmnaumov
 */
public class ProducerConfiguration implements KafkaProducerConfigurationProvider {
    private static final String KAFKA_SERVER_CONFIG = "localhost:9094";
    private static final int MAX_RETRIES = 20;
    private static final int RETRIES_MAX_DELAY_MS = 3_000;
    private static final int BROKER_MAX_TIMEOUT_MS = 120_000;

    public Properties getProducerConfiguration() {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_SERVER_CONFIG);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class.getName());

        // Настройки для обеспечения уровня доставки сообщений "At Least Onse"
        properties.put(ProducerConfig.ACKS_CONFIG, "all");
        properties.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 1);
        properties.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);

        // Настройки поведения продьюсера при ошибке отправки сообщения
        properties.put(ProducerConfig.RETRIES_CONFIG, MAX_RETRIES);
        properties.put(ProducerConfig.RETRY_BACKOFF_MAX_MS_CONFIG, RETRIES_MAX_DELAY_MS);
        properties.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, BROKER_MAX_TIMEOUT_MS);

        return properties;
    }
}
