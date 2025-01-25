package com.ngm.consumerpull;

import java.time.Duration;
import java.util.Collections;
import java.util.logging.Logger;
import java.util.logging.Level;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import com.ngm.model.Product;
import com.ngm.service.dataprocessing.DataDeserializer;

/**
 * Класс для чтения сообщений из Kafka с использованием ручного управления коммитами
 * 
 * @author gmnaumov
 */
public class MessageReceiver {
    private static final Logger logger = Logger.getLogger(MessageReceiver.class.getName());

    private static final String KAFKA_DEFAULT_TOPIC_NAME = "practicum-kafka-topic-1";

    private DataDeserializer dataDeserializer = new DataDeserializer();

    public void recieveMessagesFromSpecificTopic(KafkaConsumer<byte[], byte[]> consumer, String topicName) {
        consumer.subscribe(Collections.singletonList(topicName));

        while (true) {
            ConsumerRecords<byte[], byte[]> records = consumer.poll(Duration.ofMillis(100));
            records.forEach(record -> {
                try {
                    Product product = dataDeserializer.getDeserializedDataFromByteArray(record.value());
                    logger.log(Level.INFO, "PULL-Consumer получил сообщение: \"{0}\"", product);

                    // Ручное смещение коммита
                    commitOffset(consumer, record.topic(), record.partition(), record.offset());
                } catch (Exception exception) {
                    logger.log(Level.SEVERE, "В процессе получения сообщения в PULL-Consumer возникло исключение", exception);
                }
            });
        }
    }

    public void recieveMessagesFromDefaultTopic(KafkaConsumer<byte[], byte[]> consumer) {
        recieveMessagesFromSpecificTopic(consumer, KAFKA_DEFAULT_TOPIC_NAME);
    }

    /**
     * Логика для ручного смещения коммита
     * 
     */
    private static void commitOffset(KafkaConsumer<byte[], byte[]> consumer, String topic, int partition, long offset) {
        TopicPartition topicPartition = new TopicPartition(topic, partition);
        OffsetAndMetadata offsetAndMetadata = new OffsetAndMetadata(offset + 1);

        consumer.commitSync(
                Collections.singletonMap(topicPartition, offsetAndMetadata));
    }
}
