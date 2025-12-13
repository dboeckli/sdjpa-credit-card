package ch.dboeckli.guru.jpa.creditcard.repository.h2;

import ch.dboeckli.guru.jpa.creditcard.domain.CreditCard;
import ch.dboeckli.guru.jpa.creditcard.repository.CreditCardRepository;
import ch.dboeckli.guru.jpa.creditcard.repository.DataHelper;
import ch.dboeckli.guru.jpa.creditcard.util.EncryptionUtil;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Map;

import static ch.dboeckli.guru.jpa.creditcard.repository.DataHelper.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
// we are using the h2 in compatible mode with mysql. to assure that it is not replaced with h2
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
class CreditCardRepositoryTest {
    @Autowired
    CreditCardRepository creditCardRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    @Transactional
    void testSaveAndStoreCreditCard() {
        CreditCard savedCC = DataHelper.createAndSaveCreditCard(creditCardRepository);
        CreditCard fetchedCC = creditCardRepository.findById(savedCC.getId()).orElseThrow(() -> new AssertionError("Credit card not found"));

        assertThat(savedCC.getCreditCardNumber()).isEqualTo(fetchedCC.getCreditCardNumber());
    }

    @Test
    // the credit card number is encryped/decrypted by the interceptors
    // the cvv is encrypted/decrypted by the listeners
    // the expiration date is encrypted/decrypted by the callbacks
    // the secret is encrypted/decrypted by the converter
    void testSaveWithEncryption() {
        CreditCard savedCC = DataHelper.createAndSaveCreditCard(creditCardRepository);

        // we are using the template to avoid the interceptor which would decrypt the encrypted card value.
        Map<String, Object> dbRow = jdbcTemplate.queryForMap("SELECT * FROM credit_card  WHERE id = " + savedCC.getId());
        String dbCardValue = (String) dbRow.get("credit_card_number");
        String cvvValue = (String) dbRow.get("cvv");
        String expirationDateValue = (String) dbRow.get("expiration_date");
        String secretValue = (String) dbRow.get("secret");

        log.info("encrypted card: {}", dbCardValue);
        log.info("encrypted cvv: {}", cvvValue);
        log.info("encrypted expiration_date: {}", expirationDateValue);
        log.info("encrypted secret: {}", secretValue);

        assertAll("Encrypted values should not be null and should differ from original values",
            () -> assertNotNull(dbCardValue),
            () -> assertTrue(EncryptionUtil.isBase64Encoded(dbCardValue)),
            () -> assertNotEquals(CREDIT_CARD, dbCardValue),

            () -> assertNotNull(cvvValue),
            () -> assertTrue(EncryptionUtil.isBase64Encoded(cvvValue)),
            () -> assertNotEquals(CVV, cvvValue),

            () -> assertNotNull(secretValue),
            () -> assertTrue(EncryptionUtil.isBase64Encoded(secretValue)),
            () -> assertNotEquals(SECRET, secretValue),

            () -> assertNotNull(expirationDateValue),
            () -> assertTrue(EncryptionUtil.isBase64Encoded(expirationDateValue)),
            () -> assertNotEquals(EXPIRATION_DATE, expirationDateValue)
        );
        creditCardRepository.findById(savedCC.getId()).ifPresent(cc -> {
            log.info("decrypted card: {}", cc.getCreditCardNumber());
            log.info("decrypted cvv: {}", cc.getCvv());
            log.info("expiration date: {}", cc.getExpirationDate());
            log.info("secret: {}", cc.getSecret());
            assertAll("Decrypted values should match the original ones",
                () -> assertEquals(CREDIT_CARD, cc.getCreditCardNumber()),
                () -> assertEquals(CVV, cc.getCvv()),
                () -> assertEquals(EXPIRATION_DATE, cc.getExpirationDate()),
                () -> assertEquals(SECRET, cc.getSecret())
            );
        });
    }

    @Test
        // the credit card number is encryped/decrypted by the interceptors
        // the cvv is encrypted/decrypted by the listeners
        // the expiration date is encrypted/decrypted by the callbacks
        // the secret is encrypted/decrypted by the converter
    void testUpdateWithEncryption() {
        CreditCard savedCC = DataHelper.createAndSaveCreditCard(creditCardRepository);

        // update credit card
        savedCC.setCreditCardNumber(UPDATED_CREDIT_CARD);
        savedCC.setCvv(UPDATED_CVV);
        savedCC.setExpirationDate(UPDATED_EXPIRATION_DATE);
        savedCC.setSecret(UPDATED_SECRET);
        creditCardRepository.saveAndFlush(savedCC);

        // we are using the template to avoid the interceptor which would decrypt the encrypted card value.
        Map<String, Object> dbRow = jdbcTemplate.queryForMap("SELECT * FROM credit_card  WHERE id = " + savedCC.getId());
        String dbCardValue = (String) dbRow.get("credit_card_number");
        String cvvValue = (String) dbRow.get("cvv");
        String expirationDateValue = (String) dbRow.get("expiration_date");
        String secretValue = (String) dbRow.get("secret");
        log.info("encrypted card: {}", dbCardValue);
        log.info("encrypted cvv: {}", cvvValue);
        log.info("encrypted expiration_date: {}", expirationDateValue);
        log.info("encrypted secret: {}", secretValue);

        assertAll("Encrypted values should not be null and should differ from original values",
            () -> assertNotNull(dbCardValue),
            () -> assertTrue(EncryptionUtil.isBase64Encoded(dbCardValue)),
            () -> assertNotEquals(UPDATED_CREDIT_CARD, dbCardValue),

            () -> assertNotNull(cvvValue),
            () -> assertTrue(EncryptionUtil.isBase64Encoded(cvvValue)),
            () -> assertNotEquals(UPDATED_CVV, cvvValue),

            () -> assertNotNull(secretValue),
            () -> assertTrue(EncryptionUtil.isBase64Encoded(secretValue)),
            () -> assertNotEquals(UPDATED_SECRET, secretValue),

            () -> assertNotNull(expirationDateValue),
            () -> assertTrue(EncryptionUtil.isBase64Encoded(expirationDateValue)),
            () -> assertNotEquals(UPDATED_EXPIRATION_DATE, expirationDateValue)
        );
        creditCardRepository.findById(savedCC.getId()).ifPresent(cc -> {
            log.info("decrypted card: {}", cc.getCreditCardNumber());
            assertEquals(UPDATED_CREDIT_CARD, cc.getCreditCardNumber()); // The decrypted credit card number should be retrieved from the database
            log.info("decrypted cvv: {}", cc.getCvv());
            assertEquals(UPDATED_CVV, cc.getCvv());
            log.info("expiration date: {}", cc.getExpirationDate());
            assertEquals(UPDATED_EXPIRATION_DATE, cc.getExpirationDate());
            log.info("secret: {}", cc.getSecret());
            assertEquals(UPDATED_SECRET, cc.getSecret());
        });
    }

}
