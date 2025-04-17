package ch.dboeckli.guru.jpa.creditcard.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class EncryptionUtilTest {

    private static final String TEST_ENCRYPTED_VALUE = "encryptedValue";

    @Test
    void encryptDecrypt() {
        String encryptedValue = EncryptionUtil.encrypt(TEST_ENCRYPTED_VALUE);
        assertEquals(TEST_ENCRYPTED_VALUE, EncryptionUtil.decrypt(encryptedValue));
    }

    @Test
    void encryptNull() {
        assertNull(EncryptionUtil.encrypt(null));
    }

    @Test
    void encryptEmpty() {
        assertEquals("", EncryptionUtil.encrypt(""));
    }

    @Test
    void decryptNull() {
        assertNull(EncryptionUtil.decrypt(null));
    }

    @Test
    void decryptEmpty() {
        assertEquals("", EncryptionUtil.decrypt(""));
    }

    @Test
    void decryptDecrypedString() {
        assertEquals("abcdefg", EncryptionUtil.decrypt("abcdefg"));
    }

    @Test
    void encryptBase64String() {
        assertEquals("MTIzNDU2Nzg5MDAwMDA=", EncryptionUtil.encrypt("MTIzNDU2Nzg5MDAwMDA="));
    }


}