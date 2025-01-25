package com.ngm.configuration;

import java.util.Properties;

public interface KafkaConsumerConfigurationProvider {
    Properties getConsumerConfiguration();
}
