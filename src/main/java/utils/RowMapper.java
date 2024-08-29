package idb2camp.b2campjufrin.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RowMapper {

    private static final String WILDCARD = "%";

    public static String addWildcard(String input) {
        return StringUtils.isEmpty(input) ? WILDCARD : new StringBuilder(WILDCARD).append(input).append(WILDCARD).toString();
    }

    public static int toInt(Object duration) {
        return Optional.ofNullable(duration).map(Object::toString).map(Integer::valueOf).orElse(0);
    }

    public static Long toLong(Object duration) {
        return Optional.ofNullable(duration).map(Object::toString).map(Long::valueOf).orElse(0L);
    }

    public static String toStr(Object string) {
        return Optional.ofNullable(string).map(Object::toString).orElse(null);
    }

    public static LocalDate toLocalDate(Object date) {
        return Optional.ofNullable(date).map(Object::toString).map(LocalDate::parse).orElse(LocalDate.now());
    }

    public static Float toFloat(Object duration) {
        return Optional.ofNullable(duration).map(Object::toString).map(Float::valueOf).orElse(0f);
    }

    public static BigDecimal toBigDecimal(Object bigDecimal) {
        return Optional.ofNullable(bigDecimal).map(Object::toString).map(BigDecimal::new).orElse(BigDecimal.ZERO);
    }

    public static boolean toBool(Object oBoolean) {
        return Boolean.TRUE.equals(Optional.ofNullable(oBoolean).map(Object::toString).map(Boolean::valueOf).orElse(false));
    }

    public static LocalDateTime toLocalDateTime(Object object) {
        return Optional.ofNullable(object).map(Object::toString).map(s -> LocalDateTime.parse(s.split("\\.")[0], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).orElse(null);
    }
    public static <T extends Enum<T>> T toEnum(Class<T> enumType, Object value) {
        if (value == null) {
            return null;
        }
        String stringValue = value.toString();
        try {
            return Enum.valueOf(enumType, stringValue);
        } catch (IllegalArgumentException e) {
            log.warn("Failed to convert {} to enum {}. Returning null.", stringValue, enumType.getSimpleName());
            return null;
        }
    }
}