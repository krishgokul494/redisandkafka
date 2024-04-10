package com.sample.redisandkafka.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.schema.registry.client.ConfluentSchemaRegistryClient;
import org.springframework.cloud.schema.registry.client.SchemaRegistryClient;
import org.springframework.cloud.stream.binder.kafka.BinderHeaderMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.KafkaHeaderMapper;

/**
 * Created by HurleyD on 09/08/2018.
 */
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

  /**
   * "This [BinderHeaderMapper] is provided for addressing some interoperability issues between
   * Spring Cloud Stream 3.0.x and 2.x apps" We are currently using an older version of Spring Kafka
   * on the consumer (in OAC).
   */
  @Bean("kafkaBinderHeaderMapper")
  public KafkaHeaderMapper kafkaBinderHeaderMapper() {
    BinderHeaderMapper mapper = new BinderHeaderMapper();
    mapper.setEncodeStrings(true);
    return mapper;
  }
}
