package com.ngm.consumerpush;

import java.util.Properties;
import java.util.logging.Logger;
import java.util.logging.Level;

import org.apache.kafka.clients.consumer.KafkaConsumer;

import com.ngm.configuration.KafkaConsumerConfigurationProvider;

/**
 * Эмуляция работы консьюмера Kafka, 
 * использующего PUSH-модель для обработки сообщений
 * 
 * @author gmnaumov
 */
public class ConsumerPushLauncher {
    private static final Logger logger = Logger.getLogger(ConsumerPushLauncher.class.getName());

    public void launch() {
        KafkaConsumerConfigurationProvider consumerPushConfiguration = new ConsumerPushConfiguration();
        Properties kafkaConsumerPushProperties = consumerPushConfiguration.getConsumerConfiguration();

        MessageReceiver messageReceiver = new MessageReceiver();

        try (KafkaConsumer<byte[], byte[]> consumer = new KafkaConsumer<>(kafkaConsumerPushProperties)) {
            messageReceiver.recieveMessagesFromDefaultTopic(consumer);
        } catch (Exception exception) {
            logger.log(Level.SEVERE, "Ошибка чтения сообщений в PUSH-Consumer", exception);
            exception.printStackTrace();
        }
    }
}
