package com.ngm.producer;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;
import java.util.logging.Level;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.ngm.service.dataprocessing.DataSerializer;

/**
 * Класс, реализующий отправку сообщений в топик Kafka
 * 
 * @author gmnaumov
 */
public class MessageSender {
    private static final Logger logger = Logger.getLogger(MessageSender.class.getName());

    private static final String KAFKA_DEFAULT_TOPIC_NAME = "practicum-kafka-topic-1";

    private DataSerializer dataSerializer = new DataSerializer();

    /**
     * Отправка сообщения в топик с указанием его имени в явном виде
     * 
     */
    public <T extends Serializable> void sendMessageToSpecificTopic(KafkaProducer<byte[], byte[]> producer,
            String topicName, T data) throws IOException {
        byte[] key = data.toString().getBytes(StandardCharsets.UTF_8);
        byte[] value = dataSerializer.getSerializedDataToByteArrayObject(data);
        ProducerRecord<byte[], byte[]> producerRecord = new ProducerRecord<>(topicName, key, value);

        producer.send(producerRecord, (metadata, exception) -> {
            if (exception != null) {
                logger.log(Level.SEVERE, "Ошибка отправки сообщения", exception);
            } else {
                logger.log(Level.INFO, "Producer отправил сообщение: \"{0}\"", data);
            }
        });
    }

    /**
     * Отправка сообщения в топик "по умолчанию", без указания названия
     * 
     */
    public <T extends Serializable> void sendMessageToDefaultTopic(KafkaProducer<byte[], byte[]> producer, T data)
            throws IOException {
        sendMessageToSpecificTopic(producer, KAFKA_DEFAULT_TOPIC_NAME, data);
    }
}
