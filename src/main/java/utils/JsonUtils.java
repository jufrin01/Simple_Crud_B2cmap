package idb2camp.b2campjufrin.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonUtils {

    private static ObjectMapper mapper = new ObjectMapper();

    @SneakyThrows
    public static String objectToStringJson(Object obj) {
        try {
            if (obj instanceof String) {
                return (String) obj;
            }
            return mapper.writeValueAsString(obj);
        } catch (IOException e) {
            log.error("Error on objectToStringJson: {} -> {}", e, obj);
            throw new IOException(e);
        }
    }

    @SneakyThrows
    public static <T> T stringJsonToObject(String jsonInString, Class<T> clazz) {
        try {
            return mapper.readValue(jsonInString, clazz);
        } catch (IOException e) {
            log.error("Error on stringJsonToObject: {} -> {}", e, jsonInString);
            throw new IOException(e);
        }
    }

    @SneakyThrows
    public static <T, U> T convertValue(U obj1, Class<T> obj2) {
        try {
            return mapper.convertValue(obj1, obj2);
        } catch (IllegalArgumentException e) {
            log.error("error on JsonUtils convertValue: ", e);
            throw new IllegalArgumentException(e);
        }
    }
}
