package com.sample.redisandkafka.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;


@EnableCaching
@Configuration
@ConditionalOnProperty(name = "spring.cache.enabled", havingValue = "true")
public class RedisCacheConfig extends CachingConfigurerSupport {

  @Autowired
  private CacheConfigurationProperties cacheConfigurationProperties = null;

  private RedisCacheConfiguration createCacheConfiguration(long timeoutInHours) {
    return RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(timeoutInHours))
     .serializeValuesWith(RedisSerializationContext.SerializationPair
     .fromSerializer(new GenericJackson2JsonRedisSerializer()));
  }

  @Bean
  public CacheManager cacheManager(LettuceConnectionFactory redisConnectionFactory) {
    Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();

    if (Objects.nonNull(cacheConfigurationProperties.getCachesTTL())) {
      for (Entry<String, Long> cacheNameAndTimeout : cacheConfigurationProperties.getCachesTTL()
          .entrySet()) {
        cacheConfigurations.put(cacheNameAndTimeout.getKey(),
            createCacheConfiguration(cacheNameAndTimeout.getValue()));
      }
    }

    return RedisCacheManager.builder(redisConnectionFactory)
        .cacheDefaults(createCacheConfiguration(cacheConfigurationProperties.getDefaultTTL()))
        .withInitialCacheConfigurations(cacheConfigurations).build();
  }

  @Bean
  public LettuceConnectionFactory redisConnectionFactory() {
    RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(
        cacheConfigurationProperties.getHost(), cacheConfigurationProperties.getPort());

    return new LettuceConnectionFactory(redisStandaloneConfiguration);
  }

  @Configuration
  @ConfigurationProperties(prefix = "spring.cache")
  @Data
  class CacheConfigurationProperties {
    private String host;
    private int port;
    private Long defaultTTL;
    private Map<String, Long> cachesTTL;
  }
}
