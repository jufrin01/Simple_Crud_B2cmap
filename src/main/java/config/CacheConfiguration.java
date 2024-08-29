package idb2camp.b2campjufrin.config;

import io.lettuce.core.ClientOptions;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;

@Configuration
public class CacheConfiguration {

    @Bean
    public GenericObjectPoolConfig poolConfig(RedisProperties redisProperties) {
        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        genericObjectPoolConfig.setMaxTotal(redisProperties.getMaxActive());
        genericObjectPoolConfig.setMaxIdle(redisProperties.getMaxIdle());
        genericObjectPoolConfig.setMinIdle(redisProperties.getMinIdle());
        genericObjectPoolConfig.setMaxWaitMillis(redisProperties.getMaxWait());

        return genericObjectPoolConfig;
    }

    @Bean
    public RedisStandaloneConfiguration redisConfig(RedisProperties redisProperties) {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(redisProperties.getHost());
        config.setPassword(RedisPassword.of(redisProperties.getPassword()));
        config.setPort(redisProperties.getPort());

        return config;
    }

    @Bean
    public LettuceConnectionFactory defaultLettuceConnectionFactory(RedisStandaloneConfiguration redisConfig,
                                                                    GenericObjectPoolConfig poolConfig, RedisProperties redisProperties) {
        final ClientOptions clientOptions = ClientOptions.builder()
                .publishOnScheduler(redisProperties.isPublishOnScheduler())
                .build();

        final LettuceClientConfiguration lettuceClientConfiguration = LettucePoolingClientConfiguration.builder()
                .commandTimeout(Duration.ofSeconds(redisProperties.getCommandTimeoutInSecond()))
                .shutdownTimeout(Duration.ofSeconds(redisProperties.getShutdownTimeoutInSecond()))
                .poolConfig(poolConfig)
                .clientOptions(clientOptions)
                .build();

        return new LettuceConnectionFactory(redisConfig, lettuceClientConfiguration);
    }
    @Bean
    public RedisTemplate<String, String> redisTemplate(
            LettuceConnectionFactory defaultLettuceConnectionFactory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();

        template.setKeySerializer(RedisSerializer.string());
        template.setHashKeySerializer(RedisSerializer.string());

        template.setValueSerializer(RedisSerializer.string());
        template.setHashValueSerializer(RedisSerializer.string());
        template.setConnectionFactory(defaultLettuceConnectionFactory);
        template.afterPropertiesSet();

        return template;
    }
}
