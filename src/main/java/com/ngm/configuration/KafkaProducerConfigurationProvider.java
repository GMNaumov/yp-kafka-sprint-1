package com.ngm.configuration;

import java.util.Properties;

public interface KafkaProducerConfigurationProvider {
    Properties getProducerConfiguration();
}
