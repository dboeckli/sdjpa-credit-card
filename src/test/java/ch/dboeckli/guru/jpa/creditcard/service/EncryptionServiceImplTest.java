package ch.dboeckli.guru.jpa.creditcard.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EncryptionServiceImplTest {

    private static final String TEST_ENCRYPTED_VALUE = "encryptedValue";

    EncryptionServiceImpl encryptionService = new EncryptionServiceImpl();

    @Test
    void encryptDecrypt() {
        String encrypted_value = encryptionService.encrypt(TEST_ENCRYPTED_VALUE);
        assertEquals(TEST_ENCRYPTED_VALUE, encryptionService.decrypt(encrypted_value));
    }
}