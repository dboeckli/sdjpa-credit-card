package ch.dboeckli.guru.jpa.creditcard.repository.mysql;

import ch.dboeckli.guru.jpa.creditcard.domain.CreditCard;
import ch.dboeckli.guru.jpa.creditcard.repository.CreditCardRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test_mysql")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)  // to assure that it is not replaced with h2
@Slf4j
public class CreditCardRepositoryIT {

    final String CREDIT_CARD = "12345678900000";

    @Autowired
    CreditCardRepository creditCardRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    @Transactional
    void testSaveAndStoreCreditCard() {
        CreditCard creditCard = new CreditCard();
        creditCard.setCreditCardNumber(CREDIT_CARD);
        creditCard.setCvv("123");
        creditCard.setExpirationDate("12/2028");

        CreditCard savedCC = creditCardRepository.saveAndFlush(creditCard);

        System.out.println("Getting CC from database");

        CreditCard fetchedCC = creditCardRepository.findById(savedCC.getId()).orElseThrow(() -> new AssertionError("Credit card not found"));

        assertThat(savedCC.getCreditCardNumber()).isEqualTo(fetchedCC.getCreditCardNumber());
    }

    @Test
    void testEncryptionViaInterceptor() {
        CreditCard creditCard = new CreditCard();
        creditCard.setCreditCardNumber(CREDIT_CARD);
        creditCard.setCvv("123");
        creditCard.setExpirationDate("12/2028");

        CreditCard savedCC = creditCardRepository.saveAndFlush(creditCard);

        // we are using the template to avoid the interceptor which would decrypt the encrypted card value.
        Map<String, Object> dbRow = jdbcTemplate.queryForMap("SELECT * FROM credit_card  WHERE id = " + savedCC.getId());
        String dbCardValue = (String) dbRow.get("credit_card_number");
        log.info("encrypted card: {}", dbCardValue);

        assertNotNull(dbCardValue);
        assertNotEquals(CREDIT_CARD, dbCardValue); // The encrypted credit card number should be stored in the database
        creditCardRepository.findById(savedCC.getId()).ifPresent(cc -> {
            log.info("decrypted card: {}", cc.getCreditCardNumber());
            assertEquals(CREDIT_CARD, cc.getCreditCardNumber()); // The decrypted credit card number should be retrieved from the database
        });
    }

}
