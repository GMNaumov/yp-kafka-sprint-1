package com.ngm.consumerpull;

import java.util.Properties;

import org.apache.kafka.clients.consumer.KafkaConsumer;

/**
 * Эмуляция работы консьюмера Kafka, 
 * использующего PUSH-модель для обработки сообщений
 * 
 * @author gmnaumov
 */
public class ConsumerPullLauncher {
    public void launch() {
        ConsumerPullConfiguration consumerPushConfiguration = new ConsumerPullConfiguration();
        Properties kafkaConsumerPullProperties = consumerPushConfiguration.getConsumerConfiguration();

        MessageReceiver messageReceiver = new MessageReceiver();

        try (KafkaConsumer<byte[], byte[]> consumer = new KafkaConsumer<>(kafkaConsumerPullProperties)) {
            messageReceiver.recieveMessagesFromDefaultTopic(consumer);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
