package ch.dboeckli.guru.jpa.creditcard.repository.h2;

import ch.dboeckli.guru.jpa.creditcard.domain.CreditCard;
import ch.dboeckli.guru.jpa.creditcard.repository.CreditCardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
// we are using the h2 in compatible mode with mysql. to assure that it is not replaced with h2
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CreditCardRepositorySpliceTest {

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