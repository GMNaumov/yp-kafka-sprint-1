# Итоговый проект Модуля №1 курса "Apache Kafka для разработки и архитектуры"

### Шаг 1. Запуск кластера Kafka

docker-compose поднимет кластер Kafka согласно заданию:

```shell
sudo docker compose up -d
```
Вместе с кластером поднимется UI, доступный по адресу: [localhost:8080](localhost:8080)

### Шаг 2. Создание топика

Согласно заданию, в файле topic.txt - команда для создания топика и вывод информации о нём. 

Поскольку топик используется основным приложением, его необходимо создать на работающем кластере:

```shell
# Подключиться к одному из контейнеров в кластере
sudo docker exec -it yp-kafka-sprint-1-cluster-kafka-1-1 bash
```

```shell
# Выполнить команду для создания топика
/opt/bitnami/kafka/bin/kafka-topics.sh \
    --create \
    --bootstrap-server localhost:9092 \
    --topic practicum-kafka-topic-1 \
    --partitions 3 \
    --replication-factor 2
```

### Шаг 3. Сборка контейнера с приложением и запуск

Для запуска приложения собираем контейнер:

```shell
sudo docker build -t yp-kafka-sprint-1-project .
```

И запускаем его:

```shell
sudo docker run --rm -it --network=host yp-kafka-sprint-1-project
```

### Немного информации о приложении

Приложение - на чистой Java, без каких-либо спрингбутов. 
ExecutorService в три потока заводит одного продюсера и двух консьюмеров, которые начинают писать/читать сообщения в созданный топик.
Информация о записанных/прочитанных сообщениях выводится в консоль. Дополнительная информация - в JavaDoc-ах исходников.
Конфигурации вынесены в отдельные классы соответствующих пакетов: 
* producer
* consumerpull
* consumerpush

