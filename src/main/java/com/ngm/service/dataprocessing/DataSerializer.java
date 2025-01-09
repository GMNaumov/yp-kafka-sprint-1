package com.ngm.service.dataprocessing;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Класс для сериализации данных перед отправкой сообщений в Kafka
 * 
 * @author gmnaumov
 */
public class DataSerializer {
    private static final Logger logger = Logger.getLogger(DataSerializer.class.getName());

    /**
     * Сериализация данных в массив байтов
     * 
     * @param <T>  параметр типа данных, используемых для сериализации объектов
     * @param data объект, данные которого будут сериализованы
     * @return массив байтов, который будет отправлен в сообщении
     */
    public <T extends Serializable> byte[] getSerializedDataToByteArrayObject(T data) throws IOException {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(data);

            return byteArrayOutputStream.toByteArray();
        } catch (Exception exception) {
            logger.log(Level.SEVERE, "Ошибка сериализации объекта", exception);
            throw new IOException("В процессе сериализации произошла ошибка", exception);
        }
    }
}
