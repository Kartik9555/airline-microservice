package com.learning.location.service.config;

import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import tools.jackson.databind.DefaultTyping;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import tools.jackson.databind.jsontype.PolymorphicTypeValidator;

import java.time.Duration;
import java.util.Map;

@Configuration
public class RedisConfig implements CachingConfigurer {

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        final PolymorphicTypeValidator typeValidator = BasicPolymorphicTypeValidator.builder()
                .allowIfSubType(Object.class)
                .build();

        final JsonMapper jsonMapper = JsonMapper.builder()
                .activateDefaultTypingAsProperty(typeValidator, DefaultTyping.NON_FINAL, "@class")
                .build();

        final GenericJacksonJsonRedisSerializer serializer = new GenericJacksonJsonRedisSerializer(jsonMapper);

        final RedisCacheConfiguration defaults = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer))
                .disableCachingNullValues();

        final Map<String, RedisCacheConfiguration> cacheConfigs = Map.of(
                "airports", defaults.entryTtl(Duration.ofHours(6)),
                "airportsByCity", defaults.entryTtl(Duration.ofHours(6)),
                "allAirports", defaults.entryTtl(Duration.ofHours(6)),
                "cities", defaults.entryTtl(Duration.ofHours(6)),
                "citiesByCode", defaults.entryTtl(Duration.ofHours(6))
        );
        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(defaults.entryTtl(Duration.ofHours(6)))
                .withInitialCacheConfigurations(cacheConfigs)
                .build();
    }
}
