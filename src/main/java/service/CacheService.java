package idb2camp.b2campjufrin.service;

import idb2camp.b2campjufrin.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@Slf4j
@Service
public class CacheService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public <T> T findCacheByKey(String key, Class<T> clazz) {
        try {
            String cachedValue = redisTemplate.opsForValue().get(key);
            log.debug("Cached value for key '{}': {}", key, cachedValue);

            if (cachedValue != null) {
                return JsonUtils.stringJsonToObject(cachedValue, clazz);
            } else {
                log.debug("Cache miss for key '{}'", key);
                return null;
            }
        } catch (Exception e) {
            log.error("findCacheByKey Key={} Error={}", key, e.getMessage());
            return null;
        }
    }

    public <T> Boolean createCache(String key, T value, long expirySeconds) {
        try {
            String jsonValue = JsonUtils.objectToStringJson(value);
            redisTemplate.opsForValue().set(key, jsonValue);
            redisTemplate.expire(key, Duration.ofSeconds(expirySeconds)); // Set the proper expiration duration
            log.debug("Storing value in cache for key '{}': {}", key, jsonValue);

            return true;
        } catch (Exception e) {
            log.error("createCache Key={} Error={}", key, e.getMessage());
            return false;
        }
    }
public <T> T findCacheByKeys(String key, Class<T> clazz) {
        try {
            return Optional.ofNullable(redisTemplate.opsForValue().get(key))
                    .map(s -> JsonUtils.stringJsonToObject(s, clazz))
                    .orElseGet(() -> null);
        } catch (Exception e) {
            log.error("findCacheByKey Key={} Error={}",  key, e.getMessage());
            return null;
        }
    }

    public String findCacheStringByKey(String key) {
        try {
            return Optional.ofNullable(redisTemplate.opsForValue().get(key))
                    .orElseGet(() -> null);
        } catch (Exception e) {
            log.error("findCacheStringByKey Key={} Error={}",  key, e.getMessage());
            return null;
        }
    }

    public <T> Boolean createStringCache(String key, String value, long expirySeconds) {
        try {
            redisTemplate.opsForValue().set(key, value);
            redisTemplate.expire(key, Duration.ZERO.withSeconds(expirySeconds));
            return true;
        } catch (Exception e) {
            log.error("createStringCache Key={} Error={}",  key, e.getMessage());
            return false;
        }
    }

    public <T> Boolean createCaches(String key, T value, long expirySeconds) {
        try {
            redisTemplate.opsForValue().set(key, JsonUtils.objectToStringJson(value));
            redisTemplate.expire(key, Duration.ZERO.withSeconds(expirySeconds));
            return true;
        } catch (Exception e) {
            log.error("createCache Key={} Error={}",  key, e.getMessage());
            return false;
        }
    }

    public Boolean deleteCache(String key) {
        try {
            return Optional.ofNullable(redisTemplate.delete(key)).orElseGet(() -> false);
        } catch (Exception e) {
            log.error("deleteCache Key={} Error={}",  key, e.getMessage());
            return false;
        }
    }
}
