package com.ngm.service.dataprocessing;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Класс для десериализации данных, полученных из Kafka
 * 
 * @author gmnaumov
 */
public class DataDeserializer {
    private static final Logger logger = Logger.getLogger(DataDeserializer.class.getName());

    /**
     * Десериализация данных из байтового массива
     * 
     * @param <T>  параметр целевого типа данных, в который будет десереализовано
     *             сообщение
     * @param data полученные данные из Kafka
     * @return объек целевого типа
     */
    public <T extends Serializable> T getDeserializedDataFromByteArray(byte[] data)
            throws IOException {
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);) {

            return (T) objectInputStream.readObject();
        } catch (Exception exception) {
            logger.log(Level.SEVERE, "Ошибка десериализации сообщения", exception);
            throw new IOException("В процессе десериализации произошла ошибка", exception);
        }
    }
}
