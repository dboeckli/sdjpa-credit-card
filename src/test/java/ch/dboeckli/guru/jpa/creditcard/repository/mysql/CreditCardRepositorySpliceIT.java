package ch.dboeckli.guru.jpa.creditcard.repository.mysql;

import ch.dboeckli.guru.jpa.creditcard.domain.CreditCard;
import ch.dboeckli.guru.jpa.creditcard.repository.CreditCardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test_mysql")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)  // to assure that it is not replaced with h2
class CreditCardRepositorySpliceIT {

    @Autowired
    CreditCardRepository creditCardRepository;

    @Test
    void testJpaTestSplice() {
        long countBefore = creditCardRepository.count();

        creditCardRepository.save(new CreditCard());

        long countAfter = creditCardRepository.count();

        assertEquals(countAfter, countBefore + 1);
    }

}