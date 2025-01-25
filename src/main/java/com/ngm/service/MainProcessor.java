package com.ngm.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.ngm.consumerpull.ConsumerPullLauncher;
import com.ngm.consumerpush.ConsumerPushLauncher;
import com.ngm.producer.ProducerLauncher;

/**
 * Класс для запуска работы эмуляторов работы продьюсера и консьюмеров Kafka
 * 
 * @author gmnaumov
 */
public class MainProcessor {

    /**
     * Запуск работы продьюсера и консьюмеров.
     * Используем ExecutorService для запуска задач в отдельных потоках
     */
    public void start() {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        executor.submit(() -> {
            ProducerLauncher producerLauncher = new ProducerLauncher();
            producerLauncher.launch();
        });

        executor.submit(() -> {
            ConsumerPullLauncher consumerPullLauncher = new ConsumerPullLauncher();
            consumerPullLauncher.launch();
        });

        executor.submit(() -> {
            ConsumerPushLauncher consumerPushLauncher = new ConsumerPushLauncher();
            consumerPushLauncher.launch();
        });

        executor.shutdown();
    }
}
