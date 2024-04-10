package com.sample.redisandkafka.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.schema.registry.client.ConfluentSchemaRegistryClient;
import org.springframework.cloud.schema.registry.client.SchemaRegistryClient;
import org.springframework.cloud.stream.binder.kafka.BinderHeaderMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.KafkaHeaderMapper;

@Configuration
public class KafkaConfig {

  @Value("${spring.cloud.stream.schemaRegistryClient.endpoint}")
  private String endpoint;

  @Bean
  public SchemaRegistryClient confluentSchemaRegistryClient() {
    ConfluentSchemaRegistryClient client = new ConfluentSchemaRegistryClient();
    client.setEndpoint(endpoint);
    return client;
  }

  @Bean("kafkaBinderHeaderMapper")
  public KafkaHeaderMapper kafkaBinderHeaderMapper() {
    BinderHeaderMapper mapper = new BinderHeaderMapper();
    mapper.setEncodeStrings(true);
    return mapper;
  }
}
