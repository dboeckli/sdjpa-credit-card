package ch.dboeckli.guru.jpa.creditcard.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EncryptionUtilTest {

    private static final String TEST_ENCRYPTED_VALUE = "encryptedValue";

    @Test
    void encryptDecrypt() {
        String encrypted_value = EncryptionUtil.encrypt(TEST_ENCRYPTED_VALUE);
        assertEquals(TEST_ENCRYPTED_VALUE, EncryptionUtil.decrypt(encrypted_value));
    }


}