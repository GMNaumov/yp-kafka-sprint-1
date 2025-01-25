package com.ngm.producer;

import java.io.IOException;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.logging.Level;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.errors.RetriableException;

import com.ngm.model.Product;

public class ProducerLauncher {
    private static final Logger logger = Logger.getLogger(ProducerLauncher.class.getName());

    private static final int DELAY_BETWEEN_MESSAGES_MS = 1_000;

    private static Random random = new Random();

    public void launch() {
        ProducerConfiguration producerConfiguration = new ProducerConfiguration();
        Properties kafkaProducerProperties = producerConfiguration.getProducerConfiguration();

        MessageSender messageSender = new MessageSender();

        // Генерируем каждые 5 секунд новое сообщение и отправляем его в Kafka
        try (KafkaProducer<byte[], byte[]> producer = new KafkaProducer<>(kafkaProducerProperties)) {
            logger.log(Level.INFO, "Конфигурация продьюсера успешно загружена, запуск отправки сообщений");

            while (true) {
                Product product = generateRandomProduct();

                messageSender.sendMessageToDefaultTopic(producer, product);

                Thread.sleep(DELAY_BETWEEN_MESSAGES_MS);
            }
        } catch (RetriableException retriableException) {
            logger.log(Level.SEVERE, "Возникла ошибка при отправке сообщения", retriableException);
        } catch (IOException ioException) {
            logger.log(Level.SEVERE, "Возникла ошибка в процессе подготовки сообщения", ioException);
        } catch (Exception exception) {
            logger.log(Level.SEVERE, "Возникла ошибка в работе приложения", exception);
        }
    }

    /**
     * Генерация случайного объекта для сообщения
     * 
     * @return новый объект типа Product со случайными значениями свойств
     */
    private Product generateRandomProduct() {
        UUID uuid = UUID.randomUUID();
        String designation = generateRandomString();
        String name = generateRandomString();
        double weight = random.nextDouble() * 100;

        return new Product(uuid, designation, name, weight);
    }

    private String generateRandomString() {
        int leftLimit = 97;
        int rightLimit = 122;
        int targetStringLength = 10;
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }
}
