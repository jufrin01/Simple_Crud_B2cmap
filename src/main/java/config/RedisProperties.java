package idb2camp.b2campjufrin.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("spring.redis")
public class RedisProperties {

    private int shutdownTimeoutInSecond;
    private int commandTimeoutInSecond;
    private String host;
    private int port;
    private String password;
    private boolean publishOnScheduler;

    @Value("${spring.redis.lettuce.pool.max-active:8}")
    private int maxActive;

    @Value("${spring.redis.lettuce.pool.max-idle:8}")
    private int maxIdle;

    @Value("${spring.redis.lettuce.pool.max-wait:4}")
    private long maxWait;

    @Value("${spring.redis.lettuce.pool.min-idle:0}")
    private int minIdle;
}
