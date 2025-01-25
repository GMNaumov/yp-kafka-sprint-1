package com.ngm;

import java.util.logging.Logger;
import java.util.logging.Level;

import com.ngm.service.MainProcessor;

public class App {
    private static final Logger logger = Logger.getLogger(App.class.getName());

    public static void main(String[] args) {
        logger.log(Level.INFO, "Запуск работы эмулятора работы продьюсера и консьюмеров Kafka");
        MainProcessor mainProcessor = new MainProcessor();
        mainProcessor.start();
    }
}
