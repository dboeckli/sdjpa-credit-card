package ch.dboeckli.guru.jpa.creditcard.util;

import lombok.experimental.UtilityClass;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.regex.Pattern;

@UtilityClass
public class EncryptionUtil {

    private static final Pattern BASE64_PATTERN = Pattern.compile("^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)?$");

    public static String encrypt(String freeText) {
        if (freeText == null || freeText.isEmpty()) {
            return freeText;
        }
        if (isBase64Encoded(freeText)) {
            return freeText; // Already encoded, return as is
        }
        return Base64.getEncoder().encodeToString(freeText.getBytes(StandardCharsets.UTF_8));
    }

    public static String decrypt(String encryptedText) {
        if (encryptedText == null || encryptedText.isEmpty()) {
            return encryptedText;
        }
        if (!isBase64Encoded(encryptedText)) {
            return encryptedText; // Not encoded, return as is
        }
        return new String(Base64.getDecoder().decode(encryptedText));
    }

    public boolean isBase64Encoded(String text) {
        return BASE64_PATTERN.matcher(text).matches();
    }
}
