package idb2camp.b2campjufrin.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.security.SecureRandom;
import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;

public class StringHelper {

    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");
    private static final Pattern EDGESDHASHES = Pattern.compile("(^-|-$)");
    private static final String WILDCARD = "%";

    public StringHelper() {
    }

    public static String toSlug(String input) {
        String noWhitespace = WHITESPACE.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(noWhitespace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        slug = EDGESDHASHES.matcher(slug).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH);
    }

    public static String generateAlphaNumericRandom() {
        char[] possibleCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[]|;:,<.>/?".toCharArray();
        return RandomStringUtils.random(15, 0, possibleCharacters.length - 1, false, false, possibleCharacters, new SecureRandom());
    }

    public static String wrapWithWildcard(String input) {
        return StringUtils.isEmpty(input) ? "%" : "%" + input + "%";
    }

    public static String optionalWildcard(String input) {
        return StringUtils.isEmpty(input) ? "%" : input;
    }
}
